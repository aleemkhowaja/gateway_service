package com.path.bo.common.pwsmapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * @author: RichardZourob
 * 
 *          AssetsConstant.java used to hold the constant variables to be used
 *          in most of assets screen
 */
public class CommonPwsMappingConstant
{

    public static final String ACC_REP_ID = "ACC_REP_ID";
    public static final String PRINT_FLAG = "PRINT_FLAG";
    public static final String ACTIV_FLAG = "ACTIV_FLAG";
    public static final String ACTIV_EVT = "ACTIV_EVT";
    public static final String REACTIV_FLAG = "REACTIV_FLAG";
    public static final String REACTIV_EVT = "REACTIV_EVT";
    public static final String SUSPEND_FLAG = "SUSPEND_FLAG";
    public static final String SUSPEND_EVT = "SUSPEND_EVT";
    public static final String MOBILE_FLAG = "MOBILE_FLAG";
    public static final String MOBILE_EVT = "MOBILE_EVT";
    public static final String MOBILE = "MOBILE";
    public static final String CENTRALIZE_CORE = "CENTRALIZE_CORE";
    public static final String ALRT_QUEUE = "alrt_queue";
    public static final String ALRT_SUB_EVT = "ALRT_SUB_EVT";
    public static final String ALRT_SUB_GRP = "ALRT_SUB_GRP";
    public static final String ALRT_EVT_PKG = "ALRT_EVT_PKG";
    public static final String ALRT_GRP = "ALRT_GRP";
    public static final String ALRT_SUB = "ALRT_SUB";
    public static final String ALRT_PKG = "ALRT_PKG";
    
    public static final String PROTOCOL_TYPE = "PROTOCOL_TYPE";
    public static final String SMTP_IP = "SMTP_IP";
    public static final String SMTP_PORT = "SMTP_PORT";
    public static final String SMTP_SENDER = "SMTP_SENDER";
    public static final String SMTP_USER = "SMTP_USER";
    public static final String SMTP_PASSWORD = "SMTP_PASSWORD";
    public static final String SMTP = "SMTP";
    public static final String OUTLOOK_CLIENT ="O";
    public static final String EMAIL_CONFIG_TYPE ="EMAIL_CONFIG_TYPE";
    
    public static final String CRUD_F  = "F";
    public static final String CRUD_UP  = "UP";
    public static final String CRUD_P  = "P";
    public static final String CRUD_R  = "R";
    public static final String CRUD_D  = "D";
    public static final String CRUD_SP  = "SP";
    public static final String CRUD_S  = "S";
    public static final String CRUD_RA  = "RA";
    public static final String CRUD_A  = "A";
    public static final String CRUD_TD  = "T";
    public static final String CRUD_TS  = "SP";
    public static final String CRUD_TS_SUBS_PKG  = "TS";
    public static final String CRUD_TR  = "TR";
    public static final String STATUS_P  = "P";
    public static final String STATUS_NEW_CREADTED = "C";
    public static final String STATUS_MODIFIED = "M";
    public static final String STATUS_DELETED = "D";
    public static final String STATUS_TO_BE_DELETED = "T";
    public static final String STATUS_TO_BE_SUSPENDED = "SP";
    public static final String STATUS_TO_BE_REACTIVATE = "TR";
    public static final String STATUS_APPROVED = "P";
    public static final String STATUS_TO_SUSPEND = "T";
    public static final String STATUS_TO_REACTIVATE = "R";
    public static final String STATUS_SUSPEND = "S"; 
    public static final String STATUS_NEW = "N";
    public static final String STATUS_ACTIVE = "A";
    public static final String NEW = "New";
    public static final String GROUP_PACKAGE = "GP";
    public static final String EVENT_GROUP = "EG";
    public static final String SUB_PARAM = "SP";
    public static final String SE = "SE";

    public static final String TYPE_O = "O";
    public static final String TYPE_C = "C";
    public static final String TYPE_A = "A";
    public static final String TYPE_U = "U";
    public static final String TYPE_I = "I";
    public static final String TYPE_B = "B";
    public static final String TYPE_D = "D";
    public static final String TYPE_N = "N";
    
    public static final String Protocol_Type_O = "O";
    public static final String Protocol_Type_S = "S";
    
    // language
    public static final String DEFAULT = "D";
    public static final String ARABIC = "A";
    public static final String ENGLISH = "E";
    // drop down lists
    public static final BigDecimal subscriberTypeDropDown = new BigDecimal(872);
    public static final BigDecimal SUBSCRIBER_STATUS_LOV_TYPE = new BigDecimal(871);
    public static final BigDecimal Lang_LOV_TYPE = new BigDecimal(412);
    public static final BigDecimal COMMON_LANG_LOV = new BigDecimal(18);
    public static final BigDecimal protocolTypeDropDown = new BigDecimal(926);
    public static final BigDecimal emailConfigTypeDropDown = new BigDecimal(1552);
    //Event
    public static final BigDecimal REPORT_QUERY_LOV = new BigDecimal(944);

    // Event
    public static final BigDecimal EVENT_STATUS_LOV_TYPE = new BigDecimal(904);
    public static final BigDecimal eventTypeDropDown = new BigDecimal(883);
    public static final BigDecimal eventModeDropDown = new BigDecimal(884);
    public static final BigDecimal eventPriorityDropDown = new BigDecimal(885);
    public static final BigDecimal periodTypeDropDown = new BigDecimal(886);
    public static final BigDecimal dayInMonthDropDown = new BigDecimal(887);
    public static final BigDecimal fixedOperator = new BigDecimal(1653);
    public static final BigDecimal cardsparam = new BigDecimal(894);
    public static final BigDecimal chequebookparam = new BigDecimal(895);
    public static final BigDecimal BIG_DECIMAL_TWO = new BigDecimal(2);
    public static final BigDecimal eventModeOmni = new BigDecimal(1335);
    public static final BigDecimal FIX_EVENT_CHANNEL_SERVICE_ID = new BigDecimal(9);
    public static final BigDecimal SourceOfContactOmni = new BigDecimal(1471);//740965
    public static final BigDecimal oldNewValue = new BigDecimal(1648);
    public static final BigDecimal colTypeLov = new BigDecimal(632);
    
    
    //DynamicTag - AIBB180035 - 678042
    public static final BigDecimal LOV_TYPE_ALERT_TAG_TYPE = new BigDecimal(1420);
    public static final String TYPE_S = "S";
    public static final String TYPE_Y = "Y";
    
    
    public static final String TYPE_EVT_F = "F";
    public static final String TYPE_EVT_M = "M";
    public static final String TYPE_EVT_R = "R";
    public static final String TYPE_EVT_Q = "Q";
    public static final String TYPE_EVT_S = "S";
    public static final String EVT_MODE_E = "E";
    public static final String EVT_MODE_B = "B";
    public static final String EVT_MODE_S = "S";
    public static final String EVT_MODE_A = "A";
    public static final String EVT_MODE_F = "F";
    public static final String EVT_MODE_M = "M";
    public static final String EVT_MODE_L = "L";
    public static final String PERIOD_TYPE_M = "M";
    public static final String FIXED_FLAG_B = "B";
    public static final String FIXED_FLAG_E = "E";
    public static final String FixedOperator_B = "3";// "Between";
    public static final String FixedOperator_E = "1";// "Equals";
    public static final BigDecimal Fixed_EVT_CH = new BigDecimal(5);// chequebook
    public static final BigDecimal Fixed_EVT_CD = new BigDecimal(6);// cards
    public static final BigDecimal Fixed_EVT_O = new BigDecimal(2);// overdraft
    public static final BigDecimal Fixed_EVT_JV_TYPE = new BigDecimal(1);// Jv Type
    public static final BigDecimal Fixed_EVT_TRX_TYPE = new BigDecimal(8);// Transaction Type
    public static final String PARAM_TYPE_3 =  "3";//Decimal
    public static final String PARAM_TYPE_4 =  "4";//Decimal
    public static final String NOTSUSPENDED = "N";
    public static final String SUSPENDED = "Y";

    //
    public static final BigDecimal MINUS_ONE = new BigDecimal(-1);
    public static final Integer INT_ONE = 1;
    public static final String PERIOD_MODE_SPECIFIC_DATE="S";
    public static final String PERIOD_MODE_AFTER_EOD="A";
    //
    
    public static final String ALRT_APP_NAME = "ALRT";

    public static final List<String> getStatusFields = new ArrayList<String>();
    static
    {
		getStatusFields.add("CREATED_BY," + STATUS_NEW_CREADTED + ",DATE_CREATED");
		getStatusFields.add("MODIFIED_BY," + STATUS_MODIFIED + ",DATE_MODIFIED");
		getStatusFields.add("DELETED_BY," + STATUS_DELETED + ",DATE_DELETED");
		getStatusFields.add("AUTHORIZED_BY," + STATUS_APPROVED + ",DATE_AUTHORIZED");	
		getStatusFields.add("TOBE_SUSPENDED_BY," + STATUS_TO_SUSPEND + ",TOBE_SUSPENDED_DATE");
		getStatusFields.add("TOBE_REACTIVATED_BY," + STATUS_TO_REACTIVATE + ",TOBE_REACTIVATED_DATE");
		getStatusFields.add("SUSPENDED_BY," + STATUS_SUSPEND + ",DATE_SUSPENDED");
		getStatusFields.add("REACTIVATED_BY," + STATUS_APPROVED + ",DATE_REACTIVATED");	
    }
    
    public static final String ALERT_WEB_SERVICE_EXPLORER_LIST_ACTION_NAME = "PwsWebServiceExplorerList_*";
    
    public static final String ALERT_WEB_SERVICE_EXPLORER_SCREEN_NAME_SPACE = "/path/common/pws/";
    
    public static final String ALERT_WEB_SERVICE_EXPLORER_SCREEN_NAME = "alert";

    public static final List<String> MAPPING_FIELDS = new ArrayList<String>();
    static
    {
    	MAPPING_FIELDS.add("mobile_number");
    	MAPPING_FIELDS.add("sms_body");
    }   

}
