package com.path.bo.impl.mxmessageparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.lib.common.util.StringUtil;
import com.path.vo.mxmessageparser.SimpleTypeCO;
import com.path.vo.mxmessageparser.TagsCo;
import com.path.vo.mxmessageparser.XsdParserCO;
import com.predic8.schema.Choice;
import com.predic8.schema.ComplexType;
import com.predic8.schema.Element;
import com.predic8.schema.Schema;
import com.predic8.schema.SchemaParser;
import com.predic8.schema.Sequence;
import com.predic8.schema.SimpleType;
import com.predic8.schema.restriction.BaseRestriction;
import com.predic8.schema.restriction.facet.EnumerationFacet;
import com.predic8.schema.restriction.facet.Facet;
import com.predic8.schema.restriction.facet.FractionDigits;
import com.predic8.schema.restriction.facet.LengthFacet;
import com.predic8.schema.restriction.facet.MaxExclusiveFacet;
import com.predic8.schema.restriction.facet.MaxInclusiveFacet;
import com.predic8.schema.restriction.facet.MaxLengthFacet;
import com.predic8.schema.restriction.facet.MinExclusiveFacet;
import com.predic8.schema.restriction.facet.MinInclusiveFacet;
import com.predic8.schema.restriction.facet.MinLengthFacet;
import com.predic8.schema.restriction.facet.PatternFacet;
import com.predic8.schema.restriction.facet.TotalDigitsFacet;
import com.predic8.schema.restriction.facet.WhiteSpaceFacet;

/**
 * Prepare the tags after parsing the xsd file
 * @author Alim.Khowaja
 *
 */
public class PrepareXsdTags {

	public final static String SIMPLE_TYPE = "simpleType";
	public final static String COMPLEX_TYPE = "complexType";
	static HashMap<String, String> types = new HashMap<String, String>();
	static HashMap<String, SimpleTypeCO> map = new HashMap<String, SimpleTypeCO>();

	/**
	 * Parse Schema from uploaded Xsd file or saved schema
	 * @param mxMessageDefinitionCO
	 * @return
	 * @throws IOException
	 */
	public static Schema parseSchema(XsdParserCO xsdParserCO) throws IOException {
		SchemaParser parser = new SchemaParser();
		Schema schema = null;

		/**
		 * if the schema already available in db the load and parse then prepare the
		 * tags
		 */
		if (StringUtil.isNotEmpty(xsdParserCO.getXsdSchema())) 
		{
			Path temp = Files.createTempFile("", ".tmp");
			String absolutePath = temp.toString();
			Files.write(Paths.get(absolutePath), xsdParserCO.getXsdSchema().getBytes());
			schema = parser.parse(absolutePath);
		} else {
			schema = parser.parse(StringUtil.nullToEmpty(xsdParserCO.getXsdFile().getAbsolutePath()));
		}
		
		// Check If Schema is null the return
		if (schema == null)
			return schema;

		// get name from schema
		String schemaName[] = StringUtil.nullToEmpty(schema.getTargetNamespace()).split(":");
		if (schemaName != null)
			xsdParserCO.setIdentifier(schemaName[schemaName.length - 1]);

		return schema;
	}

	/**
	 * prepare the tags for grid
	 * 
	 * @param mxMessageDefinitionCO
	 * @throws IOException
	 */
	public static void prepareXsdTags(XsdParserCO xsdParserCO) throws IOException {
		Schema schema = xsdParserCO.getSchema();

		/**
		 * Add Schema as Stirng
		 */
		xsdParserCO.setXsdSchema(schema.getAsString());

		/**
		 * prepare all complex type
		 */
		types = prepareComplexTypes(schema);

		/**
		 * prepare all simple types
		 */
		types.putAll(prepareSimpleTypes(schema));

		/**
		 * prepare all simple types
		 */
		prepareSimpleTypeData(schema);

		/**
		 * get complex type of Document
		 */
		ComplexType sct = schema.getComplexType("Document");

		Sequence sequence = (Sequence) sct.getModel();
		List<com.predic8.schema.Element> elements = sequence.getElements();

		List<TagsCo> tagsList = new ArrayList<TagsCo>();
		
		/**
		 * 
		 */
		for (Element element : elements) 
		{
			TagsCo tagsCo = new TagsCo();
			tagsCo.setParentId("0");
			tagsCo.setId("" + System.nanoTime());

			tagsCo.setComplexTypeName(sct.getName().toString());
			tagsCo.setTagName(element.getName());
			tagsCo.setType(element.getType().toString().replaceAll("^\\{(.*?)\\}", ""));
			tagsCo.setMinOccur(element.getMinOccurs());
			tagsCo.setMaxOccur(element.getMaxOccurs());
			
			tagsList.add(tagsCo);

			/**
			 * if the type is complex type then call tags recursion
			 */
			if (types.get(tagsCo.getType().toString()).equals(COMPLEX_TYPE)) 
			{
				/**
				 * call recursion while the type is complex
				 */
				HashMap<String, Object> params = new HashMap<>();
				
				params.put("parentId", tagsCo.getId());
				params.put("schema", schema);
				params.put("parentTag", element.getName());
				params.put("elementType", tagsCo.getType().toString());

				/**
				 * visit all childs tags from xsd and
				 * add into the list
				 */
				visitAllChildTags(params, tagsList);
			}
		}
		
		/**
		 * set all tags in list
		 */
		xsdParserCO.setAllTagsCos(tagsList);

	}

	/**
	 * Prepare All Complex Types
	 * 
	 * @param schema
	 * @return
	 */
	private static HashMap<String, String> prepareComplexTypes(Schema schema) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (null == schema.getComplexTypes() || schema.getSimpleTypes().size() <= 0)
			return map;

		for (ComplexType complexType : schema.getComplexTypes()) {
			map.put(complexType.getName(), "complexType");
		}
		return map;
	}

	/**
	 * Prepare All Simple Types
	 * 
	 * @param schema
	 * @return
	 */
	private static HashMap<String, String> prepareSimpleTypes(Schema schema) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (null == schema.getSimpleTypes() || schema.getSimpleTypes().size() <= 0)
			return map;

		for (SimpleType st : schema.getSimpleTypes()) {
			map.put(st.getName(), "simpleType");
		}
		return map;
	}

	/**
	 * prepare All Simple Types
	 * 
	 * @param schema
	 * @return
	 */
	public static HashMap<String, SimpleTypeCO> prepareSimpleTypeData(Schema schema) {
		map = new HashMap<String, SimpleTypeCO>();

		if (null == schema.getSimpleTypes() || schema.getSimpleTypes().size() <= 0)
			return map;

		for (SimpleType st : schema.getSimpleTypes()) {
			SimpleTypeCO co = new SimpleTypeCO();

			// set simple type name
			co.setTypeName(st.getName());

			// set Restrictions
			BaseRestriction restriction = (BaseRestriction) st.getRestriction();

			// set restriction type like xs:decimal
			co.setRestrictionType(restriction.getBase().getQualifiedName());

			// get all facets
			List<Facet> facets = restriction.getFacets();
			List<String> enums = new ArrayList<String>();
			if (null != facets) {
				for (Facet facet : facets) {
					/**
					 * if facet is object of PatternFacet then get value from facet object and set
					 * in co variable
					 */
					if (facet instanceof PatternFacet) {
						co.setPattern(facet.getValue());
					}

					/**
					 * if facet is object of EnumerationFacet then get value from facet object and
					 * set in co variable
					 */
					else if (facet instanceof EnumerationFacet) {
						enums.add(facet.getValue());
					}

					/**
					 * if facet is object of FractionDigits then get value from facet object and set
					 * in co variable
					 */
					else if (facet instanceof FractionDigits) {
						co.setFractionDigits(facet.getValue());
					}

					/**
					 * if facet is object of TotalDigitsFacet then get value from facet object and
					 * set in co variable
					 */
					else if (facet instanceof TotalDigitsFacet) {
						co.setTotalDigits(facet.getValue());
					}

					/**
					 * if facet is object of MinInclusiveFacet then get value from facet object and
					 * set in co variable
					 */
					else if (facet instanceof MinInclusiveFacet) {
						co.setMinInclusive(facet.getValue());
					}

					/**
					 * if facet is object of MaxInclusiveFacet then get value from facet object and
					 * set in co variable
					 */
					else if (facet instanceof MaxInclusiveFacet) {
						co.setMaxInclusive(facet.getValue());
					}

					/**
					 * if facet is object of MinExclusiveFacet then get value from facet object and
					 * set in co variable
					 */
					else if (facet instanceof MinExclusiveFacet) {
						co.setMinExclusive(facet.getValue());
					}

					/**
					 * if facet is object of MaxExclusiveFacet then get value from facet object and
					 * set in co variable
					 */
					else if (facet instanceof MaxExclusiveFacet) {
						co.setMaxExclusive(facet.getValue());
					}

					/**
					 * if facet is object of MinLengthFacet then get value from facet object and set
					 * in co variable
					 */
					else if (facet instanceof MinLengthFacet) {
						co.setMinLength(facet.getValue());
					}

					/**
					 * if facet is object of MaxLengthFacet then get value from facet object and set
					 * in co variable
					 */
					else if (facet instanceof MaxLengthFacet) {
						co.setMaxLength(facet.getValue());
					}

					/**
					 * if facet is object of LengthFacet then get value from facet object and set in
					 * co variable
					 */
					else if (facet instanceof LengthFacet) {
						co.setLength(facet.getValue());
					}

					/**
					 * if facet is object of WhiteSpaceFacet then get value from facet object and
					 * set in co variable
					 */
					else if (facet instanceof WhiteSpaceFacet) {
						co.setWhiteSpace(facet.getValue());
					}
				}
				co.setEnumValues(enums);
			}
			map.put(st.getName(), co);
		}
		return map;
	}

	/**
	 * 
	 * @param id
	 * @param parentTag = this is parent tag if the type contains tag
	 * @param ctype     = complex type object
	 * @param stype     = simple type object
	 * @param tagsList  = taglist where every tag will be add
	 */
	private static void visitAllChildTags(HashMap<String, Object> additionalParams, List<TagsCo> tagsList) {
		String parentId = (String) additionalParams.get("parentId");
		Schema schema = (Schema) additionalParams.get("schema");
		String parentTag = (String) additionalParams.get("parentTag");
		String elementType = (String) additionalParams.get("elementType");

		String hierarchy = additionalParams.containsKey("hierarchy") ? (String) additionalParams.get("hierarchy") : "";

		HashMap<String, TagsCo> tagsDupMap = new HashMap<String, TagsCo>();

		ComplexType ctype = null;
		if (types.get(elementType).equals(COMPLEX_TYPE)) {
			ctype = schema.getComplexType(elementType);
		}

		HashMap<String, ComplexType> ctMap = new HashMap<String, ComplexType>();

		if (ctype != null) {
			List<Element> elements = new ArrayList<Element>();

			/**
			 * get elements from Sequence and Choice
			 */
			if (ctype.getModel() instanceof Sequence) {
				Sequence sequence = (Sequence) ctype.getModel();
				elements = sequence.getElements();
			} else if (ctype.getModel() instanceof Choice) {
				Choice choice = (Choice) ctype.getModel();
				elements = choice.getElements();
			}

			for (Element element : elements) {
				TagsCo tagsCo = new TagsCo();

				tagsCo.setParentId(parentId);
				tagsCo.setId("" + System.nanoTime());
				tagsCo.setTagHierarchy(StringUtil.isEmptyString(hierarchy) ? parentTag : hierarchy + "_" + parentTag);
				tagsCo.setComplexTypeName(ctype.getName().toString());
				tagsCo.setParentTagName(parentTag);
				tagsCo.setTagName(element.getName());
				tagsCo.setType(element.getType().toString().replaceAll("^\\{(.*?)\\}", ""));
				tagsCo.setMinOccur(element.getMinOccurs());
				tagsCo.setMaxOccur(element.getMaxOccurs());

				if (types.get(tagsCo.getType().toString()).equals(COMPLEX_TYPE)) {
					// @todochange
					ctMap.put(element.getName() + "-" + tagsCo.getId() + "-" + tagsCo.getTagHierarchy(),
							schema.getComplexType(tagsCo.getType().toString()));
					// complexTypes.add(schema.getComplexType(element.getType().toString()));
				} else {
					SimpleTypeCO simpleTypeCO = map.get(tagsCo.getType().toString());

					// System.out.println(tagsCo.getTagHierarchy()+"-"+tagsCo.getTagName()+"-"+simpleTypeCO.getRestrictionType()+"-"+simpleTypeCO.getEnumValues());

					tagsCo.setSimpleTypeCO(simpleTypeCO);
					/**
					 * add mandatory tags and set flag for Leaf tag
					 */
					if (simpleTypeCO != null) {
						/**
						 * set is leaf flag
						 */
						tagsCo.setIsLeafYN(ConstantsCommon.YES);

						/**
						 * set mandatory flag
						 */
						if (StringUtil.isEmptyString(StringUtil.nullToEmpty(simpleTypeCO.getMinLength())))
							simpleTypeCO.setMinLength("0");

						if (Integer.parseInt(simpleTypeCO.getMinLength()) > 0)
							tagsCo.setIsMandatoryYN(ConstantsCommon.YES);
					}
				}

				/**
				 * check duplication reord in map and then add in list
				 */
				if (!tagsDupMap.containsKey(tagsCo.getId() + "_" + tagsCo.getParentId() + "-" + element.getName())) {
					tagsDupMap.put(tagsCo.getId() + "_" + tagsCo.getParentId() + "-" + element.getName(), tagsCo);
					tagsList.add(tagsCo);
				}

			}

			if (null == ctMap || ctMap.size() <= 0)
				return;

			for (Map.Entry<String, ComplexType> entry : ctMap.entrySet()) {
				if (!parentTag.equals(entry.getKey())) {
					parentTag = entry.getKey();
				}

				String data[] = parentTag.split("-");
				parentTag = data[0];
				String pId = data[1];
				String tagHierrchy = data[2];

				ComplexType complexType = entry.getValue();
				List<Element> e = new ArrayList<Element>();

				if (complexType.getModel() instanceof Sequence) {
					Sequence sequence = (Sequence) complexType.getModel();
					e = sequence.getElements();
				} else if (complexType.getModel() instanceof Choice) {
					Choice choice = (Choice) complexType.getModel();
					e = choice.getElements();
				}

				for (Element element : e) {
					TagsCo tagsCo = new TagsCo();
					tagsCo.setParentId(pId);
					tagsCo.setId("" + System.nanoTime());
					tagsCo.setComplexTypeName(complexType.getName());

					tagsCo.setTagHierarchy(tagHierrchy + "_" + parentTag);

					tagsCo.setParentTagName(parentTag);
					tagsCo.setTagName(element.getName());
					tagsCo.setType(element.getType().toString().replaceAll("^\\{(.*?)\\}", ""));
					tagsCo.setMaxOccur(element.getMinOccurs());
					tagsCo.setMaxOccur(element.getMaxOccurs());

					if (types.get(tagsCo.getType().toString()).equals(COMPLEX_TYPE)) {
						/**
						 * check duplication reord in map and then add in list
						 */
						if (!tagsDupMap
								.containsKey(tagsCo.getId() + "_" + tagsCo.getParentId() + "-" + element.getName())) {
							tagsDupMap.put(tagsCo.getId() + "_" + tagsCo.getParentId() + "-" + element.getName(),
									tagsCo);
							tagsList.add(tagsCo);
						}

						/**
						 * call recursion while the type is complex
						 */
						HashMap<String, Object> params = new HashMap<>();
						params.put("parentId", tagsCo.getId());
						params.put("schema", schema);
						params.put("parentTag", element.getName());
						params.put("elementType", tagsCo.getType().toString());
						params.put("hierarchy", tagsCo.getTagHierarchy());

						/**
						 * call recursion method to visit child tags if the type is complext type
						 */
						visitAllChildTags(params, tagsList);

					} else {
						SimpleTypeCO simpleTypeCO = map.get(tagsCo.getType().toString());

						// System.out.println(tagsCo.getTagHierarchy()+"-"+tagsCo.getTagName()+"-"+simpleTypeCO.getRestrictionType()+"-"+simpleTypeCO.getEnumValues());

						tagsCo.setSimpleTypeCO(simpleTypeCO);

						/**
						 * add mandatory tags and set flag for Leaf tag
						 */
						if (simpleTypeCO != null) {

							/**
							 * set is leaf flag
							 */
							tagsCo.setIsLeafYN(ConstantsCommon.YES);

							/**
							 * set mandatory flag
							 */
							if (StringUtil.isEmptyString(StringUtil.nullToEmpty(simpleTypeCO.getMinLength())))
								simpleTypeCO.setMinLength("0");

							if (Integer.parseInt(simpleTypeCO.getMinLength()) > 0)
								tagsCo.setIsMandatoryYN(ConstantsCommon.YES);

						}
					}

					/**
					 * check duplication reord in map and then add in list
					 */
					if (!tagsDupMap
							.containsKey(tagsCo.getId() + "_" + tagsCo.getParentId() + "-" + element.getName())) {
						tagsDupMap.put(tagsCo.getId() + "_" + tagsCo.getParentId() + "-" + element.getName(), tagsCo);
						tagsList.add(tagsCo);
					}
				}
			}
		}
	}

	/**
	 * prepare tree of tags
	 * @param mxMessageDefinitionCO
	 * @throws JSONException
	 */
	public static void prepareTreeTags(XsdParserCO xsdParserCO) throws JSONException 
	{
		List<TagsCo> tagsCos = new ArrayList<>();
		tagsCos.addAll(xsdParserCO.getAllTagsCos());

		xsdParserCO.setAllTagsCos(new ArrayList<>());

		Map<String, TagsCo> mapTmp = new HashMap<>();

		// Save all nodes to a map
		for (TagsCo current : tagsCos) {

			String key = current.getParentId() + "-" + current.getId() + "-" + current.getTagName();

			// add record in map
			mapTmp.put(key, current);
		}

		// loop and assign parent/child relationships
		for (TagsCo current : tagsCos) {

			TagsCo parent = current.getParentCO(tagsCos, current.getParentId(), current.getParentTagName());
			
			if (parent == null)
				continue;

			String parentId = parent.getParentId() + "-" + parent.getId() + "-" + parent.getTagName();

			current.setParentTagsCo(parent);
			parent.addChild(current);
			mapTmp.put(parentId, parent);
			mapTmp.put(current.getParentId() + "-" + current.getId() + "-" + current.getTagName(), current);

			// }
			// }
		}

		List<TagsCo> finalTags = new ArrayList<>();

		for (TagsCo tagsCo : mapTmp.values()) {
			if (StringUtil.isEmptyString(tagsCo.getParentTagName())) {
				finalTags.add(tagsCo);
				break;
			}
		}
		xsdParserCO.setTagsCos(finalTags);
	}

}
