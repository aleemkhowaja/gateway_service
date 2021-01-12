package com.path.bo.impl.customexpression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.customexpression.CustomExpressionBO;
import com.path.dao.customexpression.CustomExpressionDAO;
import com.path.dbmaps.vo.SYS_PARAM_CUSTOM_EXPRESSIONVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.StringUtil;
import com.path.struts2.json.PathJSONUtil;
import com.path.vo.customexpression.CustomExpressionCO;
import com.path.vo.customexpression.CustomExpressionSC;
import com.path.vo.customexpression.ExpressionParamCO;

public class CustomExpressionBOImpl extends BaseBO implements CustomExpressionBO
{
    
    private CustomExpressionDAO customExpressionDAO;

    @Override
    public CustomExpressionCO returnCustomExpressionList(CustomExpressionSC  customExpressionSC,StringBuffer autoCompleteList) throws Exception
    {
	customExpressionSC.setNbRec(-1);
	
	CustomExpressionCO customExpressionCO = new CustomExpressionCO();
	//Retrieve Customn Expression List
	List<SYS_PARAM_CUSTOM_EXPRESSIONVO> customExpressionList = customExpressionDAO.returnCustomExpressionList(customExpressionSC);

	List<SYS_PARAM_CUSTOM_EXPRESSIONVO> expressionList = new ArrayList<SYS_PARAM_CUSTOM_EXPRESSIONVO>();
	for(int i=0; i<customExpressionList.size(); i++)
	{
	    SYS_PARAM_CUSTOM_EXPRESSIONVO custom_EXPRESSIONVO = customExpressionList.get(i);
	    autoCompleteList.append("[GLOBAL." + custom_EXPRESSIONVO.getSHORT_NAME() + "];");
	    
	    custom_EXPRESSIONVO.setSHORT_NAME("[GLOBAL." + custom_EXPRESSIONVO.getSHORT_NAME() + "]");
	    expressionList.add(custom_EXPRESSIONVO);
	}
	customExpressionCO.setCustomExpressionAutoCompleteData(autoCompleteList.toString());
	customExpressionCO.setCustomExpressionList("{\"root\":".concat(PathJSONUtil.serialize(expressionList, null, null, false, true)).concat("}"));
	
	customExpressionCO.setIsGlobalYN(customExpressionSC.getAddToExpressionList());
	customExpressionCO.getCustom_EXPRESSIONVO().setSHORT_NAME(customExpressionSC.getShortName());
	customExpressionCO.getCustom_EXPRESSIONVO().setDESCRIPTION(customExpressionSC.getDescription());
	return customExpressionCO;
    }

    @Override
    public Integer saveCustomExpression(CustomExpressionCO customExpressionCO) throws BaseException
    {
	SYS_PARAM_CUSTOM_EXPRESSIONVO   cUSTOM_EXPRESSIONVO = customExpressionCO.getCustom_EXPRESSIONVO();
	
	Integer records  = 0;
	if(StringUtil.isNotEmpty(cUSTOM_EXPRESSIONVO.getEXPRESSION()))
	{
	    //validate the expression
	    boolean isValidExpression = commonLibBO.checkExpressionSyntax(cUSTOM_EXPRESSIONVO.getEXPRESSION());
	    
	    if(isValidExpression)
	    {
		//prepare the expression
		prepareExpression(cUSTOM_EXPRESSIONVO.getEXPRESSION(), customExpressionCO.getAllGridList());
	    }
	    
	    if(isValidExpression && StringUtil.nullToEmpty(customExpressionCO.getIsGlobalYN()).equalsIgnoreCase(ConstantsCommon.YES))
	    {
		//insert Gateway Custom Expression
		records =  customExpressionDAO.saveCustomExpression(cUSTOM_EXPRESSIONVO);
	    }
	}
	return records;
    }

    @Override
    public CustomExpressionCO validateCustomExpressionByShortName(CustomExpressionSC  customExpressionSC) throws BaseException
    {
	CustomExpressionCO customExpressionCO = new CustomExpressionCO();
	
	/**
	 * Check if short name is empty then it shouldn't be validated
	 */
	if(StringUtil.isEmptyString(customExpressionSC.getShortName())) return customExpressionCO;
	
	//return Expression by shortName
	SYS_PARAM_CUSTOM_EXPRESSIONVO  custom_EXPRESSIONVO = customExpressionDAO.validateCustomExpressionByShortName(customExpressionSC);
	
	if(custom_EXPRESSIONVO != null)
	{
	    throw new BOException(MessageCodes.DUPLICATE_VALUE);
	}
	return customExpressionCO;
    }
    
    
    @Override
    public CustomExpressionCO applyVisiblityOnCustomExpressionFields(CustomExpressionCO customExpressionCO) throws BaseException
    {
	
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> elementMap = customExpressionCO.getElementMap();
	SYS_PARAM_SCREEN_DISPLAYVO sysParam = new SYS_PARAM_SCREEN_DISPLAYVO();
	if(StringUtil.nullToEmpty(customExpressionCO.getIsGlobalYN()).equalsIgnoreCase(ConstantsCommon.YES))
	{
	    sysParam.setIS_VISIBLE(BigDecimal.ONE);
	    sysParam.setIS_MANDATORY(BigDecimal.ONE);
	}
	else
	{
	    sysParam.setIS_VISIBLE(BigDecimal.ZERO);
	    sysParam.setIS_MANDATORY(BigDecimal.ZERO);
	}
	elementMap.put("shortName", sysParam);
	elementMap.put("description", sysParam);

	return customExpressionCO;
    }
    
    //prepare the Expression Parameters
    private Object prepareExpression(String expression, List<ExpressionParamCO> expressionIsoFieldList) throws BaseException
    {
	/**
	 * Add all ISO Fields inside map
	 */
	Map<String, Object> exprDataMap  = new HashMap<String, Object>();
	if(expressionIsoFieldList != null && expressionIsoFieldList.size() > 0)
	{
	    expressionIsoFieldList.stream().forEach(expressionCO->{
		String value = expressionCO.getParameterValue().replaceAll("\\[|]", "");
		exprDataMap.put(value, "0");
	    });
	}
	Object  isMatchExpression = executeExpression(expression, exprDataMap);
	return isMatchExpression;
    }
    
    
    /**
     * execute the expression
     * @param exprBoolSyntax
     * @param exprDataMap
     * @return
     * @throws BaseException
     */
    private Object executeExpression(String exprBoolSyntax, Map<String, Object> exprDataMap) throws BaseException
    {
	if(StringUtil.isNotEmpty(exprBoolSyntax))
	{
	    String exprSyntax = exprBoolSyntax;
	    List<Map<String,Object>> exprDataList = new ArrayList<Map<String,Object>>(); 
	    exprDataList.add(exprDataMap);
	    Object result = commonLibBO.executeExpression(exprSyntax, 0, exprDataList);
	    log.debug("the result of dummy Expression is dataForExpr" + result);
	    return result;
	}
	return null;
    }
    
    public void setCustomExpressionDAO(CustomExpressionDAO customExpressionDAO)
    {
        this.customExpressionDAO = customExpressionDAO;
    }
}