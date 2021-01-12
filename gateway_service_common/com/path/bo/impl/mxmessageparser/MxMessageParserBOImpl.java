package com.path.bo.impl.mxmessageparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts2.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.path.bo.mxmessageparser.MxMessageParserBO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.mxmessageparser.MxMessageParserCO;
import com.path.vo.mxmessageparser.MxMessageParserSC;
import com.path.vo.mxmessageparser.XsdParserCO;
import com.predic8.schema.Schema;

/**
 * 
 * Copyright 2020, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * @author: Alim Khowaja
 * MxMessageParserBOImpl.java used to
 */
public class MxMessageParserBOImpl extends BaseBO implements MxMessageParserBO {

	@Override
	public HashMap<String, Object> parseXmlAndPreparetags(MxMessageParserSC messageParserSC) throws BaseException 
	{
		HashMap<String , Object> leafNodesMap = new HashMap<String , Object>();
		/**
		 * 
		 * Parse Xml file and return the leaf node name and value
		 * with all its parents hierarchy
		 */
		parseXml(messageParserSC.getFile(), leafNodesMap);
		
		return leafNodesMap;
	}
	
	

	@Override
	public XsdParserCO parseXsdAndPrepareTags(XsdParserCO xsdParserCO) throws BaseException 
	{
		/**
    	 * Parse the Schema from file or from database 
    	 */
    	try 
    	{
			
        	/**
             * Prepare tags from Xsd to Grid
             */
            PrepareXsdTags.prepareXsdTags(xsdParserCO);
            
            /**
             * prepare Tags parent Child
             */
			PrepareXsdTags.prepareTreeTags(xsdParserCO);
			
		} catch (IOException | JSONException e) {
			log.error(e, "Error while parsing Tags from Xsd : " + e);
		}
    	
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * Parse Xml file
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	private void parseXml(File file, HashMap<String, Object> nodes) 
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try 
		{
			builder = factory.newDocumentBuilder();

			Document document = builder.parse(file);

			List<Node> leafNodes = new ArrayList<Node>();

			NodeList nList = document.getElementsByTagName("Document");

			/**
			 * call the recursion method and 
			 * read all the nodes parent and childs
			 * then add all leaf tag node inside in leafNodes list
			 */
			visitChildNodes(nList, leafNodes);
			
			for(Node n : leafNodes)
			{
				String hierarchy = "";
				
				/**
				 * get full hierarchy of leaf tag/node
				 */
				hierarchy = getParentHierarchy(hierarchy, n.getParentNode());
				
				
				/**
				 * above getParentHierarchy method returns the hierarchy child to parent
				 * like child_parent3_parent_2_parent_1
				 * But we need parent to child like 
				 * parent1_parent2_parent3_child
				 * 
				 * So let's split the hierarchy and re-arrange
				 * 
				 */
				String splittedParent[] = hierarchy.split("_");
				String finalHierarchy = "";
				for(int i=splittedParent.length-1; i>=0; i--)
				{
					finalHierarchy+=splittedParent[i];
					if(i != 0) finalHierarchy+="_";
				}
               
				finalHierarchy = finalHierarchy+"_"+n.getParentNode().getNodeName();
				
				/**
				 * prepare the CO and put the name and value 
				 * of the leaf node
				 */
				MxMessageParserCO parserCO = new MxMessageParserCO();
				parserCO.setName(n.getParentNode().getNodeName()) ;
				parserCO.setValue(n.getNodeValue());
				
				/**
				 * put the object 
				 */
				if(nodes.containsKey(finalHierarchy))
				{
					ArrayList<MxMessageParserCO> list = new ArrayList<MxMessageParserCO>();
					
					if(nodes.get(finalHierarchy) instanceof MxMessageParserCO)
					{
						MxMessageParserCO co = (MxMessageParserCO) nodes.get(finalHierarchy);
						list.add(co);
					}
					else 
						if(nodes.get(finalHierarchy) instanceof java.util.ArrayList)
						{
							list = (ArrayList<MxMessageParserCO>) nodes.get(finalHierarchy);
							list.add(parserCO);
						}
					
					nodes.put(finalHierarchy, list);
					continue;
				}
				
				nodes.put(finalHierarchy, parserCO);
			}
			

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * visit children of parent nodes
	 * @param nList
	 * @param leafNodes
	 */
	private void visitChildNodes(NodeList nList, List<Node> leafNodes) 
	{
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);

			/**
			 * If the node is value /leaf
			 * then add that node in list
			 */
			if (node.getNodeType() == Node.TEXT_NODE) {
				if (node.getNodeValue() != null && !node.getNodeValue().trim().equals("")) {
					leafNodes.add(node);
				}
			}
			/**
			 * If the node is still parent 
			 * then call the recursion method read its children
			 */
			else if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (node.hasChildNodes()) {
					// We got more childs; Let's visit them as well
					visitChildNodes(node.getChildNodes(), leafNodes);
				}
			}
		}
	}
	
	
	/**
	 * get all parent Hierarchy of the leaf node
	 * @param hierarchy
	 * @param childNode
	 * @return
	 */
	public String getParentHierarchy(String hierarchy, Node childNode)
	{
		if(null != childNode.getParentNode()  
				&& !childNode.getParentNode().getNodeName().toLowerCase().contains("document"))
		{
			hierarchy+=childNode.getParentNode().getNodeName()+"_";
			hierarchy = getParentHierarchy(hierarchy, childNode.getParentNode());
		}
		return hierarchy;
	}

}
