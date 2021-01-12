package com.path.dao.atminterfaces;

import java.util.List;

import com.path.lib.common.exception.DAOException;
import com.path.vo.atminterfaces.AtmInterfacesCO;
import com.path.vo.atminterfaces.AtmInterfacesSC;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * AtmInterfacesDAO.java used to
 */
public interface AtmInterfacesDAO
{
	/**
	 * Method used to return List of Interfaces
	 * @param criteria Search Criteria Parameter
	 * @return List Result
	 * @throws DAOException
	 */
	public List returnInterfaceList(AtmInterfacesSC criteria) throws DAOException;
	
	/**
	 * Method used to return Count of Interfaces
	 * @param criteria Search Criteria Parameter
	 * @return int Result number of Records
	 * @throws DAOException
	 */	
	public int returnInterfaceListCount(AtmInterfacesSC criteria) throws DAOException;
	
	/**
	 * Method to Load Interface Details by ID
	 * @param AtmInterfaceSC 
	 * @return AtmInterfaceCO 
	 * @throws DAOException
	 */
	public AtmInterfacesCO returnInterfaceById(AtmInterfacesSC criteria) throws DAOException;
	
	/**
	 * Method to validate the Interface in Channel table (required for channel screen)
	 * @param criteria
	 * @return
	 * @throws DAOException
	 */
	public int returnChannelByInterfaceCodeCount(AtmInterfacesSC criteria) throws DAOException;
}
