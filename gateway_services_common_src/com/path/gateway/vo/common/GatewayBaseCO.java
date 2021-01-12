package com.path.gateway.vo.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;

public class GatewayBaseCO extends BaseVO
{

    private String statusColorCode;
    private String userId;
    private BigDecimal compCode;
    private BigDecimal branchCode;
    private Date runningDate;
    private String updateMode;
    private String statusDesc;
    private String toBeStatusDesc;
    private String appName;
    private String progRef;
    private String userID;
    private String language;

    private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementMap = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

    public String getStatusColorCode()
    {
	return statusColorCode;
    }

    public void setStatusColorCode(String statusColorCode)
    {
	this.statusColorCode = statusColorCode;
    }

    public String getUserId()
    {
	return userId;
    }

    public void setUserId(String userId)
    {
	this.userId = userId;
    }

    public BigDecimal getCompCode()
    {
	return compCode;
    }

    public void setCompCode(BigDecimal compCode)
    {
	this.compCode = compCode;
    }

    public BigDecimal getBranchCode()
    {
	return branchCode;
    }

    public void setBranchCode(BigDecimal branchCode)
    {
	this.branchCode = branchCode;
    }

    public Date getRunningDate()
    {
	return runningDate;
    }

    public void setRunningDate(Date runningDate)
    {
	this.runningDate = runningDate;
    }

    public String getStatusDesc()
    {
	return statusDesc;
    }

    public void setStatusDesc(String statusDesc)
    {
	this.statusDesc = statusDesc;
    }

    public String getToBeStatusDesc()
    {
	return toBeStatusDesc;
    }

    public void setToBeStatusDesc(String toBeStatusDesc)
    {
	this.toBeStatusDesc = toBeStatusDesc;
    }

    public String getUpdateMode()
    {
	return updateMode;
    }

    public void setUpdateMode(String updateMode)
    {
	this.updateMode = updateMode;
    }

    public String getAppName()
    {
	return appName;
    }

    public void setAppName(String appName)
    {
	this.appName = appName;
    }

    public String getProgRef()
    {
	return progRef;
    }

    public void setProgRef(String progRef)
    {
	this.progRef = progRef;
    }

    public String getUserID()
    {
	return userID;
    }

    public void setUserID(String userID)
    {
	this.userID = userID;
    }

    public String getLanguage()
    {
	return language;
    }

    public void setLanguage(String language)
    {
	this.language = language;
    }

    public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getElementMap()
    {
	return elementMap;
    }

    public void setElementMap(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementMap)
    {
	this.elementMap = elementMap;
    }

}
