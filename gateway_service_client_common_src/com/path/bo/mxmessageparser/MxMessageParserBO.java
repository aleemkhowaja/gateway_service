package com.path.bo.mxmessageparser;

import java.util.HashMap;

import com.path.lib.common.exception.BaseException;
import com.path.vo.mxmessageparser.MxMessageParserSC;
import com.path.vo.mxmessageparser.XsdParserCO;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * CustomExpressionBO.java used to
 */
public interface MxMessageParserBO 
{

	/**
	 * parse xml and prepare tags
	 * @param messageParserSC
	 * @return
	 * @throws BaseException
	 */
	public HashMap<String, Object> parseXmlAndPreparetags(MxMessageParserSC  messageParserSC) throws BaseException;
	
	/**
	 * parse Xsd and prepare the tags
	 * @param mxMessageDefinitionCO
	 * @return
	 * @throws BaseException
	 */
	public XsdParserCO parseXsdAndPrepareTags(XsdParserCO xsdParserCO) throws BaseException;

}