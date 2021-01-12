package com.path.gateway.dao.channel;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.OPTVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNELVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNEL_JMSVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_MASTERVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_USRVO;
import com.path.gateway.vo.channel.ChannelCO;
import com.path.gateway.vo.channel.ChannelSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;


/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * ChannelDAO.java used to
 */
public interface ChannelDAO
{

    /**
     * Return Channel List
     * 
     * @param criteria
     * @return
     * @throws DAOException
     */
    public List returnChannelList(ChannelSC criteria) throws DAOException;

    /**
     * return Channel List Count
     * 
     * @param criteria
     * @return
     * @throws DAOException
     */
    public int returnChannelListCount(ChannelSC criteria) throws DAOException;

    /**
     * save Channel
     * 
     * @param gtw_CHANNELVO
     * @return
     * @throws DAOException
     */
    public Integer saveGTW_CHANNELVO(GTW_CHANNELVO gtw_CHANNELVO) throws DAOException;

    /**
     * @param channelSC
     * @return
     */
    public ChannelCO edit(ChannelSC channelSC) throws DAOException;

    /**
     * return Machine Id List
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public List<ChannelCO> returnVerficationListForGrid(ChannelSC sc) throws DAOException;

    /**
     * return Machine Id list Count
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public int returnVerficationListCountForGrid(ChannelSC sc) throws DAOException;

    /**
     * delete All Machine Id
     * 
     * @param channelCO
     * @throws DAOException
     */
    public void deleteAllVarificationDetails(ChannelCO channelCO) throws DAOException;

    /**
     * delete All TemplateId
     * 
     * @param channelCO
     * @throws DAOException
     */
    public void deleteAllTemplateId(ChannelCO channelCO) throws DAOException;

    /**
     * return TempId List Count
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public int returnTempIdListCount(ChannelSC sc) throws DAOException;

    /**
     * return TempId List
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public List<GTW_PWS_TMPLT_MASTERVO> returnTempIdList(ChannelSC sc) throws DAOException;

    /**
     * load Assigned TemplateId ListGrid
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public List<GTW_PWS_TMPLT_USRVO> loadAssignedTemplateIdListGrid(ChannelSC sc) throws DAOException;

    /**
     * 
     * @param channelSC
     * @return
     * @throws DAOException
     */
    int validateUserId(ChannelSC channelSC) throws DAOException;

    /**
     * insert OPT
     * 
     * @param channelCO
     * @throws DAOException
     */
    public BigDecimal returnLatestDisplayOrder(OPTVO optvo) throws DAOException;
    
    
    public Integer saveGTW_CHANNEL_JMSVO(GTW_CHANNEL_JMSVO jmsVO) throws DAOException;
    

}