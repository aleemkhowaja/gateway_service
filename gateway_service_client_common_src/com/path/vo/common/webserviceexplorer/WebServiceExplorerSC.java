package com.path.vo.common.webserviceexplorer;
/**
 * @Auther:Raed Saad
 * @Date:MARCH 2018
 * @Team: PEMTS JAVA Team.
 * @copyright: PathSolution 2018
 * @User Story: USER STORY #653853  WSDL explorer
 * @Description: Base Class to be extended
 **/

import java.math.BigDecimal;

import com.path.dbmaps.vo.IMCO_PWS_TEST_DETAILSVO;
import com.path.dbmaps.vo.IMCO_PWS_TEST_MASTERVO;
import com.path.struts2.lib.common.GridParamsSC;

public class WebServiceExplorerSC extends GridParamsSC{
	private int code;
	private String applicationName;
	private String applicationUrl;
	private String CONFIG_NAME;
	private String webServiceName;
	private String operationName;
	
	private IMCO_PWS_TEST_MASTERVO webServiceExplorerConfigVO;
	private IMCO_PWS_TEST_DETAILSVO  webServiceExplorerDatVOa;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getApplicationUrl() {
		return applicationUrl;
	}
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	public String getWebServiceName() {
		return webServiceName;
	}
	public void setWebServiceName(String webServiceName) {
		this.webServiceName = webServiceName;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public IMCO_PWS_TEST_MASTERVO getWebServiceExplorerConfigVO() {
		return webServiceExplorerConfigVO;
	}
	public void setWebServiceExplorerConfigVO(IMCO_PWS_TEST_MASTERVO webServiceExplorerConfigVO) {
		this.webServiceExplorerConfigVO = webServiceExplorerConfigVO;
	}
	public IMCO_PWS_TEST_DETAILSVO getWebServiceExplorerDatVOa() {
		return webServiceExplorerDatVOa;
	}
	public void setWebServiceExplorerDatVOa(IMCO_PWS_TEST_DETAILSVO webServiceExplorerDatVOa) {
		this.webServiceExplorerDatVOa = webServiceExplorerDatVOa;
	}
	public String getCONFIG_NAME() {
		return CONFIG_NAME;
	}
	public void setCONFIG_NAME(String cONFIG_NAME) {
		CONFIG_NAME = cONFIG_NAME;
	}

}
