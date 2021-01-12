package com.path.vo.customexpression;

import java.math.BigDecimal;
import java.util.HashMap;

import com.path.dbmaps.vo.GTW_CUSTOM_EXPRESSIONVO;
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
public class ExpressionParamCO extends BaseVO
{
    private String parameterName ;
    private String parameterValue ;
    
    public String getParameterName()
    {
        return parameterName;
    }
    public void setParameterName(String parameterName)
    {
        this.parameterName = parameterName;
    }
    public String getParameterValue()
    {
        return parameterValue;
    }
    public void setParameterValue(String parameterValue)
    {
        this.parameterValue = parameterValue;
    }
    
    
}