package com.path.bo.common.webserviceexplorer.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.WebServiceCommonBO;
import com.path.bo.common.webserviceexplorer.WebServiceExplorerBO;
import com.path.bo.common.webserviceexplorer.WebServiceExplorerConstant;
//import com.path.codegen.common.lib.exception.GeneratorException;
//import com.path.codegen.webservicegen.generators.FileGenerator;
import com.path.dao.common.webserviceexplorer.WebServiceExplorerDAO;
import com.path.dbmaps.vo.IMCO_PWS_TEST_DETAILSVO;
import com.path.dbmaps.vo.IMCO_PWS_TEST_MASTERVOWithBLOBs;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.StringUtil;
import com.path.vo.common.select.SelectCO;
import com.path.vo.common.webserviceexplorer.ParserCO;
import com.path.vo.common.webserviceexplorer.RequestResponseCO;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerCO;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerSC;
import com.path.vo.common.webserviceexplorer.WebServiceUtil;
import com.predic8.schema.All;
import com.predic8.schema.ComplexContent;
import com.predic8.schema.ComplexType;
import com.predic8.schema.Derivation;
import com.predic8.schema.Element;
import com.predic8.schema.Extension;
import com.predic8.schema.ModelGroup;
import com.predic8.schema.Restriction;
import com.predic8.schema.Schema;
import com.predic8.schema.SchemaComponent;
import com.predic8.schema.Sequence;
import com.predic8.schema.SimpleType;
import com.predic8.schema.restriction.BaseRestriction;
import com.predic8.schema.restriction.facet.EnumerationFacet;
import com.predic8.schema.restriction.facet.Facet;
import com.predic8.schema.restriction.facet.FractionDigits;
import com.predic8.schema.restriction.facet.MaxInclusiveFacet;
import com.predic8.schema.restriction.facet.MaxLengthFacet;
import com.predic8.schema.restriction.facet.MinLengthFacet;
import com.predic8.schema.restriction.facet.PatternFacet;
import com.predic8.schema.restriction.facet.TotalDigitsFacet;
import com.predic8.wsdl.AbstractSOAPBinding;
import com.predic8.wsdl.Binding;
import com.predic8.wsdl.BindingOperation;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Input;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Output;
import com.predic8.wsdl.Port;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.Service;
import com.predic8.wsdl.WSDLParser;

import groovy.xml.QName;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class WebServiceExplorerBOImpl extends BaseBO implements WebServiceExplorerBO{
	private WebServiceExplorerDAO webServiceExplorerDAO;
	private HashMap<String,String> paramType;
	private String name;
	public Map<String,String> webServiceExplorerSelectedGridRows;
	public Map<String,Map<String,String>> webServiceExplorerSelectedHashMaps;
	public Map<String,RequestResponseCO> webServiceExplorerGridParamDataToSave;
	public HashMap<String,String> excludedFields = new HashMap<String,String>();

	private WebServiceCommonBO webServiceCommonBO;
	
	@Override
	public Object loadWsdlOperations(WebServiceExplorerCO webServiceExplorerCO) throws BaseException 
	{
		HashMap<String,String> propVal = null;	
		String webServiceUrl = null;
		WSDLParser parser = new WSDLParser();
		Definitions defs = null;
		InputStream inputStream = null;
		webServiceUrl = webServiceExplorerCO.getWsdlUrl();
		if(webServiceUrl.contains("https"))
		{
			try{
				WebServiceUtil w = new WebServiceUtil();
				InputStream i = w.returnInputStreamWsdlData(webServiceUrl);
				defs = parser.parse(i);
			}
			catch(Exception e)
			{
				throw new BOException(e.getMessage());
			}
		}
		else{
			defs = parser.parse(webServiceUrl);
		}
		PortType portType = null;
		portType = (PortType) defs.getPortTypes().get(0);
		List<Operation> operations = portType.getOperations();
		Service service = defs.getServices().get(0);
		Map<String,List<String>> wsdlData = new HashMap<String,List<String>>();
		List<String> oprs = new ArrayList<String>();
		for(Operation opr : operations)
		{
			oprs.add(opr.getName());
		}
		wsdlData.put(service.getName(), oprs);
		return wsdlData;
	}
	
	@Override
	public WebServiceExplorerCO loadFuncRespArg(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		String key = null;
		ArrayList<Map<String, Object>> aList = new ArrayList<>();
		Map newMap = new HashMap<String,Object>();
		Map newNestedMap = new HashMap<String,Object>();

//		if(webServiceExplorerCO.getOperationName().equals("createSignature"))
//		try{
//			newMap.put("compCode", "1");
//			newMap.put("channelId", "1");
//			newMap.put("branchCode", "1");
//			newMap.put("userCifNo", "33");
//
//			newNestedMap.put("ISO14", "123");
//			newNestedMap.put("ISO18", "1570026231938");
//			newNestedMap.put("ISO26", "EN");
//			newNestedMap.put("BRCODE", "abss");
//			newNestedMap.put("COMPCODE", "abss");
//
//			newMap.put("requesterContext", newNestedMap);
//
//			webServiceCommonBO.callPWS(new BigDecimal(400044), (HashMap)newMap);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			System.out.println("remove call to webServiceCommonBO.callPWS from WebServiceExplorerBOImpl loadFuncRespArg");
//		}
		HashMap<String,String> propVal = null;	
		String webServiceUrl = null;
		WSDLParser parser = new WSDLParser();
		Definitions defs = null;
		InputStream inputStream = null;
		this.generateExcludedParams(webServiceExplorerCO);
		
		if(null != webServiceExplorerCO.getIsFromBpelScreen() && webServiceExplorerCO.getIsFromBpelScreen().equalsIgnoreCase("true"))
		{
			webServiceUrl = webServiceExplorerCO.getWsdlUrl();
		}
		else if(null != webServiceExplorerCO.getAdapterType() && webServiceExplorerCO.getAdapterType().equals("Wsdl"))
		{
			webServiceUrl = webServiceExplorerCO.getWsdlUrl();
		}
		else if(null != webServiceExplorerCO.getIsFromSoapScreen() && webServiceExplorerCO.getIsFromSoapScreen().equalsIgnoreCase("true"))
		{
			webServiceUrl = webServiceExplorerCO.getWsdlUrl();
		}
//		else if(null != webServiceExplorerCO.getIsFromSoapScreen() && webServiceExplorerCO.getIsFromSoapScreen().equalsIgnoreCase("true"))
//		{
//			webServiceUrl = webServiceExplorerCO.getInputwsdlUrl();
//		}
		else{
			webServiceUrl = this.generateServiceUrl(webServiceExplorerCO);
		}
		if(webServiceUrl.contains("https"))
		{
			try{
				WebServiceUtil w = new WebServiceUtil();
				InputStream i = w.returnInputStreamWsdlData(webServiceUrl);
				defs = parser.parse(i);
			}
			catch(Exception e)
			{
				throw new BOException(e.getMessage());
			}
		}
		else{
			defs = parser.parse(webServiceUrl);
		}
		//Schema schema = this.loadSchema((Definitions)(new Cloner()).deepClone(defs));
		Schema schema = this.loadSchema(defs);

		webServiceExplorerCO.setWsdlUrl(webServiceUrl);
		webServiceExplorerCO.setNameSpaceUri(schema.getTargetNamespace());
		HashMap<String,String> bindingHash = new HashMap<String,String>();
		
		for (Binding bnd : defs.getBindings()) 
		{
			bindingHash.put(bnd.getPortType().getName(), bnd.getName());	
			bnd.getStyle();
		}

		HashMap<String,String> types = new HashMap<String,String>();
		
		for(ComplexType complexType : schema.getComplexTypes())
		{
			types.put(complexType.getName(), "complexType");
		}
		
		for(SimpleType simpleType : schema.getSimpleTypes())
		{
			types.put(simpleType.getName(), "simpleType");
		}
		
		this.setParamType(types);
		
		Map<String,String> elements = new HashMap<String,String>();
		
		for(Element e:schema.getAllElements())
		{
			if (null != e.getType())
			{
				try{
					elements.put(e.getName(), schema.getType(e.getType().getLocalPart()).getMetaClass().getTheClass().getSimpleName());
				}
				catch(Exception e1)
				{
					String fieldType = e.getType().getLocalPart();
				}
			}
			else
			{
				elements.put(e.getName(), e.getEmbeddedType().getMetaClass().getTheClass().getSimpleName()+"_embeddedtype");
			}
			
		}
//		for (PortType portType : defs.getPortTypes()) 
//		{
			PortType portType = null;
			List<Service> services = defs.getServices();
			String webServiceName = webServiceExplorerCO.getWebServiceName();
			webServiceName = this.updateServiceName(defs, webServiceExplorerCO);
			for(Service service : services)
			{
				if(null != webServiceName && webServiceName.equals(service.getQName().getLocalPart()))
				{
					String bindingType = defs.getBinding(((Port)service.getPorts().get(0)).getBindingPN().getLocalName()).getType().getLocalPart();
					webServiceExplorerCO.setWebServiceName(bindingType);
				}
			}
			if(null != defs.getPortType(webServiceName))
			{
				portType = defs.getPortType(webServiceName);
			}
			else
			{
				//portType = defs.getPortTypes().get(0);
				portType = schema.getDefinitions().getPortType(webServiceName);
			}
			groovy.xml.QName portTypeQName = portType.getQName();
			webServiceExplorerCO.setPortTypePrefix(portType.getPrefix());			
			List<Operation> operations = portType.getOperations();			
			String operationName = portType.getName();
			Operation operation = portType.getOperation(webServiceExplorerCO.getOperationName());			
			groovy.xml.QName operationQName = operation.getQName();
			if(!defs.getBindings().isEmpty())
			{
				Binding binding = defs.getBinding(bindingHash.get(portTypeQName.getLocalPart()));			
				BindingOperation bindingOperation  = binding.getOperation(operationQName.getLocalPart());
				if(binding.getBinding() instanceof AbstractSOAPBinding)
				{ 
					webServiceExplorerCO.setSoapAction(bindingOperation.getOperation().getSoapAction()); 
				}
			}
			
			String functionName = operation.getName();
			String documentation = null;
			if(null!= operation.getDocumentation())
			{
				documentation = operation.getDocumentation().getContent();
			}
			
//			String input = operation.getInput().getMessage().getName();
//
//			String output = operation.getInput().getMessage().getName();
		
			/*************************************************/
						   // Parsing //
			/*************************************************/
			String requestClassName = null; 			
			Sequence sequence = null;
			List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
			List<RequestResponseCO> lstInResponse = null;
			String fieldName = null;
			String fieldType = null;
			String QName = null;
			String fieldMinOCC = null;
			int  hashChildren = 0; // This field will be set as one Incase the type is not a premitave type
			RequestResponseCO reqResCO;	
			ComplexType complexType = null;	
			webServiceExplorerCO.setWsdlUrl(webServiceUrl);
			ParserCO parserCO = null;
//			String className = null;
			
//			switch((defs.getMessage(output).getParts()).size())
//			{
//				case 1: className = (((Part)defs.getMessage(output).getParts().get(0)).getElement()).getName();
//				break;
//			}	
			parserCO = new ParserCO();
			parserCO.setSchema(schema);
			parserCO.setOperation(operation);
			parserCO.setWebServiceExplorerCO(webServiceExplorerCO);
			
			requestClassName =  webServiceExplorerCO.getSelectedFieldType();
			String type = types.get(requestClassName);
			
//			if(WebServiceExplorerConstant.SIMPLE_TYPE.equals(type))
//			{
//				return webServiceExplorerCO;
//			}
			//TODO All update
			ComplexType comType = schema.getComplexTypes().get(0);
			if(null !=  requestClassName)
			{//List<chequebookCodeDC>
				requestClassName = requestClassName.replace("List<", "").replace(">", "");
				webServiceExplorerCO.setRequestName(requestClassName);
				complexType = schema.getComplexType(requestClassName);
			}
			else{//TODO All update
//				requestClassName = this.returnOperationRequestClassName(parserCO); 
				if(comType.getModel() instanceof All)
				{
					if(null != webServiceExplorerCO.getRequestType() && "response".equals(webServiceExplorerCO.getRequestType()))
					{
						requestClassName = this.responseComplexTypeName(parserCO);
					}
					else{
						requestClassName = this.requestComplexTypeName(parserCO);
					}
				}
				else{
					requestClassName = this.returnOperationRequestClassName(parserCO);
				}
				webServiceExplorerCO.setRequestName(requestClassName);
				complexType = schema.getComplexType(requestClassName);
			}
			parserCO.setWebServiceExplorerCO(webServiceExplorerCO);
			parserCO.setComplexType(complexType);
			//TODO Recheck after demo
			if(null != webServiceExplorerCO.getIsFromBpelScreen() && webServiceExplorerCO.getIsFromBpelScreen().equalsIgnoreCase("true"))
			{
				webServiceExplorerCO = this.loadComplexType(parserCO);
				if(null == webServiceExplorerCO)
				{
					return new WebServiceExplorerCO();
				}
				lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();
				webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
			}
			else{
			if(null == elements.get(operation.getName()))
			{
				webServiceExplorerCO = this.loadComplexType(parserCO);
				if(null == webServiceExplorerCO)
				{
					return new WebServiceExplorerCO();
				}
				lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();
				webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
			}
			else{
				switch(elements.get(operation.getName()).toLowerCase())
				{			
					case "complextype":	
						webServiceExplorerCO = this.loadComplexType(parserCO);
						if(null == webServiceExplorerCO)
						{
							return new WebServiceExplorerCO();
						}
						lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();		
						break;
						
					case "complextype_embeddedtype":
					//	complexType = ((ComplexType)(schema.getElement(requestClassName).getEmbeddedType()));
						webServiceExplorerCO = loadComplexEmbeddedType(webServiceExplorerCO,schema,requestClassName);
						if(null == webServiceExplorerCO)
						{
							return new WebServiceExplorerCO();
						}
						lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();
						break;
				}		
			}
			}
			webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
//		}
	
		webServiceExplorerCO.setWsdlUrl(webServiceUrl);
		return webServiceExplorerCO;
	}
	
	@Override
	public WebServiceExplorerCO loadBPELFuncRespArg(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		HashMap<String,String> propVal = null;	
		String webServiceUrl = null;
		WSDLParser parser = new WSDLParser();
		Definitions defs = null;
		InputStream inputStream = null;
		this.generateExcludedParams(webServiceExplorerCO);
		
		if(null != webServiceExplorerCO.getIsFromBpelScreen() && webServiceExplorerCO.getIsFromBpelScreen().equalsIgnoreCase("true"))
		{
			webServiceUrl = webServiceExplorerCO.getWsdlUrl();
		}
		else if(null != webServiceExplorerCO.getAdapterType() && webServiceExplorerCO.getAdapterType().equals("Wsdl"))
		{
			webServiceUrl = webServiceExplorerCO.getWsdlUrl();
		}
		else if(null != webServiceExplorerCO.getIsFromSoapScreen() && webServiceExplorerCO.getIsFromSoapScreen().equalsIgnoreCase("true"))
		{
			webServiceUrl = webServiceExplorerCO.getWsdlUrl();
		}
		else{
			webServiceUrl = this.generateServiceUrl(webServiceExplorerCO);
		}
		if(null !=webServiceUrl && webServiceUrl.contains("https"))
		{
			try{
				WebServiceUtil w = new WebServiceUtil();
				InputStream i = w.returnInputStreamWsdlData(webServiceUrl);
				defs = parser.parse(i);
			}
			catch(Exception e)
			{
				throw new BOException(e.getMessage());
			}
		}
		else if((null != webServiceExplorerCO.getAdapterType() && webServiceExplorerCO.getAdapterType().equalsIgnoreCase("BPEL"))||(null != webServiceExplorerCO.getIsFromBpelScreen() && webServiceExplorerCO.getIsFromBpelScreen().equalsIgnoreCase("true")))
		{
//			defs = webServiceExplorerCO.getdef
			try{
				webServiceExplorerCO = this.processMultipleWsdls(webServiceExplorerCO);
				defs = parser.parse(webServiceExplorerCO.getMainWsdlFullPath());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else{
			defs = parser.parse(webServiceUrl);
		}
		//Schema schema = this.loadSchema((Definitions)(new Cloner()).deepClone(defs));
		Schema schema = null;//this.loadSchema(defs);
		
		String s = defs.getTargetNamespace();
		Schema schemas = defs.getSchema(s);	
		defs.getSchemas();
		schemas.getComplexTypes();
		Map<String,String> nameSpaces = (Map<String, String>) defs.getNamespaceContext();
		List<com.predic8.wsdl.Import> imports = defs.getImports();
		Map<String,Object> importsMap = new HashMap<String,Object>();
		for(com.predic8.wsdl.Import imp : imports)
		{
			importsMap.put(imp.getNamespace(), imp);
		}
		PortType portType1 = defs.getPortTypes().get(0);
		List<Operation> operations1= portType1.getOperations();
		Input input = operations1.get(0).getInput();
		Output output = operations1.get(0).getOutput();
		
		String requestName = input.getMessagePrefixedName().getLocalName();
		String reqWsdlNameSpaceRef = input.getMessagePrefixedName().getPrefix();
		
		String responseName = output.getMessagePrefixedName().getLocalName();
		String respWsdlNameSpaceRef = output.getMessagePrefixedName().getPrefix();
		
		if(null != reqWsdlNameSpaceRef && reqWsdlNameSpaceRef.equalsIgnoreCase("tns"))
		{
			schema = defs.getSchemas().get(0);
		}
		else{
			com.predic8.wsdl.Import imp1 = (com.predic8.wsdl.Import)importsMap.get(nameSpaces.get(reqWsdlNameSpaceRef));
			schema = imp1.getImportedWSDL().getSchemas().get(0);
		}
		webServiceExplorerCO.setWsdlUrl(webServiceUrl);
		webServiceExplorerCO.setNameSpaceUri(schema.getTargetNamespace());
		HashMap<String,String> bindingHash = new HashMap<String,String>();
		
		HashMap<String,String> types = new HashMap<String,String>();
		
		for(ComplexType complexType : schema.getComplexTypes())
		{
			types.put(complexType.getName(), "complexType");
		}
		
		for(SimpleType simpleType : schema.getSimpleTypes())
		{
			types.put(simpleType.getName(), "simpleType");
		}
		
		this.setParamType(types);
		
		Map<String,String> elements = new HashMap<String,String>();
		
		for(Element e:schema.getAllElements())
		{
			if (null != e.getType())
			{
				try{
					elements.put(e.getName(), schema.getType(e.getType().getLocalPart()).getMetaClass().getTheClass().getSimpleName());
				}
				catch(Exception e1)
				{
					String fieldType = e.getType().getLocalPart();
				}
			}
			else
			{
				elements.put(e.getName(), e.getEmbeddedType().getMetaClass().getTheClass().getSimpleName()+"_embeddedtype");
			}
			
		}
//		for (PortType portType : defs.getPortTypes()) 
//		{
			PortType portType = null;
			List<Service> services = defs.getServices();
			String webServiceName = webServiceExplorerCO.getWebServiceName();
			webServiceName = this.updateServiceName(defs, webServiceExplorerCO);
			for(Service service : services)
			{
				if(null != webServiceName && webServiceName.equals(service.getQName().getLocalPart()))
				{
					String bindingType = defs.getBinding(((Port)service.getPorts().get(0)).getBindingPN().getLocalName()).getType().getLocalPart();
					webServiceExplorerCO.setWebServiceName(bindingType);
				}
			}
			portType = defs.getPortTypes().get(0);
//			if(null != defs.getPortType(webServiceName))
//			{
//				portType = defs.getPortType(webServiceName);
//			}
//			else
//			{
//				//portType = defs.getPortTypes().get(0);
//				portType = schema.getDefinitions().getPortType(webServiceName);
//			}
			groovy.xml.QName portTypeQName = portType.getQName();
			webServiceExplorerCO.setPortTypePrefix(portType.getPrefix());			
			List<Operation> operations = portType.getOperations();			
			String operationName = portType.getName();
			Operation operation = portType.getOperation(webServiceExplorerCO.getOperationName());			
			groovy.xml.QName operationQName = operation.getQName();
//			if(!defs.getBindings().isEmpty())
//			{
//				Binding binding = defs.getBinding(bindingHash.get(portTypeQName.getLocalPart()));			
//				BindingOperation bindingOperation  = binding.getOperation(operationQName.getLocalPart());
//				if(binding.getBinding() instanceof AbstractSOAPBinding)
//				{ 
//					webServiceExplorerCO.setSoapAction(bindingOperation.getOperation().getSoapAction()); 
//				}
//			}
			
			String functionName = operation.getName();
			String documentation = null;
			if(null!= operation.getDocumentation())
			{
				documentation = operation.getDocumentation().getContent();
			}
			
//			String input = operation.getInput().getMessage().getName();
//
//			String output = operation.getInput().getMessage().getName();
		
			/*************************************************/
						   // Parsing //
			/*************************************************/
			String requestClassName = null; 			
			Sequence sequence = null;
			List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
			List<RequestResponseCO> lstInResponse = null;
			String fieldName = null;
			String fieldType = null;
			String QName = null;
			String fieldMinOCC = null;
			int  hashChildren = 0; // This field will be set as one Incase the type is not a premitave type
			RequestResponseCO reqResCO;	
			ComplexType complexType = null;	
			webServiceExplorerCO.setWsdlUrl(webServiceUrl);
			ParserCO parserCO = null;
//			String className = null;
			
//			switch((defs.getMessage(output).getParts()).size())
//			{
//				case 1: className = (((Part)defs.getMessage(output).getParts().get(0)).getElement()).getName();
//				break;
//			}	
			parserCO = new ParserCO();
			parserCO.setSchema(schema);
			parserCO.setOperation(operation);
			parserCO.setWebServiceExplorerCO(webServiceExplorerCO);
			
			requestClassName =  webServiceExplorerCO.getSelectedFieldType();
			String type = types.get(requestClassName);
			
//			if(WebServiceExplorerConstant.SIMPLE_TYPE.equals(type))
//			{
//				return webServiceExplorerCO;
//			}
			//TODO All update
			ComplexType comType = schema.getComplexTypes().get(0);
			if(null !=  requestClassName)
			{//List<chequebookCodeDC>
				requestClassName = requestClassName.replace("List<", "").replace(">", "");
				webServiceExplorerCO.setRequestName(requestClassName);
				complexType = schema.getComplexType(requestClassName);
			}
			else{//TODO All update
//				requestClassName = this.returnOperationRequestClassName(parserCO); 
				if(comType.getModel() instanceof All)
				{
					if(null != webServiceExplorerCO.getRequestType() && "response".equals(webServiceExplorerCO.getRequestType()))
					{
						requestClassName = this.responseComplexTypeName(parserCO);
					}
					else{
						requestClassName = this.requestComplexTypeName(parserCO);
					}
				}
				else{
					requestClassName = this.returnOperationRequestClassName(parserCO);
				}
				webServiceExplorerCO.setRequestName(requestClassName);
				complexType = schema.getComplexType(requestClassName);
			}
			parserCO.setWebServiceExplorerCO(webServiceExplorerCO);
			parserCO.setComplexType(complexType);
			//TODO Recheck after demo
			if(null != webServiceExplorerCO.getIsFromBpelScreen() && webServiceExplorerCO.getIsFromBpelScreen().equalsIgnoreCase("true"))
			{
				webServiceExplorerCO = this.loadComplexType(parserCO);
				if(null == webServiceExplorerCO)
				{
					return new WebServiceExplorerCO();
				}
				lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();
				webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
			}
			else{
			if(null == elements.get(operation.getName()))
			{
				webServiceExplorerCO = this.loadComplexType(parserCO);
				if(null == webServiceExplorerCO)
				{
					return new WebServiceExplorerCO();
				}
				lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();
				webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
			}
			else{
				switch(elements.get(operation.getName()).toLowerCase())
				{			
					case "complextype":	
						webServiceExplorerCO = this.loadComplexType(parserCO);
						if(null == webServiceExplorerCO)
						{
							return new WebServiceExplorerCO();
						}
						lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();		
						break;
						
					case "complextype_embeddedtype":
					//	complexType = ((ComplexType)(schema.getElement(requestClassName).getEmbeddedType()));
						webServiceExplorerCO = loadComplexEmbeddedType(webServiceExplorerCO,schema,requestClassName);
						if(null == webServiceExplorerCO)
						{
							return new WebServiceExplorerCO();
						}
						lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();
						break;
				}		
			}
			}
			webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
//		}
	
		webServiceExplorerCO.setWsdlUrl(webServiceUrl);
		return webServiceExplorerCO;
	}
	
	/**
	 * 
	 * @param webServiceExplorerCO
	 * @return
	 * @throws BOException
	 */
	public WebServiceExplorerCO processMultipleWsdls(WebServiceExplorerCO webServiceExplorerCO) throws BOException
	{
		try{
			String tempWsdlRep = FileUtil.getFileURLByName("repository")+File.separator+"bpel"+File.separator+webServiceExplorerCO.getBpelFileName().replace(".zip", "");
			Map<String,String> zipData = webServiceExplorerCO.getZipData();
			Map<String,String> zipDataAsString = webServiceExplorerCO.getZipDataAsString();
			String wsdl = null;
			webServiceExplorerCO.getMainWsdl();
			webServiceExplorerCO.getMainWsdlName();
			webServiceExplorerCO.setMainWsdlFullPath(tempWsdlRep+File.separator+webServiceExplorerCO.getMainWsdlName());
			for(String str : zipData.keySet())
			{
				wsdl = zipDataAsString.get(zipData.get(str));
				this.exportWsdl(zipData.get(str), tempWsdlRep, wsdl);
			}
			}
			catch(Exception e)
			{
				throw new BOException(e.getMessage());
			}
		return webServiceExplorerCO;
	}

	/**
	 * 
	 * @param fileName
	 * @param dest
	 * @param wsdl
	 * @throws IOException
	 * @throws GeneratorException
	 * @throws BOException
	 */
	public void exportWsdl(String fileName,String dest,String wsdl) throws IOException, Exception, BOException
	{
		String absoluteFilePath = null;//createDirectory(dest) + File.separator + fileName;
		File file = new PathFileSecure(absoluteFilePath);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		byte[] contentInBytes = wsdl.getBytes();
		fileOutputStream.write(contentInBytes);
		fileOutputStream.flush();
		fileOutputStream.close();			
	}
	
	/**
	 * 
	 * @param dest
	 * @return
	 * @throws GeneratorException
	 * @throws BOException
	 */
	public  String createDirectory(String dest) throws Exception, BOException
	{
		String fileSeperator = null;//FileGenerator.fileSeperator();
		String packageDir = null;
		packageDir = dest;
		String absoluteFilePath = "";
		absoluteFilePath = dest;
		absoluteFilePath = absoluteFilePath.replace("null", "");
		File file = new PathFileSecure(absoluteFilePath);
        Path path = Paths.get(absoluteFilePath);
        if (!Files.exists(path)) 
        {
            try {
                Files.createDirectories(path);
            } 
            catch (IOException e) 
            {
            	throw new BOException(e.getMessage());
            }
        }
        return absoluteFilePath;
	}
	
	/**
	 * @description function to generate exclude fields
	 * @param webServiceExplorerCO
	 */
	private void generateExcludedParams(WebServiceExplorerCO webServiceExplorerCO)
	{
		List<String> defaultExcluded = WebServiceExplorerConstant.defaultExcludedParams;
		List<String> newExcluded = webServiceExplorerCO.getExcludedParams();
		if(null == newExcluded || newExcluded.size() == 0)
		{
			newExcluded = new ArrayList<String>();
		}
		newExcluded.addAll(defaultExcluded);
		for(String str : newExcluded)
		{
			excludedFields.put(str.toLowerCase(), str);
		}
	}

	/**
	 * @description function used to generate the wsdl url
	 * @param webServiceExplorerCO
	 * @return
	 * @throws BaseException
	 */
	public String generateServiceUrl(WebServiceExplorerCO webServiceExplorerCO) throws BaseException
	{
		String webServiceUrl= null;
		Map<String,HashMap<String,List<String>>> nestedHash;
		WebServiceUtil webServiceUtil = new WebServiceUtil();
		try{
			String hostURL = null;
			String address = null;
			String webServiceName = null;
			String wsdl = "?wsdl";
			if(StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(webServiceExplorerCO.getApplicationName(), "")))
			{
				hostURL = webServiceUtil.getApplicationURL(WebServiceExplorerConstant.PROPERTY_FILE_NAME, webServiceExplorerCO.getApplicationName());
				webServiceName = webServiceUtil.getWebServiceNameFromNameSpace(hostURL,webServiceExplorerCO.getWebServiceName());
//				address = webServiceUtil.getApplicationAddress(hostURL,webServiceExplorerCO.getWebServiceName());
				address = webServiceUtil.getApplicationAddress(hostURL,webServiceName);
				hostURL = hostURL + webServiceUtil.PATH_SERVICE_MAPPING +  address;
				webServiceUrl = hostURL + wsdl;// + "="+ webServiceExplorerCO.getWebServiceName() +".wsdl";
				webServiceExplorerCO.setWebServiceName(webServiceName);
			}		
		}
		catch(Exception e)
		{
			throw new BOException("Unable to locate webservice address");
		}
		return webServiceUrl;
	}
	/**
	 * 
	 * @param defs
	 * @param webServiceExplorerCO
	 * @return
	 */
	public String updateServiceName(Definitions defs,WebServiceExplorerCO webServiceExplorerCO)
	{
		String bindingType = webServiceExplorerCO.getWebServiceName();
		List<Service> services = defs.getServices();
		String webServiceName = webServiceExplorerCO.getWebServiceName();	
		if(null != webServiceExplorerCO.getIsFromSoapScreen() && webServiceExplorerCO.getIsFromSoapScreen().equalsIgnoreCase("true"))
		{
			Binding b = defs.getBindings().get(0);
			return b.getPortType().getName();
		}
		for(Service service : services)
		{
			if(null != webServiceName && webServiceName.equals(service.getQName().getLocalPart()))
			{
				return defs.getBinding(((Port)service.getPorts().get(0)).getBindingPN().getLocalName()).getType().getLocalPart();
			}
		}
		return bindingType;
	}
	/**
	 * @author RaedSaad
	 * @Description This function will load the field restrictions
	 * @param name
	 */
	@Override
	public WebServiceExplorerCO loadSimpleType(WebServiceExplorerCO webServiceExplorerCO) throws BaseException
	{
		String id = webServiceExplorerCO.getRowId();
		String fieldName = null;
		String ids[] = id.split("_concat_");
		HashMap<String,String> idsHashMap = new HashMap<String,String>();
		for(int i = 1;i<ids.length-1;i++)
		{
			try{
				String x [] = ids[i].split("-");
				idsHashMap.put(x[0],x[1]);
			}
			catch(Exception e)
			{
				throw new BOException(e);
			}
		}
		webServiceExplorerCO.setApplicationName(StringUtil.nullToEmpty(idsHashMap.get("applicationName")));
		webServiceExplorerCO.setWebServiceName(StringUtil.nullToEmpty(idsHashMap.get("webServiceName")));
		webServiceExplorerCO.setOperationName(StringUtil.nullToEmpty(idsHashMap.get("operationName")));
		webServiceExplorerCO.setFieldType(StringUtil.nullToEmpty(idsHashMap.get("fieldType")));
		fieldName = StringUtil.nullToEmpty(idsHashMap.get("fieldName"));
		String webServiceUrl= null;
		HashMap<String,String> propVal = null;
		
		WebServiceUtil webServiceUtil = new WebServiceUtil();
		
		try{
			String hostURL = null;
			String address = null;
			String wsdl = "?wsdl";
			if(StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(webServiceExplorerCO.getApplicationName(), "")))
			{
				hostURL = webServiceUtil.getApplicationURL(WebServiceExplorerConstant.PROPERTY_FILE_NAME, webServiceExplorerCO.getApplicationName());
				address = webServiceUtil.getApplicationAddress(hostURL,webServiceExplorerCO.getWebServiceName());
				hostURL = hostURL + webServiceUtil.PATH_SERVICE_MAPPING +  address;
				webServiceUrl = hostURL + wsdl;// + "="+ webServiceExplorerCO.getWebServiceName() +".wsdl";
			}		
		}
		catch(Exception e)
		{
			  throw new BaseException(e.getMessage(), e);
		}

		WSDLParser parser = new WSDLParser();
		Definitions defs = parser.parse(webServiceUrl);
		String s = defs.getTargetNamespace();
		for(com.predic8.wsdl.Import imports  : defs.getImports())
		{
			s = imports.getLocation();
			WSDLParser parser1 = new WSDLParser();
			Definitions defs1 = parser.parse(s);
			s = defs1.getTargetNamespace();			
		}
		Schema schema = defs.getSchema(s);
		webServiceExplorerCO = loadSimpleType(webServiceExplorerCO,schema,fieldName);		
		return webServiceExplorerCO;		
	}
	/**
	 * @author RaedSaad
	 * @Description This Function Will parse Simple Type objects in WSDL File
	 * @param name
	 */
	private WebServiceExplorerCO loadSimpleType(WebServiceExplorerCO webServiceExplorerCO,Schema schema, String name) throws BaseException
	{
		SimpleType simpleType = null;
		SelectCO selectCO = null;
		ComplexType complexType = null;
		Sequence sequence = null;
		All allObj = null;
		Element element = null;
		String fieldType = webServiceExplorerCO.getFieldType();
		List<SelectCO> restrictions = new ArrayList<SelectCO>();
		RequestResponseCO requestResponseCO = new RequestResponseCO();
		if(null != webServiceExplorerCO.getEmbeddedTypeEnumParent() && StringUtil.isNotEmpty(webServiceExplorerCO.getEmbeddedTypeEnumParent()))
		{
			complexType = schema.getComplexType(webServiceExplorerCO.getEmbeddedTypeEnumParent());
			ModelGroup obj = (ModelGroup)this.extractSequence(complexType);
			element = obj.getElement(name);
			/*if(obj instanceof Sequence)
			{
				sequence = (Sequence)obj;
				element = sequence.getElement(name);
			}
			else
			{
				allObj = (All)obj;
				element = allObj.getElement(name);
			}*/
			simpleType = (SimpleType)element.getEmbeddedType();
		}
		else
		{
			simpleType = schema.getSimpleType(fieldType);
		}
		selectCO = new SelectCO();
		selectCO.setCode("default");
		selectCO.setDescValue("");
		selectCO.setDefaultValue("");
		restrictions.add(selectCO);
		for(EnumerationFacet facet : (ArrayList<EnumerationFacet>)simpleType.getRestriction().getEnumerationFacets())
		{
			selectCO = new SelectCO();
			selectCO.setCode(facet.getValue());
			selectCO.setDescValue(facet.getValue());
			selectCO.setDefaultValue(facet.getValue());
			restrictions.add(selectCO);
		}
		requestResponseCO.setRestrictions(restrictions);
		webServiceExplorerCO.setRequestResponseCO(requestResponseCO);
	    return webServiceExplorerCO;		
	}

	/*
	 * @author RaedSaad
	 * @description function to check if a sequence or object contain nested objects
	 * true = > contain nested object
	 * false = > all types are non primative datatypes
	 * */
	private Boolean containsNestedSequence(Sequence sequence) throws BaseException
	{
		QName qNameType = null;
		String elementType = null;
		String booleanStringVal = null;
		List<Element> elements = sequence.getElements();
		for(Element e : elements)
		{
			qNameType = e.getType();
			elementType	= qNameType.getLocalPart();	
			booleanStringVal = this.isNonPrimativeDataType(elementType).toString();
			switch(booleanStringVal)
			{
				case "false" : 
					return true;
			}
		}
		return false;		
	}
	
	/**
	 * @description function to return request Complex Type Name
	 * @param parserCO
	 * @return
	 * @throws BaseException
	 */
	private String requestComplexTypeName(ParserCO parserCO) throws BaseException
	{	//TODO All update
		Schema schema = parserCO.getSchema();
		Operation operation = parserCO.getOperation();
		WebServiceExplorerCO webServiceExplorerCO = parserCO.getWebServiceExplorerCO();
		Definitions defs = schema.getDefinitions();
		ComplexType complexType = schema.getComplexTypes().get(0);
		Element element = operation.getInput().getMessage().getParts().get(0).getElement();		
		return element.getType().getLocalPart();
	}
	
	/**
	 * @description function to return response complex type name
	 * @param parserCO
	 * @return
	 * @throws BaseException
	 */
	private String responseComplexTypeName(ParserCO parserCO) throws BaseException
	{//TODO All update
		Schema schema = parserCO.getSchema();
		Operation operation = parserCO.getOperation();
		WebServiceExplorerCO webServiceExplorerCO = parserCO.getWebServiceExplorerCO();
		Definitions defs = schema.getDefinitions();
		return operation.getOutput().getMessage().getParts().get(0).getElement().getType().getLocalPart();		
	}
	
	/**
	 * @description: function to return the operation request class name
	 * @Note incase the input is non primative data type the it will return operation name 
	 * since we need to read its elements
	 * */
	private String returnOperationRequestClassName(ParserCO parserCO) throws BaseException
	{
		Schema schema = parserCO.getSchema();
		Operation operation = parserCO.getOperation();
		WebServiceExplorerCO webServiceExplorerCO = parserCO.getWebServiceExplorerCO();
		Definitions defs = schema.getDefinitions();
		String input = operation.getInput().getMessage().getName();
		String output = operation.getOutput().getMessage().getName();
		String req = operation.getInput().getMessage().getParts().get(0).getElement().getType().getLocalPart();//TODO All update
		if(WebServiceExplorerConstant.RESPONSE_TYPE.equals(webServiceExplorerCO.getRequestType()))
		{
			input = new String(output);
		}
		switch((defs.getMessage(input).getParts()).size())
		{
			case 1: 
				ComplexType complexType = schema.getComplexType(input);
				Sequence sequence = complexType.getSequence();
				if(sequence.getElements().size() == 0)
				{
					break;
				}
				if(null == sequence.getElements().get(0).getType() && null != sequence.getElements().get(0).getEmbeddedType())
				{//incase of request we return input name
					break;
				}
				String type = sequence.getElements().get(0).getType().getLocalPart();
				if(this.isNonPrimativeDataType(type))
				{
					break;
				}
				return type;		
		}	
		return input;	
	}
	
	/**
	 * @description: function used to load wsdl schema
	 * */
	private Schema loadSchema(Definitions definition)
	{
		String s = definition.getTargetNamespace();
		Schema schema = definition.getSchema(s);		
		for(com.predic8.wsdl.Import imports  : definition.getImports())
		{
			s = imports.getLocation();
			WSDLParser parser1 = new WSDLParser();
			Definitions defs1 = parser1.parse(s);
			return defs1.getSchema(defs1.getTargetNamespace());			
		}
		return schema;
	}
	
	/**
	 * @Description return the type of the request
	 * Object, Non Primative Data Type, HashMap,List 
	 * */
	private String returnRequestType(WebServiceExplorerCO webServiceExplorerCO,ComplexType complextype)
	{
		Sequence sequence = null;
		All all = null;
		List<Element> elements = null;
		ModelGroup obj = (ModelGroup) complextype.getModel();
		elements = obj.getElements();
		
		/*if(complextype.getModel() instanceof All)//TODO All update
		{
			all = (All) complextype.getModel();
			elements = all.getElements();
		}
		else
		{
			sequence = this.extractSequence(complextype);
			elements = sequence.getElements();
		}*/
		int size = elements.size();
		switch(size)
		{
			case 1:
				Element element = elements.get(0);
				if(null == element.getType())
				{
					if(!WebServiceExplorerConstant.SIMPLE_TYPE.equals(this.returnEmbeddedType(element)))
					{//TODO All update
						Sequence seq = null;
						All all1 = null;
						List<Element> elems = null;
						if(complextype.getModel() instanceof All)
						{
							all1 = (All) complextype.getModel();
							elems = all.getElements();
						}
						else if(complextype.getModel() instanceof Sequence)
						{
							 seq = this.returnSequence(sequence);
							 elems = seq.getElements();
						}
						String indexType = elems.get(0).getType().getLocalPart();
						String valueType = elems.get(1).getType().getLocalPart();
						switch(this.isNonPrimativeDataType(valueType).toString().toLowerCase())
						{
							case "true":
								return "HashMap";
							case "false":
								return "HashMap<"+indexType+","+valueType+">";
						}
					}
					else{
						break;
						// TODO handle simple type
					}
					
				}
				else if("unbounded".equals(element.getMaxOccurs()))
				{
					String type = element.getType().getLocalPart();
					switch(this.isNonPrimativeDataType(type).toString().toLowerCase())
					{
						case "false":
							return "listOfObjects";
					}
					return "list";
				}
				else if(null != element.getQname().getLocalPart() && !this.isNonPrimativeDataType(webServiceExplorerCO.getRequestName())){
					return "Object"; 
				}
				else{
					return "NonPrimativeDataType";//String, int, boolean ...
				}
		}
		return "Object";		
	}
	
	/**
	 * @Description parses Elements
	 * */
	private RequestResponseCO parseObjectElement(WebServiceExplorerCO webServiceExplorerCO, Element element)
	{
		RequestResponseCO reqResCO = new RequestResponseCO();
		Schema schema = element.getSchema();	
		String parentId = null;
		parentId = webServiceExplorerCO.getParentRowId();
		String fieldId = null;
		if(null != element.getEmbeddedType()) //case of hash map in object
		{
			if(WebServiceExplorerConstant.SIMPLE_TYPE.equals(this.returnEmbeddedType(element)))
			{
				SimpleType simpleType = (SimpleType)element.getEmbeddedType();
				reqResCO = this.parseSimpleType(webServiceExplorerCO, element);
			}
			else
			{
				ComplexType complexType = (ComplexType) element.getEmbeddedType();
				if(complexType.getModel() instanceof Sequence && ((Element)((Sequence)complexType.getModel()).getParticles().get(0)).getMaxOccurs().equals("unbounded"))
				{
					//list in all 31/01/2019
					//TODO List
					String fieldName = element.getName();
					String fieldType = null;
					Element lstElement = (Element) ((Sequence)((ComplexType)element.getEmbeddedType()).getModel()).getParticles().get(0);
					fieldType = lstElement.getName();
					String maxOcc = lstElement.getMaxOccurs();
					String defaultValue = element.getDefaultValue();
					boolean nillable = element.getNillable();			
					switch(maxOcc)
					{
						case "unbounded":
							fieldType = "List<"+fieldType+">";
						break;
					}
					String fieldMinOCC = null;
					switch(StringUtil.nullToEmpty(element.getProperty("minOccurs").toString()))
					{
						case "":
							break;
						case "0": fieldMinOCC = "optional";
							break;
						case "1": fieldMinOCC = "mandatory";
							break;
						default: fieldMinOCC = "optional";
							break;
					}
					String isNonPriOrListOrHash = this.isNonPrimativeDataTypeOrListORHash(fieldType).toString();
					switch(isNonPriOrListOrHash)
					{
						case "true":
							reqResCO.setHasChildren(WebServiceExplorerConstant.NO_CHILDREN);
							break;
						case "false":
							reqResCO.setHasChildren(WebServiceExplorerConstant.HAS_CHILDREN);
							break;
					}
					fieldId = parentId + "_" + fieldName+"_"+webServiceExplorerCO.getPageRef();
					fieldId = fieldId.replaceAll("null_", "");
					reqResCO.setFieldName(fieldName);
					reqResCO.setFieldType(fieldType);
					reqResCO.setMandatory(fieldMinOCC);
					reqResCO.setValue(defaultValue);
					reqResCO.setNillable(nillable);
					String gridColumnId = reqResCO.getHasRestriction()+"_concat_"+"hashRestrictions-"+reqResCO.getHasRestriction()+"_concat_"+"applicationName-"+webServiceExplorerCO.getApplicationName()+"_concat_"+"webServiceName-"+webServiceExplorerCO.getWebServiceName()+"_concat_"+"operationName-"+webServiceExplorerCO.getOperationName()+"_concat_"+"fieldName-"+fieldName+"_concat_"+"fieldType-"+fieldType+"_concat_"+"fieldMinOCC-"+fieldMinOCC+"_concat_"+"hasChildren-"+reqResCO.getHasChildren()+"_concat_"+reqResCO.getHasChildren();
					reqResCO.setGridColumnID(gridColumnId);
					reqResCO.setID(fieldId);
					reqResCO.setWsdlUrl(webServiceExplorerCO.getWsdlUrl());
					reqResCO.setSoapAction(webServiceExplorerCO.getSoapAction());
					reqResCO.setNameSpaceUri(webServiceExplorerCO.getNameSpaceUri());
					return reqResCO;
				}
				else{
					reqResCO = this.parseHashMapTypeRequest(webServiceExplorerCO, complexType);
				}
			}
		}		
		else if(this.isExcludedField(element.getName())||(null != element.getType() && this.isExcludedField(element.getType().getLocalPart())))
		{
			return null;
		}
		else{
			QName qName = element.getType();
			String fieldName = element.getName();
			String fieldType = null;
			if(null == qName)
			{
				fieldType = "decimal";
			}
			else{
				fieldType = qName.getLocalPart();
			}
			String maxOcc = element.getMaxOccurs();
			String defaultValue = element.getDefaultValue();
			boolean nillable = element.getNillable();			
			switch(maxOcc)
			{
				case "unbounded":
					fieldType = "List<"+fieldType+">";
				break;
			}
			String fieldMinOCC = null;
			switch(StringUtil.nullToEmpty(element.getProperty("minOccurs").toString()))
			{
				case "":
					break;
				case "0": fieldMinOCC = "optional";
					break;
				case "1": fieldMinOCC = "mandatory";
					break;
				default: fieldMinOCC = "optional";
					break;
			}
			HashMap<String, String> simpleTypesHashMap = null;
			HashMap<String, String> params = this.getParamType();
			String isNonPriOrListOrHash = this.isNonPrimativeDataTypeOrListORHash(fieldType).toString();
			switch(isNonPriOrListOrHash)
			{
				case "true":
					reqResCO.setHasChildren(WebServiceExplorerConstant.NO_CHILDREN);
					break;
				case "false":
					reqResCO.setHasChildren(WebServiceExplorerConstant.HAS_CHILDREN);
					break;
			}
			String type = StringUtil.nullToEmpty(params.get(fieldType));
			switch (type.toLowerCase()) 
			{
				case "simpletype":
					reqResCO.setHasRestriction(WebServiceExplorerConstant.HAS_ENUM_RESTRICTION);
					reqResCO.setHasChildren(WebServiceExplorerConstant.NO_CHILDREN); // set of the hasChildren class since the type is enum
					String value = StringUtil.nullEmptyToValue(schema.getSimpleType(fieldType).getRestriction().getEnumerationFacets(), "null");
					switch (value) {
						case "null":
							webServiceExplorerCO.setHasRestrictions(WebServiceExplorerConstant.HAS_ENUM_RESTRICTION);
							reqResCO.setHasRestriction(WebServiceExplorerConstant.NO_RESTRICTION);
							break;
					}
					String fieldPrec = null;
					String precMin = "0";
					String precMax = "0";
					MinLengthFacet minLengthFacet = schema.getSimpleType(fieldType).getRestriction().getMinLengthFacet();
					MaxLengthFacet maxLengthFacet = schema.getSimpleType(fieldType).getRestriction().getMaxLengthFacet();
					if(null != minLengthFacet)
					{
						precMin = StringUtil.nullEmptyToValue(schema.getSimpleType(fieldType).getRestriction().getMinLengthFacet().getValue(), "null");
					}
					if(null != maxLengthFacet)
					{
						precMax = StringUtil.nullEmptyToValue(schema.getSimpleType(fieldType).getRestriction().getMaxLengthFacet().getValue(), "null");
					}
					fieldPrec = "(" + precMin + "-" + precMax + ")";
					switch (fieldPrec) {
						case "(-)":
							fieldPrec = "";
							break;
						case "(-null)":
							fieldPrec = "";
							break;
						case "(null-)":
							fieldPrec = "";
							break;
						case "(null-null)":
							fieldPrec = "";
						case "(0-0)":
							fieldPrec = "";
						break;
					}
					break;
			}
			fieldId = parentId + "_" + fieldName+"_"+webServiceExplorerCO.getPageRef();
			fieldId = fieldId.replaceAll("null_", "");
			reqResCO.setFieldName(fieldName);
			reqResCO.setFieldType(fieldType);
			reqResCO.setMandatory(fieldMinOCC);
			reqResCO.setValue(defaultValue);
			reqResCO.setNillable(nillable);
			String gridColumnId = reqResCO.getHasRestriction()+"_concat_"+"hashRestrictions-"+reqResCO.getHasRestriction()+"_concat_"+"applicationName-"+webServiceExplorerCO.getApplicationName()+"_concat_"+"webServiceName-"+webServiceExplorerCO.getWebServiceName()+"_concat_"+"operationName-"+webServiceExplorerCO.getOperationName()+"_concat_"+"fieldName-"+fieldName+"_concat_"+"fieldType-"+fieldType+"_concat_"+"fieldMinOCC-"+fieldMinOCC+"_concat_"+"hasChildren-"+reqResCO.getHasChildren()+"_concat_"+reqResCO.getHasChildren();
			reqResCO.setGridColumnID(gridColumnId);
			reqResCO.setID(fieldId);
			reqResCO.setWsdlUrl(webServiceExplorerCO.getWsdlUrl());
			reqResCO.setSoapAction(webServiceExplorerCO.getSoapAction());
			reqResCO.setNameSpaceUri(webServiceExplorerCO.getNameSpaceUri());
		}
		return reqResCO;
	}
	
	/**
	 * @Description Called incase the Input Request is an object (of type CO)
	 * */
	private WebServiceExplorerCO parseObjectTypeRequest(WebServiceExplorerCO webServiceExplorerCO, ComplexType complexType)
	{
		Schema schema = complexType.getSchema();
		Sequence sequence = null;
		List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
		RequestResponseCO reqResCO = null;
		List<Element> elements = null;
		All all = null;//TODO All update
		if(schema.getComplexType(webServiceExplorerCO.getRequestName()).getModel() instanceof All)
		{
			all = (All) schema.getComplexType(webServiceExplorerCO.getRequestName()).getModel();
			elements = all.getElements();
		}
		else if(schema.getComplexType(webServiceExplorerCO.getRequestName()).getModel() instanceof Sequence)
		{			//TODO All update
			sequence =  returnSequence((Sequence)schema.getComplexType(webServiceExplorerCO.getRequestName()).getModel());	
			if(null == sequence && this.hasSimpleType(complexType))
			{
				sequence = ((Sequence)complexType.getModel());
			}
			elements = sequence.getElements();
		}
		else if (schema.getComplexType(webServiceExplorerCO.getRequestName()).getModel() instanceof ComplexContent) {
			sequence = (Sequence) ((ComplexContent) schema.getComplexType(webServiceExplorerCO.getRequestName()).getModel()).getDerivation().getModel();
			//load All extension fields
			switch (this.hasExtension((ComplexContent)(schema.getComplexType(webServiceExplorerCO.getRequestName()).getModel())).toString())
			{
				case "true":
					Derivation derivation =  ((ComplexContent)schema.getComplexType(webServiceExplorerCO.getRequestName()).getModel()).getDerivation();
					groovy.xml.QName qName = derivation.getBase();
					//incase we have an empty class
					Sequence s;
					lstReqResCO = this.loadExtension((ComplexType)schema.getComplexType(qName.getLocalPart()),webServiceExplorerCO);
					break;
			}//TODO All update
			if(null == sequence && this.hasSimpleType(complexType))
			{
				sequence = ((Sequence)complexType.getModel());
			}
			elements = sequence.getElements();
		}
		String isNonPri = null;
		for(Element element : elements)
		{//to add exclude here
			reqResCO = this.parseObjectElement(webServiceExplorerCO, element);		
			if(null != reqResCO)
			{
				lstReqResCO.add(reqResCO);
			}
		}
		//lstReqResCO = removeRequestResponseListDuplicates(lstReqResCO);
		webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
		return webServiceExplorerCO;
	}
	
	/**
	 * @Description parse hash map request
	 * this function only support sequence
	 * */
	private RequestResponseCO parseHashMapTypeRequest(WebServiceExplorerCO webServiceExplorerCO, ComplexType complexType)
	{
		List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
		RequestResponseCO reqResCO = new RequestResponseCO();
		Sequence sequence = complexType.getSequence();
		List<Element> elements = sequence.getElements();
		String fieldName = elements.get(0).getQname().getLocalPart();		
		switch(fieldName)
		{
			case "entry":
				fieldName = ((Element)complexType.getParent()).getName();
				break;
		}
		String parentId =  null;
		parentId = webServiceExplorerCO.getParentRowId();
		String fieldId = parentId + "_" + fieldName+"_"+webServiceExplorerCO.getPageRef();
		fieldId = fieldId.replace("null_", "");
		sequence = this.returnSequence(sequence);
		elements = sequence.getElements();
		String indexType = elements.get(0).getType().getLocalPart();
		String valueType = elements.get(1).getType().getLocalPart();
		String fieldType = "HashMap<"+indexType+","+valueType+">";
		String fieldMinOCC = null;
		fieldMinOCC = elements.get(0).getMinOccurs();
		reqResCO.setHasChildren(WebServiceExplorerConstant.NO_CHILDREN);
		reqResCO.setHasRestriction(WebServiceExplorerConstant.NO_RESTRICTION);
		reqResCO.setFieldName(fieldName);
		reqResCO.setFieldType(fieldType);
		reqResCO.setMandatory(fieldMinOCC);
		String gridColumnId = reqResCO.getHasRestriction()+"_concat_"+"hashRestrictions-"+reqResCO.getHasRestriction()+"_concat_"+"applicationName-"+webServiceExplorerCO.getApplicationName()+"_concat_"+"webServiceName-"+webServiceExplorerCO.getWebServiceName()+"_concat_"+"operationName-"+webServiceExplorerCO.getOperationName()+"_concat_"+"fieldName-"+fieldName+"_concat_"+"fieldType-"+fieldType+"_concat_"+"fieldMinOCC-"+fieldMinOCC+"_concat_"+"hasChildren-"+reqResCO.getHasChildren()+"_concat_"+reqResCO.getHasChildren();
		reqResCO.setGridColumnID(gridColumnId);
		reqResCO.setID(fieldId);
		reqResCO.setWsdlUrl(webServiceExplorerCO.getWsdlUrl());
		reqResCO.setSoapAction(webServiceExplorerCO.getSoapAction());
		return reqResCO;
	}
	
	/**
	 * @description parse simple type elements
	 * @param webServiceExplorerCO
	 * @param simpleType
	 * @return
	 */
	private RequestResponseCO parseSimpleType(WebServiceExplorerCO webServiceExplorerCO,Element element)
	{
		ComplexType complexType = null;
		element.getParent();
		RequestResponseCO reqResCO = new RequestResponseCO();
		String minOccurs = element.getMinOccurs();
		String maxOccurs = element.getMaxOccurs();
		String fieldName = element.getName();
		boolean nillable = element.getNillable();
		String defaultValue = element.getDefaultValue();
		int minLen = 0;
		//TotalDigitsFacet
		String fieldPrec = null;
		if(Integer.parseInt(minOccurs)>0)
		{
			minLen = 1;
		}
		SimpleType simpleType = (SimpleType) element.getEmbeddedType();
		BaseRestriction restriction = simpleType.getRestriction();
		List<Facet> facets = restriction.getFacets();
		String buildInType = restriction.getBuildInTypeName();
		MinLengthFacet minLengthFacet =  restriction.getMinLengthFacet();
		MaxLengthFacet maxLengthFacet =  restriction.getMaxLengthFacet();
		MaxInclusiveFacet maxInclusiveFacet = restriction.getMaxInclusiveFacet();		
		List<EnumerationFacet>enumerationList = restriction.getEnumerationFacets();
		//restriction.getMetaClass().getTheClass().getSimpleName();
		if(WebServiceExplorerConstant.DECIMAL_TYPE.equals(buildInType))
		{
			TotalDigitsFacet totalDigitsFacet = null;
			FractionDigits fractionDigitsFacet = null;
			for(Facet facet : facets)
			{
				if(facet instanceof TotalDigitsFacet)
				{
					totalDigitsFacet = (TotalDigitsFacet)facet;
				}
				else if(facet instanceof FractionDigits)
				{
					fractionDigitsFacet = (FractionDigits)facet;
				}
			}
			if(null != totalDigitsFacet)
			{
				int i = 0;
				int totalValue = Integer.parseInt(totalDigitsFacet.getValue());
				int fractionValue = 0;
				StringBuilder frac = new StringBuilder();
				if(totalValue>5)
				{
					frac.append((totalValue-fractionValue)+"*#");
					if(fractionValue>0)
					{
						frac.append(","+fractionValue+"*#");
					}
				}
				else{
					while(i<totalValue-fractionValue)
					{
						frac.append("#");
						i++;
					}
					if(null != fractionDigitsFacet && fractionValue > 0)
					{
						fractionValue = Integer.parseInt(fractionDigitsFacet.getValue());
						frac.append(".");
						i = 0;
						while(i<fractionValue)
						{
							frac.append("#");
							i++;
						}
					}
				}
				//fieldPrec = "("+frac.toString()+")";
				fieldPrec = "("+(totalValue-fractionValue) + ","+fractionValue+")";
			}
		}
		else if(WebServiceExplorerConstant.DATE_TYPE.equals(buildInType) || WebServiceExplorerConstant.DATETIME_TYPE.equals(buildInType))
		{
			PatternFacet patternFacet = null;
			for(Facet facet : facets)
			{
				if(facet instanceof PatternFacet)
				{
					patternFacet = (PatternFacet)facet;
				}
			}
			fieldPrec = patternFacet.getValue();
		}
		else
		{
			String min = "0";
			String max = "0";
			if(null != minLengthFacet)
			{
				min = StringUtil.nullEmptyToValue(minLengthFacet.getValue(), "0");
			}
			if(null != maxLengthFacet)
			{
				max = StringUtil.nullEmptyToValue(maxLengthFacet.getValue(), "0");
			}						
			fieldPrec = "(" + min + "-" + max + ")";
			switch (fieldPrec) 
			{
				case "(-)":
					fieldPrec = "";
					break;
				case "(-null)":
					fieldPrec = "";
					break;
				case "(null-)":
					fieldPrec = "";
					break;
				case "(null-null)":
					fieldPrec = "";
				case "(0-0)":
					fieldPrec = "";
				break;
			}
		}		
		String parentId =  null;
		parentId = webServiceExplorerCO.getParentRowId();
		String fieldId = parentId + "_" + fieldName+"_"+webServiceExplorerCO.getPageRef();
		fieldId = fieldId.replace("null_", "");
		String fieldType = buildInType;
		String fieldMinOCC = minOccurs;
		reqResCO.setHasChildren(WebServiceExplorerConstant.NO_CHILDREN);
		if(enumerationList.isEmpty())
		{
			reqResCO.setHasRestriction(WebServiceExplorerConstant.NO_RESTRICTION);	
		}
		else
		{
			reqResCO.setHasRestriction(WebServiceExplorerConstant.HAS_ENUM_RESTRICTION);
			complexType = this.returnElementParentComplexType(element);
			reqResCO.setEmbeddedTypeEnumParent(complexType.getName());
		}
		//fieldName = fieldName + " " + fieldPrec;
		switch(fieldMinOCC)
		{
			case "":
				break;
			case "0": fieldMinOCC = "optional";
				break;
			case "1": fieldMinOCC = "mandatory";
				break;
			default: fieldMinOCC = "optional";
				break;
		}
		reqResCO.setFieldName(fieldName);
		reqResCO.setFieldType(fieldType);
		reqResCO.setPrecision(fieldPrec);		
		reqResCO.setMandatory(fieldMinOCC);
		reqResCO.setNillable(nillable);
		reqResCO.setValue(defaultValue);
		String gridColumnId = reqResCO.getHasRestriction()+"_concat_"+"hashRestrictions-"+reqResCO.getHasRestriction()+"_concat_"+"applicationName-"+webServiceExplorerCO.getApplicationName()+"_concat_"+"webServiceName-"+webServiceExplorerCO.getWebServiceName()+"_concat_"+"operationName-"+webServiceExplorerCO.getOperationName()+"_concat_"+"fieldName-"+fieldName+"_concat_"+"fieldType-"+fieldType+"_concat_"+"fieldMinOCC-"+fieldMinOCC+"_concat_"+"hasChildren-"+reqResCO.getHasChildren()+"_concat_"+reqResCO.getHasChildren();
		reqResCO.setGridColumnID(gridColumnId);
		reqResCO.setID(fieldId);
		reqResCO.setWsdlUrl(webServiceExplorerCO.getWsdlUrl());
		reqResCO.setSoapAction(webServiceExplorerCO.getSoapAction());
		return reqResCO;	
	}
	
	/**
	 * @description used incase the web service parameter is a non primative data type
	 * String, Boolean, int ...
	 * */
	public WebServiceExplorerCO parseNonPrimativeDataTypeRequest(WebServiceExplorerCO webServiceExplorerCO, ComplexType complexType)
	{
		List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
		RequestResponseCO reqResCO = new RequestResponseCO();
		Sequence sequence = null;
		All all = null;
		List<Element> elements = null;
		Element element = null;
		if(complexType.getModel() instanceof All)
		{//TODO All update
			all = (All) complexType.getModel();
			elements = all.getElements();
		}
		else if(complexType.getModel() instanceof Sequence)
		{
			sequence = complexType.getSequence();
			elements = sequence.getElements();
		}		
		element = elements.get(0);
		QName qName = element.getType();
		String fieldName = element.getName();
		String fieldType = qName.getLocalPart();
		String fieldMinOCC = null;
		String parentId = null;
		parentId = webServiceExplorerCO.getParentRowId();
		switch(StringUtil.nullToEmpty(element.getProperty("minOccurs").toString()))
		{
			case "":
				break;
			case "0": fieldMinOCC = "optional";
				break;
			case "1": fieldMinOCC = "mandatory";
				break;
			default: fieldMinOCC = "optional";
				break;
		}
		String fieldId = parentId + "_" + fieldName+"_"+webServiceExplorerCO.getPageRef();
		fieldId = fieldId.replace("null_", "");
		reqResCO.setFieldName(fieldName);
		reqResCO.setFieldType(fieldType);
		reqResCO.setMandatory(fieldMinOCC);
		String gridColumnId = reqResCO.getHasRestriction()+"_concat_"+"hashRestrictions-"+reqResCO.getHasRestriction()+"_concat_"+"applicationName-"+webServiceExplorerCO.getApplicationName()+"_concat_"+"webServiceName-"+webServiceExplorerCO.getWebServiceName()+"_concat_"+"operationName-"+webServiceExplorerCO.getOperationName()+"_concat_"+"fieldName-"+fieldName+"_concat_"+"fieldType-"+fieldType+"_concat_"+"fieldMinOCC-"+fieldMinOCC+"_concat_"+"hasChildren-"+reqResCO.getHasChildren()+"_concat_"+reqResCO.getHasChildren();
		reqResCO.setGridColumnID(gridColumnId);
		reqResCO.setID(fieldId);
		reqResCO.setWsdlUrl(webServiceExplorerCO.getWsdlUrl());
		reqResCO.setSoapAction(webServiceExplorerCO.getSoapAction());
		lstReqResCO.add(reqResCO);
		//lstReqResCO = removeRequestResponseListDuplicates(lstReqResCO);
		webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
		return webServiceExplorerCO;
	}
	/**
	 * @author RaedSaad
	 * @Description This Function Will parse Complex Type objects in WSDL File
	 * @param name
	 */
	private WebServiceExplorerCO loadComplexType(ParserCO parserCO) {
		WebServiceExplorerCO webServiceExplorerCO = parserCO.getWebServiceExplorerCO();
		Schema schema = parserCO.getSchema();
		String name = parserCO.getWebServiceExplorerCO().getRequestName();
		ComplexType complextype = parserCO.getComplexType();
		String requestType = null;
		String fieldName = null;
		String QName = null;
		Sequence sequence = null;
		List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
		String fieldType= null;
		String fieldMinOCC = null;
		String fieldPrec = null;
		RequestResponseCO reqResCO = null;	
		
		int size = 0;
//		if(complextype.getModel() instanceof All)
//		{
//			All all = null;
//			size = 0;
//			all = (All) complextype.getModel();
//			size = all.getElements().size();
//			
//		}
//		else
			if(complextype.getModel() instanceof Sequence)
		{//TODO All update
			/*Incase the web service does not take any input*/
//			Sequence seq1 = null;
			ModelGroup seq1 = null;
			size = 0;
			seq1 = (ModelGroup)this.extractSequence(complextype);
			size = seq1.getElements().size();
			switch(size)
			{
				case 0 :
					if(this.isComplexContent(complextype))
					{
						Derivation derivation =  ((ComplexContent)complextype.getModel()).getDerivation();
						groovy.xml.QName qName = derivation.getBase();
						if(null != qName && null != qName.getLocalPart())
						{
							break;
						}
					}
					return null;
			}		
		}
		requestType = this.returnRequestType(webServiceExplorerCO,complextype);		
		switch(requestType)
		{
			case "NonPrimativeDataType":
				webServiceExplorerCO = this.parseNonPrimativeDataTypeRequest(webServiceExplorerCO, complextype);
				return webServiceExplorerCO;			
			case "HashMap":
				if(!(complextype.getModel() instanceof All))
				{//TODO All update
					reqResCO = this.parseHashMapTypeRequest(webServiceExplorerCO, complextype);
					lstReqResCO.add(reqResCO);
				//	lstReqResCO = removeRequestResponseListDuplicates(lstReqResCO);
					webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
					return webServiceExplorerCO;	
				}
				else{
					//all hash map case
				}				
			case "Object":
				webServiceExplorerCO = this.parseObjectTypeRequest(webServiceExplorerCO, complextype);
				return webServiceExplorerCO;				
//			case "list":
//				
//				break;
//				
//			case "listOfObjects":
//				
//				break;				
		}
		return webServiceExplorerCO;
	}
	
	/*
	 * remove duplicate field incase it is defined protected in root class
	 * */
	private List<RequestResponseCO> removeRequestResponseListDuplicates(List<RequestResponseCO> lstReqResCO)
	{
		Map<String,RequestResponseCO> hashMap = new HashMap<String,RequestResponseCO>();
		for(RequestResponseCO reqResCO : lstReqResCO)
		{
			hashMap.put(reqResCO.getFieldName(), reqResCO);
		}
		lstReqResCO = null;
		lstReqResCO = new ArrayList<RequestResponseCO>();
		for(String key : hashMap.keySet())
		{
			lstReqResCO.add(hashMap.get(key));
		}
		return lstReqResCO;
	}
	
	/*
	 * @author RaedSaad
	 * @Description This function will load the Base class incase we have inheritace
	 * */
	public List<RequestResponseCO> loadExtension(ComplexType complextType , WebServiceExplorerCO webServiceExplorerCO)
	{
		String fieldName = null;
		RequestResponseCO reqResCO = null;
		String QName = null;
		String fieldType = null;
		String fieldMinOCC = null;
		String parentId = null;
		parentId = webServiceExplorerCO.getParentRowId();
		List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
		ModelGroup obj = (ModelGroup)this.extractSequence(complextType);
		List<Element> elements = obj.getElements();
		Schema schema = complextType.getSchema();
		
		if(null != complextType && null != complextType.getModel() && (complextType.getModel() instanceof ComplexContent) && this.hasExtension((ComplexContent)complextType.getModel()))
		{
			Derivation derivation =  ((ComplexContent)complextType.getModel()).getDerivation();
			groovy.xml.QName qName = derivation.getBase();
			ComplexType complexType = (ComplexType)schema.getComplexType(qName.getLocalPart());
			//incase we have an empty class
			
			Sequence s;
			if(complexType.getModel() instanceof Sequence)
			{
				s = (Sequence) complexType.getModel();
				if(null != s)
				{
					lstReqResCO = this.loadExtension((ComplexType)schema.getComplexType(qName.getLocalPart()),webServiceExplorerCO);
				}
			}
			else if(complexType.getModel() instanceof ComplexContent && this.hasExtension((ComplexContent)complextType.getModel()))
			{
				lstReqResCO = this.loadExtension((ComplexType)schema.getComplexType(qName.getLocalPart()),webServiceExplorerCO);
			}
		}
		String isNonPri = null;
		for(Element element : elements)
		{
			reqResCO = this.parseObjectElement(webServiceExplorerCO, element);
			if(null != reqResCO)
			{
				lstReqResCO.add(reqResCO);
			}
		}
		return lstReqResCO;
	}
	
	/**
	 * @author RaedSaad
	 * @Description This Function Will parse ComplexEmbedded Types objects in WSDL File
	 * @param name
	 */
	private WebServiceExplorerCO loadComplexEmbeddedType(WebServiceExplorerCO webServiceExplorerCO, Schema schema, String name) 
	{
		RequestResponseCO reqResCO;
		Sequence sequence = (Sequence)((ComplexType)(schema.getElement(name).getEmbeddedType())).getModel();
		String fieldName = null;
		String QName = null;
		String fieldType = null;
		String fieldMinOCC = null;
		String fieldPrec = null;
		List<SchemaComponent> lstSchemaComponent = sequence.getParticles();
		String responseName = null;				 
		List<RequestResponseCO>lstReqResCO = new ArrayList<RequestResponseCO>();
		for (SchemaComponent schemaComponent : lstSchemaComponent) 
		{
			reqResCO = new RequestResponseCO();
			fieldName = schemaComponent.getName();
			if (null != schemaComponent.getProperty("type")) {
				QName = schemaComponent.getProperty("type").toString();
				String[] fieldTypeSplit = QName.split("}");
				fieldType = fieldTypeSplit[1];
				reqResCO.setColType("text");
				reqResCO.setEditOptions("");
				reqResCO.setEdittype("");
				reqResCO.setLoadOnce("");
				if(!this.isNonPrimativeDataType(fieldType))
				{
						HashMap<String,String> simpleTypesHashMap = null;
						HashMap<String,String> params = this.getParamType();
						reqResCO.setHasChildren(WebServiceExplorerConstant.NO_CHILDREN);
						String type = StringUtil.nullToEmpty(params.get(fieldType).toLowerCase());
						switch(type)
						{
							case "simpletype":
								webServiceExplorerCO.setHasRestrictions("1");
								reqResCO.setHasRestriction("1");	
								String value = StringUtil.nullEmptyToValue(schema.getSimpleType(fieldType).getRestriction().getEnumerationFacets(), "null");
								switch(value)
								{
									case "null":
										webServiceExplorerCO.setHasRestrictions("0");
										reqResCO.setHasRestriction("0");
										break;
								}
								
								String precMin = StringUtil.nullEmptyToValue(schema.getSimpleType(fieldType).getRestriction().getMinLengthFacet(), "null");
								String precMax = StringUtil.nullEmptyToValue(schema.getSimpleType(fieldType).getRestriction().getMaxLengthFacet(), "null");
								
								fieldPrec = "("+precMin+"-"+precMax+")";
								switch(fieldPrec)
								{
									case"(-)":
										fieldPrec="";
										break;
									case"(-null)":
										fieldPrec="";
										break;
									case"(null-)":
										fieldPrec="";
										break;
									case"(null-null)":
										fieldPrec = "";
										break;	
								}
								
								break;
						}				
				}
				else{			
					switch(fieldType)
					{
						case "decimal": fieldType="bigdecimal";
						break;
					}
					break;
				}
			}
			switch(StringUtil.nullToEmpty(schemaComponent.getProperty("minOccurs")))
			{
				case "":
					break;
				case "0": fieldMinOCC = "optional";
					break;
				case "1": fieldMinOCC = "mandatory";
					break;
				default: fieldMinOCC = "optional";
					break;
			}
			reqResCO.setFieldName(fieldName);
			reqResCO.setFieldType(fieldType+" "+ fieldPrec);
			reqResCO.setMandatory(fieldMinOCC);
			String gridColumnId = reqResCO.getHasRestriction()+"_concat_"+"hashRestrictions-"+reqResCO.getHasRestriction()+"_concat_"+"applicationName-"+webServiceExplorerCO.getApplicationName()+"_concat_"+"webServiceName-"+webServiceExplorerCO.getWebServiceName()+"_concat_"+"operationName-"+webServiceExplorerCO.getOperationName()+"_concat_"+"fieldName-"+fieldName+"_concat_"+"fieldType-"+fieldType+"_concat_"+"fieldMinOCC-"+fieldMinOCC+"_concat_"+"hasChildren-"+reqResCO.getHasChildren()+"_concat_"+reqResCO.getHasChildren();

			reqResCO.setGridColumnID(gridColumnId);
			reqResCO.setID(fieldName);
			reqResCO.setWsdlUrl(webServiceExplorerCO.getWsdlUrl());
			reqResCO.setSoapAction(webServiceExplorerCO.getSoapAction());
			lstReqResCO.add(reqResCO);
			webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
		}
			return webServiceExplorerCO;
	}
	
	/**
	 * @Raed Saad
	 * This function will handle webservice inputs such as HashMap 
	 * ComplexType/Sequence/Element/ComplexType/Sequence/Element
	 * */
	private Sequence returnSequence(Sequence seq)
	{
		Sequence sequence = null;					
		for (Element element : seq.getElements()) {
			if (null != element.getEmbeddedType()) {
				this.setName(element.getName());
				if(!WebServiceExplorerConstant.SIMPLE_TYPE.equals(this.returnEmbeddedType(element)))
				{
					ComplexType complexType = (ComplexType) element.getEmbeddedType();
					sequence = (Sequence) complexType.getModel();
				}
				else{
					return null;
				}
			}
			else{
				return seq;
			}
		}
		return returnSequence(sequence);		
	}
	
	/**
	 * @description return the parent complex type
	 * @param object
	 * @return
	 */
	private ComplexType returnElementParentComplexType(Object object)
	{		
		while(!(object instanceof ComplexType))
		{
			object = this.returnParentType(object);
		}
		return (ComplexType)object;
	}
	
	/**
	 * @description return element parent
	 * @param object
	 * @return
	 */
	private Object returnParentType(Object object)
	{
		if(!(object instanceof ComplexType))
		{
			return ((SchemaComponent)object).getParent();
		}
		return object;
	}
	
	/**
	 * @description returns true if a certain type has a simple type
	 * @param object
	 * @return
	 */
	private Boolean hasSimpleType(Object object)
	{
		Sequence sequence = null;
		ModelGroup obj = (ModelGroup)this.extractSequence((ComplexType)object);
		/*if(!(object instanceof Sequence))
		{
			sequence = this.extractSequence((ComplexType)object);
		}
		else{
			sequence = (Sequence)object;
		}*/
		List<Element> elements = (List<Element>) obj.getElements();
		for(Element element : elements)
		{
			if(this.isEmbeddedTypeSimpleType(element))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @Raed Saad
	 * This function is used to extract a sequence from a complex type
	 * @param complexType
	 * @return
	 */
	 private Object extractSequence(ComplexType complexType)
	 {
		 if(this.isComplexContent(complexType))
		 {
			 ComplexContent complextContent = (ComplexContent) complexType.getModel();
			 Extension extension = (Extension) complextContent.getDerivation();
			 return (Sequence) extension.getModel();
		 }
		return complexType.getModel();
	 }
	
	
	/*
	 * Check if xsd type is primative
	 * */
	private Boolean isNonPrimativeDataType(String fieldType)
	{
		return ("int".equals(fieldType) || "string".equals(fieldType) || "decimal".equals(fieldType) || "bigdecimal".equals(fieldType) || "dateTime".equals(fieldType) || "long".equals(fieldType) || "date".equals(fieldType) || "float".equals(fieldType) || "double".equals(fieldType) || "boolean".equals(fieldType));
	}
	
	/*
	 * Check if it is non primatative or hash or list
	 * */
	private Boolean isNonPrimativeDataTypeOrListORHash(String fieldType)
	{
		return (this.isNonPrimativeDataType(fieldType) || fieldType.contains("List<") || fieldType.contains("HashMap<"));
	}
	
	/*
	 * check if type is hash map
	 * */
	private Boolean isHashMap(String fieldType)
	{
		return fieldType.contains("HashMap<");
	}
	
	private Boolean hasExtension(ComplexContent complexContent)
	{
		return complexContent.getDerivation() instanceof Extension;		
	}
	
	private Boolean hashRestriction(ComplexContent complexContent)
	{
		return complexContent.getDerivation() instanceof Restriction;
	}
	
	private Boolean isSequence(ComplexType complexType)
	{
		return complexType.getModel() instanceof Sequence;
	}
	
	private Boolean isComplexContent(ComplexType complexType)
	{
		return complexType.getModel() instanceof ComplexContent;
	}
	
	private Boolean isEmbeddedTypeSimpleType(Element element)
	{
		return element.getEmbeddedType() instanceof SimpleType;
	}
	
	private Boolean isEmbeddedTypeComplexType(Element element)
	{
		return element.getEmbeddedType() instanceof ComplexType;
	}
	
	private String returnEmbeddedType(Element element)
	{
		String type = null;
		if(this.isEmbeddedTypeSimpleType(element))
		{
			type = WebServiceExplorerConstant.SIMPLE_TYPE;
		}
		else if(this.isEmbeddedTypeComplexType(element))
		{
			type = WebServiceExplorerConstant.COMPLEX_TYPE;
		}
		return type;		
	}
	
//	/**
//	 * @author RaedSaad
//	 * @Description lookup dependency 
//	 * @input code
//	 */
//	@Override
//	public WebServiceExplorerCO applicationNameLookUpDependency(WebServiceExplorerCO webServiceExplorerCO) throws BaseException
//	{
//		int code = Integer.parseInt(webServiceExplorerCO.getApplicationOperationCO().getCode());
//		webServiceExplorerCO = this.loadApplications(new WebServiceExplorerSC());
//		List<ApplicationOperationCO> applicationOperationLst = webServiceExplorerCO.getApplicationOperationLstCO();		
//		ApplicationOperationCO applicationOperationCO = new ApplicationOperationCO();
//		if(null != applicationOperationLst && code>0)
//		{
//			applicationOperationCO = applicationOperationLst.get(code-1);
//		}
//		else if(null != applicationOperationCO && code == 0)
//		{
//			applicationOperationCO = applicationOperationLst.get(0);
//		}
//		//webServiceExplorerCO.setCode(applicationOperationCO.getCode().toString());
//		webServiceExplorerCO.setApplicationNameCode(applicationOperationCO.getCode().toString());
//		webServiceExplorerCO.setApplicationName(applicationOperationCO.getApplicationName());
//		return webServiceExplorerCO;
//	}
	
//	/*
//	 * (non-Javadoc)
//	 * @see com.path.webserviceexplorer.bo.WebServiceGuiBO#webServiceNameLookUpDependency(com.path.imco.vo.webservicegui.WebServiceExplorerCO)
//	 * @author RaedSaad
//	 * @Description Webservice lookup dependency 
//	 * @input application name & code
//	 */
//	@Override
//	public WebServiceExplorerCO webServiceNameLookUpDependency(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
//		int code = Integer.parseInt(webServiceExplorerCO.getApplicationOperationCO().getCode());
//		WebServiceExplorerSC webServiceExplorerSC = new WebServiceExplorerSC();
//		webServiceExplorerSC.setApplicationName(webServiceExplorerCO.getApplicationName());
//		webServiceExplorerCO = this.loadApplicationWebService(webServiceExplorerSC);
//		List<ApplicationOperationCO> applicationOperationLst = webServiceExplorerCO.getApplicationOperationLstCO();	
//		ApplicationOperationCO applicationOperationCO = new ApplicationOperationCO();
//		if(null != applicationOperationLst && code>0 && code<=applicationOperationLst.size())
//		{
//			applicationOperationCO = applicationOperationLst.get(code-1);
//		}
//		else if(null != applicationOperationCO && code == 0)
//		{
//			applicationOperationCO = applicationOperationLst.get(0);
//		}
//		//webServiceExplorerCO.setCode(applicationOperationCO.getCode());
//		webServiceExplorerCO.setWebServiceNameCode(applicationOperationCO.getCode());
//		webServiceExplorerCO.setWebServiceName(applicationOperationCO.getWebServiceName());
//		return webServiceExplorerCO;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see com.path.webserviceexplorer.bo.WebServiceGuiBO#operationNameDependency(com.path.imco.vo.webservicegui.WebServiceExplorerCO)
//	 * @author RaedSaad
//	 * @Description load operation name based on application or webservice name
//	 *  
//	 */
//	@Override
//	public WebServiceExplorerCO operationNameDependency(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
//		int code = Integer.parseInt(webServiceExplorerCO.getApplicationOperationCO().getCode());
//		WebServiceExplorerSC webServiceExplorerSC = new WebServiceExplorerSC();
//		List<ApplicationOperationCO> applicationOperationLst = new ArrayList<ApplicationOperationCO>();
//		ApplicationOperationCO applicationOperationCO = new ApplicationOperationCO();
//		webServiceExplorerSC.setApplicationName(webServiceExplorerCO.getApplicationName());		
//		if(StringUtil.isNotEmpty(webServiceExplorerCO.getWebServiceName()))
//		{
//			webServiceExplorerSC.setWebServiceName(webServiceExplorerCO.getWebServiceName());
//			webServiceExplorerCO = this.loadWebServiceOperationNames(webServiceExplorerSC);
//			applicationOperationLst = webServiceExplorerCO.getApplicationOperationLstCO();
//			if(null != applicationOperationLst && code>0 && code<=applicationOperationLst.size())
//			{
//				applicationOperationCO = applicationOperationLst.get(code-1);
//			}
//			else if(null != applicationOperationCO && code == 0)
//			{
//				applicationOperationCO = applicationOperationLst.get(0);
//			}
//			//webServiceExplorerCO.setCode(applicationOperationCO.getCode());
//			webServiceExplorerCO.setOperationNameCode(applicationOperationCO.getCode());
//			webServiceExplorerCO.setOperationName(applicationOperationCO.getOperationName());
//		}
//		else{
//			throw new BOException("Missing webservice name");
//			//incase web service lookup is invisible
////			webServiceExplorerSC.setWebServiceName(webServiceExplorerCO.getWebServiceName());
////			webServiceExplorerCO = this.loadOperationNamesByApplication(webServiceExplorerSC);
////			applicationOperationLst = webServiceExplorerCO.getApplicationOperationLstCO();
////			if(null != applicationOperationLst && code>0 && code<=applicationOperationLst.size())
////			{
////				applicationOperationCO = applicationOperationLst.get(code-1);
////			}
////			else if(null != applicationOperationCO && code == 0)
////			{
////				applicationOperationCO = applicationOperationLst.get(0);
////			}
////			webServiceExplorerCO.setCode(applicationOperationCO.getCode());
////			webServiceExplorerCO.setOperationName(applicationOperationCO.getOperationName());	
//		}
//		return webServiceExplorerCO;
//	}	

//	/*
//	 * @ author Raed Saad
//	 * @ Description: This method is called on application list lookup, and will return the list of applications define in PathWebServiceGuiRemoting.properties
//	 * */
//	public WebServiceExplorerCO loadApplications(WebServiceExplorerSC webServiceExplorerSC) throws BaseException
//	{		
//		WebServiceExplorerCO webServiceExplorerCO = new WebServiceExplorerCO();
//		HashMap<String,String> appList = this.returnUniquePathPropertyListFromFile("PathWebServicesList.properties");
//		ApplicationOperationCO applicationOperationCO;
//		List<ApplicationOperationCO> applicationOperationLstCO = new ArrayList<ApplicationOperationCO>();
//		int index = 0;
//		for(Entry<String, String> entry:appList.entrySet())
//		{
//			applicationOperationCO = new ApplicationOperationCO();
//			applicationOperationCO.setCode(index+1+"");
//			applicationOperationCO.setApplicationName(entry.getKey());
//			applicationOperationCO.setApplicationUrl(entry.getValue());
//			index++;
//			applicationOperationLstCO.add(applicationOperationCO);
//		}
//		webServiceExplorerCO.setCount(index+1);
//		webServiceExplorerCO.setApplicationOperationLstCO(applicationOperationLstCO);
//		return webServiceExplorerCO;		
//	}
	
//	/*
//	 * @author RaedSaad
//	 * @Description
//	 * @Required Input ApplicationName 
//	 * */
//	public WebServiceExplorerCO loadApplicationWebService(WebServiceExplorerSC webServiceExplorerSC)throws BOException
//	{		
//		WebServiceExplorerCO webServiceExplorerCO = new WebServiceExplorerCO();
//		List<ApplicationOperationCO> applicationOperationLstCO = new ArrayList<ApplicationOperationCO>();
//		ApplicationOperationCO applicationOperationCO;
//		int index = 0;
//		String webServiceUrl = null;	
//		WSDLParser parser = null;
//		Definitions defs = null;
//		Schema schema = null;	
//		try{	
//			List<String> webServiceNames = this.returnWebServiceNames("PathWebServicesList.properties", webServiceExplorerSC.getApplicationName());
//			HashMap<String,String> applicationWebServices= this.returnApplicationWebServices("PathWebServicesList.properties", webServiceExplorerSC.getApplicationName());
//			for(String name : webServiceNames)
//			{
//				applicationOperationCO = new ApplicationOperationCO();
//				applicationOperationCO.setWebServiceName(name);
//				applicationOperationCO.setCode(index+1+"");
//				applicationOperationLstCO.add(applicationOperationCO);
//				index++;
//				webServiceUrl = applicationWebServices.get(name);
//				parser = new WSDLParser();	
//				defs = parser.parse(webServiceUrl);
//				schema = defs.getSchema(defs.getTargetNamespace());
//				for (PortType portType : defs.getPortTypes()) 
//				{
//					if(!name.equalsIgnoreCase(portType.getName()))
//					{
//						applicationOperationCO = new ApplicationOperationCO();
//						applicationOperationCO.setWebServiceName(portType.getName());
//						applicationOperationCO.setCode(index+1+"");
//						applicationOperationLstCO.add(applicationOperationCO);
//						index++;
//					}
//				}	
//			}
//			webServiceExplorerCO.setApplicationOperationLstCO(applicationOperationLstCO);
//		}
//		catch(Exception e)
//		{
//			throw new BOException(e);
//		}
//		return webServiceExplorerCO;		
//	}
	
//	/*
//	 * @author RaedSaad
//	 * @Description
//	 * @Required Input ApplicationName,WebServiceName
//	 */
//	public WebServiceExplorerCO loadWebServiceOperationNames(WebServiceExplorerSC webServiceExplorerSC) throws BaseException
//	{
//		WebServiceExplorerCO webServiceExplorerCO = new WebServiceExplorerCO();
//		//List<String> webServiceNames = this.returnWebServiceNames("PathWebServicesList.properties", webServiceExplorerSC.getApplicationName());
//		HashMap<String,String> applicationWebServices= this.returnApplicationWebServices("PathWebServicesList.properties", webServiceExplorerSC.getApplicationName());
//		List<ApplicationOperationCO> applicationOperationLstCO = new ArrayList<ApplicationOperationCO>();
//		ApplicationOperationCO applicationOperationCO;	
//	//	String webServiceUrl = applicationWebServices.get(webServiceExplorerSC.getWebServiceName().toLowerCase());	
//		String webServiceUrl = applicationWebServices.get(webServiceExplorerSC.getWebServiceName());		
//
//		WSDLParser parser = new WSDLParser();
//		try{
//			Definitions defs = parser.parse(webServiceUrl);
//			Schema schema = defs.getSchema(defs.getTargetNamespace());	
//	////		for (PortType portType : defs.getPortTypes()) 
//	////		{
//				PortType portType = null;
//				if(null != defs.getPortType(webServiceExplorerCO.getWebServiceName()))
//				{
//					portType = defs.getPortType(webServiceExplorerCO.getWebServiceName());
//				}
//				else
//				{
//					portType = defs.getPortTypes().get(0);
//				}
//				String operationName = portType.getName();
//				int index = 0;
//				for (Operation operation : portType.getOperations()) 
//				{
//					applicationOperationCO = new ApplicationOperationCO();
//					applicationOperationCO.setCode(index+1+"");
//					applicationOperationCO.setOperationName(operation.getName());				
//					applicationOperationLstCO.add(applicationOperationCO);
//					index++;
//				}
//	////		}
//				webServiceExplorerCO.setApplicationOperationLstCO(applicationOperationLstCO);
//				webServiceExplorerCO.setWsdlUrl(webServiceUrl);
//				webServiceExplorerCO.setNameSpaceUri(defs.getTargetNamespace());
//				webServiceExplorerCO.setNameSpacePrefix(defs.getTargetNamespacePrefix());
//		}
//		catch(Exception e)
//		{
//			throw new BOException("Unable to parse schema");
//		}
//		return webServiceExplorerCO;
//	}
	
//	/*
//	 * @author RaedSaad
//	 * @Description load OperationNames By Application incase each application is linked to one service
//	 * @Required Input ApplicationName
//	 */
//	public WebServiceExplorerCO loadOperationNamesByApplication(WebServiceExplorerSC webServiceExplorerSC) throws BaseException
//	{
//		WebServiceExplorerCO webServiceExplorerCO = new WebServiceExplorerCO();
//		List<ApplicationOperationCO> applicationOperationLstCO = new ArrayList<ApplicationOperationCO>();
//		ApplicationOperationCO applicationOperationCO;	
//		String webServiceUrl = null;
//		try{
//			 webServiceUrl = StringUtil.nullToEmpty(PathPropertyUtil.returnPathPropertyFromFile("PathWebServicesList.properties",webServiceExplorerSC.getApplicationName()));
//		}
//		catch(Exception e)
//		{
//			  throw new BaseException(e.getMessage(), e);
//		}
//		WSDLParser parser = new WSDLParser();
//		Definitions defs = parser.parse(webServiceUrl);
//		Schema schema = defs.getSchema(defs.getTargetNamespace());	
//////		for (PortType portType : defs.getPortTypes()) 
//////		{
//			PortType portType = null;
//			if(null != defs.getPortType(webServiceExplorerCO.getWebServiceName()))
//			{
//				portType = defs.getPortType(webServiceExplorerCO.getWebServiceName());
//			}
//			else
//			{
//				portType = defs.getPortTypes().get(0);
//			}			String operationName = portType.getName();
//			int index = 0;
//			for (Operation operation : portType.getOperations()) 
//			{
//				applicationOperationCO = new ApplicationOperationCO();
//				applicationOperationCO.setCode(index+1+"");
//				applicationOperationCO.setOperationName(operation.getName());				
//				applicationOperationLstCO.add(applicationOperationCO);
//				index++;
//			}
//////		}
//			webServiceExplorerCO.setApplicationOperationLstCO(applicationOperationLstCO);		
//		return webServiceExplorerCO;
//	}

	public HashMap<String, String> getParamType() {
		return paramType;
	}

	public void setParamType(HashMap<String, String> paramType) {
		this.paramType = paramType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @save data into IMCO_PWS_TEST_MASTER and IMCO_PWS_TEST_DETAILS tables
	 */
	@Override
	public void insertGridData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		webServiceExplorerCO.setWebServiceExplorerConfigVO(webServiceExplorerCO.getWebServiceExplorerConfigVO());
		webServiceExplorerCO.getUnformattedXmlRequest();
		webServiceExplorerCO.getXmlRequest();
		try {
			if(null!= webServiceExplorerCO.getXmlRequest() && StringUtil.isNotEmpty(webServiceExplorerCO.getXmlRequest()))
			{
				webServiceExplorerCO.getWebServiceExplorerConfigVO().setCONFIG_REQUEST(webServiceExplorerCO.getXmlRequest());
			}
			if(null != webServiceExplorerCO.getWebServiceExplorerGridUpdates() && StringUtil.isNotEmpty(webServiceExplorerCO.getWebServiceExplorerGridUpdates()))
			{
				webServiceExplorerCO.getWebServiceExplorerConfigVO().setJSON_REQUEST(webServiceExplorerCO.getWebServiceExplorerGridUpdates());
			}
			if(null != webServiceExplorerCO.getXmlResponse() && StringUtil.isNotEmpty(webServiceExplorerCO.getXmlResponse()))
			{
				//Response
			}
		} 
		catch (Exception e)
		{
			throw new BOException(e);
		}
		webServiceExplorerDAO.insertGridData(webServiceExplorerCO);
	}
	
	/**
	 * @description function to update data into IMCO_PWS_TEST_MASTER and IMCO_PWS_TEST_DETAILS tables
	 */
	
	public WebServiceExplorerCO updateGridData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		webServiceExplorerCO.setWebServiceExplorerConfigVO(webServiceExplorerCO.getWebServiceExplorerConfigVO());
		webServiceExplorerCO.getUnformattedXmlRequest();
		webServiceExplorerCO.getXmlRequest();
		try {
			if(null!= webServiceExplorerCO.getXmlRequest() && StringUtil.isNotEmpty(webServiceExplorerCO.getXmlRequest()))
			{
				webServiceExplorerCO.getWebServiceExplorerConfigVO().setCONFIG_REQUEST(webServiceExplorerCO.getXmlRequest());
			}
			if(null != webServiceExplorerCO.getWebServiceExplorerGridUpdates() && StringUtil.isNotEmpty(webServiceExplorerCO.getWebServiceExplorerGridUpdates()))
			{
				webServiceExplorerCO.getWebServiceExplorerConfigVO().setJSON_REQUEST(webServiceExplorerCO.getWebServiceExplorerGridUpdates());
			}
			if(null != webServiceExplorerCO.getXmlResponse() && StringUtil.isNotEmpty(webServiceExplorerCO.getXmlResponse()))
			{
				//Response
			}			
			webServiceExplorerDAO.deleteGridData(webServiceExplorerCO);
			webServiceExplorerDAO.upateGridData(webServiceExplorerCO);
			if(null != webServiceExplorerCO.getWebServiceExplorerConfigVO())
			{
				IMCO_PWS_TEST_MASTERVOWithBLOBs pwsExplorerConfigVO = webServiceExplorerCO.getWebServiceExplorerConfigVO();
				pwsExplorerConfigVO.setCONFIG_NAME(null);
				pwsExplorerConfigVO.setAPPLICATION_NAME(null);
				pwsExplorerConfigVO.setOPERATION_NAME(null);
				pwsExplorerConfigVO.setENDPOINT_NAME(null);
				genericDAO.update(pwsExplorerConfigVO);
			}
			webServiceExplorerCO.setMethod(null);
			webServiceExplorerCO = this.applySysParamOption(webServiceExplorerCO);
		} 
		catch (Exception e)
		{
			throw new BOException(e);
		}
		return webServiceExplorerCO;
	}
	
	/**
	 * @description return xml object
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public Document loadXML(String xml) throws Exception
	{
		DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
		DocumentBuilder bldr = fctr.newDocumentBuilder();
		InputSource insrc = new InputSource(new StringReader(xml));
		return bldr.parse(insrc);
	
	}
	
	/**
	 * @description function to load web service explorer saved data 
	 * @note list and hash map saved data will be loaded by using loadWebServiceExplorerListSavedData and loadWebServiceExplorerHashMapSavedData
	 */
	@Override
	public WebServiceExplorerCO loadWebServiceExplorerSavedData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		List<RequestResponseCO> lstReqResCO = webServiceExplorerCO.getLstRequestResposneCO();
		List<IMCO_PWS_TEST_DETAILSVO> webServiceExplorerDATAVOLst = null;
		Map<String,String> dataValues = new HashMap<String,String>();
		String fieldID = null;
		String fieldValue = null;
		String dest = null;
		List<String> retrievedFields = new ArrayList<String>();
		webServiceExplorerDATAVOLst = webServiceExplorerDAO.loadWebServiceExplorerSavedData(webServiceExplorerCO);
		for(IMCO_PWS_TEST_DETAILSVO vo : webServiceExplorerDATAVOLst)
		{
			fieldID = vo.getFIELD_ROW_ID();
			fieldValue = vo.getVALUE();
			dataValues.put(fieldID,fieldValue);
			retrievedFields.add(fieldID);
		}
		String fields = retrievedFields.toString();
		fieldID = null;
		for(RequestResponseCO reqResCO : lstReqResCO)
		{
			fieldID = reqResCO.getID();
			fieldValue = dataValues.get(fieldID);
			reqResCO.setValue(fieldValue);
			if(dataValues.containsKey(fieldID) && StringUtil.isEmptyString(fieldValue))
			{
				reqResCO.setLoadSubGrid(WebServiceExplorerConstant.WEB_SERVICE_EXPLORER_LOAD_SUB_GRID_BY_FORCE);
			}
		}
		return webServiceExplorerCO;
	}


	/**
	 * @description function to load web service explorer list saved data  
	 */
	@Override
	public WebServiceExplorerCO loadWebServiceExplorerListSavedData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
		String rowId = null;
		if(!this.isNonPrimativeDataType(webServiceExplorerCO.getRequestResponseCO().getFieldType()))
		{		
			//Case List Of Objects
			rowId = webServiceExplorerCO.getRequestResponseCO().getID().substring(0,webServiceExplorerCO.getRequestResponseCO().getID().length()-2) + "\\_%";
			webServiceExplorerCO.setRowId(rowId);
		}
		else{
			//Case List of String
			rowId = webServiceExplorerCO.getParentRowId() + "\\_%";
			webServiceExplorerCO.setRowId(rowId);
		}
		webServiceExplorerCO.getFieldType();
		webServiceExplorerCO.getRequestResponseCO().getFieldType();
		List<IMCO_PWS_TEST_DETAILSVO> webServiceExplorerDATAVOLst = webServiceExplorerDAO.loadWebServiceExplorerListSavedData(webServiceExplorerCO);
		Map<String,String> dataValues = new HashMap<String,String>();
		RequestResponseCO reqResCO = null;
		String fieldID = null;
		String fieldValue = null;
		for(IMCO_PWS_TEST_DETAILSVO vo : webServiceExplorerDATAVOLst)
		{
			reqResCO = new RequestResponseCO();
			fieldValue =vo.getVALUE();
			reqResCO.setID(vo.getFIELD_ROW_ID());
			reqResCO.setFieldType(vo.getFIELD_TYPE());
			reqResCO.setValue(fieldValue);
			if(StringUtil.isEmptyString(fieldValue) || "map".equals(vo.getFIELD_TYPE()))
			{
				reqResCO.setLoadSubGrid(WebServiceExplorerConstant.WEB_SERVICE_EXPLORER_LOAD_SUB_GRID_BY_FORCE);
			}
			lstReqResCO.add(reqResCO);
		}
		webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
		return webServiceExplorerCO;
	}

	/**
	 * @description function to load web service explorer saved hash map values stored in database
	 */
	@Override
	public WebServiceExplorerCO loadWebServiceExplorerHashMapSavedData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		List<RequestResponseCO> lstReqResCO = new ArrayList<RequestResponseCO>();
		RequestResponseCO reqResCO = null;
		String fieldType = webServiceExplorerCO.getRequestResponseCO().getFieldName();
		String rowId = fieldType + "_%";
		webServiceExplorerCO.setRowId(rowId);
		webServiceExplorerCO.getRequestResponseCO().setFieldType(fieldType);
		List<IMCO_PWS_TEST_DETAILSVO> webServiceExplorerDATAVOLst = webServiceExplorerDAO.loadWebServiceExplorerHashMapSavedData(webServiceExplorerCO);
		Map<String,String> dataValues = new HashMap<String,String>();
		String fieldID = null;
		String fieldName = null;
		String fieldValue = null;
		JSONObject jsonObject = null;
		for(IMCO_PWS_TEST_DETAILSVO vo : webServiceExplorerDATAVOLst)
		{
			reqResCO = new RequestResponseCO();
			fieldValue =vo.getVALUE();
			fieldValue = vo.getVALUE();
			jsonObject = (JSONObject) JSONSerializer.toJSON(fieldValue);
			for(Object key : jsonObject.keySet())
			{
				fieldID = key.toString();
				fieldName = key.toString();
				fieldValue = jsonObject.get(key).toString();
			}
			reqResCO.setID(fieldID);
			reqResCO.setFieldName(fieldName);
			reqResCO.setValue(fieldValue);
			lstReqResCO.add(reqResCO);
		}
		fieldID = null;
		webServiceExplorerCO.setLstRequestResposneCO(lstReqResCO);
		return webServiceExplorerCO;
	}	
	
	/**
	 * @description function triggered on config lookup dependecy
	 * @throws now record found exception
	 */
	@Override
	public WebServiceExplorerCO configLookupDependency(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		BigDecimal configId = webServiceExplorerCO.getWebServiceExplorerConfigVO().getCONFIG_ID();
		IMCO_PWS_TEST_MASTERVOWithBLOBs webServiceExplorerConfigVO = webServiceExplorerDAO.configLookupDependency(webServiceExplorerCO);
		if (null == webServiceExplorerConfigVO && !NumberUtil.isEmptyDecimal(configId)) {
			throw new BOException("Record with config id " + configId + " was not found");
		}
		webServiceExplorerCO.setWebServiceExplorerConfigVO(webServiceExplorerConfigVO);

		return webServiceExplorerCO;
	}
	
	/**
	 * @description function to apply visibility option on screen
	 */
	@Override
	public WebServiceExplorerCO applySysParamOption(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementHashmap = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
		String buttonIds [] = {
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_SHOW_WSDL_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_SHOW_REQUEST_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_PROCESS_REQUEST_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_SAVE_REQUEST_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_REQUEST_RESPOSNE_TAB_ID
		};
		String newRecordVisibleButtonIds [] = {
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_SHOW_WSDL_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_SHOW_REQUEST_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_SAVE_REQUEST_ID
		};
		if(null != webServiceExplorerCO && null == webServiceExplorerCO.getOnOfFlag())
		{
			webServiceExplorerCO.setOnOfFlag(WebServiceExplorerConstant.WEBSERVICE_EXPLORER_OF);
		}
		else if(null == webServiceExplorerCO)
		{
			webServiceExplorerCO = new WebServiceExplorerCO();
		}
		applyDynScreenDisplay(buttonIds,ConstantsCommon.ACTION_TYPE_READONLY,webServiceExplorerCO.getOnOfFlag(),elementHashmap, null);
		String upDateButtonId[] = {WebServiceExplorerConstant.WEBSERVICE_EXPLORER_UPDATE_REQUEST_ID};
		String saveButtonId[] = {WebServiceExplorerConstant.WEBSERVICE_EXPLORER_SAVE_REQUEST_ID};
		if(null != webServiceExplorerCO.getMethod())
		{
			applyDynScreenDisplay(saveButtonId,ConstantsCommon.ACTION_TYPE_READONLY,"1",elementHashmap, null);
			applyDynScreenDisplay(upDateButtonId,ConstantsCommon.ACTION_TYPE_READONLY,"0",elementHashmap, null);
		}
		else{
			applyDynScreenDisplay(upDateButtonId,ConstantsCommon.ACTION_TYPE_READONLY,"1",elementHashmap, null);
			applyDynScreenDisplay(newRecordVisibleButtonIds,ConstantsCommon.ACTION_TYPE_READONLY,"0",elementHashmap, null);

//			if(null == webServiceExplorerCO.getOnOfFlag())
//			{
//				applyDynScreenDisplay(saveButtonId,ConstantsCommon.ACTION_TYPE_READONLY,"0",elementHashmap, null);
//			}
		}
		webServiceExplorerCO.setHm(elementHashmap);
		this.applyCommonFieldsSysParamOption(webServiceExplorerCO);
		return webServiceExplorerCO;
	}
	
	/**
	 * @description common fields sys params
	 * @param webServiceExplorerCO
	 * @return
	 * @throws BaseException
	 */
	public WebServiceExplorerCO applyCommonFieldsSysParamOption(WebServiceExplorerCO webServiceExplorerCO)throws BaseException{
		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementHashmap;
		if(null == webServiceExplorerCO.getHm())
		{
			elementHashmap = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
		}
		else
		{
			elementHashmap = webServiceExplorerCO.getHm();
		}
		/*
		String buttonIds [] = {
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_CHANNLE_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_USER_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORE_HOST_NAME_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_HASH_KEY_ID
		};
		*/
	//	applyDynScreenDisplay(buttonIds,ConstantsCommon.ACTION_TYPE_MANDATORY,"1",elementHashmap, null);
		
		String readOnlyFieldsIds [] = {
				WebServiceExplorerConstant.WEBSERVICE_EXPLORE_HOST_NAME_ID,
				WebServiceExplorerConstant.WEBSERVICE_EXPLORER_HASH_KEY_ID
		};
		applyDynScreenDisplay(readOnlyFieldsIds,ConstantsCommon.ACTION_TYPE_READONLY,"1",elementHashmap, null);
		webServiceExplorerCO.setHm(elementHashmap);
		return webServiceExplorerCO;
		
	}
	
	/**
	 * @description function to check if a field is a common field as defined
	 * @param fieldName
	 * @return
	 */
	private Boolean isExcludedField(String fieldName)
	{
		if(null != excludedFields.get(fieldName.toLowerCase()) && StringUtil.isNotEmpty(excludedFields.get(fieldName.toLowerCase())))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @description function to convert an input string into an input stream
	 * @param str
	 * @return
	 */
	public InputStream stringToInputStream(String str)	
	{
		return new ByteArrayInputStream(str.getBytes(Charset.forName("UTF-8")));
	}

	public WebServiceExplorerDAO getWebServiceExplorerDAO() {
		return webServiceExplorerDAO;
	}

	public void setWebServiceExplorerDAO(WebServiceExplorerDAO webServiceExplorerDAO) {
		this.webServiceExplorerDAO = webServiceExplorerDAO;
	}

	public Map<String, String> getWebServiceExplorerSelectedGridRows() {
		return webServiceExplorerSelectedGridRows;
	}

	public void setWebServiceExplorerSelectedGridRows(Map<String, String> webServiceExplorerSelectedGridRows) {
		this.webServiceExplorerSelectedGridRows = webServiceExplorerSelectedGridRows;
	}

	public Map<String, Map<String, String>> getWebServiceExplorerSelectedHashMaps() {
		return webServiceExplorerSelectedHashMaps;
	}

	public void setWebServiceExplorerSelectedHashMaps(Map<String, Map<String, String>> webServiceExplorerSelectedHashMaps) {
		this.webServiceExplorerSelectedHashMaps = webServiceExplorerSelectedHashMaps;
	}

	public Map<String, RequestResponseCO> getWebServiceExplorerGridParamDataToSave() {
		return webServiceExplorerGridParamDataToSave;
	}

	public void setWebServiceExplorerGridParamDataToSave(
			Map<String, RequestResponseCO> webServiceExplorerGridParamDataToSave) {
		this.webServiceExplorerGridParamDataToSave = webServiceExplorerGridParamDataToSave;
	}

	@Override
	public BigDecimal returnNextServiceDataIdSequence(WebServiceExplorerCO webServiceExplorerCO)
			throws BaseException {
		return webServiceExplorerDAO.returnNextServiceDataIdSequence(webServiceExplorerCO);
	}

	@Override
	public BigDecimal returnNextCofigIdSequence(WebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		return webServiceExplorerDAO.returnNextCofigIdSequence(webServiceExplorerCO);
	}

	@Override
	public List<IMCO_PWS_TEST_MASTERVOWithBLOBs> loadWebServiceSavedConfiguration(WebServiceExplorerSC webServiceExplorerSC) throws BaseException {
		return webServiceExplorerDAO.loadWebServiceSavedConfiguration(webServiceExplorerSC);
	}

	@Override
	public int webServiceExplorerConfigCoun(WebServiceExplorerSC criteria) throws BaseException {
		return webServiceExplorerDAO.webServiceExplorerConfigCount(criteria);
	}

	@Override
	public List<WebServiceExplorerCO> loadWebServiceExplorerCommonFieldsLookUp(WebServiceExplorerSC criteria) throws BaseException {
		return webServiceExplorerDAO.loadWebServiceExplorerCommonFieldsLookUp(criteria);
	}

	public WebServiceCommonBO getWebServiceCommonBO() {
		return webServiceCommonBO;
	}

	public void setWebServiceCommonBO(WebServiceCommonBO webServiceCommonBO) {
		this.webServiceCommonBO = webServiceCommonBO;
	}
}
