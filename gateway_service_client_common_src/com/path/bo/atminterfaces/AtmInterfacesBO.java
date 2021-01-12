package com.path.bo.atminterfaces;

import java.util.List;

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
 * AtmInterfacesBO.java used to
 */
public interface AtmInterfacesBO
{
    
    /**
     * Method used to return List of Interfaces
     * 
     * @param criteria Search Criteria Parameter
     * @return List Result
     * @throws BaseException
     */
    public List returnInterfaceList(AtmInterfacesSC criteria) throws BaseException;

    /**
     * Method used to return Count of Interfaces
     * 
     * @param criteria Search Criteria Parameter
     * @return int Result number of Records
     * @throws BaseException
     */
    public int returnInterfaceListCount(AtmInterfacesSC criteria) throws BaseException;
    
    /**
     * Method to Load Interface Details by ID
     * 
     * @param AtmInterfaceCO
     * @return AtmInterfaceCO
     * @throws BaseException
     */
    public AtmInterfacesCO returnInterfaceById(AtmInterfacesSC criteria) throws BaseException;
 
}
