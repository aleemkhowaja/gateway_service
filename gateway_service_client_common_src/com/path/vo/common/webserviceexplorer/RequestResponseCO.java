package com.path.vo.common.webserviceexplorer;
/**
 * @Auther:Raed Saad
 * @Date:MARCH 2018
 * @Team: PEMTS JAVA Team.
 * @copyright: PathSolution 2018
 * @User Story: USER STORY #653853  WSDL explorer
 * @Description: Base Class that Represent the Grid Model
 **/

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.path.vo.common.select.SelectCO;

public class RequestResponseCO implements Serializable {
	private String ID;
	private String fieldName;
	private String fieldType;
	private String mandatory;
	private String precision;
	private String restriction;
	private Boolean nillable;
	private String embeddedTypeEnumParent;
	private String value;
	private String mappingField;
	private String expressionHiddenField;
	private String hasChildren;
	private String hasRestriction;
	private String wsdlUrl;
	private String soapAction;
	private String lstParentName;
	private String nameSpaceUri;
	private String checkGridRow;
	private RequestResponseCO reqResCO;
	private List<RequestResponseCO> lstInReqRes;
	private RequestResponseCO parent;
	private RequestResponseCO child;
	private List<String> list;
	private Map<String,String> map;
	
	private List<SelectCO> restrictions;
	
	private String colType;
	private String edittype;
	private String loadOnce;
	private String editOptions;
	
	private String gridColumnID;
	private String loadSubGrid;
	private String loadedFields;
	
	private String destination;
	private String inOut;
	
	private Stack<String> stackTrace = new Stack<String>();

	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	public List<RequestResponseCO> getLstInReqRes() {
		return lstInReqRes;
	}
	public void setLstInReqRes(List<RequestResponseCO> lstInReqRes) {
		this.lstInReqRes = lstInReqRes;
	}

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public List<SelectCO> getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(List<SelectCO> restrictions) {
		this.restrictions = restrictions;
	}
	public String getRestriction() {
		return restriction;
	}
	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(String hasChildren) {
		this.hasChildren = hasChildren;
	}
	public String getHasRestriction() {
		return hasRestriction;
	}
	public void setHasRestriction(String hasRestriction) {
		this.hasRestriction = hasRestriction;
	}
	public String getWsdlUrl() {
		return wsdlUrl;
	}
	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}
	public String getSoapAction() {
		return soapAction;
	}
	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	public String getEdittype() {
		return edittype;
	}
	public void setEdittype(String edittype) {
		this.edittype = edittype;
	}
	public String getLoadOnce() {
		return loadOnce;
	}
	public void setLoadOnce(String loadOnce) {
		this.loadOnce = loadOnce;
	}
	public String getEditOptions() {
		return editOptions;
	}
	public void setEditOptions(String editOptions) {
		this.editOptions = editOptions;
	}
	public String getGridColumnID() {
		return gridColumnID;
	}
	public void setGridColumnID(String gridColumnID) {
		this.gridColumnID = gridColumnID;
	}
	public RequestResponseCO getReqResCO() {
		return reqResCO;
	}
	public void setReqResCO(RequestResponseCO reqResCO) {
		this.reqResCO = reqResCO;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getLstParentName() {
		return lstParentName;
	}
	public void setLstParentName(String lstParentName) {
		this.lstParentName = lstParentName;
	}
	public String getLoadSubGrid() {
		return loadSubGrid;
	}
	public void setLoadSubGrid(String loadSubGrid) {
		this.loadSubGrid = loadSubGrid;
	}
	public String getNameSpaceUri() {
		return nameSpaceUri;
	}
	public void setNameSpaceUri(String nameSpaceUri) {
		this.nameSpaceUri = nameSpaceUri;
	}
	public String getEmbeddedTypeEnumParent() {
		return embeddedTypeEnumParent;
	}
	public void setEmbeddedTypeEnumParent(String embeddedTypeEnumParent) {
		this.embeddedTypeEnumParent = embeddedTypeEnumParent;
	}
	public Boolean getNillable() {
		return nillable;
	}
	public void setNillable(Boolean nillable) {
		this.nillable = nillable;
	}
	public String getLoadedFields() {
		return loadedFields;
	}
	public void setLoadedFields(String loadedFields) {
		this.loadedFields = loadedFields;
	}
	public String getMappingField() {
		return mappingField;
	}
	public void setMappingField(String mappingField) {
		this.mappingField = mappingField;
	}
	public String getExpressionHiddenField() {
		return expressionHiddenField;
	}
	public void setExpressionHiddenField(String expressionHiddenField) {
		this.expressionHiddenField = expressionHiddenField;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getInOut() {
		return inOut;
	}
	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
	public RequestResponseCO getParent() {
		return parent;
	}
	public void setParent(RequestResponseCO parent) {
		this.parent = parent;
	}
	public RequestResponseCO getChild() {
		return child;
	}
	public void setChild(RequestResponseCO child) {
		this.child = child;
	}
	public Stack<String> getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(Stack<String> stackTrace) {
		this.stackTrace = stackTrace;
	}
	public void push(String x)
	{
		this.getStackTrace().push(x);
	}
	public String pop()
	{
		return this.getStackTrace().pop();
	}
	public String getCheckGridRow() {
		return checkGridRow;
	}
	public void setCheckGridRow(String checkGridRow) {
		this.checkGridRow = checkGridRow;
	}
}
