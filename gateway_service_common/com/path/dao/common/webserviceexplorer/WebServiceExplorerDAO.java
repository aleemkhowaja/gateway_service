package com.path.dao.common.webserviceexplorer;
/**
 * @auther Raed Saad
 */
import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.IMCO_PWS_TEST_DETAILSVO;
import com.path.dbmaps.vo.IMCO_PWS_TEST_MASTERVOWithBLOBs;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerCO;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerSC;

public interface WebServiceExplorerDAO {
	public void insertGridData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException;
	public BigDecimal returnNextServiceDataIdSequence(WebServiceExplorerCO webServiceExplorerCO) throws DAOException;
	public BigDecimal returnNextCofigIdSequence(WebServiceExplorerCO webServiceExplorerCO) throws DAOException;
    public int webServiceExplorerConfigCount(WebServiceExplorerSC criteria)throws DAOException;
	public List<IMCO_PWS_TEST_MASTERVOWithBLOBs> loadWebServiceSavedConfiguration(WebServiceExplorerSC webServiceExplorerSC) throws DAOException; 
	public List<IMCO_PWS_TEST_DETAILSVO> loadWebServiceExplorerSavedData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException;
	public IMCO_PWS_TEST_MASTERVOWithBLOBs configLookupDependency(WebServiceExplorerCO webServiceExplorerCO)  throws DAOException;
	public List<IMCO_PWS_TEST_DETAILSVO> loadWebServiceExplorerListSavedData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException;
	public List<IMCO_PWS_TEST_DETAILSVO> loadWebServiceExplorerHashMapSavedData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException;
	public List<WebServiceExplorerCO> loadWebServiceExplorerCommonFieldsLookUp(WebServiceExplorerSC criteria) throws DAOException;
	public void upateGridData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException;
	public void deleteGridData(WebServiceExplorerCO webServiceExplorerCO) throws DAOException;
}
