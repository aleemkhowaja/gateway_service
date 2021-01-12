package com.path.bo.customexpression;

import com.path.lib.common.exception.BaseException;
import com.path.vo.customexpression.CustomExpressionCO;
import com.path.vo.customexpression.CustomExpressionSC;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * CustomExpressionBO.java used to
 */
public interface CustomExpressionBO 
{

    
    /**
     * @author: Alim Khowaja
     * @param hashIn
     * @return
     * @throws BaseException
     * method used to return list of expressions
     */
    public CustomExpressionCO returnCustomExpressionList(CustomExpressionSC  customExpressionSC,StringBuffer autoCompleteList) throws Exception;
    
    
    /**
     * @author: Alim Khowaja
     * @param hashIn
     * @return
     * @throws BaseException
     * method used to save Expression
     */
    public Integer saveCustomExpression(CustomExpressionCO customExpressionCO) throws BaseException;
    
    
    /**
     * @author: Alim Khowaja
     * @param hashIn
     * @return
     * @throws BaseException
     * method used to return list of expressions
     */
    public  CustomExpressionCO validateCustomExpressionByShortName(CustomExpressionSC  customExpressionSC) throws BaseException;
    
    /**
     * @author: Alim Khowaja
     * @param customExpressionCO
     * @return
     * @throws BaseException
     */
    public CustomExpressionCO applyVisiblityOnCustomExpressionFields(CustomExpressionCO customExpressionCO) throws BaseException;
    
    
}