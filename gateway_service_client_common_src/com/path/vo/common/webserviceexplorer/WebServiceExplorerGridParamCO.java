package com.path.vo.common.webserviceexplorer;

import java.math.BigDecimal;

public class WebServiceExplorerGridParamCO {
	private String grildListActionName;
	private String mainGridActionRef;
	private String subGridActionRef; 
	private String listSubGridRef;
	private String hashSubGridRef;
	private String screenNameSpace;
	private String screenName;
	private String mappingFields;
	private BigDecimal mappingId;
	private String dialogValidationFlag;
	
	public String getMainGridActionRef() {
		return mainGridActionRef;
	}
	public void setMainGridActionRef(String mainGridActionRef) {
		this.mainGridActionRef = mainGridActionRef;
	}
	public String getSubGridActionRef() {
		return subGridActionRef;
	}
	public void setSubGridActionRef(String subGridActionRef) {
		this.subGridActionRef = subGridActionRef;
	}
	public String getListSubGridRef() {
		return listSubGridRef;
	}
	public void setListSubGridRef(String listSubGridRef) {
		this.listSubGridRef = listSubGridRef;
	}
	public String getHashSubGridRef() {
		return hashSubGridRef;
	}
	public void setHashSubGridRef(String hashSubGridRef) {
		this.hashSubGridRef = hashSubGridRef;
	}
	public String getGrildListActionName() {
		return grildListActionName;
	}
	public void setGrildListActionName(String grildListActionName) {
		this.grildListActionName = grildListActionName;
	}
	public String getScreenNameSpace() {
		return screenNameSpace;
	}
	public void setScreenNameSpace(String screenNameSpace) {
		this.screenNameSpace = screenNameSpace;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getMappingFields() {
		return mappingFields;
	}
	public void setMappingFields(String mappingFields) {
		this.mappingFields = mappingFields;
	}
	public BigDecimal getMappingId() {
		return mappingId;
	}
	public void setMappingId(BigDecimal mappingId) {
		this.mappingId = mappingId;
	}
	public String getDialogValidationFlag() {
		return dialogValidationFlag;
	}
	public void setDialogValidationFlag(String dialogValidationFlag) {
		this.dialogValidationFlag = dialogValidationFlag;
	}
}