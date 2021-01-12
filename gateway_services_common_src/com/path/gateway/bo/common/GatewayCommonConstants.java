package com.path.gateway.bo.common;

import java.math.BigDecimal;

import com.path.lib.common.util.PathPropertyUtil;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * GatewayCommonConstants.java used to
 */
public class GatewayCommonConstants
{

    // LOV ID

    // IV CRUD VALUES

    // Status
    public static final String STATUS_CREATED = "0_C";
    public static final String STATUS_MODIFIED = "0_M";
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_DELETED = "D";
    public static final String STATUS_APPROVED = "P";
    public static final String STATUS_SUSPENDED = "S";
    public static final String STATUS_REACTIVATE = "RA";
    public static final String STATUS_APPROVE_REJECTED = "R";
    public static final String STATUS_UPDATE_AFTER_APPROVE = "UP";
    public static final String STATUS_APPROVED_I = "I";
    
    public static final String ACTIVE = "Active";

    public static final boolean TRUE_BOOLEAN = true;
    public static final boolean FALSE_BOOLEAN = false;
    public static final String STATUS_COLOR_CODE_B = "B";
    public static final String APP_NAME_FCSR = "GTW";

    // used to exclude sum values of the LOV

    // used to compare sum variables
    public static final String APPLY_ON_PARENT = "P";
    public static final String APPLY_ON_RELATION = "R";
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final BigDecimal TELLER_TYPE_VALET = new BigDecimal(12);

    /**
     * ATM Constants
     */
    // LOV ID
    public static final BigDecimal LOV_TYPE_CONS_COMMON_STATUS = new BigDecimal(949);

    // IV CRUD VALUES
    public static final String IV_CRUD_MAINTENANCE = "R";
    public static final String IV_CRUD_APPROVE = "P";
    public static final String IV_CRUD_UPDATE_AFTER_APPROVE = "UP";
    public static final String IV_CRUD_SUSPENDED = "S";
    public static final String IV_CRUD_REACTIVATE = "RA";

    // ISO Field Types
    public static final String ISO_FIELD_TYPE_NUMERIC = "N";
    public static final String ISO_FIELD_TYPE_ALPHA = "V";
    public static final String ISO_FIELD_TYPE_BITMAP = "T";
    public static final String ISO_FIELD_TYPE_BINARY = "B";
    public static final String ISO_FIELD_TYPE_AMOUNT = "A";
    public static final String ISO_FIELD_TYPE_EXP_DATE = "E";
    public static final String ISO_FIELD_TYPE_TIME = "H";
    public static final String ISO_FIELD_TYPE_DATE4 = "M";
    public static final String ISO_FIELD_TYPE_DATE10 = "D";

    public static final String DYNAMIC_FILE_MESSAGE = "DYNAMIC_FILE_MESSAGE";
    public static final String DYNAMIC_FILE_TAGS = "DYNAMIC_FILE_TAGS";
    public static final String DYNAMIC_FILE_STRUCTURE = "DYNAMIC_FILE_STRUCTURE";
    public static final String IMCO_DYN_FILE_VALUES_TMP = "IMCO_DYN_FILE_VALUES_TMP";

    public static final BigDecimal COMMON_STATUS_LOV = new BigDecimal(1478);

    public static final String RET_SERVICE_URL = "integration.RET.serviceURL";
    public static final String RET_SERVICE_REMOTING = "PathGatewayRemoting.properties";

    public static String returnRETRmi()
    {
	String rmiUrl = null;
	try
	{
	    rmiUrl = PathPropertyUtil.returnPathPropertyFromFile(RET_SERVICE_REMOTING, RET_SERVICE_URL);
	    rmiUrl = (rmiUrl == null) ? "" : rmiUrl;
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
	return rmiUrl;
    }

}
