package com.path.vo.common.webserviceexplorer;
/**
 * @Auther:Raed Saad
 * @Date:MARCH 2018
 * @Team: PEMTS JAVA Team.
 * @copyright: PathSolution 2018
 * @User Story: USER STORY #653853  WSDL explorer
 * @Description: Base Class WebServiceExplorerCO 
 * @note: to be extended
 **/

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.path.dbmaps.vo.COMMON_PWS_MAPPING_DETAILSVO;
import com.path.dbmaps.vo.DGTL_GTW_ADAPTER_PARAM_MAPVO;
import com.path.dbmaps.vo.DGTL_GTW_WS_ADAPTERVO;
import com.path.dbmaps.vo.IMCO_PWS_CHANNELVO;
import com.path.dbmaps.vo.IMCO_PWS_CHANNEL_DETVO;
import com.path.dbmaps.vo.IMCO_PWS_TEST_DETAILSVO;
import com.path.dbmaps.vo.IMCO_PWS_TEST_MASTERVOWithBLOBs;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;
import com.path.vo.common.select.SelectCO;

public class WebServiceExplorerCO extends BaseVO implements Serializable {
	private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> hm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	private String channelId;
	private String userID;
	private String hashKey;
	private String hostName;
	private String langId;
	private String password;
	private String requesterTimeStamp;
	
	//private String code;
	private String applicationNameCode;
	private String applicationName;
	private String applicationUrl;
	private int count;
	private String webServiceNameCode;
	private String webServiceName;
	private String endPointName;
	private String operationNameCode;
	private String operationName;
	private String operationType;
	private String functionName;
	
	private String responseName;
	private String requestName;
	
	private List<SelectCO> appTypeLst;
	private List<SelectCO> lstSelectCO;
	
	private RequestResponseCO requestResponseCO;
	private List<RequestResponseCO> lstRequestResposneCO;
	
	private String selectedFieldType;
	private String parameterName;
	private String parameterType;
	private String requestType;
	
	private String rowId;
	private String hasRestrictions;
	private String hasChildren;
	private String fieldType;
	
	private String webServiceExplorerGridUpdates;
	private String webServiceExplorerSubGridUpdates;
	private String webServiceExplorerHashGridListUpdates;
	
	private String webServiceExplorerResponseGridUpdates;
	
	private List<RequestResponseCO> modifiedGridRows;
	private String unformattedXmlRequest;
	private String xmlRequest;
	private String xmlResponse;
	
	private String wsdlUrl;
	private String portTypePrefix;
	private String soapAction;
	
	private String nameSpaceUri;
	private String nameSpacePrefix;	
	
	private Map<String,List<RequestResponseCO>> objMap; //subGrids
	private Map<String,HashMap<String,String>> hashMapObjectMap; //hashMap SubGrids
	private List<String> mappingParams;
	
	private String mappingFields;	
	
	private String mainGridId;
	private String dynamicGridId;	
	private String parentGridId;
	private String parentRowId;
	private String gridColumnIDField;
	
	private IMCO_PWS_TEST_MASTERVOWithBLOBs webServiceExplorerConfigVO;
	private IMCO_PWS_TEST_DETAILSVO  webServiceExplorerDataVO;
	private List<IMCO_PWS_TEST_DETAILSVO>webServiceExplorerDataVOLst;
	private IMCO_PWS_CHANNEL_DETVO imcoPwsChannelDetVO;
	private IMCO_PWS_CHANNELVO imcoPwsChannelVO;
	
	private List<String> rowIds;
	private String sequenceName;
	private BigDecimal configSequence;
	private BigDecimal serviceDataSequence;
	
	private Boolean loadConfig;
	
	private Map<String,RequestResponseCO> gridRows;
		
	private String strServiceId;
	
	private String embeddedTypeEnumParent;
	
	private String onOfFlag;
	
	private String authUserID;
	
	private String authPassword;
	
	private String method;
	
	private String pageRef;
	
	private String wsdl;
	
	private String screenName;
	
	private String isFromBpelScreen;
	
	private String bpelFullPath;
	
	private String bpelFileName;
	
	private List<String> excludedParams;
	
	private List<COMMON_PWS_MAPPING_DETAILSVO> lstCommonPwsMappingDetailsVO;
	
	private BigDecimal mappingID;
	
	private String isFromSoapScreen;
	
	private String adapterType;

	private String tempWsdlRep;
	
	public Map<String,Object> wsdls;
	
	public String mainWsdl;
	public String mainWsdlName;
	private String mainWsdlFullPath;
	
	public Map<String,String> zipData;
	public Map<String,String> zipDataEntry;
	public Map<String,String> zipDataAsString;
	
	public String serviceName;
	public List<String> operations;
	
	private String inputwsdlUrl;
	
	private DGTL_GTW_WS_ADAPTERVO dgtlAdapterVO;

	private List<DGTL_GTW_ADAPTER_PARAM_MAPVO> combinedReqResp;
	
	private String reqType;
	private String mappingGridUrl;
	private String filter;
	private String description;
	private String mappingFieldsUrl;
	
	private String parentListFieldName;
		
	
	public String getMappingGridUrl() {
		return mappingGridUrl;
	}
	public void setMappingGridUrl(String mappingGridUrl) {
		this.mappingGridUrl = mappingGridUrl;
	}
	public String getResponseName() {
		return responseName;
	}
	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public String getEndPointName() {
		return endPointName;
	}
	public void setEndPointName(String endPointName) {
		this.endPointName = endPointName;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getHm() {
		return hm;
	}
	public void setHm(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> hm) {
		this.hm = hm;
	}
	public List<SelectCO> getLstSelectCO() {
		return lstSelectCO;
	}
	public void setLstSelectCO(List<SelectCO> lstSelectCO) {
		this.lstSelectCO = lstSelectCO;
	}
	public List<RequestResponseCO> getLstRequestResposneCO() {
		return lstRequestResposneCO;
	}
	public void setLstRequestResposneCO(List<RequestResponseCO> lstRequestResposneCO) {
		this.lstRequestResposneCO = lstRequestResposneCO;
	}
	public List<SelectCO> getAppNameLst() {
		return appTypeLst;
	}
	public void setAppNameLst(List<SelectCO> appNameLst) {
		this.appTypeLst = appNameLst;
	}
	public String getSelectedFieldType() {
		return selectedFieldType;
	}
	public void setSelectedFieldType(String selectedFieldType) {
		this.selectedFieldType = selectedFieldType;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public List<SelectCO> getAppTypeLst() {
		return appTypeLst;
	}
	public void setAppTypeLst(List<SelectCO> appTypeLst) {
		this.appTypeLst = appTypeLst;
	}

	public String getApplicationUrl() {
		return applicationUrl;
	}
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getWebServiceName() {
		return webServiceName;
	}
	public void setWebServiceName(String webServiceName) {
		this.webServiceName = webServiceName;
	}	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public RequestResponseCO getRequestResponseCO() {
		return requestResponseCO;
	}
	public void setRequestResponseCO(RequestResponseCO requestResponseCO) {
		this.requestResponseCO = requestResponseCO;
	}

	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	public String getWebServiceNameCode() {
		return webServiceNameCode;
	}
	public void setWebServiceNameCode(String webServiceNameCode) {
		this.webServiceNameCode = webServiceNameCode;
	}
	public String getOperationNameCode() {
		return operationNameCode;
	}
	public void setOperationNameCode(String operationNameCode) {
		this.operationNameCode = operationNameCode;
	}
	public String getApplicationNameCode() {
		return applicationNameCode;
	}
	public void setApplicationNameCode(String applicationNameCode) {
		this.applicationNameCode = applicationNameCode;
	}
	public List<RequestResponseCO> getModifiedGridRows() {
		return modifiedGridRows;
	}
	public void setModifiedGridRows(List<RequestResponseCO> modifiedGridRows) {
		this.modifiedGridRows = modifiedGridRows;
	}
	public String getHasRestrictions() {
		return hasRestrictions;
	}
	public void setHasRestrictions(String hasRestrictions) {
		this.hasRestrictions = hasRestrictions;
	}
	public String getXmlResponse() {
		return xmlResponse;
	}
	public void setXmlResponse(String xmlResponse) {
		this.xmlResponse = xmlResponse;
	}

	public String getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(String hasChildren) {
		this.hasChildren = hasChildren;
	}
	public String getXmlRequest() {
		return xmlRequest;
	}
	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}
	public String getPortTypePrefix() {
		return portTypePrefix;
	}
	public void setPortTypePrefix(String portTypePrefix) {
		this.portTypePrefix = portTypePrefix;
	}
	public String getWsdlUrl() {
		return wsdlUrl;
	}
	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}
	public String getSoapAction() {
		return soapAction;
	}
	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}
	public String getNameSpaceUri() {
		return nameSpaceUri;
	}
	public void setNameSpaceUri(String nameSpaceUri) {
		this.nameSpaceUri = nameSpaceUri;
	}
	public String getNameSpacePrefix() {
		return nameSpacePrefix;
	}
	public void setNameSpacePrefix(String nameSpacePrefix) {
		this.nameSpacePrefix = nameSpacePrefix;
	}
	public Map<String, List<RequestResponseCO>> getObjMap() {
		return objMap;
	}
	public void setObjMap(Map<String, List<RequestResponseCO>> objMap) {
		this.objMap = objMap;
	}
	public String getWebServiceExplorerGridUpdates() {
		return webServiceExplorerGridUpdates;
	}
	public void setWebServiceExplorerGridUpdates(String webServiceExplorerGridUpdates) {
		this.webServiceExplorerGridUpdates = webServiceExplorerGridUpdates;
	}
	public String getWebServiceExplorerSubGridUpdates() {
		return webServiceExplorerSubGridUpdates;
	}
	public void setWebServiceExplorerSubGridUpdates(String webServiceExplorerSubGridUpdates) {
		this.webServiceExplorerSubGridUpdates = webServiceExplorerSubGridUpdates;
	}
	public List<String> getMappingParams() {
		return mappingParams;
	}
	public void setMappingParams(List<String> mappingParams) {
		this.mappingParams = mappingParams;
	}
	public String getMappingFields() {
		return mappingFields;
	}
	public void setMappingFields(String mappingJsonString) {
		this.mappingFields = mappingJsonString;
	}
	public String getWebServiceExplorerHashGridListUpdates() {
		return webServiceExplorerHashGridListUpdates;
	}
	public void setWebServiceExplorerHashGridListUpdates(String webServiceExplorerHashGridListUpdates) {
		this.webServiceExplorerHashGridListUpdates = webServiceExplorerHashGridListUpdates;
	}
	public Map<String, HashMap<String, String>> getHashMapObjectMap() {
		return hashMapObjectMap;
	}
	public void setHashMapObjectMap(Map<String, HashMap<String, String>> hashMapObjectMap) {
		this.hashMapObjectMap = hashMapObjectMap;
	}
	public String getParentRowId() {
		return parentRowId;
	}
	public void setParentRowId(String parentRowId) {
		this.parentRowId = parentRowId;
	}
	public String getGridColumnIDField() {
		return gridColumnIDField;
	}
	public void setGridColumnIDField(String gridColumnIDField) {
		this.gridColumnIDField = gridColumnIDField;
	}
	public String getMainGridId() {
		return mainGridId;
	}
	public void setMainGridId(String mainGridId) {
		this.mainGridId = mainGridId;
	}
	public String getDynamicGridId() {
		return dynamicGridId;
	}
	public void setDynamicGridId(String dynamicGridId) {
		this.dynamicGridId = dynamicGridId;
	}
	public Map<String, RequestResponseCO> getGridRows() {
		return gridRows;
	}
	public void setGridRows(Map<String, RequestResponseCO> gridRows) {
		this.gridRows = gridRows;
	}

	public BigDecimal getConfigSequence() {
		return configSequence;
	}
	public void setConfigSequence(BigDecimal configSequence) {
		this.configSequence = configSequence;
	}
	public BigDecimal getServiceDataSequence() {
		return serviceDataSequence;
	}
	public void setServiceDataSequence(BigDecimal serviceDataSequence) {
		this.serviceDataSequence = serviceDataSequence;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public List<String> getRowIds() {
		return rowIds;
	}
	public void setRowIds(List<String> rowIds) {
		this.rowIds = rowIds;
	}
	public Boolean getLoadConfig() {
		return loadConfig;
	}
	public void setLoadConfig(Boolean loadConfig) {
		this.loadConfig = loadConfig;
	}
	public IMCO_PWS_TEST_MASTERVOWithBLOBs getWebServiceExplorerConfigVO() {
		return webServiceExplorerConfigVO;
	}
	public void setWebServiceExplorerConfigVO(IMCO_PWS_TEST_MASTERVOWithBLOBs webServiceExplorerConfigVO) {
		this.webServiceExplorerConfigVO = webServiceExplorerConfigVO;
	}
	public IMCO_PWS_TEST_DETAILSVO getWebServiceExplorerDataVO() {
		return webServiceExplorerDataVO;
	}
	public void setWebServiceExplorerDataVO(IMCO_PWS_TEST_DETAILSVO webServiceExplorerDataVO) {
		this.webServiceExplorerDataVO = webServiceExplorerDataVO;
	}
	public List<IMCO_PWS_TEST_DETAILSVO> getWebServiceExplorerDataVOLst() {
		return webServiceExplorerDataVOLst;
	}
	public void setWebServiceExplorerDataVOLst(List<IMCO_PWS_TEST_DETAILSVO> webServiceExplorerDataVOLst) {
		this.webServiceExplorerDataVOLst = webServiceExplorerDataVOLst;
	}
	public String getUnformattedXmlRequest() {
		return unformattedXmlRequest;
	}
	public void setUnformattedXmlRequest(String unformattedXmlRequest) {
		this.unformattedXmlRequest = unformattedXmlRequest;
	}
	public String getStrServiceId() {
		return strServiceId;
	}
	public void setStrServiceId(String strServiceId) {
		this.strServiceId = strServiceId;
	}
	public String getEmbeddedTypeEnumParent() {
		return embeddedTypeEnumParent;
	}
	public void setEmbeddedTypeEnumParent(String embeddedTypeEnumParent) {
		this.embeddedTypeEnumParent = embeddedTypeEnumParent;
	}
	public String getOnOfFlag() {
		return onOfFlag;
	}
	public void setOnOfFlag(String onOfFlag) {
		this.onOfFlag = onOfFlag;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getHashKey() {
		return hashKey;
	}
	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getLangId() {
		return langId;
	}
	public void setLangId(String langId) {
		this.langId = langId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRequesterTimeStamp() {
		return requesterTimeStamp;
	}
	public void setRequesterTimeStamp(String requesterTimeStamp) {
		this.requesterTimeStamp = requesterTimeStamp;
	}
	public String getAuthUserID() {
		return authUserID;
	}
	public void setAuthUserID(String authUserID) {
		this.authUserID = authUserID;
	}
	public String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	public IMCO_PWS_CHANNEL_DETVO getImcoPwsChannelDetVO() {
		return imcoPwsChannelDetVO;
	}
	public void setImcoPwsChannelDetVO(IMCO_PWS_CHANNEL_DETVO imcoPwsChannelDetVO) {
		this.imcoPwsChannelDetVO = imcoPwsChannelDetVO;
	}
	public IMCO_PWS_CHANNELVO getImcoPwsChannelVO() {
		return imcoPwsChannelVO;
	}
	public void setImcoPwsChannelVO(IMCO_PWS_CHANNELVO imcoPwsChannelVO) {
		this.imcoPwsChannelVO = imcoPwsChannelVO;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPageRef() {
		return pageRef;
	}
	public void setPageRef(String pageRef) {
		this.pageRef = pageRef;
	}
	public String getWsdl() {
		return wsdl;
	}
	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}
	public String getIsFromBpelScreen() {
		return isFromBpelScreen;
	}
	public void setIsFromBpelScreen(String isFromBpelScreen) {
		this.isFromBpelScreen = isFromBpelScreen;
	}
	public String getBpelFileName() {
		return bpelFileName;
	}
	public void setBpelFileName(String bpelFileName) {
		this.bpelFileName = bpelFileName;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public List<COMMON_PWS_MAPPING_DETAILSVO> getLstCommonPwsMappingDetailsVO() {
		return lstCommonPwsMappingDetailsVO;
	}
	public void setLstCommonPwsMappingDetailsVO(List<COMMON_PWS_MAPPING_DETAILSVO> lstCommonPwsMappingDetailsVO) {
		this.lstCommonPwsMappingDetailsVO = lstCommonPwsMappingDetailsVO;
	}
	public String getWebServiceExplorerResponseGridUpdates() {
		return webServiceExplorerResponseGridUpdates;
	}
	public void setWebServiceExplorerResponseGridUpdates(String webServiceExplorerResponseGridUpdates) {
		this.webServiceExplorerResponseGridUpdates = webServiceExplorerResponseGridUpdates;
	}
	public List<String> getExcludedParams() {
		return excludedParams;
	}
	public void setExcludedParams(List<String> excludeParams) {
		this.excludedParams = excludeParams;
	}
	public BigDecimal getMappingID() {
		return mappingID;
	}
	public void setMappingID(BigDecimal mappingID) {
		this.mappingID = mappingID;
	}
	public String getIsFromSoapScreen() {
		return isFromSoapScreen;
	}
	public void setIsFromSoapScreen(String isFromSoapScreen) {
		this.isFromSoapScreen = isFromSoapScreen;
	}
	public String getBpelFullPath() {
		return bpelFullPath;
	}
	public void setBpelFullPath(String bpelFullPath) {
		this.bpelFullPath = bpelFullPath;
	}
	public String getAdapterType() {
		return adapterType;
	}
	public void setAdapterType(String adapterType) {
		this.adapterType = adapterType;
	}
	public String getTempWsdlRep() {
		return tempWsdlRep;
	}
	public void setTempWsdlRep(String tempWsdlRep) {
		this.tempWsdlRep = tempWsdlRep;
	}
	public Map<String, Object> getWsdls() {
		return wsdls;
	}
	public void setWsdls(Map<String, Object> wsdls) {
		this.wsdls = wsdls;
	}
	public String getMainWsdl() {
		return mainWsdl;
	}
	public void setMainWsdl(String mainWsdl) {
		this.mainWsdl = mainWsdl;
	}
	public Map<String, String> getZipData() {
		return zipData;
	}
	public void setZipData(Map<String, String> zipData) {
		this.zipData = zipData;
	}
	public Map<String, String> getZipDataEntry() {
		return zipDataEntry;
	}
	public void setZipDataEntry(Map<String, String> zipDataEntry) {
		this.zipDataEntry = zipDataEntry;
	}
	public Map<String, String> getZipDataAsString() {
		return zipDataAsString;
	}
	public void setZipDataAsString(Map<String, String> zipDataAsString) {
		this.zipDataAsString = zipDataAsString;
	}
	public String getMainWsdlName() {
		return mainWsdlName;
	}
	public void setMainWsdlName(String mainWsdlName) {
		this.mainWsdlName = mainWsdlName;
	}
	public String getMainWsdlFullPath() {
		return mainWsdlFullPath;
	}
	public void setMainWsdlFullPath(String mainWsdlFullPath) {
		this.mainWsdlFullPath = mainWsdlFullPath;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public List<String> getOperations() {
		return operations;
	}
	public void setOperations(List<String> operations) {
		this.operations = operations;
	}
	public String getInputwsdlUrl() {
		return inputwsdlUrl;
	}
	public void setInputwsdlUrl(String inputwsdlUrl) {
		this.inputwsdlUrl = inputwsdlUrl;
	}
	public DGTL_GTW_WS_ADAPTERVO getDgtlAdapterVO() {
		return dgtlAdapterVO;
	}
	public void setDgtlAdapterVO(DGTL_GTW_WS_ADAPTERVO dgtlAdapterVO) {
		this.dgtlAdapterVO = dgtlAdapterVO;
	}
	public List<DGTL_GTW_ADAPTER_PARAM_MAPVO> getCombinedReqResp() {
		return combinedReqResp;
	}
	public void setCombinedReqResp(List<DGTL_GTW_ADAPTER_PARAM_MAPVO> combinedReqResp) {
		this.combinedReqResp = combinedReqResp;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getParentGridId() {
		return parentGridId;
	}
	public void setParentGridId(String parentGridId) {
		this.parentGridId = parentGridId;
	}
	
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMappingFieldsUrl() {
		return mappingFieldsUrl;
	}
	public void setMappingFieldsUrl(String mappingFieldsUrl) {
		this.mappingFieldsUrl = mappingFieldsUrl;
	}
	public String getParentListFieldName() {
		return parentListFieldName;
	}
	public void setParentListFieldName(String parentListFieldName) {
		this.parentListFieldName = parentListFieldName;
	}
}

