package com.path.vo.customexpression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.path.dbmaps.vo.SYS_PARAM_CUSTOM_EXPRESSIONVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * CustomExpressionCO.java used to
 */
public class CustomExpressionCO extends BaseVO
{
    private BigDecimal interfaceId;
    private String customExpressionAutoCompleteData;
    private String customExpressionList;
    private SYS_PARAM_CUSTOM_EXPRESSIONVO custom_EXPRESSIONVO  = new SYS_PARAM_CUSTOM_EXPRESSIONVO();
    private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementMap = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
    private String gridUrl;
    private String expression;
    private String mti_code;
    private String arguments;
    private String expressionIsofields;
    private String responseSelectedRowFields;
    private List allGridList = new ArrayList<>(); 
    private String status;
    private String isGlobalYN;

    public BigDecimal getInterfaceId()
    {
	return interfaceId;
    }

    public void setInterfaceId(BigDecimal interfaceId)
    {
	this.interfaceId = interfaceId;
    }

    public String getCustomExpressionAutoCompleteData()
    {
	return customExpressionAutoCompleteData;
    }

    public void setCustomExpressionAutoCompleteData(String customExpressionAutoCompleteData)
    {
	this.customExpressionAutoCompleteData = customExpressionAutoCompleteData;
    }

    public String getCustomExpressionList()
    {
	return customExpressionList;
    }

    public void setCustomExpressionList(String customExpressionList)
    {
	this.customExpressionList = customExpressionList;
    }

    public SYS_PARAM_CUSTOM_EXPRESSIONVO getCustom_EXPRESSIONVO()
    {
        return custom_EXPRESSIONVO;
    }

    public void setCustom_EXPRESSIONVO(SYS_PARAM_CUSTOM_EXPRESSIONVO custom_EXPRESSIONVO)
    {
        this.custom_EXPRESSIONVO = custom_EXPRESSIONVO;
    }

    public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getElementMap()
    {
        return elementMap;
    }

    public void setElementMap(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementMap)
    {
        this.elementMap = elementMap;
    }

    public String getGridUrl()
    {
        return gridUrl;
    }

    public void setGridUrl(String gridUrl)
    {
        this.gridUrl = gridUrl;
    }

    public String getExpression()
    {
        return expression;
    }

    public void setExpression(String expression)
    {
        this.expression = expression;
    }

	public String getMti_code() {
		return mti_code;
	}

	public void setMti_code(String mti_code) {
		this.mti_code = mti_code;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getExpressionIsofields()
	{
	    return expressionIsofields;
	}

	public void setExpressionIsofields(String expressionIsofields)
	{
	    this.expressionIsofields = expressionIsofields;
	}

	public List getAllGridList()
	{
	    return allGridList;
	}

	public void setAllGridList(List allGridList)
	{
	    this.allGridList = allGridList;
	}

	public String getResponseSelectedRowFields() {
		return responseSelectedRowFields;
	}

	public void setResponseSelectedRowFields(String responseSelectedRowFields) {
		this.responseSelectedRowFields = responseSelectedRowFields;
	}

	public String getStatus()
	{
	    return status;
	}

	public void setStatus(String status)
	{
	    this.status = status;
	}
	public String getIsGlobalYN()
	{
	    return isGlobalYN;
	}

	public void setIsGlobalYN(String isGlobalYN)
	{
	    this.isGlobalYN = isGlobalYN;
	}
}