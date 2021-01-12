/**
 * PwsMappingExpressionCO.java - Jul 11, 2019  
 *
 * Copyright 2019, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * @author: Raed Saad
 *
 * @USER STORY #862852 Adding Expressions to Mapping parameters in PWS dialog
 */
package com.path.vo.common.mappingexpression;

public class PwsMappingExpressionCO {
	
	private String messageBody;
	private String selectedBodyTextarea;
	private String selectedBodyTextareaLength;
	private String explorerGridRowId;
	private String mappingGridFields;
	private String parameterName;
	private String parameterValue;
	
	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getSelectedBodyTextarea() {
		return selectedBodyTextarea;
	}

	public void setSelectedBodyTextarea(String selectedBodyTextarea) {
		this.selectedBodyTextarea = selectedBodyTextarea;
	}

	public String getSelectedBodyTextareaLength() {
		return selectedBodyTextareaLength;
	}

	public void setSelectedBodyTextareaLength(String selectedBodyTextareaLength) {
		this.selectedBodyTextareaLength = selectedBodyTextareaLength;
	}

	public String getExplorerGridRowId() {
		return explorerGridRowId;
	}

	public void setExplorerGridRowId(String explorerGridRowId) {
		this.explorerGridRowId = explorerGridRowId;
	}

	public String getMappingGridFields() {
		return mappingGridFields;
	}

	public void setMappingGridFields(String mappingFields) {
		this.mappingGridFields = mappingFields;
	}

}
