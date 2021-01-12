package com.path.bo.common.webserviceexplorer;
/**
 * @Auther:Raed Saad
 * @Date:MARCH 2018
 * @Team: PEMTS JAVA Team.
 * @copyright: PathSolution 2018
 * @User Story: USER STORY #653853  WSDL explorer
 * @Description: BO Interface
 **/
import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.IMCO_PWS_TEST_MASTERVOWithBLOBs;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerCO;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerSC;

public interface WebServiceExplorerBO{
		
	public WebServiceExplorerCO loadFuncRespArg(WebServiceExplorerCO webServiceCO)throws BaseException;
	
	public WebServiceExplorerCO loadSimpleType(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;

	public void insertGridData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;
	
	public BigDecimal returnNextServiceDataIdSequence(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;

	public BigDecimal returnNextCofigIdSequence(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;

    public int webServiceExplorerConfigCoun(WebServiceExplorerSC criteria)throws BaseException;

	public List<IMCO_PWS_TEST_MASTERVOWithBLOBs> loadWebServiceSavedConfiguration(WebServiceExplorerSC webServiceExplorerSC) throws BaseException;
	
	public WebServiceExplorerCO loadWebServiceExplorerSavedData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;
	
	public WebServiceExplorerCO loadWebServiceExplorerListSavedData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;

	public WebServiceExplorerCO loadWebServiceExplorerHashMapSavedData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;

	public WebServiceExplorerCO configLookupDependency(WebServiceExplorerCO webServiceExplorerCO)  throws BaseException;
	
	public WebServiceExplorerCO applySysParamOption(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;
	
	public List<WebServiceExplorerCO> loadWebServiceExplorerCommonFieldsLookUp(WebServiceExplorerSC criteria) throws BaseException;

	public WebServiceExplorerCO updateGridData(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;
	
	public WebServiceExplorerCO loadBPELFuncRespArg(WebServiceExplorerCO webServiceExplorerCO) throws BaseException;

	public Object loadWsdlOperations(WebServiceExplorerCO webServiceExplorerCO) throws BaseException; 

}

