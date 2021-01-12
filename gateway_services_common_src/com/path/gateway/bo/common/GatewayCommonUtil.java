/**
 * 
 */
package com.path.gateway.bo.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.path.bo.common.MessageCodes;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.StringUtil;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * GatewayCommonUtil.java used to
 */
public class GatewayCommonUtil
{
    /*
     * validate Email Address
     */
    public static void validateEmail(String email) throws BaseException
    {

	if(StringUtil.isNotEmpty(email))
	{
	    int indexOfAt, indexOfDot, indexOfSpace;
	    indexOfAt = email.indexOf("@");
	    indexOfDot = email.indexOf(".");
	    indexOfSpace = email.indexOf(" ");
	    if(indexOfDot <= 0 || indexOfAt <= 0)
	    {
		throw new BOException(MessageCodes.PLEASE_ENTER_A_VALID_EMAIL_ADDR);
	    }
	    else if(indexOfSpace != 0 && indexOfSpace != -1)
	    {
		throw new BOException(MessageCodes.THE_EMAIL_ADDR_CAN_NOT_CONTAIN_SPACES);

	    }
	}

    }
}
