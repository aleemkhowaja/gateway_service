package com.path.bo.impl.atminterfaces;

import java.util.List;

import com.path.bo.atminterfaces.AtmInterfacesBO;
import com.path.dao.atminterfaces.AtmInterfacesDAO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.atminterfaces.AtmInterfacesCO;
import com.path.vo.atminterfaces.AtmInterfacesSC;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * AtmInterfacesBOImpl.java used to
 */
public class AtmInterfacesBOImpl extends BaseBO implements AtmInterfacesBO
{

    private AtmInterfacesDAO atmInterfacesDAO;
    
    @Override
    public List returnInterfaceList(AtmInterfacesSC criteria) throws BaseException
    {
	return this.atmInterfacesDAO.returnInterfaceList(criteria);
    }

    @Override
    public int returnInterfaceListCount(AtmInterfacesSC criteria) throws BaseException
    {
	return atmInterfacesDAO.returnInterfaceListCount(criteria);
    }
    
    @Override
    public AtmInterfacesCO returnInterfaceById(AtmInterfacesSC criteria) throws BaseException
    {
	if(criteria.getIsInterfaceValidationRequired() != null && criteria.getIsInterfaceValidationRequired())
	{
	    int count = atmInterfacesDAO.returnChannelByInterfaceCodeCount(criteria);
	    if(count > 0)
	    {
		AtmInterfacesCO atmInterfacesCO = new AtmInterfacesCO();
		atmInterfacesCO.setIsChannelExistByInterface(true);
		return atmInterfacesCO;
	    }
	}
	return atmInterfacesDAO.returnInterfaceById(criteria);
    }

    public void setAtmInterfacesDAO(AtmInterfacesDAO atmInterfacesDAO)
    {
        this.atmInterfacesDAO = atmInterfacesDAO;
    }
    
    


 
}
