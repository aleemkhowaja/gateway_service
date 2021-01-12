package com.path.vo.common.webserviceexplorer;

/**
 * @Auther:Raed Saad
 * @Date:MARCH 2018
 * @Team: PEMTS JAVA Team.
 * @copyright: PathSolution 2018
 * @User Story: USER STORY #653853  WSDL explorer
 * @Description: Base Class that handles the communication between the screen and web service project to retrieve the Endpoints and operations
 **/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.path.bo.common.webserviceexplorer.WebServiceExplorerConstant;
import com.path.dbmaps.vo.IMCO_PWS_TEST_DETAILSVO;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class WebServiceUtil {
	private String applicationName;
	private String webServiceHostUrl;
	private String propertiesFileName;
	public static String PATH_SERVICE_MAPPING = "/pathservices";
	public static String PATH_SERVICE_MAPPING_ALL = "/pathservicesall";
	public static String PATH_LIST_ALL_ENDPOINT_MAPPING = "/listAllEndPoints";
	public static final String PATH_LIST_ENDPOINTS_SERVICE_MAPPING = PATH_SERVICE_MAPPING_ALL + PATH_LIST_ALL_ENDPOINT_MAPPING;
	public Map<String, String> webServiceExplorerSelectedGridRows;
	public Map<String, Map<String, String>> webServiceExplorerSelectedHashMaps;
	public Map<String, RequestResponseCO> webServiceExplorerGridParamDataToSave;
	protected IMCO_PWS_TEST_DETAILSVO webServiceExplorerDataVO = null;
	protected List<IMCO_PWS_TEST_DETAILSVO> webServiceExplorerDataVOLst = null;
	protected int inc;
	protected Stack<Integer> incStack;
	protected Stack<String> stack;
	public WebServiceExplorerGridParamCO webServiceExplorerGridParamCO;

	public WebServiceUtil() {
	}
	
	public static WebServiceUtil getInstance()
	{
		return new WebServiceUtil();
	}

	public WebServiceUtil(String webServiceHostUrl) {
		this.webServiceHostUrl = webServiceHostUrl;
	}

	public WebServiceUtil(String propertiesFileName, String applicationName) {
		this.applicationName = applicationName;
		this.propertiesFileName = propertiesFileName;
	}

	/**
	 * this function will generate the mainGridActionRef and subGridActionRef which are used in webServiceExplorerList.js to load the main and subgrids
	 * @param actionName
	 * @return
	 */
	public WebServiceExplorerGridParamCO returnGridParamCO(String actionName)
	{
		WebServiceExplorerGridParamCO webServiceExplorerGridParamCO = new WebServiceExplorerGridParamCO();
		String mainGridActionRef = this.returnMainGridActionRef(actionName);
		String subGridActionRef = this.returnSubGridActionRef(actionName);
		String listSubGridActionRef = this.returnSubGridActionRef(actionName);
		String hashSubGridActionRef = this.returnHashGridActionRef(actionName);
		webServiceExplorerGridParamCO.setMainGridActionRef(mainGridActionRef);
		webServiceExplorerGridParamCO.setSubGridActionRef(subGridActionRef);
		webServiceExplorerGridParamCO.setListSubGridRef(listSubGridActionRef);
		webServiceExplorerGridParamCO.setHashSubGridRef(hashSubGridActionRef);
		return webServiceExplorerGridParamCO;
	}
	
	/**
	 * 
	 * @param actionName
	 * @return
	 */
	public String returnMainGridActionRef(String actionName)
	{
		actionName = actionName.replace("*", "");
		return actionName + WebServiceExplorerConstant.WEB_SERVICE_EXPLORER_MAIN_GRID;
	}
	
	/**
	 * 
	 * @param actionName
	 * @return
	 */
	public String returnSubGridActionRef(String actionName)
	{
		actionName = actionName.replace("*", "");
		return actionName + WebServiceExplorerConstant.WEB_SERVICE_EXPLORER_SUB_GRID;
	}
	
	/**
	 * 
	 * @param actionName
	 * @return
	 */
	public String returnHashGridActionRef(String actionName)
	{
		actionName = actionName.replace("*", "");
		return actionName + WebServiceExplorerConstant.WEB_SERVICE_EXPLORER_HASHMAP_SUB_GRID;
	}
	
	/**
	 * 
	 * @param actionName
	 * @return
	 */
	public String returnListSubGridActionRef(String actionName)
	{
		actionName = actionName.replace("*", "");
		return actionName + WebServiceExplorerConstant.WEB_SERVICE_EXPLORER_LIST_SUB_GRID;
	}
	/**
	 * @author Raed Saad
	 * @Input WebService HostedService Url
	 * @Return JSON Object of endpoints with their operations
	 */
	public List<RequestResponseCO> returnGridParam(String jsonString) {
		if (null == webServiceExplorerSelectedGridRows || null == webServiceExplorerGridParamDataToSave
				|| null == webServiceExplorerSelectedHashMaps) {
			this.initializeCollections();
		}
		List<RequestResponseCO> grid = new ArrayList<RequestResponseCO>();
		List<RequestResponseCO> subGrid = new ArrayList<RequestResponseCO>();
		RequestResponseCO gridRow;
		RequestResponseCO subGridRow;
		Map<String, String> map = null;
		List<String> list = null;
		if (StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(jsonString, ""))) {
			List<JSONObject> jsonLst = (List<JSONObject>) JSONSerializer.toJSON(jsonString);
			for (JSONObject jsonObj : jsonLst) {
				webServiceExplorerDataVO = new IMCO_PWS_TEST_DETAILSVO();
				gridRow = new RequestResponseCO();
				gridRow.setID(jsonObj.get("ID").toString());
				gridRow.setFieldName(jsonObj.get("fieldName").toString());
				gridRow.setFieldType(jsonObj.get("fieldType").toString());
				if("true".equals(jsonObj.get("nillable").toString()))
				{
					gridRow.setNillable(true);
				}
				else{
					gridRow.setNillable(false);
				}				
				if(null != jsonObj.get("value") && jsonObj.get("value").toString().length()>0)
				{
					gridRow.setValue(jsonObj.get("value").toString());
				}
				if(!StringUtil.isEmptyString(jsonObj.get("mappingField")+""))
				{
					gridRow.setMappingField(jsonObj.get("mappingField")+"");
				}
				if(!StringUtil.isEmptyString(jsonObj.get("expressionHiddenField")+""))
				{
					gridRow.setExpressionHiddenField(jsonObj.get("expressionHiddenField")+"");
				}
				gridRow.setReqResCO(null);
				gridRow.setLstInReqRes(null);
				String fType = gridRow.getFieldType();
				webServiceExplorerDataVO.setFIELD_ROW_ID(gridRow.getID());
				webServiceExplorerDataVO.setVALUE(gridRow.getValue());
				webServiceExplorerDataVO.setFIELD_TYPE(fType);
				webServiceExplorerDataVOLst.add(webServiceExplorerDataVO);
				String dynamicId= null;
				if(stack.size()>0 && fType.equals(stack.peek()) && StringUtil.isEmptyString(gridRow.getID()))
				{
					inc = incStack.peek();
					inc++;
					incStack.pop();
					incStack.push(inc);
					dynamicId = stack.peek()+"_"+inc;
					webServiceExplorerDataVO.setFIELD_ROW_ID(dynamicId);
				}
				else if(stack.size()>0 && StringUtil.isEmptyString(gridRow.getID()))
				{
					String stackType = stack.peek();
					String[] splitter = stackType.split("_");
					if(fType.equals(splitter[splitter.length-1]))
					{
						inc = incStack.peek();
						inc++;
						incStack.pop();
						incStack.push(inc);
						dynamicId = stack.peek()+"_"+inc;
						webServiceExplorerDataVO.setFIELD_ROW_ID(dynamicId);
					}
				}
				else if(stack.size()>0 && fType.equals(stack.peek()) && StringUtil.isNotEmpty(gridRow.getID()))
					{
						inc = incStack.peek();
						inc++;
						incStack.pop();
						incStack.push(inc);
						dynamicId = stack.peek()+"_"+inc;
					}
				if (StringUtil.isNotEmpty(jsonObj.get("reqResCO").toString())) {
					subGridRow = new RequestResponseCO();
					List<JSONObject> olst = (List<JSONObject>) jsonObj.get("reqResCO");
					String jsString = jsonObj.get("reqResCO").toString();
					List<RequestResponseCO> subGridRows = returnGridParam(jsString);
					subGridRow.setLstInReqRes(subGridRows);
					gridRow.setReqResCO(subGridRow);
//					gridRow.setLstInReqRes(subGridRows);
				}
				if (StringUtil.isNotEmpty(jsonObj.get("list").toString())) {
					subGridRow = new RequestResponseCO();
					list = (List<String>) jsonObj.get("list");
					gridRow.setList(list);
					String key = null;
					int i = 1;
					String type = gridRow.getFieldType();
					type = type.replace("List<", "");
					type = type.replace(">", "");
					for (String s : list) {
						webServiceExplorerDataVO = new IMCO_PWS_TEST_DETAILSVO();
						key = gridRow.getID() + "_" + i;
						webServiceExplorerDataVO.setFIELD_ROW_ID(key);
						webServiceExplorerDataVO.setVALUE(s);
						webServiceExplorerDataVO.setFIELD_TYPE(type);
						webServiceExplorerDataVOLst.add(webServiceExplorerDataVO);
						// webServiceExplorerSelectedGridRows.put(key,s);
						i++;
					}
				}
				if (StringUtil.isNotEmpty(jsonObj.get("map").toString())) {
					map = this.jsonStringToHashMap(jsonObj.get("map").toString());
					gridRow.setMap(map);
					String key = gridRow.getID() + "";
					webServiceExplorerSelectedHashMaps.put(key, map);
					String value = null;
					String hValue = null;
					String [] hValArr = null;
					int i = 0;
					for (String k : map.keySet()) {
						i++;
						hValue =  map.get(k);
						if(null != hValue)
						{
							hValue = hValue.toString();
							if(hValue.contains(","))
							{
								hValArr = hValue.replace("[", "").replace("]", "").replace("\"", "").split(",");
								hValue = hValArr[hValArr.length-1];								
							}
						}
						value = "{\"" + k + "\":\"" + hValue + "\"}";
						webServiceExplorerDataVO = new IMCO_PWS_TEST_DETAILSVO();
						webServiceExplorerDataVO.setFIELD_ROW_ID(gridRow.getFieldName()+"_"+i);
						webServiceExplorerDataVO.setVALUE(value);
						webServiceExplorerDataVO.setFIELD_TYPE(gridRow.getFieldName());
						webServiceExplorerDataVOLst.add(webServiceExplorerDataVO);
					}
				}
				if (StringUtil.isNotEmpty(jsonObj.get("lstInReqRes").toString())) {
					inc = 0;
					subGridRow = new RequestResponseCO();
					String type = gridRow.getFieldType();
					type = type.replace("List<", "");
					type = type.replace(">", "");
					if(StringUtil.isNotEmpty(gridRow.getID()))
					{
						String[] id = gridRow.getID().split("_");
						String x = "";
						int length = id.length;
						id[length-1] = type;
						for(int i = 0 ;i < length; i++)
						{
							x = x + id[i] + "_";
						}
						x = x.substring(0, x.length()-1);
						incStack.push(inc);
						stack.push(x);   
					}
					else{
						incStack.push(inc);
						stack.push(type);
					}
					List<JSONObject> lstJsonObj = (List<JSONObject>) jsonObj.get("lstInReqRes");
					List<RequestResponseCO> subGridRows = returnGridParam(jsonObj.get("lstInReqRes").toString());
					subGridRow.setLstInReqRes(subGridRows);
					gridRow.setReqResCO(subGridRow);
//					gridRow.setLstInReqRes(subGridRows);
					incStack.pop();
					stack.pop();
					inc = 0;
				}
				grid.add(gridRow);
			}
			return grid;
		}
		return null;
	}

	public WebServiceExplorerCO returnGridDataToSave(WebServiceExplorerCO webServiceExplorerCO) {
		this.initializeCollections();
		List<RequestResponseCO> lstReqRes = this.returnGridParam(webServiceExplorerCO.getWebServiceExplorerGridUpdates());
		// Map<String,String> map =
		// this.getWebServiceExplorerSelectedGridRows();
		// Map<String,RequestResponseCO> mapReqResCO =
		// this.getWebServiceExplorerGridParamDataToSave();
		// webServiceExplorerCO.setGridRows(mapReqResCO);
		// webServiceExplorerCO.setLstRequestResposneCO(lstReqRes);

		IMCO_PWS_TEST_DETAILSVO webServiceExplorerDataVO = null;
		// List<WEBSERVICEEXPLORER_DATAVO>webServiceExplorerDataVOLst = new
		// ArrayList<WEBSERVICEEXPLORER_DATAVO>();
		List<IMCO_PWS_TEST_DETAILSVO> webServiceExplorerDataVOLst = this.getWebServiceExplorerDataVOLst();

		// for(String key : map.keySet())
		// {
		// webServiceExplorerDataVO = new WEBSERVICEEXPLORER_DATAVO();
		// webServiceExplorerDataVO.setFIELD_ROW_ID(key);
		// webServiceExplorerDataVO.setVALUE(map.get(key));
		// webServiceExplorerDataVOLst.add(webServiceExplorerDataVO);
		// }
		webServiceExplorerCO.setLstRequestResposneCO(lstReqRes);
		webServiceExplorerCO.setWebServiceExplorerDataVOLst(webServiceExplorerDataVOLst);
		return webServiceExplorerCO;
	}

	/**
	 * @description: initialize collections
	 */
	public void initializeCollections() {
		webServiceExplorerSelectedGridRows = new HashMap<String, String>();
		webServiceExplorerSelectedHashMaps = new HashMap<String, Map<String, String>>();
		webServiceExplorerGridParamDataToSave = new HashMap<String, RequestResponseCO>();
		webServiceExplorerDataVOLst = new ArrayList<IMCO_PWS_TEST_DETAILSVO>();
		inc = 0;
		incStack  = new Stack<Integer>();
		stack = new Stack<String>();
	}

	/**
	 * @author Raed Saad
	 * @Input WebService HostedService Url
	 * @Return HashMap<String,HashMap<String,List<String>>> of endpoins with
	 *         their operations
	 */
	public Map<String, HashMap<String, List<String>>> getWebServiceEndPointsWithInfo(String webServiceHostUrl)
			throws MalformedURLException, IOException {
		try{
			if (StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(webServiceHostUrl, ""))) {
				JSONObject jsonObject = this.getWebServiceEndPointsJSONObject(webServiceHostUrl);
				List<String> operationsList = new ArrayList<String>();
				String[] operationsArray = null;
				Map<String, HashMap<String, List<String>>> infoHash;
				infoHash = new HashMap<String, HashMap<String, List<String>>>();
				JSONObject objectInfo;
				for (Object key : jsonObject.keySet()) {
					HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
					objectInfo = (JSONObject) jsonObject.get(key.toString());
					hashMap.put("NameSpaceUri", ((List<String>) objectInfo.get("NameSpaceUri")));
					hashMap.put("Address", ((List<String>) objectInfo.get("Address")));
					hashMap.put("portTypeName", ((List<String>) objectInfo.get("portTypeName")));
					hashMap.put("bindingName", ((List<String>) objectInfo.get("bindingName")));
					hashMap.put("serviceName", ((List<String>) objectInfo.get("serviceName")));
					hashMap.put("operations", ((List<String>) objectInfo.get("operations")));
					infoHash.put(key.toString(), (HashMap<String, List<String>>)hashMap.clone());
					hashMap = null;
				}
				return infoHash;
			}
		}
		catch(Exception e)
		{
			return null;
		}		
		return null;
	}
	
	/**
	 * @description get wsdl service name from input namespace
	 * @param webServiceHostUrl
	 * @param targetNameSpace
	 * @return
	 */
	public String getWebServiceNameFromNameSpace(String webServiceHostUrl,String targetNameSpace)
	{
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> map1 = new HashMap<String,Object>();
			if (StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(webServiceHostUrl, ""))) 
			{
				JSONObject jsonObject = this.getWebServiceEndPointsJSONObject(webServiceHostUrl);
				List<String> operationsList = new ArrayList<String>();
				String[] operationsArray = null;
				Map<String, HashMap<String, List<String>>> infoHash;
				infoHash = new HashMap<String, HashMap<String, List<String>>>();
				JSONObject objectInfo;
				String value = null;
				for (Object key : jsonObject.keySet())
				{
					objectInfo = (JSONObject) jsonObject.get(key.toString());
					value = ((List<String>)objectInfo.get("NameSpaceUri")).get(0);
					map.put(value, key);
					map1.put((String) key, value);
				}
				if(null != (String) map.get(targetNameSpace))
				{
					return (String) map.get(targetNameSpace);
				}
				else if(null != (String) map1.get(targetNameSpace))
				{
					return targetNameSpace;
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return null;
	}

	/**
	 * @author Raed Saad
	 * @Input WebService HostedService Url
	 * @Return HashMap<String,List<String>> of endpoins with their operations
	 */
	public HashMap<String, List<String>> getWebServiceEndPointsHashMap(String webServiceHostUrl)
			throws MalformedURLException, IOException {
		HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
		if (StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(webServiceHostUrl, ""))) {
			JSONObject jsonObject = this.getWebServiceEndPointsJSONObject(webServiceHostUrl);
			List<String> operationsList = new ArrayList<String>();
			String[] operationsArray = null;
			Map<String, List<String>> info;
			JSONObject objectInfo;
			for (Object key : jsonObject.keySet()) {
				objectInfo = (JSONObject) jsonObject.get(key.toString());
				hashMap.put(key.toString(), (List<String>) objectInfo.get("operations"));
			}
			return hashMap;
		}
		return null;
	}

	/**
	 * @author Raed Saad
	 * @Input WebService HostedService Url given in constructor
	 * @Return JSON Object of endpoins with their operations
	 */
	public JSONObject getWebServiceEndPointsJSONObject() throws MalformedURLException, IOException {
		if (StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(this.getWebServiceHostUrl(), ""))) {
			return this.getWebServiceEndPointsJSONObject(this.getWebServiceHostUrl());
		}
		return null;
	}

	/**
	 * @author Raed Saad
	 * @Input WebService HostedService Url
	 * @Return JSON Object of endpoins with their operations
	 */
	public JSONObject getWebServiceEndPointsJSONObject(String webServiceHostUrl)
			throws MalformedURLException, IOException {

		if (StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(webServiceHostUrl, ""))) {
			String jsonString = null;
			try{
				 jsonString = getWebServiceEndPointsJSONString(webServiceHostUrl);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());				
				return null;				
			}
			return (JSONObject) JSONSerializer.toJSON(jsonString);
		}
		return null;
	}

	/**
	 * @author Raed Saad
	 * @Input WebService HostedService Url
	 * @Return JSON String of endpoins with their operations
	 */
	public String getWebServiceEndPointsJSONString(String webServiceHostUrl) throws MalformedURLException, IOException {
		if(webServiceHostUrl.endsWith("/"))
		{
			webServiceHostUrl = webServiceHostUrl.substring(0, webServiceHostUrl.length()-1);
		}
		if (webServiceHostUrl.contains(this.PATH_SERVICE_MAPPING_ALL)) 
		{
			if (!(webServiceHostUrl.contains(this.PATH_LIST_ALL_ENDPOINT_MAPPING))) 
			{
				webServiceHostUrl = webServiceHostUrl + this.PATH_LIST_ALL_ENDPOINT_MAPPING;
			}
		} else {
			webServiceHostUrl = webServiceHostUrl + this.PATH_LIST_ENDPOINTS_SERVICE_MAPPING;
		}
		InputStreamReader in = null;
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		if (webServiceHostUrl.toLowerCase().contains(WebServiceExplorerConstant.HTTPS_EXTENSION)) {
			this.disableHostNameAuthentication();
			HttpsURLConnection httpsConn = returnSecureURLConnection(webServiceHostUrl);
			return this.returnHttpsContent(httpsConn);
		} else {
			urlConn = this.returnURLConnection(webServiceHostUrl);
		}
		if (urlConn != null) {
			urlConn.setConnectTimeout(0);
			urlConn.setReadTimeout(0);
		}
		if (urlConn != null && urlConn.getInputStream() != null) {
			in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
			BufferedReader bufferedReader = new BufferedReader(in);
			if (bufferedReader != null) {
				int cp;
				while ((cp = bufferedReader.read()) != -1) {
					sb.append((char) cp);
				}
				bufferedReader.close();
			}
		}
		return sb.toString();
	}
	
	/**
	 * @description return WSDL file as an input stream
	 * @param webServiceHostUrl
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public InputStream returnInputStreamWsdlData(String webServiceHostUrl) throws IOException, NoSuchAlgorithmException, KeyManagementException
	{		
		InputStreamReader in = null;
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		if (webServiceHostUrl.toLowerCase().contains(WebServiceExplorerConstant.HTTPS_EXTENSION))
		{
			this.disableHostNameAuthentication();
			HttpsURLConnection httpsConn = returnSecureURLConnection(webServiceHostUrl);
			return httpsConn.getInputStream();
		}
		return null;
	}
	
	/** In case of HTTPS call, to avoid getting the exception:
	 * javax.net.ssl.SSLHandshakeException: java.security.cert.CertificateException: No subject alternative names present.
	 * We need to disable the hostname verification between the url and the certificate owner*/
	private void disableHostNameAuthentication()
	{
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		} };
		SSLContext sc;
		try {
			sc = SSLContext.getInstance(WebServiceExplorerConstant.SSL_EXTENSION);
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
	
	/**
	 * @description function to print https certificates for testing purpose
	 * @param con
	 * @throws IOException 
	 */
	private void printHttpsCertificates(HttpsURLConnection con) throws IOException {
		if (con != null) 
		{
			System.out.println("Response Code : " + con.getResponseCode());
			System.out.println("Cipher Suite : " + con.getCipherSuite());
			System.out.println("\n");
			Certificate[] certs = con.getServerCertificates();
			for (Certificate cert : certs) {
				System.out.println("Cert Type : " + cert.getType());
				System.out.println("Cert Hash Code : " + cert.hashCode());
				System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
				System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
				System.out.println("\n");
			}
		}
	}

	/**
	 * @description function to return data from https connection object
	 * @param con
	 * @return
	 * @throws IOException 
	 */
	private String returnHttpsContent(HttpsURLConnection con) throws IOException {
		if (con != null) 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String input;
			String data = br.readLine();
			br.close();
			return data;
		}
		return null;
	}

	/**
	 * @author RaedSaad
	 * @return List Of Application Names
	 * @Input property File Name
	 */
	public List<String> getApplicationNames(String propertyFile) throws IOException {
		HashMap<String, String> fileKeyValue = this.returnFileKeysAndValue(propertyFile);
		List<String> lstOfApplicationNames = new ArrayList<String>();
		for(String key : fileKeyValue.keySet())
		{
			if(key.contains(WebServiceExplorerConstant.PROR_KEY_NAME_REF))
			{
				lstOfApplicationNames.add(fileKeyValue.get(key));
			}
		}
		return lstOfApplicationNames;
	}
	
	/**
	 * 
	 * @param propertyFile
	 * @return application name keys
	 * @throws IOException
	 */
	public List<String> getApplicationNamesKey(String propertyFile) throws IOException {
		HashMap<String, String> fileKeyValue = this.returnFileKeysAndValue(propertyFile);
		List<String> lstOfApplicationNameKeys = new ArrayList<String>();
		for(String key : fileKeyValue.keySet())
		{
			if(key.contains(WebServiceExplorerConstant.PROR_KEY_NAME_REF))
			{
				lstOfApplicationNameKeys.add(key);
			}
		}
		return lstOfApplicationNameKeys;				
	}
	
	/**
	 * @description get specific application names and keys
	 * @param propertyFile
	 * @return application name keys
	 * @throws IOException
	 */
	public List<String> getApplicationNamesKey(String propertyFile,List<String> typeList, List<String> appNameList) throws IOException {
		HashMap<String, String> fileKeyValue = this.returnFileKeysAndValue(propertyFile);
		if(null != typeList && typeList.size()>0)
		{
			fileKeyValue = (HashMap<String,String>)this.filterByAppName(fileKeyValue, typeList);
		}
		if(null != appNameList && appNameList.size()>0)
		{
			fileKeyValue = (HashMap<String,String>)this.filterByType(fileKeyValue, appNameList);		
		}
		List<String> lstOfApplicationNameKeys = new ArrayList<String>();
		for(String key : fileKeyValue.keySet())
		{
			if(key.contains(WebServiceExplorerConstant.PROR_KEY_NAME_REF))
			{
				lstOfApplicationNameKeys.add(key);
			}
		}
		return lstOfApplicationNameKeys;				
	}
	/**
	 * @author RaedSaad
	 * @return application URL based on application Name
	 * @Input property File Name and application name
	 */
	public String getApplicationURL(String propertyFile, String applicationName) throws IOException {
		List<String> propKeys = new ArrayList<String>();
		Map<String, String> hashMap = this.getApplicationNameAndServerPATHMap(propertyFile);
		if (null != hashMap) {
			return hashMap.get(applicationName);
		}
		return null;
	}

	public String getApplicationAddress(String webServiceUrl, String endPoint)
			throws MalformedURLException, IOException {
		Map<String, HashMap<String, List<String>>>  map = getWebServiceEndPointsWithInfo(webServiceUrl);
		return map.get(endPoint).get("Address").get(0);
	}

	/**
	 * @author RaedSaad
	 * @return application Name based on application URL
	 * @Input property File Name and application URL
	 */
	public String getApplicationName(String propertyFile, String applicationURL) throws IOException {
		List<String> propKeys = new ArrayList<String>();
		Map<String, String> hashMap = this.getApplicationNameAndServerPATHMap(propertyFile);
		if (null != hashMap) {
			for (String key : hashMap.keySet()) {
				if (applicationURL == hashMap.get(key)) {
					return key;
				}
			}
		}
		return null;
	}

	/**
	 * @author RaedSaad
	 * @return HashMap key = appName and value = appUrl
	 * @Input property File Name
	 */
	public HashMap<String, String> getApplicationNameAndServerPATHMap(String propertyFile) throws IOException {
	HashMap<String, String> fileKeyValue = this.returnFileKeysAndValue(propertyFile);
		HashMap<String,String> appNameWithPath = new HashMap<String,String>();		
		List<String>listApplicationNameKey = this.getApplicationNamesKey(propertyFile);
		String url = null;
		String appName = null;
		String index = null;
		for(String app : listApplicationNameKey)
		{
			index = app.replace(".name", ".url");
			url = fileKeyValue.get(index);
			url = url.replaceAll("\"", "");
			appName = fileKeyValue.get(app);
			appNameWithPath.put(appName, url);
		}
		return appNameWithPath;
	}
	
	public List<HashMap<String,String>> getApplicationNameAndServerPATHMap(String propertyFile,List<String> typeList,List<String>appNameList) throws IOException {
	    HashMap<String, String> fileKeyValue = this.returnFileKeysAndValue(propertyFile);
		HashMap<String,String> appNameWithPath = new HashMap<String,String>();	
		List<HashMap<String,String>> listAppNameWithPath = new ArrayList<HashMap<String,String>>();
		List<String>listApplicationNameKey = this.getApplicationNamesKey(propertyFile,typeList,appNameList);
		String url = null;
		String appName = null;
		String index = null;
		for(String app : listApplicationNameKey)
		{
			index = app.replace(".name", ".url");
			url = fileKeyValue.get(index);
			url = url.replaceAll("\"", "");
			appName = fileKeyValue.get(app);
			appNameWithPath.put(appName, url);
			listAppNameWithPath.add(appNameWithPath);
		}
		return listAppNameWithPath;
	}
	
	/**
	 * @description function that returns a hash map containing application name and keys
	 * @param propertyFile
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, String> getApplicationNameAndKey(String propertyFile,List<String> typeList,List<String>appNameList) throws IOException {
	HashMap<String, String> fileKeyValue = this.returnFileKeysAndValue(propertyFile);
		HashMap<String,String> appNameWithKey = new HashMap<String,String>();	
		List<String>listApplicationNameKey = this.getApplicationNamesKey(propertyFile,typeList,appNameList);
		String index = null;
		String url = null;
		String appName = null;
		for(String app : listApplicationNameKey)
		{
			index = app.replace(".name", ".url");
			url = fileKeyValue.get(index);
			url = url.replaceAll("\"", "");
			appName = fileKeyValue.get(app);
			appName = appName.replaceAll("\"", "");
			appNameWithKey.put(appName, app);
		}
		return appNameWithKey;
	}
	
	public HashMap<String, String> getApplicationNameAndKey(String propertyFile) throws IOException {
	HashMap<String, String> fileKeyValue = this.returnFileKeysAndValue(propertyFile);
		HashMap<String,String> appNameWithKey = new HashMap<String,String>();		
		List<String>listApplicationNameKey = this.getApplicationNamesKey(propertyFile);
		String index = null;
		StringBuilder appIndex = null;
		String url = null;
		String appName = null;
		for(String app : listApplicationNameKey)
		{
			index = app.replace(".name", ".url");
			url = fileKeyValue.get(index);
			url = url.replaceAll("\"", "");
			appName = fileKeyValue.get(app);
			appName = appName.replaceAll("\"", "");
			appNameWithKey.put(appName, app);
		}
		return appNameWithKey;
	}

	
	/**
	 * @author RaedSaad
	 * @return property file keys
	 * @Input property File Name
	 */
	private List<String> returnFileKeys(String propertyFile) throws IOException {
		List<String> propKeys = new ArrayList<String>();
		if (StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(propertyFile, ""))) {
			Properties prop = new Properties();

			String propFileName = (propertyFile.indexOf(".properties") > -1) ? propertyFile
					: propertyFile.concat(".properties");
			prop.load(PathPropertyUtil.class.getClassLoader().getResourceAsStream(propFileName));
			Set<String> propertyNames = prop.stringPropertyNames();

			for (String property : propertyNames) {
				propKeys.add(property);
			}
			return propKeys;
		}
		return null;
	}

	/**
	 * @description function to filter pathwebserviceList By tye
	 * @param input List ex : pws or cpws
	 * @param typeList
	 * @return
	 */
	public Object filterByType(Map<String,String> input, List<String> typeList)
	{
		Map<String,String> filteredMapByType = new HashMap<String,String>();
		for(String in : input.keySet())
		{
			for(String t : typeList)
			{
				if(in.contains(t))
				{
					filteredMapByType.put(in, input.get(in));
				}
			}
		}
		return filteredMapByType;		
	}
	/**
	 * @description function to filter pathwebserviceList By tye
	 * @param input List ex : pws or cpws
	 * @param typeList
	 * @return
	 */
	public Object filterByAppName(Map<String,String> input, List<String> appNameList)
	{
		Map<String,String> filteredMapByappName = new HashMap<String,String>();
		for(String in : input.keySet())
		{
			for(String t : appNameList)
			{
				if(in.contains(t))
				{
					filteredMapByappName.put(in, input.get(in));
				}
			}
		}
		return filteredMapByappName;	
	}
    static
    {
	/* In case of HTTPS call, to avoid getting the exception:
	 * javax.net.ssl.SSLHandshakeException: java.security.cert.CertificateException: No subject alternative names present.
	 * We need to disable the hostname verification between the url and the certificate owner*/
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() 
        {
            public boolean verify(String hostname, SSLSession session)
            {
               return true;
            }
        });
    }
    
	/**
	 * @author RaedSaad
	 * @return property file values
	 * @Input property File Name
	 */
	private HashMap<String, String> returnFileKeysAndValue(String propertyFile) throws IOException {
		HashMap<String, String> propVals = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(StringUtil.nullEmptyToValue(propertyFile, ""))) {
			Properties prop = new Properties();

			String propFileName = (propertyFile.indexOf(".properties") > -1) ? propertyFile
					: propertyFile.concat(".properties");
			prop.load(PathPropertyUtil.class.getClassLoader().getResourceAsStream(propFileName));
			Set<String> propertyNames = prop.stringPropertyNames();

			for (String property : propertyNames) {
				propVals.put(property, prop.getProperty(property));
			}
			return propVals;
		}
		return null;
	}

	/**
	 * @author RaedSaad
	 * @description creates a connection
	 * 
	 */
	private URLConnection returnURLConnection(String uri) throws MalformedURLException, IOException {
		return new URL(uri).openConnection();
	}
	private HttpsURLConnection returnSecureURLConnection(String uri) throws MalformedURLException, IOException {
		return  (HttpsURLConnection)(new URL(uri).openConnection());
	}

	/**
	 * @author RaedSaad
	 * @description converts a json string to json object
	 */
	public JSONObject jsonStringToJsonObject(String jsonString) {
		if (StringUtil.isNotEmpty(StringUtil.nullToEmpty(jsonString))) {
			return (JSONObject) JSONSerializer.toJSON(jsonString);
		}
		return null;
	}

	/**
	 * @author RaedSaad
	 * @description converts a jsonObject into a hashMap<String,String>
	 */
	public HashMap<String, String> jsonStringToHashMap(String jsonString) {
		JSONObject jsonObject = this.jsonStringToJsonObject(jsonString);
		if (null != jsonObject) {
			Map<String, String> infoHash;
			infoHash = new HashMap<String, String>();
			for (Object key : jsonObject.keySet()) {
				infoHash.put(key.toString(), jsonObject.getString(key.toString()));
			}
			return (HashMap<String, String>) infoHash;
		}
		return null;
	}
	
	/**
	 * @description function to convert a hashmap into a json object
	 * @param map
	 * @return
	 */
	public static JSONObject mapToJson(Map<String,String> map)
	{
		JSONObject jsonObj = new JSONObject();
		for(String s : map.keySet())
		{
			jsonObj.put(s, map.get(s));
		}
		return jsonObj;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getWebServiceHostUrl() {
		return webServiceHostUrl;
	}

	public void setWebServiceHostUrl(String webServiceHostUrl) {
		this.webServiceHostUrl = webServiceHostUrl;
	}

	public String getPropertiesFileName() {
		return propertiesFileName;
	}

	public void setPropertiesFileName(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
	}

	public Map<String, String> getWebServiceExplorerSelectedGridRows() {
		return webServiceExplorerSelectedGridRows;
	}

	public void setWebServiceExplorerSelectedGridRows(Map<String, String> webServiceExplorerSelectedGridRows) {
		this.webServiceExplorerSelectedGridRows = webServiceExplorerSelectedGridRows;
	}

	public Map<String, Map<String, String>> getWebServiceExplorerSelectedHashMaps() {
		return webServiceExplorerSelectedHashMaps;
	}

	public void setWebServiceExplorerSelectedHashMaps(
			Map<String, Map<String, String>> webServiceExplorerSelectedHashMaps) {
		this.webServiceExplorerSelectedHashMaps = webServiceExplorerSelectedHashMaps;
	}

	public Map<String, RequestResponseCO> getWebServiceExplorerGridParamDataToSave() {
		return webServiceExplorerGridParamDataToSave;
	}

	public void setWebServiceExplorerGridParamDataToSave(
			Map<String, RequestResponseCO> webServiceExplorerGridParamDataToSave) {
		this.webServiceExplorerGridParamDataToSave = webServiceExplorerGridParamDataToSave;
	}

	public IMCO_PWS_TEST_DETAILSVO getWebServiceExplorerDataVO() {
		return webServiceExplorerDataVO;
	}

	public void setWebServiceExplorerDataVO(IMCO_PWS_TEST_DETAILSVO webServiceExplorerDataVO) {
		this.webServiceExplorerDataVO = webServiceExplorerDataVO;
	}

	public List<IMCO_PWS_TEST_DETAILSVO> getWebServiceExplorerDataVOLst() {
		return webServiceExplorerDataVOLst;
	}

	public void setWebServiceExplorerDataVOLst(List<IMCO_PWS_TEST_DETAILSVO> webServiceExplorerDataVOLst) {
		this.webServiceExplorerDataVOLst = webServiceExplorerDataVOLst;
	}

	public WebServiceExplorerGridParamCO getWebServiceExplorerGridParamCO() {
		return webServiceExplorerGridParamCO;
	}

	public void setWebServiceExplorerGridParamCO(WebServiceExplorerGridParamCO webServiceExplorerGridParamCO) {
		this.webServiceExplorerGridParamCO = webServiceExplorerGridParamCO;
	}
}
