package com.path.vo.common.webserviceexplorer;

/**
 * @Auther:Raed Saad
 * @Date:MARCH 2018
 * @Team: PEMTS JAVA Team.
 * @copyright: PathSolution 2018
 * @User Story: USER STORY #653853  WSDL explorer
 * @Description: Base Class that contain screen details
 **/

import java.util.List;

/*
 * contains request object details
 * */
public class WebServiceExplorerRequestObjectDetails {
	
	private Boolean hasExtension;
	private String extensionName;
	
	private Boolean containParams;
	private List<String> paramNames;
	
	private Boolean containListOfParams;
	private List<String> ListofParamsList;
	
	private Boolean containListOFObjects;
	private List<String> listOfObjectsName;
	
	private Boolean containObject;
	private List<String> objectsName;
	
	private int numberOfNonPrimativeParams;
	private int numberObjects;
	private int numberOfListOfObjects;
	private int numberOfListOfParams;
	
	public int getNumberOfNonPrimativeParams() {
		return paramNames.size();
	}
	public int getNumberOfListOfObjects() {
		return listOfObjectsName.size();
	}
	public int getNumberOfListOfParams() {
		return ListofParamsList.size();
	}
	public int getNumberObjects() {
		return objectsName.size();
	}
	
//	public void incrementNumberOfNonPrimativeParams() {
//		numberOfNonPrimativeParams++; 
//	}
//	public void incrementNumberObjects() {
//		this.numberObjects++;
//	}
//	public void incrementNumberOfListOfObjects() {
//		numberOfListOfObjects++;
//	}
//	public void incrementNumberOfListOfParams() {
//		numberOfListOfParams++;
//	}
	public Boolean getHasExtension() {
		return hasExtension;
	}
	public void setHasExtension(Boolean hasExtension) {
		this.hasExtension = hasExtension;
	}
	public String getExtensionName() {
		return extensionName;
	}
	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}
	public Boolean getContainListOfParams() {
		return containListOfParams;
	}
	public void setContainListOfParams(Boolean containListOfParams) {
		this.containListOfParams = containListOfParams;
	}
	public List<String> getParamNames() {
		return paramNames;
	}
	public void setParamNames(List<String> paramNames) {
		this.paramNames = paramNames;
	}
	public Boolean getContainListOFObjects() {
		return containListOFObjects;
	}
	public void setContainListOFObjects(Boolean containListOFObjects) {
		this.containListOFObjects = containListOFObjects;
	}
	public List<String> getListOfObjectsName() {
		return listOfObjectsName;
	}
	public void setListOfObjectsName(List<String> listOfObjectsName) {
		this.listOfObjectsName = listOfObjectsName;
	}
	public Boolean getContainObject() {
		return containObject;
	}
	public void setContainObject(Boolean containObject) {
		this.containObject = containObject;
	}
	public List<String> getObjectsName() {
		return objectsName;
	}
	public void setObjectsName(List<String> objectsName) {
		this.objectsName = objectsName;
	}
	public Boolean getContainParams() {
		return containParams;
	}
	public void setContainParams(Boolean containParams) {
		this.containParams = containParams;
	}
	public List<String> getListofParamsList() {
		return ListofParamsList;
	}
	public void setListofParamsList(List<String> listofParamsList) {
		ListofParamsList = listofParamsList;
	}
}
