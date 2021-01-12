package com.path.gateway.vo.channel;

import java.math.BigDecimal;

import com.path.gateway.vo.common.GatewayBaseSC;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * ChannelSC.java used to
 */
public class ChannelSC extends GatewayBaseSC
{
    private BigDecimal ChannelId;
    private String channelHostName;
    private String channelUserId;

    private BigDecimal interfaceTypeLovCode;
    private String communicationProtocol;
    private BigDecimal interfaceCode;

    private BigDecimal communicationProtocolLovCode;
    private BigDecimal serverTypeLovCode;

    private String childOneProgRef;
    private String childTwoProgRef;

    public BigDecimal getChannelId()
    {
	return ChannelId;
    }

    public void setChannelId(BigDecimal channelId)
    {
	ChannelId = channelId;
    }

    public String getChannelHostName()
    {
	return channelHostName;
    }

    public void setChannelHostName(String channelHostName)
    {
	this.channelHostName = channelHostName;
    }

    public String getChannelUserId()
    {
	return channelUserId;
    }

    public void setChannelUserId(String channelUserId)
    {
	this.channelUserId = channelUserId;
    }

    public BigDecimal getInterfaceTypeLovCode()
    {
	return interfaceTypeLovCode;
    }

    public void setInterfaceTypeLovCode(BigDecimal interfaceTypeLovCode)
    {
	this.interfaceTypeLovCode = interfaceTypeLovCode;
    }

    public String getCommunicationProtocol()
    {
	return communicationProtocol;
    }

    public void setCommunicationProtocol(String communicationProtocol)
    {
	this.communicationProtocol = communicationProtocol;
    }

    public BigDecimal getInterfaceCode()
    {
	return interfaceCode;
    }

    public void setInterfaceCode(BigDecimal interfaceCode)
    {
	this.interfaceCode = interfaceCode;
    }

    public BigDecimal getCommunicationProtocolLovCode()
    {
	return communicationProtocolLovCode;
    }

    public void setCommunicationProtocolLovCode(BigDecimal communicationProtocolLovCode)
    {
	this.communicationProtocolLovCode = communicationProtocolLovCode;
    }

    public BigDecimal getServerTypeLovCode()
    {
	return serverTypeLovCode;
    }

    public void setServerTypeLovCode(BigDecimal serverTypeLovCode)
    {
	this.serverTypeLovCode = serverTypeLovCode;
    }

    public String getChildOneProgRef()
    {
	return childOneProgRef;
    }

    public void setChildOneProgRef(String childOneProgRef)
    {
	this.childOneProgRef = childOneProgRef;
    }

    public String getChildTwoProgRef()
    {
	return childTwoProgRef;
    }

    public void setChildTwoProgRef(String childTwoProgRef)
    {
	this.childTwoProgRef = childTwoProgRef;
    }

}
