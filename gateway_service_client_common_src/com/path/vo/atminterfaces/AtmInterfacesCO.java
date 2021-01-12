package com.path.vo.atminterfaces;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.path.dbmaps.vo.GTW_ATM_INTERFACESVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;

/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * AtmInterfacesCO.java used to
 */
public class AtmInterfacesCO extends BaseVO
{
    
    private String fieldName;
    private String parameterName;
    private String parameterValue;

    private GTW_ATM_INTERFACESVO iso_INTERFACESVO = new GTW_ATM_INTERFACESVO();

    // Strings
    private String interfaceTypeDesc;
    private String PARTIAL_MASK;
    private String TYPE;
    private String EXPRESSION;
    private String IS_DECIMAL_YN;
    private String HEADER;
    private String STATUS;
//    private String statusDesc;
    private String mode;
    private String data;
    private String subGridData;

    // BigDedcimals
    private BigDecimal FIELD_LENL;
    private BigDecimal TOTAL_MASK;

    // ATM Interface grid data
    HashMap<String, Object> gridsDataMap = new HashMap<String, Object>();
    Map<BigDecimal, BigDecimal> interfaceFieldMap = new HashMap<BigDecimal, BigDecimal>();
    
    private Boolean isChannelExistByInterface;
    
    
    //screen Display
    private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementMap = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

    
    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
    
    public String getParameterName()
    {
        return parameterName;
    }

    public void setParameterName(String parameterName)
    {
        this.parameterName = parameterName;
    }

    public String getParameterValue()
    {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue)
    {
        this.parameterValue = parameterValue;
    }

    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
    }

    public String getEXPRESSION()
    {
	return EXPRESSION;
    }

    public void setEXPRESSION(String eXPRESSION)
    {
	EXPRESSION = eXPRESSION;
    }

    public String getIS_DECIMAL_YN()
    {
	return IS_DECIMAL_YN;
    }

    public void setIS_DECIMAL_YN(String iS_DECIMAL_YN)
    {
	IS_DECIMAL_YN = iS_DECIMAL_YN;
    }

    public String getTYPE()
    {
	return TYPE;
    }

    public void setTYPE(String tYPE)
    {
	TYPE = tYPE;
    }

    public BigDecimal getFIELD_LENL()
    {
	return FIELD_LENL;
    }

    public void setFIELD_LENL(BigDecimal fIELD_LENL)
    {
	FIELD_LENL = fIELD_LENL;
    }

    public BigDecimal getTOTAL_MASK()
    {
	return TOTAL_MASK;
    }

    public void setTOTAL_MASK(BigDecimal tOTAL_MASK)
    {
	TOTAL_MASK = tOTAL_MASK;
    }

    public String getPARTIAL_MASK()
    {
	return PARTIAL_MASK;
    }

    public void setPARTIAL_MASK(String pARTIAL_MASK)
    {
	PARTIAL_MASK = pARTIAL_MASK;
    }

    public String getInterfaceTypeDesc()
    {
	return interfaceTypeDesc;
    }

    public void setInterfaceTypeDesc(String interfaceTypeDesc)
    {
	this.interfaceTypeDesc = interfaceTypeDesc;
    }

    public String getHEADER()
    {
	return HEADER;
    }

    public void setHEADER(String hEADER)
    {
	HEADER = hEADER;
    }

    public String getSTATUS()
    {
	return STATUS;
    }

    public void setSTATUS(String sTATUS)
    {
	STATUS = sTATUS;
    }

    public String getData()
    {
	return data;
    }

    public void setData(String data)
    {
	this.data = data;
    }

    public String getSubGridData()
    {
	return subGridData;
    }

    public void setSubGridData(String subGridData)
    {
	this.subGridData = subGridData;
    }
    public GTW_ATM_INTERFACESVO getIso_INTERFACESVO()
    {
	return iso_INTERFACESVO;
    }

    public void setIso_INTERFACESVO(GTW_ATM_INTERFACESVO iso_INTERFACESVO)
    {
	this.iso_INTERFACESVO = iso_INTERFACESVO;
    }
    
    public HashMap<String, Object> getGridsDataMap()
    {
	return gridsDataMap;
    }

    public void setGridsDataMap(HashMap<String, Object> gridsDataMap)
    {
	this.gridsDataMap = gridsDataMap;
    }

    public Map<BigDecimal, BigDecimal> getInterfaceFieldMap()
    {
        return interfaceFieldMap;
    }

    public void setInterfaceFieldMap(Map<BigDecimal, BigDecimal> interfaceFieldMap)
    {
        this.interfaceFieldMap = interfaceFieldMap;
    }
    
    public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getElementMap()
    {
	return elementMap;
    }

    public void setElementMap(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementMap)
    {
	this.elementMap = elementMap;
    }

    public Boolean getIsChannelExistByInterface()
    {
        return isChannelExistByInterface;
    }

    public void setIsChannelExistByInterface(Boolean isChannelExistByInterface)
    {
        this.isChannelExistByInterface = isChannelExistByInterface;
    }
    
}