package com.path.dao.common.pwsmapping.impl;
import java.math.BigDecimal;
import java.util.List;

import com.path.dao.common.pwsmapping.CommonPwsMappingDAO;
import com.path.dbmaps.vo.COMMON_PWS_MAPPING_DETAILSVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.pwsmapping.CommonMappingArgCO;
import com.path.vo.common.pwsmapping.PwsDefinitionCO;
import com.path.vo.common.pwswebserviceexplorer.PwsWebServiceExplorerCO;

/**
 * @author MohammadAliMezzawi
 *
 */
public class CommonPwsMappingDAOImpl extends BaseDAO implements CommonPwsMappingDAO {

	@Override
	public PwsDefinitionCO returnPwsInfo(PwsDefinitionCO pwsDef) throws DAOException {
		return (PwsDefinitionCO) getSqlMap().queryForObject("commonPwsMappingMapper.returnPwsInfo", pwsDef);
	}

	@Override
	public List<CommonMappingArgCO> returnApiMappingArgs(PwsDefinitionCO pwsDefCO) throws DAOException {
		return (List<CommonMappingArgCO>) getSqlMap().queryForList("commonPwsMappingMapper.returnApiMappingArgs", pwsDefCO);
	}

    /**
     * @return
     * @throws DAOException
     */
	@Override
    public BigDecimal getMaxApiCode() throws DAOException
    {
    	BigDecimal maxApiCode = ((BigDecimal) getSqlMap().
    		queryForObject("commonPwsMappingMapper.getMaxApiCode", null));
    	
    	//@todo replace with Constant
    	if(maxApiCode == null )
    		maxApiCode = BigDecimal.valueOf(600000);
    	
    	return maxApiCode;
    }
    
	@Override
    public BigDecimal getMaxMappingID() throws DAOException
    {
    	BigDecimal maxMappingId = ((BigDecimal) getSqlMap().
    		queryForObject("commonPwsMappingMapper.getMaxMappingID", null));
    	
    	//@todo replace with Constant
    	if(maxMappingId == null )
    		maxMappingId = BigDecimal.valueOf(300000);
    	
    	return maxMappingId;
    }
	
	@Override
	public void insertArgumentMapping(COMMON_PWS_MAPPING_DETAILSVO argMap) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteApiMappingArg(PwsDefinitionCO pwsDef) throws DAOException {
		getSqlMap().delete("commonPwsMappingMapper.deleteApiMappingArg", pwsDef);
	}

	/**
	 * USER STORY #799705 Control record - PWS mapping screen
	 * @param commonPwsMappingDetailsVO
	 * @return
	 * @throws DAOException
	 */
	@Override
	public List<COMMON_PWS_MAPPING_DETAILSVO> loadCommonPWSMappingDetails(Object obj) throws DAOException 
	{
		if(obj instanceof COMMON_PWS_MAPPING_DETAILSVO )
		{
			return (List<COMMON_PWS_MAPPING_DETAILSVO>) getSqlMap().queryForList("commonPwsMappingMapper.loadCommonPWSMappingDetails", obj);
		}
		else
		{
			return (List<COMMON_PWS_MAPPING_DETAILSVO>) getSqlMap().queryForList("commonPwsMappingMapper.loadCommonPWSMappingDetailsSub", obj);
		}
	}
	
	@Override
	public void saveCommonPWSMappingDetails(PwsWebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		getSqlMap().insert("commonPwsMappingMapper.saveCommonPWSMappingDetails", webServiceExplorerCO);
	}
	
	@Override
	public void deleteCommonPWSMappingDetails(PwsWebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		getSqlMap().delete("commonPwsMappingMapper.deleteCommonPWSMappingDetails", webServiceExplorerCO);	
	}

	@Override
	public void deleteCommonPWSMapping(PwsWebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		getSqlMap().delete("commonPwsMappingMapper.deleteCommonPWSMapping", webServiceExplorerCO);	
	}

	@Override
	public BigDecimal loadSequenceNumber(PwsWebServiceExplorerCO pwsWebServiceExplorerCO) throws DAOException
	{
    	BigDecimal seq = ((BigDecimal) getSqlMap().queryForObject("commonPwsMappingMapper.loadSequenceNumber", pwsWebServiceExplorerCO));
    	return seq;
	}

	@Override
	public void insertApiDefinition(PwsDefinitionCO pwsWebServiceExplorerCO) throws DAOException {
		getSqlMap().insert("commonPwsMappingMapper.insertApiDefinition", pwsWebServiceExplorerCO);
	}

	@Override
	public Object loadCommonPwsMapping(Object obj) throws DAOException{
		return getSqlMap().queryForList("commonPwsMappingMapper.loadCommonPwsMapping", obj);
	}

	@Override
	public void insertIntoCommonPWSMappingDef(Object obj) throws DAOException {
		getSqlMap().insert("commonPwsMappingMapper.insertIntoCommonPWSMappingDef", obj);
	}
}
