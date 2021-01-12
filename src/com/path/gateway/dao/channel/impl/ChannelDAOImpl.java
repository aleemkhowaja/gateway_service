package com.path.gateway.dao.channel.impl;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.OPTVO;
import com.path.gateway.dao.channel.ChannelDAO;
import com.path.gateway.dbmaps.vo.GTW_CHANNELVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNEL_JMSVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_MASTERVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_USRVO;
import com.path.gateway.vo.channel.ChannelCO;
import com.path.gateway.vo.channel.ChannelSC;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * ChannelDAOImpl.java used to
 */
public class ChannelDAOImpl extends BaseDAO implements ChannelDAO
{

    @Override
    public List returnChannelList(ChannelSC criteria) throws DAOException
    {
	DAOHelper.fixGridMaps(criteria, getSqlMap(), "channelMapper.resChannelListMap");
	if(criteria.getSortOrder() == null)
	{
	    criteria.setSortOrder(" ORDER BY CHANNEL_ID DESC");
	}
	if(criteria.getNbRec() == -1)
	{
	    return getSqlMap().queryForList("channelMapper.returnChannelList", criteria);
	}
	else
	{
	    return getSqlMap().queryForList("channelMapper.returnChannelList", criteria, criteria.getRecToskip(),
		    criteria.getNbRec());
	}
    }

    @Override
    public int returnChannelListCount(ChannelSC criteria) throws DAOException
    {
	DAOHelper.fixGridMaps(criteria, getSqlMap(), "channelMapper.resChannelListMap");
	return ((Integer) getSqlMap().queryForObject("channelMapper.returnChannelListCount", criteria)).intValue();
    }

    @Override
    public Integer saveGTW_CHANNELVO(GTW_CHANNELVO gtw_CHANNELVO) throws DAOException
    {
	return (Integer) getSqlMap().insert("channelMapper.insertGTW_CHANNEL", gtw_CHANNELVO);
    }

    @Override
    public ChannelCO edit(ChannelSC channelSC) throws DAOException
    {
	return (ChannelCO) getSqlMap().queryForObject("channelMapper.edit", channelSC);
    }

    @Override
    public List<ChannelCO> returnVerficationListForGrid(ChannelSC criteria) throws DAOException
    {
	DAOHelper.fixGridMaps(criteria, getSqlMap(), "channelMapper.resVerificationListMap");
	if(criteria.getSortOrder() == null)
	{
	    criteria.setSortOrder(" ORDER BY HOST_NAME");
	}
	if(criteria.getNbRec() == -1)
	{
	    return (List<ChannelCO>) getSqlMap().queryForList("channelMapper.returnVerficationListForGrid", criteria);
	}
	else
	{
	    return (List<ChannelCO>) getSqlMap().queryForList("channelMapper.returnVerficationListForGrid", criteria,
		    criteria.getRecToskip(), criteria.getNbRec());
	}
    }

    @Override
    public int returnVerficationListCountForGrid(ChannelSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "channelMapper.resVerificationListMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("channelMapper.returnVerficationListCountForGrid", sc)).intValue();
	return nb;
    }

    @Override
    public void deleteAllVarificationDetails(ChannelCO channelCO) throws DAOException
    {
	getSqlMap().delete("channelMapper.deleteAllVarificationDetails", channelCO);
    }

    @Override
    public void deleteAllTemplateId(ChannelCO channelCO) throws DAOException
    {
	getSqlMap().delete("channelMapper.deleteAllTemplateId", channelCO);
    }

    @Override
    public int returnTempIdListCount(ChannelSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "channelMapper.resTempIdLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("channelMapper.returnTempIdListCount", sc)).intValue();
	return nb;
    }

    @Override
    public List<GTW_PWS_TMPLT_MASTERVO> returnTempIdList(ChannelSC sc) throws DAOException
    {
	if(sc.getSortOrder() == null)
	{
	    sc.setSortOrder(" ORDER BY TEMPLATE_ID");
	}
	DAOHelper.fixGridMaps(sc, getSqlMap(), "channelMapper.resTempIdLkupMap");
	return getSqlMap().queryForList("channelMapper.returnTempIdList", sc, sc.getRecToskip(), sc.getNbRec());
    }

    @Override
    public List<GTW_PWS_TMPLT_USRVO> loadAssignedTemplateIdListGrid(ChannelSC sc) throws DAOException
    {
	return getSqlMap().queryForList("channelMapper.loadAssignedTemplateIdListGrid", sc);
    }

    @Override
    public int validateUserId(ChannelSC channelSC) throws DAOException
    {
	// TODO Auto-generated method stub
	return ((Integer) getSqlMap().queryForObject("channelMapper.validateUserId", channelSC)).intValue();
    }

    @Override
    public BigDecimal returnLatestDisplayOrder(OPTVO optVO) throws DAOException
    {
	BigDecimal latestDisplayOrder = ((BigDecimal) getSqlMap().queryForObject("channelMapper.getLatestDisplayOrder",
		optVO));

	// if null then return one
	if(latestDisplayOrder == null)
	{
	    return BigDecimal.ONE;
	}
	return latestDisplayOrder.add(BigDecimal.ONE);
    }

	@Override
	public Integer saveGTW_CHANNEL_JMSVO(GTW_CHANNEL_JMSVO jsmVO) throws DAOException {
		return (Integer) getSqlMap().insert("channelMapper.saveGTW_CHANNEL_JMS", jsmVO);
	}

}