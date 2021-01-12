package com.path.gateway.bo.channel;

import java.util.List;

import com.path.gateway.dbmaps.vo.GTW_DGTL_INTERFACESVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_MASTERVO;
import com.path.gateway.vo.channel.ChannelCO;
import com.path.gateway.vo.channel.ChannelSC;
import com.path.gateway.vo.channel.TemplateCO;
import com.path.lib.common.exception.BaseException;


/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * ChannelBO.java used to
 */
public interface ChannelBO
{

    /**
     * return Channel List
     * 
     * @param criteria
     * @return
     * @throws BaseException
     */
    public List returnChannelList(ChannelSC criteria) throws BaseException;

    /**
     * return Channel List Count
     * 
     * @param criteria
     * @return
     * @throws BaseException
     */
    public int returnChannelListCount(ChannelSC criteria) throws BaseException;

    /**
     * save Channel Records
     * 
     * @param channelCO
     * @return
     * @throws BaseException
     */
    public ChannelCO save(ChannelCO channelCO) throws BaseException;

    /**
     * @param channelCO
     * @return
     */
    public ChannelCO edit(ChannelSC sc) throws BaseException;

    /**
     * @param channelCO
     * @return
     */
    public void delete(ChannelCO channelCO) throws BaseException;

    /**
     * approve ChannelId
     * 
     * @param channelCO
     * @throws BaseException
     */
    public void approve(ChannelCO channelCO) throws BaseException;

    /**
     * tobe Suspended ChannelId
     * 
     * @param channelCO
     * @throws BaseException
     */
    public void tobeSuspended(ChannelCO channelCO) throws BaseException;

    /**
     * suspend ChannelId
     * 
     * @param channelCO
     * @throws BaseException
     */
    public void suspend(ChannelCO channelCO) throws BaseException;

    /**
     * tobe Reactivat ChannelId
     * 
     * @param channelCO
     * @throws BaseException
     */
    public void tobeReactivate(ChannelCO channelCO) throws BaseException;

    /**
     * reactivate ChannelId
     * 
     * @param channelCO
     * @throws BaseException
     */
    public void reactivate(ChannelCO channelCO) throws BaseException;

    /**
     * @param channelCO
     * @return
     */
    public ChannelCO validateChannelId(ChannelCO channelCO) throws BaseException;

    /**
     * on Change UserID
     * 
     * @param channelCO
     * @return
     * @throws BaseException
     */
    public ChannelCO validateUser(ChannelCO channelCO) throws BaseException;

    /**
     * @param channelCO
     * @return
     */
    public ChannelCO applySysParamSettings(ChannelCO channelCO, Boolean isChannelParams);

    /**
     * chanel Validate Usr and Pwd
     * 
     * @param channelCO
     * @return
     *//*
        * public Integer chanelCheckUsrPwd(ChannelCO channelCO) throws
        * BaseException;
        */

    /**
     * generate Key
     * 
     * @param channelCO
     * @return
     * @throws BaseException
     */
    public String generateVarificationHostKey(ChannelCO channelCO) throws BaseException;

    /**
     * return Machine Id List
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public List<ChannelCO> returnVerficationListForGrid(ChannelSC sc) throws BaseException;

    /**
     * return Machine Id list Count
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public int returnVerficationListCountForGrid(ChannelSC sc) throws BaseException;

    /**
     * load Assigned TemplateId ListGrid
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public List<TemplateCO> loadAssignedTemplateIdListGrid(ChannelSC sc) throws BaseException;

    /* *//**
	  * return HashKey
	  * 
	  * @param channelCO
	  * @return
	  * @throws BaseException
	  *//*
	     * public String returnHashKey(ChannelCO channelCO) throws
	     * BaseException;
	     */

    /**
     * return TempId List Count
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public int returnTempIdListCount(ChannelSC sc) throws BaseException;

    /**
     * return TempId List
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public List<GTW_PWS_TMPLT_MASTERVO> returnTempIdList(ChannelSC sc) throws BaseException;

    /**
     * validate user id
     * 
     * @param channelSC
     * @return
     * @throws BaseException
     */
    int validateUserId(ChannelSC channelSC) throws BaseException;

}