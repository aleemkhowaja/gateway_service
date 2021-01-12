package com.path.dao.customexpression;

import java.util.List;
import com.path.dbmaps.vo.GTW_CUSTOM_EXPRESSIONVO;
import com.path.dbmaps.vo.SYS_PARAM_CUSTOM_EXPRESSIONVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.customexpression.CustomExpressionSC;

/**
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * CustomExpressionDAO.java used to
 */
public interface CustomExpressionDAO
{
    
    /**
     * 
     * @param criteria
     * @return
     * @throws DAOException
     */
    public List returnCustomExpressionList(CustomExpressionSC criteria) throws DAOException;
    
    /**
     * 
     * @param cUSTOM_EXPRESSIONVO
     * @return
     * @throws DAOException
     */
    public Integer saveCustomExpression(SYS_PARAM_CUSTOM_EXPRESSIONVO cUSTOM_EXPRESSIONVO) throws DAOException;
    
    /**
     * 
     * @param criteria
     * @return
     * @throws DAOException
     */
    public SYS_PARAM_CUSTOM_EXPRESSIONVO validateCustomExpressionByShortName(CustomExpressionSC criteria)throws DAOException;
}
