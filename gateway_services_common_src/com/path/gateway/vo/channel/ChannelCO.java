package com.path.gateway.vo.channel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.OPT_EXTENDEDVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNELVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNEL_DETVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNEL_ISO_INT_PARAMSVO;
import com.path.gateway.dbmaps.vo.GTW_CHANNEL_JMSVO;
import com.path.gateway.dbmaps.vo.GTW_DGTL_INTERFACESVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_MASTERVO;
import com.path.gateway.dbmaps.vo.GTW_PWS_TMPLT_USRVO;
import com.path.gateway.vo.common.GatewayBaseCO;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * ChannelCO.java used to
 */
public class ChannelCO extends GatewayBaseCO
{

    private GTW_CHANNELVO gtw_CHANNELVO;
    private GTW_CHANNEL_ISO_INT_PARAMSVO gtw_CHANNEL_ISO_INT_PARAMSVO;
    private GTW_CHANNEL_DETVO imApiChannelsDetVO;
    private GTW_DGTL_INTERFACESVO gtw_DGTL_INTERFACESVO;
    private GTW_PWS_TMPLT_MASTERVO imcoPwsTmpltMasterVO;
    private GTW_PWS_TMPLT_USRVO imcoPwsTmpltUserVO;

    private String updateMode, channelUserPassword;

    private Integer allowUserAccess;
    private List<String> listMachineId;
    private String jsonMultiselectArray;
    private String communicationType;
    private String ip;
    private String port;
    private String folderLocation;
    private String communicationFormal;
    private String activeJMS;
    private BigDecimal parallelismControl;
    
    private String interfaceType;
    private String interfaceDescription;

    private OPTVO optVO = new OPTVO();
    private OPT_EXTENDEDVO optExtVO = new OPT_EXTENDEDVO();
    private AXSVO axsVO = new AXSVO();

    ArrayList<GTW_PWS_TMPLT_MASTERVO> accessServiceList = new ArrayList<GTW_PWS_TMPLT_MASTERVO>();
    ArrayList<ChannelCO> varificationDetailList = new ArrayList<ChannelCO>();

    private String communicationProtocol;
    private String serverType;
    private GTW_CHANNEL_JMSVO channelJMSVO = new GTW_CHANNEL_JMSVO();

    public List<String> getListMachineId()
    {
	return listMachineId;
    }

    public void setListMachineId(List<String> listMachineId)
    {
	this.listMachineId = listMachineId;
    }

    public Integer getAllowUserAccess()
    {
	return allowUserAccess;
    }

    public void setAllowUserAccess(Integer allowUserAccess)
    {
	this.allowUserAccess = allowUserAccess;
    }

    /**
     * @return the updateMode
     */
    public String getUpdateMode()
    {
	return updateMode;
    }

    /**
     * @param updateMode the updateMode to set
     */
    public void setUpdateMode(String updateMode)
    {
	this.updateMode = updateMode;
    }

    public String getChannelUserPassword()
    {
	return channelUserPassword;
    }

    public void setChannelUserPassword(String channelUserPassword)
    {
	this.channelUserPassword = channelUserPassword;
    }

    public GTW_CHANNELVO getGtw_CHANNELVO()
    {
	return gtw_CHANNELVO;
    }

    public void setGtw_CHANNELVO(GTW_CHANNELVO gtw_CHANNELVO)
    {
	this.gtw_CHANNELVO = gtw_CHANNELVO;
    }
    
    public GTW_CHANNEL_ISO_INT_PARAMSVO getGtw_CHANNEL_ISO_INT_PARAMSVO()
    {
        return gtw_CHANNEL_ISO_INT_PARAMSVO;
    }

    public void setGtw_CHANNEL_ISO_INT_PARAMSVO(GTW_CHANNEL_ISO_INT_PARAMSVO gtw_CHANNEL_ISO_INT_PARAMSVO)
    {
        this.gtw_CHANNEL_ISO_INT_PARAMSVO = gtw_CHANNEL_ISO_INT_PARAMSVO;
    }

    public GTW_CHANNEL_DETVO getImApiChannelsDetVO()
    {
	return imApiChannelsDetVO;
    }

    public void setImApiChannelsDetVO(GTW_CHANNEL_DETVO imApiChannelsDetVO)
    {
	this.imApiChannelsDetVO = imApiChannelsDetVO;
    }

    public GTW_DGTL_INTERFACESVO getGtw_DGTL_INTERFACESVO()
    {
	return gtw_DGTL_INTERFACESVO;
    }

    public void setGtw_DGTL_INTERFACESVO(GTW_DGTL_INTERFACESVO gtw_DGTL_INTERFACESVO)
    {
	this.gtw_DGTL_INTERFACESVO = gtw_DGTL_INTERFACESVO;
    }

    public GTW_PWS_TMPLT_MASTERVO getImcoPwsTmpltMasterVO()
    {
	return imcoPwsTmpltMasterVO;
    }

    public void setImcoPwsTmpltMasterVO(GTW_PWS_TMPLT_MASTERVO imcoPwsTmpltMasterVO)
    {
	this.imcoPwsTmpltMasterVO = imcoPwsTmpltMasterVO;
    }

    public String getJsonMultiselectArray()
    {
	return jsonMultiselectArray;
    }

    public void setJsonMultiselectArray(String jsonMultiselectArray)
    {
	this.jsonMultiselectArray = jsonMultiselectArray;
    }

    public GTW_PWS_TMPLT_USRVO getImcoPwsTmpltUserVO()
    {
	return imcoPwsTmpltUserVO;
    }

    public void setImcoPwsTmpltUserVO(GTW_PWS_TMPLT_USRVO imcoPwsTmpltUserVO)
    {
	this.imcoPwsTmpltUserVO = imcoPwsTmpltUserVO;
    }

    public String getCommunicationType()
    {
	return communicationType;
    }

    public void setCommunicationType(String communicationType)
    {
	this.communicationType = communicationType;
    }

    public String getIp()
    {
	return ip;
    }

    public void setIp(String ip)
    {
	this.ip = ip;
    }

    public String getPort()
    {
	return port;
    }

    public void setPort(String port)
    {
	this.port = port;
    }

    public String getFolderLocation()
    {
	return folderLocation;
    }

    public void setFolderLocation(String folderLocation)
    {
	this.folderLocation = folderLocation;
    }

    public String getCommunicationFormal()
    {
	return communicationFormal;
    }

    public void setCommunicationFormal(String communicationFormal)
    {
	this.communicationFormal = communicationFormal;
    }

    public String getActiveJMS()
    {
	return activeJMS;
    }

    public void setActiveJMS(String activeJMS)
    {
	this.activeJMS = activeJMS;
    }

    public BigDecimal getParallelismControl()
    {
	return parallelismControl;
    }

    public void setParallelismControl(BigDecimal parallelismControl)
    {
	this.parallelismControl = parallelismControl;
    }

    public String getInterfaceDescription()
    {
	return interfaceDescription;
    }

    public void setInterfaceDescription(String interfaceDescription)
    {
	this.interfaceDescription = interfaceDescription;
    }

    public OPTVO getOptVO()
    {
	return optVO;
    }

    public void setOptVO(OPTVO optVO)
    {
	this.optVO = optVO;
    }

    public OPT_EXTENDEDVO getOptExtVO()
    {
	return optExtVO;
    }

    public void setOptExtVO(OPT_EXTENDEDVO optExtVO)
    {
	this.optExtVO = optExtVO;
    }

    public AXSVO getAxsVO()
    {
	return axsVO;
    }

    public void setAxsVO(AXSVO axsVO)
    {
	this.axsVO = axsVO;
    }

    public ArrayList<GTW_PWS_TMPLT_MASTERVO> getAccessServiceList()
    {
	return accessServiceList;
    }

    public void setAccessServiceList(ArrayList<GTW_PWS_TMPLT_MASTERVO> accessServiceList)
    {
	this.accessServiceList = accessServiceList;
    }

    public ArrayList<ChannelCO> getVarificationDetailList()
    {
	return varificationDetailList;
    }

    public void setVarificationDetailList(ArrayList<ChannelCO> varificationDetailList)
    {
	this.varificationDetailList = varificationDetailList;
    }

    public String getCommunicationProtocol()
    {
	return communicationProtocol;
    }

    public void setCommunicationProtocol(String communicationProtocol)
    {
	this.communicationProtocol = communicationProtocol;
    }

    public String getServerType()
    {
	return serverType;
    }

    public void setServerType(String serverType)
    {
	this.serverType = serverType;
    }

    public String getInterfaceType()
    {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }

	public GTW_CHANNEL_JMSVO getChannelJMSVO() {
		return channelJMSVO;
	}

	public void setChannelJMSVO(GTW_CHANNEL_JMSVO channelJMSVO) {
		this.channelJMSVO = channelJMSVO;
	}
    
    

}
