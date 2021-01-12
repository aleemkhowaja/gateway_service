/**
 * 
 */
package com.path.dao.customexpression.impl;
import java.util.List;

import com.path.dao.customexpression.CustomExpressionDAO;
import com.path.dbmaps.vo.SYS_PARAM_CUSTOM_EXPRESSIONVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.customexpression.CustomExpressionSC;

/**
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * CustomExpressionDAOImpl.java used to
 */
public class CustomExpressionDAOImpl  extends BaseDAO implements CustomExpressionDAO
{

    @Override
    public List returnCustomExpressionList(CustomExpressionSC criteria) throws DAOException
    {
	DAOHelper.fixGridMaps(criteria, getSqlMap(), "customExpressionMapper.resultCustomExpressionListMap");
	if(criteria.getSortOrder() == null)
	{
	    criteria.setSortOrder(" ORDER BY EXPRESSION_ID DESC");
	}
	if(criteria.getNbRec() == -1)
	{
	    return getSqlMap().queryForList("customExpressionMapper.returnCustomExpressionList", criteria);
	}
	else
	{
	    return getSqlMap().queryForList("customExpressionMapper.returnCustomExpressionList", criteria, criteria.getRecToskip(),
		    criteria.getNbRec());
	}
    }

    @Override
    public Integer saveCustomExpression(SYS_PARAM_CUSTOM_EXPRESSIONVO cUSTOM_EXPRESSIONVO) throws DAOException
    {
	return (Integer) getSqlMap().insert("customExpressionMapper.saveCustomExpression", cUSTOM_EXPRESSIONVO);
    }

    @Override
    public SYS_PARAM_CUSTOM_EXPRESSIONVO validateCustomExpressionByShortName(CustomExpressionSC criteria) throws DAOException
    {
	return (SYS_PARAM_CUSTOM_EXPRESSIONVO) getSqlMap().queryForObject("customExpressionMapper.validateCustomExpressionByShortName", criteria);
    }
    

}
