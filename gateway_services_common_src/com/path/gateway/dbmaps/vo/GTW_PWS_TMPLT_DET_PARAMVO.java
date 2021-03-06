package com.path.gateway.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;

public class GTW_PWS_TMPLT_DET_PARAMVO extends BaseVO {
    /**
     * This field corresponds to the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.APP_NAME
     */
    private String APP_NAME;

    /**
     * This field corresponds to the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.OPERATION
     */
    private String OPERATION;

    /**
     * This field corresponds to the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.PARAM_ID
     */
    private String PARAM_ID;

    /**
     * This field corresponds to the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.TEMPLATE_ID
     */
    private BigDecimal TEMPLATE_ID;

    /**
     * This field corresponds to the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.WS_NAME
     */
    private String WS_NAME;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.APP_NAME
     *
     * @return the value of DGTL_GTW_PWS_TMPLT_DET_PARAM.APP_NAME
     */
    public String getAPP_NAME() {
        return APP_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.APP_NAME
     *
     * @param APP_NAME the value for DGTL_GTW_PWS_TMPLT_DET_PARAM.APP_NAME
     */
    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME == null ? null : APP_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.OPERATION
     *
     * @return the value of DGTL_GTW_PWS_TMPLT_DET_PARAM.OPERATION
     */
    public String getOPERATION() {
        return OPERATION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.OPERATION
     *
     * @param OPERATION the value for DGTL_GTW_PWS_TMPLT_DET_PARAM.OPERATION
     */
    public void setOPERATION(String OPERATION) {
        this.OPERATION = OPERATION == null ? null : OPERATION.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.PARAM_ID
     *
     * @return the value of DGTL_GTW_PWS_TMPLT_DET_PARAM.PARAM_ID
     */
    public String getPARAM_ID() {
        return PARAM_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.PARAM_ID
     *
     * @param PARAM_ID the value for DGTL_GTW_PWS_TMPLT_DET_PARAM.PARAM_ID
     */
    public void setPARAM_ID(String PARAM_ID) {
        this.PARAM_ID = PARAM_ID == null ? null : PARAM_ID.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.TEMPLATE_ID
     *
     * @return the value of DGTL_GTW_PWS_TMPLT_DET_PARAM.TEMPLATE_ID
     */
    public BigDecimal getTEMPLATE_ID() {
        return TEMPLATE_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.TEMPLATE_ID
     *
     * @param TEMPLATE_ID the value for DGTL_GTW_PWS_TMPLT_DET_PARAM.TEMPLATE_ID
     */
    public void setTEMPLATE_ID(BigDecimal TEMPLATE_ID) {
        this.TEMPLATE_ID = TEMPLATE_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.WS_NAME
     *
     * @return the value of DGTL_GTW_PWS_TMPLT_DET_PARAM.WS_NAME
     */
    public String getWS_NAME() {
        return WS_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DGTL_GTW_PWS_TMPLT_DET_PARAM.WS_NAME
     *
     * @param WS_NAME the value for DGTL_GTW_PWS_TMPLT_DET_PARAM.WS_NAME
     */
    public void setWS_NAME(String WS_NAME) {
        this.WS_NAME = WS_NAME == null ? null : WS_NAME.trim();
    }
}