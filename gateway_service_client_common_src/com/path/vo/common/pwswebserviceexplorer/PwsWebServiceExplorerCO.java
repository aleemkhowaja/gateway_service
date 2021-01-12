/**
 * PwsWebServiceExplorerCO.java - Mar 21, 2019  
 *
 * Copyright 2019, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * @author: Raed Saad
 *
 */
package com.path.vo.common.pwswebserviceexplorer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import com.path.dbmaps.vo.COMMON_PWS_MAPPINGVO;
import com.path.dbmaps.vo.COMMON_PWS_MAPPING_DEFVO;
import com.path.dbmaps.vo.COMMON_PWS_MAPPING_DETAILSVO;
import com.path.lib.vo.BaseVO;
import com.path.vo.common.webserviceexplorer.RequestResponseCO;
import com.path.vo.common.webserviceexplorer.WebServiceExplorerCO;

public class PwsWebServiceExplorerCO extends BaseVO implements Serializable {
	
	private WebServiceExplorerCO webServiceExplorerCO;
 	private List<COMMON_PWS_MAPPING_DETAILSVO> lstCommonPwsMappingDetailsVO;
	private COMMON_PWS_MAPPING_DETAILSVO commonPwsMappingDetailsVO;
	private COMMON_PWS_MAPPINGVO commonPwsMappingVO;	
	private COMMON_PWS_MAPPING_DEFVO commonPwsMappingDefVO;
	private BigDecimal mappingDetID;
	private Date runningDate;
	private Date modifiedDate;
	private String userName;
	private Date modifiedBy;
	private String filter;
	private String paramNameFilter;
	
	// fields used while process common pws mapping screen save
	private List<RequestResponseCO> lstReq;
	private String returnGridDataParentField;
	private List<COMMON_PWS_MAPPING_DETAILSVO> lstCommonPwsMappingVO = new ArrayList<COMMON_PWS_MAPPING_DETAILSVO>();
	private String mode;
	public Stack<String> parentFields = new Stack<String>();
	public Stack<String> parentMappingFields = new Stack<String>();
	private boolean mappedParent = false;

	private String nextType;
	
	public WebServiceExplorerCO getWebServiceExplorerCO() {
		return webServiceExplorerCO;
	}
	public void setWebServiceExplorerCO(WebServiceExplorerCO webServiceExplorerCO) {
		this.webServiceExplorerCO = webServiceExplorerCO;
	}
	public COMMON_PWS_MAPPINGVO getCommonPwsMappingVO() {
		return commonPwsMappingVO;
	}
	public void setCommonPwsMappingVO(COMMON_PWS_MAPPINGVO commonPwsMappingVO) {
		this.commonPwsMappingVO = commonPwsMappingVO;
	}
	public List<COMMON_PWS_MAPPING_DETAILSVO> getLstCommonPwsMappingDetailsVO() {
		return lstCommonPwsMappingDetailsVO;
	}
	public void setLstCommonPwsMappingDetailsVO(List<COMMON_PWS_MAPPING_DETAILSVO> lstCommonPwsMappingDetailsVO) {
		this.lstCommonPwsMappingDetailsVO = lstCommonPwsMappingDetailsVO;
	}
	public Date getRunningDate() {
		return runningDate;
	}
	public void setRunningDate(Date runningDate) {
		this.runningDate = runningDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Date modifiedName) {
		this.modifiedBy = modifiedName;
	}
	public BigDecimal getMappingDetID() {
		return mappingDetID;
	}
	public void setMappingDetID(BigDecimal mappingDetID) {
		this.mappingDetID = mappingDetID;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public COMMON_PWS_MAPPING_DETAILSVO getCommonPwsMappingDetailsVO() {
		return commonPwsMappingDetailsVO;
	}
	public void setCommonPwsMappingDetailsVO(COMMON_PWS_MAPPING_DETAILSVO commonPwsMappingDetailsVO) {
		this.commonPwsMappingDetailsVO = commonPwsMappingDetailsVO;
	}
	public COMMON_PWS_MAPPING_DEFVO getCommonPwsMappingDefVO() {
		return commonPwsMappingDefVO;
	}
	public void setCommonPwsMappingDefVO(COMMON_PWS_MAPPING_DEFVO commonPwsMappingDefVO) {
		this.commonPwsMappingDefVO = commonPwsMappingDefVO;
	}
	public String getParamNameFilter() {
		return paramNameFilter;
	}
	public void setParamNameFilter(String paramNameFilter) {
		this.paramNameFilter = paramNameFilter;
	}
	public String getReturnGridDataParentField() {
		return returnGridDataParentField;
	}
	public void setReturnGridDataParentField(String returnGridDataParentField) {
		this.returnGridDataParentField = returnGridDataParentField;
	}
	public List<COMMON_PWS_MAPPING_DETAILSVO> getLstCommonPwsMappingVO() {
		return lstCommonPwsMappingVO;
	}
	public void setLstCommonPwsMappingVO(List<COMMON_PWS_MAPPING_DETAILSVO> lstCommonPwsMappingVO) {
		this.lstCommonPwsMappingVO = lstCommonPwsMappingVO;
	}
	
	/**
	 * @description this function will be used to append data to COMMON_PWS_MAPPING_DETAILSVO while saving a record
	 * @param reqRespCO
	 */
	public void appendToCommonPwsMappingList(COMMON_PWS_MAPPING_DETAILSVO commonPwsMappingVO)
	{
		this.getLstCommonPwsMappingVO().add(commonPwsMappingVO);
	}
	
	/**
	 * @description this function will be used to append data to COMMON_PWS_MAPPING_DETAILSVO list while saving a record
	 * @param reqRespCO
	 */
	public void appendListToCommonPwsMappingList(List<COMMON_PWS_MAPPING_DETAILSVO> lstCommonPwsMappingVO)
	{
		this.getLstCommonPwsMappingVO().addAll(lstCommonPwsMappingVO);
	}
	
	public void pushParentMappingFields(String field)
	{
		parentMappingFields.push(field);
	}
	
	public void popParentMappingFields()
	{
		parentMappingFields.pop();
	}
	
	public void pushParentField(String field)
	{
		parentFields.push(field);
	}
	
	public void popParentField()
	{
		parentFields.pop();
	}
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public List<RequestResponseCO> getLstReq() {
		return lstReq;
	}
	public void setLstReq(List<RequestResponseCO> lstReq) {
		this.lstReq = lstReq;
	}
	public Stack<String> getParentFields() {
		return parentFields;
	}
	public void setParentFields(Stack<String> parentFields) {
		this.parentFields = parentFields;
	}
	public Stack<String> getParentMappingFields() {
		return parentMappingFields;
	}
	public void setParentMappingFields(Stack<String> parentMappingFields) {
		this.parentMappingFields = parentMappingFields;
	}
	public String getNextType() {
		return nextType;
	}
	public void setNextType(String nextType) {
		this.nextType = nextType;
	}
	public boolean isMappedParent() {
		return mappedParent;
	}
	public void setMappedParent(boolean mappedParent) {
		this.mappedParent = mappedParent;
	}
}
