/**
 *
 * Copyright 2019, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * USER STORY #799705 Control record - PWS mapping screen
 *
 */
package com.path.bo.common.pwsmapping.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.WebServiceCommonBO;
import com.path.bo.common.pwsmapping.CommonPwsMappingBO;
import com.path.dao.common.pwsmapping.CommonPwsMappingDAO;
import com.path.dbmaps.vo.COMMON_PWS_MAPPINGVO;
import com.path.dbmaps.vo.COMMON_PWS_MAPPING_DEFVO;
import com.path.dbmaps.vo.COMMON_PWS_MAPPING_DETAILSVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.vo.common.pwsmapping.CommonMappingArgCO;
import com.path.vo.common.pwsmapping.PwsDefinitionCO;
import com.path.vo.common.pwswebserviceexplorer.PwsWebServiceExplorerCO;

/**
 * 
 */
public class CommonPwsMappingBOImpl extends BaseBO implements CommonPwsMappingBO {
	
	/**
	 * Hold reference to
	 */
	private CommonPwsMappingDAO commonPwsDAO;
	private WebServiceCommonBO webServiceCommonBO;
	
	@Override
	public PwsDefinitionCO returnPwsInfo(PwsDefinitionCO pwsDef) throws BaseException {
		pwsDef = commonPwsDAO.returnPwsInfo(pwsDef);
		try {
		if(null != pwsDef.getMappingVO())
		{
			COMMON_PWS_MAPPINGVO mappingVO = pwsDef.getMappingVO();
			String appName = mappingVO.getSERVICE_APP_NAME();
			if(!appName.contains("."))
			{
				appName = appName + ".pws";
			}
			String appNameDesc = null;
			pwsDef.setAppName(appName);
			Map<String, String> propVals = new HashMap<String, String>();
			appNameDesc = PathPropertyUtil.returnPathPropertyFromFile("PathWebServicesList.properties", "app."+appName+".name");
			appName = appName.replace(".cpws", "").replace(".pws", "");
			pwsDef.setAppName(appName);
			pwsDef.setAppNameDesc(appNameDesc);			
		}
		}
		catch(Exception e)
		{
			throw new BOException(e);
		}
		return pwsDef;
	}

	@Override
	public List<CommonMappingArgCO> returnApiMappingArgs(PwsDefinitionCO pwsDefCO) throws BaseException {
		return commonPwsDAO.returnApiMappingArgs(pwsDefCO);
	}
	
	@Override
	public void savePws(PwsDefinitionCO pwsDef) throws BaseException {
		
		// generic delete the mapping
		if(pwsDef.getMappingVO().getMAPPING_ID() != null && !ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(pwsDef.getMappingVO().getMAPPING_ID() ))
			genericDAO.delete(pwsDef.getMappingVO());
		
		//@todo  this should be nullified
		if( pwsDef.getServiceType() != "pws"){
			pwsDef.getMappingVO().setWS_NAME("WS_"+pwsDef.getDefinitionVO().getMAPPING_ID());
			pwsDef.getMappingVO().setOPER_NAME("OP_"+pwsDef.getDefinitionVO().getMAPPING_ID());
			saveApiInfo(pwsDef);
		}	
		}
	
	
	/**
	 * This method save imal APi Info
	 * @param pwsDef
	 * @throws BaseException
	 */
	private void saveApiInfo(PwsDefinitionCO pwsDef ) throws BaseException {
		
		// set service type
		pwsDef.getApiDefinitionVO().setSERVICE_TYPE(pwsDef.getServiceType());
		switch(pwsDef.getServiceType()){
			case "R":
				pwsDef.getApiDefinitionVO().setSOAP_ACTION("");
				pwsDef.getApiDefinitionVO().setSOAP_MESSAGE_PREFIX("");
				pwsDef.getApiDefinitionVO().setSOAP_NAMESPACES("");
				pwsDef.getApiDefinitionVO().setSOAP_OPERATION("");
				pwsDef.getApiDefinitionVO().setSOAP_OPERATION_LOCAL_PART("");
				pwsDef.getApiDefinitionVO().setAPI_CODE(pwsDef.getMappingVO().getAPI_CODE());
				break;
			case "W":
				pwsDef.getApiDefinitionVO().setWS_ACCEPT_TYPE("");
				pwsDef.getApiDefinitionVO().setWS_CONTENT_TYPE("");
				pwsDef.getApiDefinitionVO().setWS_ENDPOINT("");
				
				pwsDef.getApiDefinitionVO().setAPI_CODE(pwsDef.getMappingVO().getAPI_CODE());
				break;
		}
		
		// if Api code exists update the mape 
		if(!NumberUtil.isEmptyDecimal(pwsDef.getMappingVO().getAPI_CODE())){
			
			pwsDef.getApiDefinitionVO().setAPI_CODE(
				pwsDef.getMappingVO().getAPI_CODE());
			genericDAO.update(pwsDef.getApiDefinitionVO());
			
		}
		else{
			this.insertApiDefinition(pwsDef);
		}
		// save APi Grid
		saveApiGrid(pwsDef);
	}
	
	
	/**
	 * This methos save Api Argument info
	 * @throws BaseException 
	 * 
	 */
	private void saveApiGrid( PwsDefinitionCO pwsDef ) throws BaseException
	{
		commonPwsDAO.deleteApiMappingArg(pwsDef);
		ArrayList<CommonMappingArgCO> listOfArgs = pwsDef.getArgGridUpdatesList();
		
		// no arguments to save
		if(listOfArgs == null || listOfArgs.size() == 0)
			return;
		
		int argId = 1;
		
		COMMON_PWS_MAPPINGVO commonPwsMappingVO;
		COMMON_PWS_MAPPING_DETAILSVO commonPwsMappingDetailsVO;
		List<COMMON_PWS_MAPPING_DETAILSVO> lstCommonPwsMappingDetailsVO = new ArrayList<COMMON_PWS_MAPPING_DETAILSVO>();
		PwsWebServiceExplorerCO pwsWebServiceExplorerCO = new PwsWebServiceExplorerCO();
		
		for(CommonMappingArgCO argument : listOfArgs){
			argument.getApiArgVO().setAPI_CODE(pwsDef.getApiDefinitionVO().getAPI_CODE());
			
			// insert api argument and it's mapping details
			argument.getApiArgVO().setARG_ID(BigDecimal.valueOf(argId));
			argument.getApiArgVO().setAPI_CODE(pwsDef.getApiCode());
			genericDAO.insert(argument.getApiArgVO());
			commonPwsMappingDetailsVO = new COMMON_PWS_MAPPING_DETAILSVO();
			commonPwsMappingDetailsVO.setDESTINATION_KEY(argument.getApiArgVO().getARG_NAME());
			
			// if Destination isn't null 
			if(!StringUtil.isEmptyString(StringUtil.nullToEmpty(commonPwsMappingDetailsVO.getDESTINATION_KEY()))){
				
				// @todo we may need to fix this in case of update
				// since we are overridding the main creator
				commonPwsMappingDetailsVO.setCREATED_DATE(pwsDef.getMappingVO().getCREATED_DATE());
				commonPwsMappingDetailsVO.setCREATED_BY(pwsDef.getMappingVO().getCREATED_BY());
				// populate main data
				commonPwsMappingDetailsVO.setMAPPING_ID(pwsDef.getMappingVO().getMAPPING_ID());
				commonPwsMappingDetailsVO.setWS_NAME(pwsDef.getMappingVO().getWS_NAME());
				commonPwsMappingDetailsVO.setOPER_NAME(pwsDef.getMappingVO().getOPER_NAME());
				commonPwsMappingDetailsVO.setARG_ID(BigDecimal.valueOf(argId));
				commonPwsMappingDetailsVO.setMAPPING_ARG_TYPE(argument.getApiArgVO().getSTATUS());
				commonPwsMappingDetailsVO.setMAPPING_DET_ID(loadSequenceNumber(pwsWebServiceExplorerCO));
				if(null == argument.getArgMap().getSOURCE_KEY() || StringUtil.isEmptyString(argument.getArgMap().getSOURCE_KEY() ))
				{
					commonPwsMappingDetailsVO.setSOURCE_KEY(argument.getApiArgVO().getARG_NAME());
				}
				else{
					commonPwsMappingDetailsVO.setSOURCE_KEY(argument.getArgMap().getSOURCE_KEY());
				}
				lstCommonPwsMappingDetailsVO.add(commonPwsMappingDetailsVO);
			}
			commonPwsMappingVO = pwsDef.getMappingVO();
			pwsWebServiceExplorerCO.setCommonPwsMappingVO(commonPwsMappingVO);
			pwsWebServiceExplorerCO.setLstCommonPwsMappingDetailsVO(lstCommonPwsMappingDetailsVO);
			pwsWebServiceExplorerCO.setRunningDate(pwsDef.getMappingVO().getCREATED_DATE());
			pwsWebServiceExplorerCO.setUserName(pwsDef.getMappingVO().getCREATED_BY());
			pwsWebServiceExplorerCO.getCommonPwsMappingVO().setAPI_CODE(pwsDef.getApiCode());
			this.saveCommonPWSMappingDetails(pwsWebServiceExplorerCO);
			// increment arg id
			argId++;
		}
	}
	
	public Object loadCommonPwsMapping(Object obj) throws BaseException{
		return commonPwsDAO.loadCommonPwsMapping(obj);
	}
	
	public CommonPwsMappingDAO getCommonPwsDAO() {
		return commonPwsDAO;
	}

	public void setCommonPwsDAO(CommonPwsMappingDAO commonPwsDAO) {
		this.commonPwsDAO = commonPwsDAO;
	}

	@Override
	public List<COMMON_PWS_MAPPING_DETAILSVO> loadCommonPWSMappingDetails(Object obj) throws BaseException 
	{
		return commonPwsDAO.loadCommonPWSMappingDetails(obj);
	}
	
	@Override
	public List<COMMON_PWS_MAPPING_DETAILSVO> loadCommonPWSMappingDetailsList() throws BaseException 
	{
		return null;
//		return commonPwsDAO.loadCommonPWSMappingDetailsList();
	}

	@Override
	public Object saveCommonPWSMappingDetails(PwsWebServiceExplorerCO pwsWebServiceExplorerCO) throws BaseException {
		BigDecimal mappingId = null;
		if(null != NumberUtil.emptyDecimalToNull( pwsWebServiceExplorerCO.getCommonPwsMappingVO().getMAPPING_ID()) )
		{
			mappingId = pwsWebServiceExplorerCO.getCommonPwsMappingVO().getMAPPING_ID();
			this.deleteCommonPWSMapping(pwsWebServiceExplorerCO);
			this.deleteCommonPWSMappingDetails(pwsWebServiceExplorerCO);
			COMMON_PWS_MAPPINGVO commonPwsMappingVO = pwsWebServiceExplorerCO.getCommonPwsMappingVO();	
			genericDAO.update(commonPwsMappingVO);
			COMMON_PWS_MAPPING_DEFVO commonPwsMappingDefVO = pwsWebServiceExplorerCO.getCommonPwsMappingDefVO();
			commonPwsMappingDefVO.setMAPPING_ID(pwsWebServiceExplorerCO.getCommonPwsMappingVO().getMAPPING_ID());
			genericDAO.update(commonPwsMappingDefVO);
		}
		else{
			mappingId = commonPwsDAO.getMaxMappingID();
			pwsWebServiceExplorerCO.getCommonPwsMappingVO().setMAPPING_ID(mappingId);
			List<COMMON_PWS_MAPPING_DETAILSVO> lst = pwsWebServiceExplorerCO.getLstCommonPwsMappingDetailsVO();
			for(COMMON_PWS_MAPPING_DETAILSVO obj : pwsWebServiceExplorerCO.getLstCommonPwsMappingDetailsVO())
			{
				obj.setMAPPING_ID(mappingId);
			}
			pwsWebServiceExplorerCO.getCommonPwsMappingDefVO().setMAPPING_ID(mappingId);
			commonPwsDAO.insertIntoCommonPWSMappingDef(pwsWebServiceExplorerCO);
//			this.deleteCommonPWSMapping(pwsWebServiceExplorerCO);
//			this.deleteCommonPWSMappingDetails(pwsWebServiceExplorerCO);
		}
		for(COMMON_PWS_MAPPING_DETAILSVO cpm : pwsWebServiceExplorerCO.getLstCommonPwsMappingDetailsVO())
		{
			cpm.setMAPPING_DET_ID(this.loadSequenceNumber(pwsWebServiceExplorerCO));
		}
		commonPwsDAO.saveCommonPWSMappingDetails(pwsWebServiceExplorerCO);		
		return mappingId;
	}
	
	@Override
	public void deleteCommonPWSMappingDetails(PwsWebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		commonPwsDAO.deleteCommonPWSMappingDetails(webServiceExplorerCO);		
	}

	@Override
	public void deleteCommonPWSMapping(PwsWebServiceExplorerCO webServiceExplorerCO) throws BaseException {
		commonPwsDAO.deleteCommonPWSMapping(webServiceExplorerCO);
	}

	@Override
	public BigDecimal loadSequenceNumber(PwsWebServiceExplorerCO pwsWebServiceExplorerCO) throws BaseException {
		return commonPwsDAO.loadSequenceNumber(pwsWebServiceExplorerCO);
	}

	@Override
	public void insertApiDefinition(PwsDefinitionCO pwsWebServiceExplorerCO) throws BaseException {
		commonPwsDAO.insertApiDefinition(pwsWebServiceExplorerCO);
	}

	public WebServiceCommonBO getWebServiceCommonBO() {
		return webServiceCommonBO;
	}

	public void setWebServiceCommonBO(WebServiceCommonBO webServiceCommonBO) {
		this.webServiceCommonBO = webServiceCommonBO;
	}

}
