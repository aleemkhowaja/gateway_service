package com.path.vo.common.webserviceexplorer;

/**
 * @Auther:Raed Saad
 * @Date:MARCH 2018
 * @Team: PEMTS JAVA Team.
 * @copyright: PathSolution 2018
 * @User Story: USER STORY #653853  WSDL explorer
 * @Description: Base Class that extends web service explorerCO and contains parsing info
 * @Used: used when we need to pass parser information to functions
 **/

import com.predic8.schema.ComplexContent;
import com.predic8.schema.ComplexType;
import com.predic8.schema.Extension;
import com.predic8.schema.Restriction;
import com.predic8.schema.Schema;
import com.predic8.schema.Sequence;
import com.predic8.schema.SimpleType;
import com.predic8.wsdl.Operation;

public class ParserCO extends WebServiceExplorerCO {
	private Schema schema;
	private Sequence sequence;
	private ComplexType complexType;
	private ComplexContent complexContent;
	private SimpleType simpleType;
	private Extension extension;
	private Restriction restriction;
	private Operation operation;

	private WebServiceExplorerCO webServiceExplorerCO;
	public Schema getSchema() {
		return schema;
	}
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	public Sequence getSequence() {
		return sequence;
	}
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
	public ComplexType getComplexType() {
		return complexType;
	}
	public void setComplexType(ComplexType complexType) {
		this.complexType = complexType;
	}
	public ComplexContent getComplexContent() {
		return complexContent;
	}
	public void setComplexContent(ComplexContent complexContent) {
		this.complexContent = complexContent;
	}
	public SimpleType getSimpleType() {
		return simpleType;
	}
	public void setSimpleType(SimpleType simpleType) {
		this.simpleType = simpleType;
	}
	public Extension getExtension() {
		return extension;
	}
	public void setExtension(Extension extension) {
		this.extension = extension;
	}
	public Restriction getRestriction() {
		return restriction;
	}
	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
	}
	public WebServiceExplorerCO getWebServiceExplorerCO() {
		return webServiceExplorerCO;
	}
	public void setWebServiceExplorerCO(WebServiceExplorerCO webServiceExplorerCO) {
		this.webServiceExplorerCO = webServiceExplorerCO;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}
