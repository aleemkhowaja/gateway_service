package com.path.dao.atminterfaces.impl;

import java.util.List;

import com.path.dao.atminterfaces.AtmInterfacesDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.atminterfaces.AtmInterfacesCO;
import com.path.vo.atminterfaces.AtmInterfacesSC;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * AtmInterfacesDAOImpl.java used to
 */
public class AtmInterfacesDAOImpl extends BaseDAO implements AtmInterfacesDAO
{
    
    @Override
    public List returnInterfaceList(AtmInterfacesSC criteria) throws DAOException
    {
	DAOHelper.fixGridMaps(criteria, getSqlMap(), "atmInterfacesMapper.interfaceMap");
	if(criteria.getSortOrder() == null)
	{
	    criteria.setSortOrder(" ORDER BY INTERFACE_CODE DESC");
	}
	if(criteria.getNbRec() == -1)
	{
	    return getSqlMap().queryForList("atmInterfacesMapper.returnInterfaceList", criteria);
	}
	else
	{
	    return getSqlMap().queryForList("atmInterfacesMapper.returnInterfaceList", criteria, criteria.getRecToskip(),
		    criteria.getNbRec());
	}
    }

    @Override
    public int returnInterfaceListCount(AtmInterfacesSC criteria) throws DAOException
    {
	DAOHelper.fixGridMaps(criteria, getSqlMap(), "atmInterfacesMapper.interfaceMap");
	return ((Integer) getSqlMap().queryForObject("atmInterfacesMapper.returnInterfaceListCount", criteria)).intValue();
    }
    
    @Override
    public AtmInterfacesCO returnInterfaceById(AtmInterfacesSC criteria) throws DAOException
    {
	return (AtmInterfacesCO) getSqlMap().queryForObject("atmInterfacesMapper.returnInterfaceByCode", criteria);
    }
    
    @Override
    public int returnChannelByInterfaceCodeCount(AtmInterfacesSC criteria) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("atmInterfacesMapper.returnChannelByInterfaceCodeCount", criteria)).intValue();
    }
    
}