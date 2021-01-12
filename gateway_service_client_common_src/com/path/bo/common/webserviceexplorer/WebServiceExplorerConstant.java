package com.path.bo.common.webserviceexplorer;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther:Raed Saad
 * @Date:MARCH 2018
 * @Team: PEMTS JAVA Team.
 * @copyright: PathSolution 2018
 * @User Story: USER STORY #653853  WSDL explorer
 * @Description: Constants
 **/

public class WebServiceExplorerConstant {

    public static final String PROPERTIES_FILE = "PathWebServicesList.properties";

    public static final String WEBSERVICE_EXPLORER_APPLICATION_ID = "application_name_";
    public static final String WEBSERVICE_EXPLORER_APPLICATION_NAME = "webServiceExplorerCO.applicationName";
    
    public static final String WEBSERVICE_EXPLORER_OPERATION_ID = "application_name_";
    public static final String WEBSERVICE_EXPLORER_OPERATION_NAME = "webServiceExplorerCO.operationName";
    
    public static final String WEBSERVICE_EXPLORER_SHOW_WSDL_ID = "webServiceGuiShowWSDL";
    
    public static final String WEBSERVICE_EXPLORER_SHOW_REQUEST_ID = "webServiceGuiShowRequest";
    
    public static final String WEBSERVICE_EXPLORER_PROCESS_REQUEST_ID = "webServiceGuiShowProcessRequest";

    public static final String WEBSERVICE_EXPLORER_SAVE_REQUEST_ID = "webServiceGuiSaveRequest";

    public static final String WEBSERVICE_EXPLORER_UPDATE_REQUEST_ID = "webServiceGuiUpdateRequest";
    
    public static final String WEBSERVICE_EXPLORER_REQUEST_RESPOSNE_TAB_ID = "custTab2TabsContent";
    
    public static final String WEBSERVICE_EXPLORER_CHANNLE_ID = "lookuptxt_channel_id";
    
    public static final String WEBSERVICE_EXPLORER_USER_ID = "user_id";
    
    public static final String WEBSERVICE_EXPLORE_HOST_NAME_ID = "host_name_id";
    public static final String WEBSERVICE_EXPLORE_HOST_NAME_NAME = "webServiceExplorerCO.imcoPwsChannelDetVO.HOST_NAME";
    
    public static final String WEBSERVICE_EXPLORER_HASH_KEY_ID = "hash_key_id";
    public static final String WEBSERVICE_EXPLORER_HASH_KEY_NAME = "webServiceExplorerCO.imcoPwsChannelDetVO.HASH_KEY";
    
    public static final String WEBSERVICE_EXPLORER_ON = "1";
    public static final String WEBSERVICE_EXPLORER_OF = "0";    
    
	public final static String PROPERTY_FILE_NAME= "PathWebServicesList.properties";
	
	public final static String MAIN_GRID_ID = "webServiceGuiTbl_Id_";
	
	public final static String UNDERSCORE = "_";
	
	public final static String HASH_MAP_GRID_REF = "_HashMap_table";
	
	public final static String LIST_GRID_REF = "_List_table";
	
	public final static String SUB_GRID_REF = "_table";
	
	public final static String HASH_MAP_ENTRY = "entry";
	
	public final static String HASH_MAP_KEY = "key";
	
	public final static String HASH_MAP_VALUE = "value";
	
	public final static String WEB_SERVICE_EXPLORER_GRID_DATA_TABLE = "IMCO_PWS_TEST_DETAILS";
	
	public final static String WEB_SERVICE_EXPLORER_DATA_SEQUENCE_NEXTVAL = "CONFIG_ID_SEQ.NEXTVAL";
	
	public final static String WEB_SERVICE_EXPLORER_DATA_SEQUENCE_CURRVAL = "CONFIG_ID_SEQ.CURRVAL";

	public final static String WEB_SERVICE_EXPLORER_CONFIG_TABLE = "IMCO_PWS_TEST_MASTER";
	
	public final static String WEB_SERVICE_EXPLORER_CONFIG_SEQUENC_NEXTVAL = "CONFIG_ID_SEQ.NEXTVAL";

	public final static String WEB_SERVICE_EXPLORER_CONFIG_SEQUENC_CURRVAL = "CONFIG_ID_SEQ.CURRVAL";

	public final static String WEB_SERVICE_EXPLORER_LOAD_SUB_GRID_BY_FORCE = "1";
	
	public final static String WEB_SERVICE_EXPLORER_RECORD_IS_LOADED_FROM_DATABASE = "2";
		
	public static final String XML_CONTENT_TYPE = "text/xml; charset=windows-1252";

	public static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	
	public static final String DOC_TYPE = "<!DOCTYPE html  \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
			+ "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
	
    public static final String REQUEST_TYPE = "request";
    
    public static final String RESPONSE_TYPE = "response";
    
    public static final String SIMPLE_TYPE = "simpleType";
    
    public static final String COMPLEX_TYPE = "complexType";
    
    public static final String COMPLEXT_CONTENT = "complexContent";
    
    public static final String WEB_SERVICE_EXPLORER_LIST_ACTION_NAME = "WebServiceExplorerList_*";
    
    public static final String WEB_SERVICE_EXPLORER_SCREEN_NAME_SPACE = "/path/common/WebserviceExplorer/";
    
    public static final String WEB_SERVICE_EXPLORER_MAIN_GRID = "loadMainGridFn";
    
    public static final String WEB_SERVICE_EXPLORER_SUB_GRID = "loadSubGridFn";
    
    public static final String WEB_SERVICE_EXPLORER_LIST_SUB_GRID = "loadListSubGridFn";
    
    public static final String WEB_SERVICE_EXPLORER_HASHMAP_SUB_GRID = "loadHashMapSubGridFn";
    
    public static final String WEB_SERVICE_EXPLORER_MAIN_GRID_RESULT = "loadGridSection";
    
    public static final String WEB_SERVICE_EXPLORER_SUB_GRID_RESULT = "loadSubGrid";
    
    public static final String WEB_SERVICE_EXPLORER_LIST_SUB_GRID_RESULT = "loadListSubGrid";
    
    public static final String WEB_SERVICE_EXPLORER_HASHMAP_SUB_GRID_RESULT = "loadHashMapSubGrid";
    
    public static final String REQUEST_RESPONSE_HAS_RESTRICTION = "hashRestrictions";
    
    public static final String REQUEST_RESPONSE_APPLICATION_NAME = "applicationName";
    
    public static final String REQUEST_RESPONSE_WEB_SERVICE_NAME = "webServiceName";
    
    public static final String REQUEST_RESPONSE_OPERATION_NAME = "operationName";
    
    public static final String REQUEST_RESPONSE_FIELD_NAME = "fieldName";
    
    public static final String REQUEST_RESPONSE_FIELD_TYPE = "fieldType";
    
    public static final String REQUEST_RESPONSEFIELD_MIN_OCC = "fieldMinOCC";
    
    public static final String REQUEST_RESPONSE_HAS_CHILDREN= "hasChildren";
    
    public static final String PROR_KEY_NAME_REF = ".name";
    
    public static final String PROR_KEY_URL_REF = ".url";
    
    public static final String CRITICAL_PROR_KEY_NAME_REF = "cpws.name";
    
    public static final String CRITICAL_PROR_KEY_URL_REF = "cpws.url";
    
    public static final String PROP_APP = "app";
    
    public static final String PROP_PWS = "pws";
    
    public static final String CRITICAL_PROP_PWS = "cpws";
    
    public static final String HTTPS_EXTENSION = "https";
    
    public static final String HTTP_EXTENSION = "http";
    
    public static final String SSL_EXTENSION = "SSL";
    
    public static final String PROP_URL = "url";
    
    public static final String PROP_NAME = "name"; 
    
    public static final String INTEGER_TYPE = "int";
    
    public static final String STRING_TYPE = "string";
    
    public static final String DECIMAL_TYPE = "decimal";
    
    public static final String BIGDECIMAL_TYPE = "bigdecimal";
    
    public static final String DATETIME_TYPE = "dateTime";
    
    public static final String LONG_TYPE = "long";
    
    public static final String DATE_TYPE = "date";
    
    public static final String FLOAT_TYPE = "float";
    
    public static final String DOUBLE_TYPE = "double";
    
    public static final String BOOLEAN_TYPE = "boolean";
    
    public static final String NO_RESTRICTION = "0";
    
    public static final String HAS_ENUM_RESTRICTION = "1";
    
    public static final String NO_CHILDREN = "0";
    
    public static final String HAS_CHILDREN = "1";
    
    private static final String dEP[] = new String[]{"channelID",
    		"hashKey",
    		"hostName",
    		"langId",
    		"password",
    		"requesterTimeStamp",
    		"userID",
    		"requestContextVO"};
   
    public static final List<String> defaultExcludedParams = Arrays.asList(dEP);

}
