/**
 *
 * Copyright 2019, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * USER STORY #799705 Control record - PWS mapping screen
 *
 */
package com.path.bo.common.pwsmapping;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.COMMON_PWS_MAPPING_DETAILSVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.pwsmapping.CommonMappingArgCO;
import com.path.vo.common.pwsmapping.PwsDefinitionCO;
import com.path.vo.common.pwswebserviceexplorer.PwsWebServiceExplorerCO;

public interface CommonPwsMappingBO {
	/**
	 * Return the pws information
	 * @param pwsDef
	 * @return
	 * @throws BaseException
	 */
	public PwsDefinitionCO returnPwsInfo(PwsDefinitionCO pwsDef) throws BaseException;


	/**
	 * @param pwsDef
	 * @throws BaseException
	 */
	public void savePws(PwsDefinitionCO pwsDef) throws BaseException;


	/**
	 * @param pwsDefCO
	 * @return
	 * @throws BaseException
	 */
	public List<CommonMappingArgCO> returnApiMappingArgs(PwsDefinitionCO pwsDefCO) throws BaseException;

	/**
	 * USER STORY #799705	Control record - PWS mapping screen
	 * @param commonPwsMappingDetailsVO
	 * @return List<COMMON_PWS_MAPPING_DETAILSVO>
	 * @throws BOExceptionException
	 */
	public List<COMMON_PWS_MAPPING_DETAILSVO> loadCommonPWSMappingDetails(Object obj) throws BaseException;
	
	/**
	 * USER STORY #799705	Control record - PWS mapping screen
	 * @param commonPwsMappingDetailsVO
	 * @return List<COMMON_PWS_MAPPING_DETAILSVO>
	 * @throws BOExceptionException
	 */
	public List<COMMON_PWS_MAPPING_DETAILSVO> loadCommonPWSMappingDetailsList() throws BaseException;

	/**
	 * USER STORY #799705 Control record - PWS mapping screen
	 * @param webServiceExplorerCO
	 * @throws BaseException
	 */
	public Object saveCommonPWSMappingDetails(PwsWebServiceExplorerCO webServiceExplorerCO) throws BaseException;

	/**
	 * USER STORY #799705 Control record - PWS mapping screen
	 * @param webServiceExplorerCO
	 * @throws BaseException
	 */
	public void deleteCommonPWSMappingDetails(PwsWebServiceExplorerCO webServiceExplorerCO) throws BaseException;

	public void deleteCommonPWSMapping(PwsWebServiceExplorerCO webServiceExplorerCO) throws BaseException;

	public BigDecimal loadSequenceNumber(PwsWebServiceExplorerCO pwsWebServiceExplorerCO) throws BaseException;

	public void insertApiDefinition(PwsDefinitionCO pwsWebServiceExplorerCO) throws BaseException;
	
	public Object loadCommonPwsMapping(Object obj) throws BaseException;

}
