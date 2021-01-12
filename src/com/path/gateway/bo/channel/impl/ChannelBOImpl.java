package com.path.gateway.bo.channel.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.login.LoginBO;
import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.OPT_EXTENDEDVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.USRVO;
import com.path.gateway.bo.channel.ChannelBO;
import com.path.gateway.bo.channel.ChannelConstant;
import com.path.gateway.bo.common.GatewayCommonConstants;
import com.path.gateway.dao.channel.ChannelDAO;
import com.path.gateway.dbmaps.vo.GTW_CHANNELVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNEL_DETVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNEL_ISO_INT_PARAMSVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNEL_JMSVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_MASTERVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_USRVO;
import com.path.gateway.vo.channel.ChannelCO;
import com.path.gateway.vo.channel.ChannelSC;
import com.path.gateway.vo.channel.TemplateCO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.SecurityUtils;
import com.path.lib.common.util.StringUtil;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * ChannelBOImpl.java used to
 */
public class ChannelBOImpl extends BaseBO implements ChannelBO
{
    private ChannelDAO channelDAO;
    private LoginBO loginBO;

    /**
     * Method used to return List of Channel
     * 
     * @param criteria Search Criteria Parameter
     * @return List Result
     * @throws BaseException
     */
    @Override
    public List returnChannelList(ChannelSC criteria) throws BaseException
    {
	return channelDAO.returnChannelList(criteria);
    }

    /**
     * Method used to return Count of Channel
     * 
     * @param criteria Search Criteria Parameter
     * @return int Result number of Records
     * @throws BaseException
     */
    @Override
    public int returnChannelListCount(ChannelSC criteria) throws BaseException
    {
	return channelDAO.returnChannelListCount(criteria);
    }

    @Override
    public ChannelCO save(ChannelCO channelCO) throws BaseException
    {
	// validate Channel Form Data
	channelCO = validateChannelData(channelCO);
	
	GTW_CHANNELVO gtw_CHANNELVO = channelCO.getGtw_CHANNELVO();
	GTW_CHANNEL_JMSVO gtw_channelJMSVO = channelCO.getChannelJMSVO();

	// set the user id uppercase
	gtw_CHANNELVO.setUSER_ID(gtw_CHANNELVO.getUSER_ID().toUpperCase());

	gtw_CHANNELVO.setSTATUS(ConstantsCommon.ACTIVE_RECORD);

	// Encrypt Password by Pass Encryption Key and set in password Field
	// required for HTTP Client
	if(StringUtil.isNotEmpty(gtw_CHANNELVO.getHTTP_PASSWORD()))
	{
	    gtw_CHANNELVO.setHTTP_PASSWORD(SecurityUtils.encryptAES(ChannelConstant.PASS_ENC_KEY, gtw_CHANNELVO.getHTTP_PASSWORD()));
	}

	if(ConstantsCommon.YES.equals(channelCO.getUpdateMode()))
	{
	    gtw_CHANNELVO.setMODIFIED_BY(channelCO.getUserID());
	    gtw_CHANNELVO.setMODIFIED_DATE(channelCO.getRunningDate());
	    gtw_CHANNELVO.setSERVER_MODIFIED_DATE(commonLibBO.returnSystemDateWithTime());

	    // delete all machine id related to this channel id they will be
	    // inserted again
	    channelDAO.deleteAllTemplateId(channelCO);

	    // delete allvarification Details by channel id
	    channelDAO.deleteAllVarificationDetails(channelCO);

	    // Update Channel Record
	    Integer row = genericDAO.update(gtw_CHANNELVO);
	    
	    if(row == null || row < 1)
	    {
		throw new BOException(MessageCodes.RECORD_CHANGED);
	    }
	    // Update Channel child record Record
		if (null != gtw_channelJMSVO && null != gtw_CHANNELVO.getCHANNEL_ID() && null != gtw_channelJMSVO.getCHANNEL_JMS_ID() && gtw_CHANNELVO.getCOMMUNICATION_PROTOCOL().equals(ChannelConstant.JMS)) {
			gtw_channelJMSVO.setCHANNEL_ID(gtw_CHANNELVO.getCHANNEL_ID());
			Integer childRow = genericDAO.update(gtw_channelJMSVO);
			if (childRow == null || childRow < 1) {
				throw new BOException(MessageCodes.RECORD_CHANGED);
			}
		}
	   // update Audit
	    ChannelCO auditObj = (ChannelCO) channelCO.getAuditObj();
	    auditBO.callAudit(auditObj.getGtw_CHANNELVO(), gtw_CHANNELVO, channelCO.getAuditRefCO());

	}
	else
	{
	    gtw_CHANNELVO.setCREATED_BY(channelCO.getUserID());
	    gtw_CHANNELVO.setCREATED_DATE(channelCO.getRunningDate());
	    gtw_CHANNELVO.setSERVER_CREATED_DATE(commonLibBO.returnSystemDateWithTime());
	    // insert Channel Record
	    channelDAO.saveGTW_CHANNELVO(gtw_CHANNELVO);
	    // genericDAO.insert(imApiChannelsVO);

	    channelCO.setGtw_CHANNELVO(gtw_CHANNELVO);
	    if(StringUtil.isNotEmpty(gtw_CHANNELVO.getCOMMUNICATION_PROTOCOL())	&& gtw_CHANNELVO.getCOMMUNICATION_PROTOCOL().equals(ChannelConstant.JMS)){
			saveJMSDetial(channelCO);
		}
		// insert Audit
	    auditBO.callAudit(null, gtw_CHANNELVO, channelCO.getAuditRefCO());

	}

	// if Communication Protoctol = HTTP and Serveer Type = HTTp Server
	if(StringUtil.isNotEmpty(gtw_CHANNELVO.getCOMMUNICATION_PROTOCOL())
		&& gtw_CHANNELVO.getCOMMUNICATION_PROTOCOL().equals(ChannelConstant.HTTP)
		&& StringUtil.isNotEmpty(gtw_CHANNELVO.getSERVER_TYPE())
		&& gtw_CHANNELVO.getSERVER_TYPE().equals(ChannelConstant.HTTP_SERVER))
	{
	    // save Access Service
	    saveAccessService(channelCO);

	    // save Varification Details
	    saveVarificationDetails(channelCO);
	}
	
	//save Channel Parameters
	if(StringUtil.nullToEmpty(channelCO.getInterfaceType()).equalsIgnoreCase(ChannelConstant.ISO))
	{
	    saveChannelParameters(channelCO);
	}
	
	// insert Audit
	auditBO.insertAudit(channelCO.getAuditRefCO());

	return channelCO;
    }
    
	public void saveJMSDetial(ChannelCO channelCO) throws BaseException {
		GTW_CHANNEL_JMSVO jmsVO = channelCO.getChannelJMSVO();
		jmsVO.setCHANNEL_ID(channelCO.getGtw_CHANNELVO().getCHANNEL_ID());
		int resut=channelDAO.saveGTW_CHANNEL_JMSVO(jmsVO);
	}

    /**
     * Save Access service
     * 
     * @param channelCO
     * @throws BaseException
     */
    private void saveAccessService(ChannelCO channelCO) throws BaseException
    {
	GTW_CHANNELVO imApiChannelsVO = channelCO.getGtw_CHANNELVO();
	GTW_PWS_TMPLT_USRVO imcoPwsTmpltUserVO;

	ArrayList<GTW_PWS_TMPLT_MASTERVO> accessServiceList = channelCO.getAccessServiceList();
	if(accessServiceList != null)
	{
	    for(int j = 0; j < accessServiceList.size(); j++)
	    {
		imcoPwsTmpltUserVO = new GTW_PWS_TMPLT_USRVO();
		imcoPwsTmpltUserVO.setCHANNEL_ID(imApiChannelsVO.getCHANNEL_ID());
		imcoPwsTmpltUserVO.setUSER_ID(imApiChannelsVO.getUSER_ID());
		imcoPwsTmpltUserVO.setTEMPLATE_ID(accessServiceList.get(j).getTEMPLATE_ID());
		genericDAO.insert(imcoPwsTmpltUserVO);
	    }
	}
    }

    /**
     * save Varification Detail
     * 
     * @param channelCO
     * @throws BaseException
     */
    private void saveVarificationDetails(ChannelCO channelCO) throws BaseException
    {

	ChannelCO chanCO;
	GTW_CHANNEL_DETVO lVO;

	ArrayList<ChannelCO> varificationDetailList = channelCO.getVarificationDetailList();

	if(varificationDetailList != null)
	{
	    for(int j = 0; j < varificationDetailList.size(); j++)
	    {
		chanCO = varificationDetailList.get(j);
		lVO = chanCO.getImApiChannelsDetVO();
		lVO.setCHANNEL_ID(channelCO.getGtw_CHANNELVO().getCHANNEL_ID());

		// insert varification Details in GTW_CHANNEL_DETVO table
		genericDAO.insert(lVO);
	    }
	}
    }

    /**
     * save Channel Parameters
     * @param channelCO
     * @throws BaseException
     */
    private void saveChannelParameters(ChannelCO channelCO) throws BaseException
    {
	GTW_CHANNEL_ISO_INT_PARAMSVO gtw_CHANNEL_ISO_INT_PARAMSVO  = channelCO.getGtw_CHANNEL_ISO_INT_PARAMSVO();
	GTW_CHANNELVO gtw_CHANNELVO = channelCO.getGtw_CHANNELVO();
	gtw_CHANNEL_ISO_INT_PARAMSVO.setCHANNEL_ID(gtw_CHANNELVO.getCHANNEL_ID());
	if(gtw_CHANNEL_ISO_INT_PARAMSVO != null)
	{
	    if(gtw_CHANNEL_ISO_INT_PARAMSVO.getGTW_CHANNEL_PARAMS_ID() == null || gtw_CHANNEL_ISO_INT_PARAMSVO.getGTW_CHANNEL_PARAMS_ID() .equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
	    {
		genericDAO.insert(gtw_CHANNEL_ISO_INT_PARAMSVO);
	    }
	    else
	    {
		genericDAO.update(gtw_CHANNEL_ISO_INT_PARAMSVO);
	    }
	}
	
    }
    /**
     * validate Channel Form Data
     * 
     * @param channelCO
     * @return
     */
    private ChannelCO validateChannelData(ChannelCO channelCO)
    {
	GTW_CHANNELVO gtw_CHANNELVO = channelCO.getGtw_CHANNELVO();
	if(StringUtil.isNotEmpty(gtw_CHANNELVO.getCOMMUNICATION_PROTOCOL()))
	{
	    if(gtw_CHANNELVO.getCOMMUNICATION_PROTOCOL().equals(ChannelConstant.HTTP))
	    {
		if(StringUtil.isNotEmpty(gtw_CHANNELVO.getSERVER_TYPE()))
		{
		    if(gtw_CHANNELVO.getSERVER_TYPE().equals(ChannelConstant.HTTP_SERVER))
		    {
			gtw_CHANNELVO.setHTTP_USER("");
			gtw_CHANNELVO.setHTTP_PASSWORD("");
		    }
		    else
		    {
			gtw_CHANNELVO.setUSER_ID("");
			channelCO.getAccessServiceList().clear();
			channelCO.getVarificationDetailList().clear();
		    }
		}
		gtw_CHANNELVO.setIP_ADDRESS("");
		gtw_CHANNELVO.setPORT("");
		gtw_CHANNELVO.setSOCKET_RESTART_INTERVAL(null);

	    }
	    else if(gtw_CHANNELVO.getCOMMUNICATION_PROTOCOL().equals(ChannelConstant.TCP))
	    {
		if(StringUtil.isNotEmpty(gtw_CHANNELVO.getSERVER_TYPE()))
		{
		    if(gtw_CHANNELVO.getSERVER_TYPE().equals(ChannelConstant.TCP_SERVER))
		    {
			//gtw_CHANNELVO.setIP_ADDRESS("");
		    }
		    else
		    {

		    }
		}
		gtw_CHANNELVO.setHTTP_USER("");
		gtw_CHANNELVO.setHTTP_PASSWORD("");
		gtw_CHANNELVO.setUSER_ID("");
		channelCO.getAccessServiceList().clear();
		channelCO.getVarificationDetailList().clear();
		gtw_CHANNELVO.setHTTP_REQUEST_TIME_OUT(null);
	    }
	}

	if(gtw_CHANNELVO.getPARALLELISM_CONTROL() == null
		|| gtw_CHANNELVO.getPARALLELISM_CONTROL().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
	{
	    //gtw_CHANNELVO.setPARALLELISM_CONTROL(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE);
	}
	if(gtw_CHANNELVO.getINTERFACE_CODE() == null
		|| gtw_CHANNELVO.getINTERFACE_CODE().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
	{
	    gtw_CHANNELVO.setINTERFACE_CODE(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE);
	}
	if(StringUtil.isNotEmpty(gtw_CHANNELVO.getACTIVE_QUEUE_YN()) && gtw_CHANNELVO.getACTIVE_QUEUE_YN().equals(GatewayCommonConstants.NO))
	{
	    gtw_CHANNELVO.setPARALLELISM_CONTROL(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE);
	}

	channelCO.setGtw_CHANNELVO(gtw_CHANNELVO);
	return channelCO;
    }

    @Override
    public ChannelCO edit(ChannelSC sc) throws BaseException
    {
	ChannelCO channelCO = channelDAO.edit(sc);
	return channelCO;
    }

    @Override
    public void delete(ChannelCO channelCO) throws BaseException
    {
	Date systemDate = commonLibBO.returnSystemDateWithTime();
	channelCO.getGtw_CHANNELVO().setDELETED_BY(channelCO.getUserID());
	channelCO.getGtw_CHANNELVO().setDELETED_DATE(channelCO.getRunningDate());
	channelCO.getGtw_CHANNELVO().setSTATUS(ChannelConstant.STATUS_DELETED);
	channelCO.getGtw_CHANNELVO().setSERVER_DELETED_DATE(systemDate);

	GTW_CHANNELVO oldAuditVO = (GTW_CHANNELVO) genericDAO.selectByPK(channelCO.getGtw_CHANNELVO());
	Integer update = genericDAO.update(channelCO.getGtw_CHANNELVO());
	if(update == null || update < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}

	// Delete All OPTs
	deleteChannelOpts(channelCO);

	GTW_CHANNELVO newAuditVO = new GTW_CHANNELVO();
	PathPropertyUtil.copyProperties(oldAuditVO, newAuditVO);

	newAuditVO.setDELETED_BY(channelCO.getUserID());
	newAuditVO.setDELETED_DATE(channelCO.getRunningDate());
	newAuditVO.setSERVER_DELETED_DATE(systemDate);
	newAuditVO.setSTATUS(ChannelConstant.STATUS_DELETED);

	auditBO.callAudit(oldAuditVO, newAuditVO, channelCO.getAuditRefCO());
	auditBO.insertAudit(channelCO.getAuditRefCO());

    }

    @Override
    public void approve(ChannelCO channelCO) throws BaseException
    {
	Date systemDate = commonLibBO.returnSystemDateWithTime();
	channelCO.getGtw_CHANNELVO().setAPPROVED_BY(channelCO.getUserID());
	channelCO.getGtw_CHANNELVO().setAPPROVED_DATE(channelCO.getRunningDate());
	channelCO.getGtw_CHANNELVO().setSTATUS(ChannelConstant.STATUS_APPROVED);
	channelCO.getGtw_CHANNELVO().setSERVER_APPROVED_DATE(systemDate);
	
	// Encrypt Password by Pass Encryption Key and set in password Field
	// required for HTTP Client
	if(StringUtil.isNotEmpty(channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()))
	{
	    channelCO.getGtw_CHANNELVO().setHTTP_PASSWORD(SecurityUtils.encryptAES(ChannelConstant.PASS_ENC_KEY, channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()));
	}
	
	GTW_CHANNELVO oldAuditVO = (GTW_CHANNELVO) genericDAO.selectByPK(channelCO.getGtw_CHANNELVO());
	Integer update = genericDAO.update(channelCO.getGtw_CHANNELVO());
	if(update == null || update < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}

	// insert OPTs
	// insertChannelOpts(channelCO);

	GTW_CHANNELVO newAuditVO = new GTW_CHANNELVO();
	PathPropertyUtil.copyProperties(oldAuditVO, newAuditVO);

	newAuditVO.setAPPROVED_BY(channelCO.getUserID());
	newAuditVO.setAPPROVED_DATE(channelCO.getRunningDate());
	newAuditVO.setSERVER_APPROVED_DATE(systemDate);
	newAuditVO.setSTATUS(ChannelConstant.STATUS_APPROVED);

	auditBO.callAudit(oldAuditVO, newAuditVO, channelCO.getAuditRefCO());
	auditBO.insertAudit(channelCO.getAuditRefCO());
    }

    /**
     * insert Channel OPTs
     * 
     * @param channelCO
     * @throws BaseException
     */
    private void insertChannelOpts(ChannelCO channelCO) throws BaseException
    {

	OPTVO parentOptVo = new OPTVO();
	/**
	 * Used as criteria to deletion operation
	 */
	parentOptVo.setPARENT_REF(ChannelConstant.CHANNEL_PARENT_REF);

	/**
	 * Set the parent ref here again to reset the old value
	 */
	parentOptVo.setPROG_REF(ChannelConstant.CHANNEL_PROG_REF + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());

	// get order
	/**
	 * Create Batch Menu Reference
	 */
	parentOptVo.setAPP_NAME(channelCO.getAppName());
	parentOptVo.setPROG_DESC(channelCO.getGtw_CHANNELVO().getDESCRIPTION());
	parentOptVo.setPROG_DESC_FR(channelCO.getGtw_CHANNELVO().getDESCRIPTION());
	parentOptVo.setPROG_DESC_ARAB(channelCO.getGtw_CHANNELVO().getDESCRIPTION());
	parentOptVo.setCATEG_ID(new BigDecimal(6));
	parentOptVo.setMENU_TITLE_ENG(channelCO.getGtw_CHANNELVO().getDESCRIPTION());
	parentOptVo.setMENU_TITLE_FR(channelCO.getGtw_CHANNELVO().getDESCRIPTION());
	parentOptVo.setMENU_TITLE_ARAB(channelCO.getGtw_CHANNELVO().getDESCRIPTION());

	parentOptVo.setPROG_ORDER(BigDecimal.ONE);
	parentOptVo.setAUDIT_RETRIEVE(BigDecimal.ONE.toString());
	parentOptVo.setDISP_ORDER(channelDAO.returnLatestDisplayOrder(parentOptVo));
	parentOptVo.setPROG_TYPE(ConstantsCommon.PROG_TYPE_PROGRAM);

	// insert Parent OPT
	genericDAO.insert(parentOptVo);

	AXSVO parentAxs = new AXSVO();
	parentAxs.setUSER_ID(channelCO.getUserID());
	parentAxs.setCOMP_CODE(channelCO.getCompCode());
	parentAxs.setBRANCH_CODE(channelCO.getBranchCode());
	parentAxs.setAPP_NAME(channelCO.getAppName());
	// logged in user @check with paty
	parentAxs.setCREATED_BY(channelCO.getUserID());

	/**
	 * Note: Granting Privilege will be occurs only in Approval.
	 */
	parentAxs.setSTATUS(ChannelConstant.STATUS_APPROVED);
	parentAxs.setDIRECT_ACCESS(channelCO.getUserID());
	parentAxs.setDATE_CREATED(channelCO.getGtw_CHANNELVO().getCREATED_DATE());
	parentAxs.setPROG_REF(parentOptVo.getPROG_REF());
	genericDAO.insert(parentAxs);

	OPTVO childOptVo = new OPTVO();
	/**
	 * set parent prog ref
	 */
	childOptVo.setPARENT_REF(parentOptVo.getPROG_REF());

	/**
	 * Set prog ref
	 */
	childOptVo
		.setPROG_REF(ChannelConstant.CHANNEL_CTRL_PROG_REF + "" + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());

	// get order
	/**
	 * Create Batch Menu Reference
	 */
	childOptVo.setAPP_NAME(channelCO.getAppName());
	childOptVo.setPROG_DESC(ChannelConstant.CHANNEL_CTRL_TITLE);
	childOptVo.setPROG_DESC_FR(ChannelConstant.CHANNEL_CTRL_TITLE);
	childOptVo.setPROG_DESC_ARAB(ChannelConstant.CHANNEL_CTRL_TITLE);

	childOptVo.setMENU_TITLE_ENG(ChannelConstant.CHANNEL_CTRL_TITLE);
	childOptVo.setMENU_TITLE_FR(ChannelConstant.CHANNEL_CTRL_TITLE);
	childOptVo.setMENU_TITLE_ARAB(ChannelConstant.CHANNEL_CTRL_TITLE);

	childOptVo.setPROG_ORDER(BigDecimal.ONE);
	childOptVo.setAUDIT_RETRIEVE(BigDecimal.ONE.toString());
	childOptVo.setDISP_ORDER(channelDAO.returnLatestDisplayOrder(childOptVo));
	childOptVo.setPROG_TYPE(ConstantsCommon.PROG_TYPE_PROGRAM);

	// insert child OPT
	genericDAO.insert(childOptVo);

	// Create the PROG REF URL by inserting a row in OPT_EXTENED
	OPT_EXTENDEDVO extendedvo = new OPT_EXTENDEDVO();
	extendedvo.setAPP_NAME(channelCO.getAppName());
	extendedvo.setPROG_REF(childOptVo.getPROG_REF());

	extendedvo.setIV_CRUD(ChannelConstant.MAINTENANCE_IVCRUD);

	// set channel control url
	extendedvo.setOPT_URL(ChannelConstant.CHANNEL_CTRL_URL);
	// insert OPT_EXTENDED
	genericDAO.insert(extendedvo);

	AXSVO childAxs = new AXSVO();
	childAxs.setUSER_ID(channelCO.getUserID());
	childAxs.setCOMP_CODE(channelCO.getCompCode());
	childAxs.setBRANCH_CODE(channelCO.getBranchCode());
	childAxs.setAPP_NAME(channelCO.getAppName());
	childAxs.setCREATED_BY(channelCO.getUserID());

	/**
	 * Note: Granting Privilege will be occurs only in Approval.
	 */
	childAxs.setSTATUS(ChannelConstant.STATUS_APPROVED);
	childAxs.setDIRECT_ACCESS(channelCO.getUserID());
	childAxs.setDATE_CREATED(channelCO.getGtw_CHANNELVO().getCREATED_DATE());
	childAxs.setPROG_REF(childOptVo.getPROG_REF());
	genericDAO.insert(childAxs);

	childOptVo = new OPTVO();
	/**
	 * set parent prog ref
	 */
	childOptVo.setPARENT_REF(parentOptVo.getPROG_REF());

	/**
	 * Set prog ref
	 */
	childOptVo.setPROG_REF(
		ChannelConstant.CHANNEL_MONITOR_PROG_REF + "" + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());

	// get order
	/**
	 * Create Batch Menu Reference
	 */
	childOptVo.setAPP_NAME(channelCO.getAppName());
	childOptVo.setPROG_DESC(ChannelConstant.CHANNEL_MONITOR_TITLE);
	childOptVo.setPROG_DESC_FR(ChannelConstant.CHANNEL_MONITOR_TITLE);
	childOptVo.setPROG_DESC_ARAB(ChannelConstant.CHANNEL_MONITOR_TITLE);

	childOptVo.setMENU_TITLE_ENG(ChannelConstant.CHANNEL_MONITOR_TITLE);
	childOptVo.setMENU_TITLE_FR(ChannelConstant.CHANNEL_MONITOR_TITLE);
	childOptVo.setMENU_TITLE_ARAB(ChannelConstant.CHANNEL_MONITOR_TITLE);

	childOptVo.setPROG_ORDER(BigDecimal.ONE);
	childOptVo.setAUDIT_RETRIEVE(BigDecimal.ONE.toString());
	childOptVo.setDISP_ORDER(channelDAO.returnLatestDisplayOrder(childOptVo));
	childOptVo.setPROG_TYPE(ConstantsCommon.PROG_TYPE_PROGRAM);

	// insert child OPT
	genericDAO.insert(childOptVo);

	// Create the PROG REF URL by inserting a row in OPT_EXTENED
	extendedvo = new OPT_EXTENDEDVO();
	extendedvo.setAPP_NAME(channelCO.getAppName());
	extendedvo.setPROG_REF(childOptVo.getPROG_REF());

	extendedvo.setIV_CRUD(ChannelConstant.MAINTENANCE_IVCRUD);

	// set channel Monitor URL
	extendedvo.setOPT_URL(ChannelConstant.CHANNEL_MONITOR_URL);
	// insert OPT_EXTENDED
	genericDAO.insert(extendedvo);

	childAxs = new AXSVO();
	childAxs.setUSER_ID(channelCO.getUserID());
	childAxs.setCOMP_CODE(channelCO.getCompCode());
	childAxs.setBRANCH_CODE(channelCO.getBranchCode());
	childAxs.setAPP_NAME(channelCO.getAppName());
	childAxs.setCREATED_BY(channelCO.getUserID());

	/**
	 * Note: Granting Privilege will be occurs only in Approval.
	 */
	childAxs.setSTATUS(ChannelConstant.STATUS_APPROVED);
	childAxs.setDIRECT_ACCESS(channelCO.getUserID());
	childAxs.setDATE_CREATED(channelCO.getGtw_CHANNELVO().getCREATED_DATE());
	childAxs.setPROG_REF(childOptVo.getPROG_REF());
	genericDAO.insert(childAxs);

    }

    @Override
    public void tobeSuspended(ChannelCO channelCO) throws BaseException
    {
	// Encrypt Password by Pass Encryption Key and set in password Field
	// required for HTTP Client
	if(StringUtil.isNotEmpty(channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()))
	{
	    channelCO.getGtw_CHANNELVO().setHTTP_PASSWORD(SecurityUtils.encryptAES(ChannelConstant.PASS_ENC_KEY, channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()));
	}
		
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS_BY(channelCO.getUserID());
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS_DATE(channelCO.getRunningDate());
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS(ChannelConstant.STATUS_TOBESUSPENDED);

	GTW_CHANNELVO oldAuditVO = (GTW_CHANNELVO) genericDAO.selectByPK(channelCO.getGtw_CHANNELVO());
	Integer update = genericDAO.update(channelCO.getGtw_CHANNELVO());
	if(update == null || update < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}

	// Delete All OPTs
	// deleteChannelOpts(channelCO);
	GTW_CHANNELVO newAuditVO = new GTW_CHANNELVO();
	PathPropertyUtil.copyProperties(oldAuditVO, newAuditVO);

	newAuditVO.setTO_BE_STATUS_BY(channelCO.getUserID());
	newAuditVO.setTO_BE_STATUS_DATE(channelCO.getRunningDate());
	newAuditVO.setTO_BE_STATUS(ChannelConstant.STATUS_TOBESUSPENDED);

	auditBO.callAudit(oldAuditVO, newAuditVO, channelCO.getAuditRefCO());
	auditBO.insertAudit(channelCO.getAuditRefCO());
    }

    @Override
    public void suspend(ChannelCO channelCO) throws BaseException
    {
	// Encrypt Password by Pass Encryption Key and set in password Field
	// required for HTTP Client
	if(StringUtil.isNotEmpty(channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()))
	{
	    channelCO.getGtw_CHANNELVO().setHTTP_PASSWORD(SecurityUtils.encryptAES(ChannelConstant.PASS_ENC_KEY, channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()));
	}
		
	Date systemDate = commonLibBO.returnSystemDateWithTime();
	channelCO.getGtw_CHANNELVO().setSUSPENDED_BY(channelCO.getUserID());
	channelCO.getGtw_CHANNELVO().setSUSPENDED_DATE(channelCO.getRunningDate());
	channelCO.getGtw_CHANNELVO().setSTATUS(ChannelConstant.STATUS_SUSPENDED);
	channelCO.getGtw_CHANNELVO().setSERVER_SUSPENDED_DATE(systemDate);

	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS_BY("");
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS("");

	GTW_CHANNELVO oldAuditVO = (GTW_CHANNELVO) genericDAO.selectByPK(channelCO.getGtw_CHANNELVO());
	Integer update = genericDAO.update(channelCO.getGtw_CHANNELVO());
	if(update == null || update < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}

	// Delete All OPTs
	// deleteChannelOpts(channelCO);

	GTW_CHANNELVO newAuditVO = new GTW_CHANNELVO();
	PathPropertyUtil.copyProperties(oldAuditVO, newAuditVO);

	newAuditVO.setAPPROVED_BY(channelCO.getUserID());
	newAuditVO.setAPPROVED_DATE(channelCO.getRunningDate());
	newAuditVO.setSERVER_APPROVED_DATE(systemDate);
	newAuditVO.setSTATUS(ChannelConstant.STATUS_SUSPENDED);

	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS_BY("");
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS("");

	auditBO.callAudit(oldAuditVO, newAuditVO, channelCO.getAuditRefCO());
	auditBO.insertAudit(channelCO.getAuditRefCO());
    }

    /**
     * delete all OPT/OPT Extended/AXS
     * 
     * @param channelCO
     * @throws BaseException
     */
    private void deleteChannelOpts(ChannelCO channelCO) throws BaseException
    {
	// Start first delete Child Opt
	AXSVO axs = new AXSVO();
	axs.setAPP_NAME(channelCO.getAppName());
	axs.setUSER_ID(channelCO.getUserID());
	axs.setCOMP_CODE(channelCO.getCompCode());
	axs.setBRANCH_CODE(channelCO.getBranchCode());
	axs.setPROG_REF(ChannelConstant.CHANNEL_CTRL_PROG_REF + "" + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());

	// delete first Child OPT AXS
	genericDAO.delete(axs);

	OPT_EXTENDEDVO opt_EXTENDEDVO = new OPT_EXTENDEDVO();
	opt_EXTENDEDVO
		.setPROG_REF(ChannelConstant.CHANNEL_CTRL_PROG_REF + "" + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());
	opt_EXTENDEDVO.setAPP_NAME(channelCO.getAppName());

	// delete first child Opt Extended
	genericDAO.delete(opt_EXTENDEDVO);

	OPTVO optvo = new OPTVO();
	optvo.setPROG_REF(ChannelConstant.CHANNEL_CTRL_PROG_REF + "" + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());
	optvo.setAPP_NAME(channelCO.getAppName());

	// delete first child Opt
	genericDAO.delete(optvo);
	// End first delete Child Opt

	// Start second delete Child Opt

	axs = new AXSVO();
	axs.setAPP_NAME(channelCO.getAppName());
	axs.setUSER_ID(channelCO.getUserID());
	axs.setCOMP_CODE(channelCO.getCompCode());
	axs.setBRANCH_CODE(channelCO.getBranchCode());
	axs.setPROG_REF(ChannelConstant.CHANNEL_MONITOR_PROG_REF + "" + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());

	// delete first Child OPT AXS
	genericDAO.delete(axs);

	opt_EXTENDEDVO = new OPT_EXTENDEDVO();
	opt_EXTENDEDVO.setPROG_REF(
		ChannelConstant.CHANNEL_MONITOR_PROG_REF + "" + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());
	opt_EXTENDEDVO.setAPP_NAME(channelCO.getAppName());

	// delete first child Opt Extended
	genericDAO.delete(opt_EXTENDEDVO);

	optvo = new OPTVO();
	optvo.setPROG_REF(ChannelConstant.CHANNEL_MONITOR_PROG_REF + "" + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());
	optvo.setAPP_NAME(channelCO.getAppName());

	// delete first child Opt
	genericDAO.delete(optvo);
	// End second delete Child Opt

	// Start delete Parent Opt

	axs = new AXSVO();
	axs.setAPP_NAME(channelCO.getAppName());
	axs.setUSER_ID(channelCO.getUserID());
	axs.setCOMP_CODE(channelCO.getCompCode());
	axs.setBRANCH_CODE(channelCO.getBranchCode());
	axs.setPROG_REF(ChannelConstant.CHANNEL_PROG_REF + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());
	// delete parent OPT AXS
	genericDAO.delete(axs);

	optvo = new OPTVO();
	optvo.setPROG_REF(ChannelConstant.CHANNEL_PROG_REF + channelCO.getGtw_CHANNELVO().getCHANNEL_ID());
	optvo.setAPP_NAME(channelCO.getAppName());

	// delete Parent Opt
	genericDAO.delete(optvo);

	// End delete Parent Opt
    }

    @Override
    public void tobeReactivate(ChannelCO channelCO) throws BaseException
    {
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS_BY(channelCO.getUserID());
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS_DATE(channelCO.getRunningDate());
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS(ChannelConstant.STATUS_TOBEREACTIVATE);
	
	// Encrypt Password by Pass Encryption Key and set in password Field
	// required for HTTP Client
	if(StringUtil.isNotEmpty(channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()))
	{
	    channelCO.getGtw_CHANNELVO().setHTTP_PASSWORD(SecurityUtils.encryptAES(ChannelConstant.PASS_ENC_KEY, channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()));
	}
	
	GTW_CHANNELVO oldAuditVO = (GTW_CHANNELVO) genericDAO.selectByPK(channelCO.getGtw_CHANNELVO());
	Integer update = genericDAO.update(channelCO.getGtw_CHANNELVO());
	if(update == null || update < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}

	GTW_CHANNELVO newAuditVO = new GTW_CHANNELVO();
	PathPropertyUtil.copyProperties(oldAuditVO, newAuditVO);
	newAuditVO.setTO_BE_STATUS_BY(channelCO.getUserID());
	newAuditVO.setTO_BE_STATUS_DATE(channelCO.getRunningDate());
	newAuditVO.setTO_BE_STATUS(ChannelConstant.STATUS_TOBEREACTIVATE);
	auditBO.callAudit(oldAuditVO, newAuditVO, channelCO.getAuditRefCO());
	auditBO.insertAudit(channelCO.getAuditRefCO());
    }

    @Override
    public void reactivate(ChannelCO channelCO) throws BaseException
    {
	// Encrypt Password by Pass Encryption Key and set in password Field
	// required for HTTP Client
	if(StringUtil.isNotEmpty(channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()))
	{
	    channelCO.getGtw_CHANNELVO().setHTTP_PASSWORD(SecurityUtils.encryptAES(ChannelConstant.PASS_ENC_KEY, channelCO.getGtw_CHANNELVO().getHTTP_PASSWORD()));
	}
		
	Date systemDate = commonLibBO.returnSystemDateWithTime();
	channelCO.getGtw_CHANNELVO().setREACTIVATED_BY(channelCO.getUserID());
	channelCO.getGtw_CHANNELVO().setREACTIVATED_DATE(channelCO.getRunningDate());
	channelCO.getGtw_CHANNELVO().setSTATUS(ChannelConstant.STATUS_APPROVED);
	channelCO.getGtw_CHANNELVO().setSERVER_REACTIVATED_DATE(systemDate);

	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS_BY("");
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS("");

	GTW_CHANNELVO oldAuditVO = (GTW_CHANNELVO) genericDAO.selectByPK(channelCO.getGtw_CHANNELVO());
	Integer update = genericDAO.update(channelCO.getGtw_CHANNELVO());
	if(update == null || update < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}

	GTW_CHANNELVO newAuditVO = new GTW_CHANNELVO();
	PathPropertyUtil.copyProperties(oldAuditVO, newAuditVO);

	newAuditVO.setAPPROVED_BY(channelCO.getUserID());
	newAuditVO.setAPPROVED_DATE(channelCO.getRunningDate());
	newAuditVO.setSERVER_APPROVED_DATE(systemDate);
	newAuditVO.setSTATUS(ChannelConstant.STATUS_APPROVED);

	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS_BY("");
	channelCO.getGtw_CHANNELVO().setTO_BE_STATUS("");

	auditBO.callAudit(oldAuditVO, newAuditVO, channelCO.getAuditRefCO());
	auditBO.insertAudit(channelCO.getAuditRefCO());
    }

    @Override
    public ChannelCO validateChannelId(ChannelCO channelCO) throws BaseException
    {
	GTW_CHANNELVO imApiChannelsVO = channelCO.getGtw_CHANNELVO();
	GTW_CHANNELVO channelVO = (GTW_CHANNELVO) genericDAO.selectByPK(imApiChannelsVO);
	if(channelVO != null)
	{
	    throw new BOException(MessageCodes.CHANNEL_ID_ALREADY_DEFINED);
	}
	return channelCO;
    }

    @Override
    public ChannelCO validateUser(ChannelCO channelCO) throws BaseException
    {
	USRVO userVO = new USRVO();
	userVO.setUSER_ID(channelCO.getGtw_CHANNELVO().getUSER_ID().toUpperCase());
	USRVO userVO1 = (USRVO) genericDAO.selectByPK(userVO);
	if(userVO1 == null)
	{
	    throw new BOException(MessageCodes.INCORRECT_CHANNEL_USER_ID);
	}
	return channelCO;
    }

    @Override
    public ChannelCO applySysParamSettings(ChannelCO channelCO, Boolean isChannelParams)
    {
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementMap = channelCO.getElementMap();

	SYS_PARAM_SCREEN_DISPLAYVO sysParam = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO mandatorySysParam = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO notMandatorySysParam = new SYS_PARAM_SCREEN_DISPLAYVO();
	
	SYS_PARAM_SCREEN_DISPLAYVO visibleSysParam = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO inVisibleSysParam = new SYS_PARAM_SCREEN_DISPLAYVO();
	
	
	SYS_PARAM_SCREEN_DISPLAYVO channelISOIntParams = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO textFieldISOIntParams = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO checkBoxISOIntParams = new SYS_PARAM_SCREEN_DISPLAYVO();
	
	//channel Parameters show/Hide
	if(isChannelParams != null && isChannelParams)
	{
	    if(StringUtil.nullToEmpty(channelCO.getInterfaceType()).equalsIgnoreCase(ChannelConstant.ISO))
	    {
		channelISOIntParams.setIS_VISIBLE(BigDecimal.ONE);
		textFieldISOIntParams.setIS_VISIBLE(BigDecimal.ONE);
		checkBoxISOIntParams.setIS_VISIBLE(BigDecimal.ONE);
		textFieldISOIntParams.setIS_MANDATORY(BigDecimal.ONE);
		checkBoxISOIntParams.setIS_MANDATORY(BigDecimal.ZERO);
	    }
	    else
	    {
		channelISOIntParams.setIS_VISIBLE(BigDecimal.ZERO);
		textFieldISOIntParams.setIS_VISIBLE(BigDecimal.ZERO);
		checkBoxISOIntParams.setIS_VISIBLE(BigDecimal.ZERO);
		textFieldISOIntParams.setIS_MANDATORY(BigDecimal.ZERO);
		checkBoxISOIntParams.setIS_MANDATORY(BigDecimal.ZERO);
	    }
	    elementMap.put("channelIsoInterfacepParams", channelISOIntParams);
	    elementMap.put("engineWorkerNumber", textFieldISOIntParams);
	    elementMap.put("engineAddLogInHandler", checkBoxISOIntParams);
		 
	    elementMap.put("engineIdleTimeout", textFieldISOIntParams);
	    elementMap.put("engineAddEchoMessageListener", checkBoxISOIntParams);
		 
	    elementMap.put("engineAcceptorWorkingNumber", textFieldISOIntParams);
	    elementMap.put("engineLogFieldDescription", checkBoxISOIntParams);
	 
	    elementMap.put("engineCharSet", textFieldISOIntParams);
	    elementMap.put("engineLogSensitiveData", checkBoxISOIntParams);
		 
	    elementMap.put("engineReplyOnError", checkBoxISOIntParams);
	    elementMap.put("taskContainerSession", textFieldISOIntParams);
	    elementMap.put("taskContainerConsumer", textFieldISOIntParams);
	    elementMap.put("taskContainerDestination", textFieldISOIntParams);
	}
	
	sysParam.setIS_READONLY(BigDecimal.ONE);
	mandatorySysParam.setIS_MANDATORY(BigDecimal.ONE);
	notMandatorySysParam.setIS_MANDATORY(BigDecimal.ZERO);
	mandatorySysParam.setIS_VISIBLE(BigDecimal.ONE);
	notMandatorySysParam.setIS_VISIBLE(BigDecimal.ZERO);
	
	visibleSysParam.setIS_VISIBLE(BigDecimal.ONE);
	inVisibleSysParam.setIS_VISIBLE(BigDecimal.ZERO);
	
	elementMap.put("channel_id", sysParam);
	elementMap.put("interfaceDescription", sysParam);
	
	if(channelCO.getGtw_CHANNELVO() != null && StringUtil.isNotEmpty(channelCO.getGtw_CHANNELVO().getSERVER_TYPE()))
	{
	    sysParam = new SYS_PARAM_SCREEN_DISPLAYVO();
	    
	    if(channelCO.getGtw_CHANNELVO().getSERVER_TYPE().equals(ChannelConstant.TCP_CLIENT))
	    {
		//elementMap.put("ip_id", mandatorySysParam);
		elementMap.put("port_id", mandatorySysParam);
		elementMap.put("endPoint_id", notMandatorySysParam);
		elementMap.put("httpUsername", notMandatorySysParam);
		elementMap.put("httpPassword", notMandatorySysParam);
		elementMap.put("userId", notMandatorySysParam);
//		elementMap.put("lookuptxt_template_id", notMandatorySysParam);
		
		elementMap.put("communication_protocol_http", inVisibleSysParam);
		elementMap.put("communication_protocol_tcp", visibleSysParam);
		elementMap.put("machineIDGridrDiv", inVisibleSysParam);
		
		elementMap.put("htp_client", inVisibleSysParam);
		elementMap.put("htp_server", inVisibleSysParam);
		elementMap.put("comm_tcp", visibleSysParam);
		
		elementMap.put("tcp_client", visibleSysParam);
		elementMap.put("http_client_basic_auth", inVisibleSysParam);
		
	    }
	    else if(channelCO.getGtw_CHANNELVO().getSERVER_TYPE().equals(ChannelConstant.TCP_SERVER))
	    {
		//elementMap.put("ip_id", notMandatorySysParam);
		elementMap.put("port_id", mandatorySysParam );
		elementMap.put("endPoint_id", notMandatorySysParam);
		elementMap.put("httpUsername", notMandatorySysParam);
		elementMap.put("httpPassword", notMandatorySysParam);
		elementMap.put("userId", notMandatorySysParam);
//		elementMap.put("lookuptxt_template_id", notMandatorySysParam);
		
		elementMap.put("communication_protocol_http", inVisibleSysParam);
		elementMap.put("communication_protocol_tcp", visibleSysParam);
		elementMap.put("machineIDGridrDiv", inVisibleSysParam);
		
		elementMap.put("htp_client", inVisibleSysParam);
		elementMap.put("htp_server", inVisibleSysParam);
		elementMap.put("comm_tcp", visibleSysParam);
		elementMap.put("tcp_client", inVisibleSysParam);
		elementMap.put("http_client_basic_auth", inVisibleSysParam);
	    }
	    else if(channelCO.getGtw_CHANNELVO().getSERVER_TYPE().equals(ChannelConstant.HTTP_CLIENT))
	    {
		//elementMap.put("ip_id", notMandatorySysParam);
		elementMap.put("port_id", notMandatorySysParam);
		elementMap.put("endPoint_id", visibleSysParam);
		elementMap.put("httpUsername", mandatorySysParam);
		elementMap.put("httpPassword", mandatorySysParam);
		elementMap.put("userId", notMandatorySysParam);
//		elementMap.put("lookuptxt_template_id", notMandatorySysParam);
		
		elementMap.put("communication_protocol_http", visibleSysParam);
		elementMap.put("communication_protocol_tcp", inVisibleSysParam);
		elementMap.put("machineIDGridrDiv", inVisibleSysParam);
		
		elementMap.put("htp_client", visibleSysParam);
		elementMap.put("htp_server", inVisibleSysParam);
		elementMap.put("comm_tcp", inVisibleSysParam);
		elementMap.put("tcp_client", inVisibleSysParam);
		elementMap.put("http_client_basic_auth", visibleSysParam);
		
	    }
	    else if(channelCO.getGtw_CHANNELVO().getSERVER_TYPE().equals(ChannelConstant.HTTP_SERVER))
	    {
		//elementMap.put("ip_id", notMandatorySysParam);
		elementMap.put("port_id", notMandatorySysParam);
		elementMap.put("endPoint_id", notMandatorySysParam);
		elementMap.put("httpUsername", notMandatorySysParam);
		elementMap.put("httpPassword", notMandatorySysParam);
		elementMap.put("userId", mandatorySysParam);
//		elementMap.put("lookuptxt_template_id", mandatorySysParam);
		
		elementMap.put("communication_protocol_http", visibleSysParam);
		elementMap.put("communication_protocol_tcp", inVisibleSysParam);
		elementMap.put("machineIDGridrDiv", visibleSysParam);
		
		elementMap.put("htp_server", visibleSysParam);
		elementMap.put("htp_client", inVisibleSysParam);
		elementMap.put("comm_tcp", inVisibleSysParam);
		
		elementMap.put("tcp_client", inVisibleSysParam);
		elementMap.put("http_client_basic_auth", inVisibleSysParam);
	    }
	}

		SYS_PARAM_SCREEN_DISPLAYVO jmsShowFields = new SYS_PARAM_SCREEN_DISPLAYVO();
		SYS_PARAM_SCREEN_DISPLAYVO jmsHiddenFields = new SYS_PARAM_SCREEN_DISPLAYVO();
		SYS_PARAM_SCREEN_DISPLAYVO jmsNonMandatoryFields = new SYS_PARAM_SCREEN_DISPLAYVO();
		
		if (null != channelCO.getGtw_CHANNELVO() && null != channelCO.getGtw_CHANNELVO().getCOMMUNICATION_PROTOCOL()
				&& ChannelConstant.JMS_COMMUNICATION_TYPE
						.equals(channelCO.getGtw_CHANNELVO().getCOMMUNICATION_PROTOCOL())) {
			jmsShowFields.setIS_VISIBLE(BigDecimal.ONE);
			jmsShowFields.setIS_MANDATORY(BigDecimal.ONE);
			jmsHiddenFields.setIS_MANDATORY(BigDecimal.ZERO);
			jmsHiddenFields.setIS_VISIBLE(BigDecimal.ZERO);
			jmsNonMandatoryFields.setIS_VISIBLE(BigDecimal.ONE);
			jmsNonMandatoryFields.setIS_MANDATORY(BigDecimal.ZERO);
		} else {
			jmsShowFields.setIS_VISIBLE(BigDecimal.ZERO);
			jmsShowFields.setIS_MANDATORY(BigDecimal.ZERO);
			jmsHiddenFields.setIS_MANDATORY(BigDecimal.ONE);
			jmsHiddenFields.setIS_VISIBLE(BigDecimal.ONE);
			jmsNonMandatoryFields.setIS_VISIBLE(BigDecimal.ZERO);
			jmsNonMandatoryFields.setIS_MANDATORY(BigDecimal.ZERO);
		}
		elementMap.put("queueName", jmsShowFields);
		elementMap.put("jmsUserName", jmsShowFields);
		elementMap.put("jmsUserPassword", jmsShowFields);
		elementMap.put("jmsConsumerNumber", jmsShowFields);
		elementMap.put("jmsProducerNumber", jmsShowFields);
		elementMap.put("jmsProvider", jmsShowFields);	
		elementMap.put("jmsRetryNumber", jmsNonMandatoryFields);
		elementMap.put("interface_id", jmsHiddenFields);
		elementMap.put("lookuptxt_interface_id", jmsHiddenFields);
		elementMap.put("interfaceDescription", jmsHiddenFields);
		elementMap.put("serverType", jmsHiddenFields);	
		elementMap.put("serverTypeLbl", jmsHiddenFields);
		elementMap.put("communication_protocol_tcp", jmsHiddenFields);
		return channelCO;
    }
    
   
    @Override
    public String generateVarificationHostKey(ChannelCO channelCO) throws BaseException
    {
	String thePass = null, encChar = null;
	try
	{
	    thePass = new String(channelCO.getGtw_CHANNELVO().getCHANNEL_ID() + channelCO.getGtw_CHANNELVO()
		    .getUSER_ID().toUpperCase() /*
						 * + channelCO.
						 * getChannelUserPassword()
						 */) + channelCO.getImApiChannelsDetVO().getHOST_NAME();
	    encChar = "SHA-512";
	    byte[] convertBytes = SecurityUtils.returnMd5Digest(thePass.getBytes(FileUtil.DEFAULT_FILE_ENCODING),
		    encChar);
	    // the blow is only to get the String Form The Hashed ByteArray
	    /*
	     * "%02X" means Flag '0' - The result will be zero-padded Width 2
	     * Conversion 'X' - The result is formatted as a hexadecimal
	     * integer, uppercase
	     */
	    String theFormat = "%02X";// upper case Formatting of The hashed
				      // ByteArray (convertBytes);
	    if("MD5".equals(encChar))
	    {
		theFormat = "%02x"; // for MD5 it is lower Case Hashing
	    }
	    Formatter formatter = new Formatter();
	    for(byte b : convertBytes)
	    {
		formatter.format(theFormat, b);
	    }
	    String result = formatter.toString().substring(20, 52);
	    formatter.close();
	    return result;
	}
	catch(Exception e)
	{
	    log.error(e, "Error in returnPwdEnc for encChar " + encChar);
	    throw new BadCredentialsException("Contact Administrator", e);
	}
    }

    @Override
    public int returnVerficationListCountForGrid(ChannelSC criteria) throws BaseException
    {
	return channelDAO.returnVerficationListCountForGrid(criteria);
    }

    @Override
    public List<ChannelCO> returnVerficationListForGrid(ChannelSC criteria) throws BaseException
    {
	return channelDAO.returnVerficationListForGrid(criteria);
    }

    @Override
    public int returnTempIdListCount(ChannelSC sc) throws BaseException
    {
	return channelDAO.returnTempIdListCount(sc);
    }

    @Override
    public List<TemplateCO> loadAssignedTemplateIdListGrid(ChannelSC sc) throws BaseException
    {
	List<GTW_PWS_TMPLT_USRVO> list = channelDAO.loadAssignedTemplateIdListGrid(sc);
	List<TemplateCO> templateCOs = new ArrayList<TemplateCO>();
	for(int i = 0; i < list.size(); i++)
	{
	    TemplateCO templateCO = new TemplateCO();
	    GTW_PWS_TMPLT_USRVO gtw_PWS_TMPLT_USRVO = list.get(i);

	    templateCO.setTEMPLATE_ID("" + gtw_PWS_TMPLT_USRVO.getTEMPLATE_ID());
	    templateCOs.add(templateCO);
	}
	return templateCOs;
    }

    @Override
    public List<GTW_PWS_TMPLT_MASTERVO> returnTempIdList(ChannelSC sc) throws BaseException
    {
	return channelDAO.returnTempIdList(sc);
    }

    @Override
    public int validateUserId(ChannelSC channelSC) throws BaseException
    {
	// TODO Auto-generated method stub
	return channelDAO.validateUserId(channelSC);
    }

    public LoginBO getLoginBO()
    {
	return loginBO;
    }

    public void setLoginBO(LoginBO loginBO)
    {
	this.loginBO = loginBO;
    }

    public ChannelDAO getChannelDAO()
    
    {
	return channelDAO;
    }

    public void setChannelDAO(ChannelDAO channelDAO)
    {
	this.channelDAO = channelDAO;
    }
}
