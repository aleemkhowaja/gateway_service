package com.path.dao.common.pwsmapping;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.COMMON_PWS_MAPPING_DETAILSVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.pwsmapping.CommonMappingArgCO;
import com.path.vo.common.pwsmapping.PwsDefinitionCO;
import com.path.vo.common.pwswebserviceexplorer.PwsWebServiceExplorerCO;

public interface CommonPwsMappingDAO {
	
	/**
	 * Query and return the information related to a PWS
	 * @param pwsDef
	 * @return
	 * @throws DAOException
	 */
	public PwsDefinitionCO returnPwsInfo(PwsDefinitionCO pwsDef) throws DAOException;

	/**
	 * @param pwsDefCO
	 * @return
	 */
	public List<CommonMappingArgCO> returnApiMappingArgs(PwsDefinitionCO pwsDefCO) throws DAOException;

	/**
	 * @param argMap
	 * @throws DAOException
	 */
	public void insertArgumentMapping(COMMON_PWS_MAPPING_DETAILSVO argMap) throws DAOException;

	/**
	 * Return max Api code above 500000
	 * @return
	 * @throws DAOException
	 */
	public BigDecimal getMaxApiCode() throws DAOException;
	
	/**
	 * @param pwsDef
	 * @throws DAOException
	 */
	public void deleteApiMappingArg(PwsDefinitionCO pwsDef) throws DAOException;

	/**
	 * USER STORY #799705	Control record - PWS mapping screen
	 * @param commonPwsMappingDetailsVO
	 * @return
	 * @throws DAOException
	 */
	public List<COMMON_PWS_MAPPING_DETAILSVO> loadCommonPWSMappingDetails(Object obj) throws DAOException;
	
	
	/**
	 * USER STORY #799705 Control record - PWS mapping screen
	 * @param webServiceExplorerCO
	 * @throws BaseException
	 */
	public void saveCommonPWSMappingDetails(PwsWebServiceExplorerCO webServiceExplorerCO) throws DAOException;

	public void deleteCommonPWSMapping(PwsWebServiceExplorerCO webServiceExplorerCO) throws DAOException;

	/**
	 * USER STORY #799705 Control record - PWS mapping screen
	 * @param webServiceExplorerCO
	 * @throws BaseException
	 */
	public void deleteCommonPWSMappingDetails(PwsWebServiceExplorerCO webServiceExplorerCO) throws DAOException;


	public BigDecimal loadSequenceNumber(PwsWebServiceExplorerCO pwsWebServiceExplorerCO) throws DAOException;
	
	public void insertApiDefinition(PwsDefinitionCO pwsWebServiceExplorerCO) throws DAOException;
	
	public Object loadCommonPwsMapping(Object obj) throws DAOException;
	
    public BigDecimal getMaxMappingID() throws DAOException;
    
    public void insertIntoCommonPWSMappingDef(Object obj) throws DAOException;

}
