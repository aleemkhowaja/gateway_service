package com.path.vo.mxmessageparser;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import com.path.lib.vo.BaseVO;
import com.predic8.schema.Schema;

/**
 * 
 * Copyright 2020, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: Alim Khowaja
 *
 * XsdParserCO.java used to
 */
public class XsdParserCO extends BaseVO
{
	private BigDecimal id;
	private String oldIdentifier;
    private String xsdSchema; 
    private File xsdFile;
    private String identifier;
    private Schema schema;
    private List<TagsCo> allTagsCos;
    private List<TagsCo> tagsCos;
    
    
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	public String getOldIdentifier() {
		return oldIdentifier;
	}
	public void setOldIdentifier(String oldIdentifier) {
		this.oldIdentifier = oldIdentifier;
	}
	public String getXsdSchema() {
		return xsdSchema;
	}
	public void setXsdSchema(String xsdSchema) {
		this.xsdSchema = xsdSchema;
	}
	public File getXsdFile() {
		return xsdFile;
	}
	public void setXsdFile(File xsdFile) {
		this.xsdFile = xsdFile;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Schema getSchema() {
		return schema;
	}
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	public List<TagsCo> getAllTagsCos() {
		return allTagsCos;
	}
	public void setAllTagsCos(List<TagsCo> allTagsCos) {
		this.allTagsCos = allTagsCos;
	}
	public List<TagsCo> getTagsCos() {
		return tagsCos;
	}
	public void setTagsCos(List<TagsCo> tagsCos) {
		this.tagsCos = tagsCos;
	}
}