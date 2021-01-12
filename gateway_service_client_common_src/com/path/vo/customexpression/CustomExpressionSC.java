/**
 * 
 */
package com.path.vo.customexpression;

import java.util.List;

import com.path.struts2.lib.common.GridParamsSC;

/**
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * CustomExpressionSCSC.java used to
 */
public class CustomExpressionSC extends GridParamsSC
{
    private String appName;
    private String addToExpressionList;
    private String shortName;
    private String description;
    private String responseSelectedRowFields;
    private List<String> responseSelectedRows;
    
    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getAddToExpressionList()
    {
        return addToExpressionList;
    }

    public void setAddToExpressionList(String addToExpressionList)
    {
        this.addToExpressionList = addToExpressionList;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
	public String getResponseSelectedRowFields() {
		return responseSelectedRowFields;
	}

	public void setResponseSelectedRowFields(String responseSelectedRowFields) {
		this.responseSelectedRowFields = responseSelectedRowFields;
	}

	public List<String> getResponseSelectedRows() {
		return responseSelectedRows;
	}

	public void setResponseSelectedRows(List<String> responseSelectedRows) {
		this.responseSelectedRows = responseSelectedRows;
	}
}
