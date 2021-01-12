package com.path.dao.common.webserviceexplorer.impl;

import java.math.BigDecimal;
import java.util.List;

import com.path.dao.common.webserviceexplorer.WebServiceExplorerDAO;
import com.path.dbmaps.vo.IMCO_PWS_TEST_DETAILSVO;
import com.path.dbmaps.vo.IMCO_PWS_TEST_MASTERVOWithBLOBs;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerCO;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerSC;

public class WebServiceExplorerDAOImpl extends BaseDAO implements WebServiceExplorerDAO{

	@Override
	public void insertGridData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		getSqlMap().insert("webServiceExplorerMapper.insertGridData", webServiceExplorerCO);
	}

	@Override
	public BigDecimal returnNextCofigIdSequence(WebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		return (BigDecimal) getSqlMap().queryForObject("webServiceExplorerMapper.returnNextCofigIdSequence",webServiceExplorerCO);
	}

	@Override
	public BigDecimal returnNextServiceDataIdSequence(WebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		return (BigDecimal) getSqlMap().queryForObject("webServiceExplorerMapper.returnNextServiceDataIdSequence",webServiceExplorerCO);
	}

	@Override
	public List<IMCO_PWS_TEST_MASTERVOWithBLOBs> loadWebServiceSavedConfiguration(WebServiceExplorerSC webServiceExplorerSC) throws DAOException {
		/*
		 * set number of rows to be displayed in the page of grid, and the total
		 * number of records
		 */
		if(com.path.lib.common.util.StringUtil.nullToEmpty(webServiceExplorerSC.getSidx()).isEmpty())
		{
		    webServiceExplorerSC.setSord("DESC");
		}
		DAOHelper.fixGridMaps(webServiceExplorerSC, getSqlMap(), "webServiceExplorerMapper.WebServiceExplorerConfigResultMap");
		if(webServiceExplorerSC.getNbRec() == -1)
		{
			return (List<IMCO_PWS_TEST_MASTERVOWithBLOBs>)getSqlMap().queryForList("webServiceExplorerMapper.loadWebServiceExplorerSavedConfiguration", webServiceExplorerSC);
		}		
		return (List<IMCO_PWS_TEST_MASTERVOWithBLOBs>)getSqlMap().queryForList("webServiceExplorerMapper.loadWebServiceExplorerSavedConfiguration", webServiceExplorerSC,webServiceExplorerSC.getRecToskip(), webServiceExplorerSC.getNbRec());
	}

	@Override
	public List<IMCO_PWS_TEST_DETAILSVO> loadWebServiceExplorerSavedData(WebServiceExplorerCO webServiceExplorerCO)throws DAOException {
		return (List<IMCO_PWS_TEST_DETAILSVO> )getSqlMap().queryForList("webServiceExplorerMapper.loadWebServiceExplorerSavedData", webServiceExplorerCO);
	}

	@Override
	public int webServiceExplorerConfigCount(WebServiceExplorerSC criteria) throws DAOException {
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("webServiceExplorerMapper.webServiceExplorerConfigCount", criteria)).intValue();
		return nb;
	}

	@Override
	public IMCO_PWS_TEST_MASTERVOWithBLOBs configLookupDependency(WebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		return (IMCO_PWS_TEST_MASTERVOWithBLOBs)getSqlMap().queryForObject("webServiceExplorerMapper.loadWebServiceExplorerSavedConfigurationById", webServiceExplorerCO);
	}

	@Override
	public List<IMCO_PWS_TEST_DETAILSVO> loadWebServiceExplorerListSavedData(
			WebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		webServiceExplorerCO.getParentRowId();
		return (List<IMCO_PWS_TEST_DETAILSVO> )getSqlMap().queryForList("webServiceExplorerMapper.loadWebServiceExplorerListSavedData", webServiceExplorerCO);
	}

	@Override
	public List<IMCO_PWS_TEST_DETAILSVO> loadWebServiceExplorerHashMapSavedData(
			WebServiceExplorerCO webServiceExplorerCO) throws DAOException {
		return (List<IMCO_PWS_TEST_DETAILSVO>)getSqlMap().queryForList("webServiceExplorerMapper.loadWebServiceExplorerHashMapSavedData", webServiceExplorerCO);

	}
	
	@Override
	public List<WebServiceExplorerCO> loadWebServiceExplorerCommonFieldsLookUp(WebServiceExplorerSC webServiceExplorerSC) throws DAOException {
		/*
		 * set number of rows to be displayed in the page of grid, and the total
		 * number of records
		 */
		if(com.path.lib.common.util.StringUtil.nullToEmpty(webServiceExplorerSC.getSidx()).isEmpty())
		{
		    webServiceExplorerSC.setSord("DESC");
		}
		DAOHelper.fixGridMaps(webServiceExplorerSC, getSqlMap(), "webServiceExplorerMapper.WebServiceExplorerCommonFieldsResultMap");
		if(webServiceExplorerSC.getNbRec() == -1)
		{
			return (List<WebServiceExplorerCO>)getSqlMap().queryForList("webServiceExplorerMapper.loadWebServiceExplorerCommonFieldsLookUp", webServiceExplorerSC);
		}		
		return (List<WebServiceExplorerCO>)getSqlMap().queryForList("webServiceExplorerMapper.loadWebServiceExplorerCommonFieldsLookUp", webServiceExplorerSC,webServiceExplorerSC.getRecToskip(), webServiceExplorerSC.getNbRec());
	}
	
	@Override
	public void deleteGridData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException 
	{
		getSqlMap().delete("webServiceExplorerMapper.deleteGridData", webServiceExplorerCO);
	}
	
	@Override
	public void upateGridData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException 
	{
		getSqlMap().insert("webServiceExplorerMapper.insertUpdatedGridData", webServiceExplorerCO);
	}
}
