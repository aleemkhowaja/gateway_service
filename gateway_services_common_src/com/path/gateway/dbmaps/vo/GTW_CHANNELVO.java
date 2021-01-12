package com.path.gateway.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class GTW_CHANNELVO extends BaseVO {
    /**
     * This field corresponds to the database column GTW_CHANNEL.CHANNEL_ID
     */
    private BigDecimal CHANNEL_ID;

    /**
     * This field corresponds to the database column GTW_CHANNEL.DESCRIPTION
     */
    private String DESCRIPTION;

    /**
     * This field corresponds to the database column GTW_CHANNEL.USER_ID
     */
    private String USER_ID;

    /**
     * This field corresponds to the database column GTW_CHANNEL.CREATED_BY
     */
    private String CREATED_BY;

    /**
     * This field corresponds to the database column GTW_CHANNEL.CREATED_DATE
     */
    private Date CREATED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.MODIFIED_BY
     */
    private String MODIFIED_BY;

    /**
     * This field corresponds to the database column GTW_CHANNEL.MODIFIED_DATE
     */
    private Date MODIFIED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.APPROVED_BY
     */
    private String APPROVED_BY;

    /**
     * This field corresponds to the database column GTW_CHANNEL.APPROVED_DATE
     */
    private Date APPROVED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    /**
     * This field corresponds to the database column GTW_CHANNEL.DELETED_BY
     */
    private String DELETED_BY;

    /**
     * This field corresponds to the database column GTW_CHANNEL.DELETED_DATE
     */
    private Date DELETED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.REACTIVATED_BY
     */
    private String REACTIVATED_BY;

    /**
     * This field corresponds to the database column GTW_CHANNEL.REACTIVATED_DATE
     */
    private Date REACTIVATED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SERVER_APPROVED_DATE
     */
    private Date SERVER_APPROVED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SERVER_CREATED_DATE
     */
    private Date SERVER_CREATED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SERVER_DELETED_DATE
     */
    private Date SERVER_DELETED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SERVER_MODIFIED_DATE
     */
    private Date SERVER_MODIFIED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SERVER_REACTIVATED_DATE
     */
    private Date SERVER_REACTIVATED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SERVER_SUSPENDED_DATE
     */
    private Date SERVER_SUSPENDED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.STATUS
     */
    private String STATUS;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SUSPENDED_BY
     */
    private String SUSPENDED_BY;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SUSPENDED_DATE
     */
    private Date SUSPENDED_DATE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.ACTIVE_QUEUE_YN
     */
    private String ACTIVE_QUEUE_YN;

    /**
     * This field corresponds to the database column GTW_CHANNEL.COMMUNICATION_PROTOCOL
     */
    private String COMMUNICATION_PROTOCOL;

    /**
     * This field corresponds to the database column GTW_CHANNEL.HTTP_PASSWORD
     */
    private String HTTP_PASSWORD;

    /**
     * This field corresponds to the database column GTW_CHANNEL.HTTP_REQUEST_TIME_OUT
     */
    private BigDecimal HTTP_REQUEST_TIME_OUT;

    /**
     * This field corresponds to the database column GTW_CHANNEL.HTTP_USER
     */
    private String HTTP_USER;

    /**
     * This field corresponds to the database column GTW_CHANNEL.INTERFACE_CODE
     */
    private BigDecimal INTERFACE_CODE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.IP_ADDRESS
     */
    private String IP_ADDRESS;

    /**
     * This field corresponds to the database column GTW_CHANNEL.PARALLELISM_CONTROL
     */
    private BigDecimal PARALLELISM_CONTROL;

    /**
     * This field corresponds to the database column GTW_CHANNEL.PORT
     */
    private String PORT;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SERVER_TYPE
     */
    private String SERVER_TYPE;

    /**
     * This field corresponds to the database column GTW_CHANNEL.SOCKET_RESTART_INTERVAL
     */
    private BigDecimal SOCKET_RESTART_INTERVAL;

    /**
     * This field corresponds to the database column GTW_CHANNEL.END_POINT
     */
    private String END_POINT;

    /**
     * This field corresponds to the database column GTW_CHANNEL.TO_BE_STATUS
     */
    private String TO_BE_STATUS;

    /**
     * This field corresponds to the database column GTW_CHANNEL.TO_BE_STATUS_BY
     */
    private String TO_BE_STATUS_BY;

    /**
     * This field corresponds to the database column GTW_CHANNEL.TO_BE_STATUS_DATE
     */
    private Date TO_BE_STATUS_DATE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.CHANNEL_ID
     *
     * @return the value of GTW_CHANNEL.CHANNEL_ID
     */
    public BigDecimal getCHANNEL_ID() {
        return CHANNEL_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.CHANNEL_ID
     *
     * @param CHANNEL_ID the value for GTW_CHANNEL.CHANNEL_ID
     */
    public void setCHANNEL_ID(BigDecimal CHANNEL_ID) {
        this.CHANNEL_ID = CHANNEL_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.DESCRIPTION
     *
     * @return the value of GTW_CHANNEL.DESCRIPTION
     */
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.DESCRIPTION
     *
     * @param DESCRIPTION the value for GTW_CHANNEL.DESCRIPTION
     */
    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION == null ? null : DESCRIPTION.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.USER_ID
     *
     * @return the value of GTW_CHANNEL.USER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.USER_ID
     *
     * @param USER_ID the value for GTW_CHANNEL.USER_ID
     */
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID == null ? null : USER_ID.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.CREATED_BY
     *
     * @return the value of GTW_CHANNEL.CREATED_BY
     */
    public String getCREATED_BY() {
        return CREATED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.CREATED_BY
     *
     * @param CREATED_BY the value for GTW_CHANNEL.CREATED_BY
     */
    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY == null ? null : CREATED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.CREATED_DATE
     *
     * @return the value of GTW_CHANNEL.CREATED_DATE
     */
    public Date getCREATED_DATE() {
        return CREATED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.CREATED_DATE
     *
     * @param CREATED_DATE the value for GTW_CHANNEL.CREATED_DATE
     */
    public void setCREATED_DATE(Date CREATED_DATE) {
        this.CREATED_DATE = CREATED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.MODIFIED_BY
     *
     * @return the value of GTW_CHANNEL.MODIFIED_BY
     */
    public String getMODIFIED_BY() {
        return MODIFIED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.MODIFIED_BY
     *
     * @param MODIFIED_BY the value for GTW_CHANNEL.MODIFIED_BY
     */
    public void setMODIFIED_BY(String MODIFIED_BY) {
        this.MODIFIED_BY = MODIFIED_BY == null ? null : MODIFIED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.MODIFIED_DATE
     *
     * @return the value of GTW_CHANNEL.MODIFIED_DATE
     */
    public Date getMODIFIED_DATE() {
        return MODIFIED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.MODIFIED_DATE
     *
     * @param MODIFIED_DATE the value for GTW_CHANNEL.MODIFIED_DATE
     */
    public void setMODIFIED_DATE(Date MODIFIED_DATE) {
        this.MODIFIED_DATE = MODIFIED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.APPROVED_BY
     *
     * @return the value of GTW_CHANNEL.APPROVED_BY
     */
    public String getAPPROVED_BY() {
        return APPROVED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.APPROVED_BY
     *
     * @param APPROVED_BY the value for GTW_CHANNEL.APPROVED_BY
     */
    public void setAPPROVED_BY(String APPROVED_BY) {
        this.APPROVED_BY = APPROVED_BY == null ? null : APPROVED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.APPROVED_DATE
     *
     * @return the value of GTW_CHANNEL.APPROVED_DATE
     */
    public Date getAPPROVED_DATE() {
        return APPROVED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.APPROVED_DATE
     *
     * @param APPROVED_DATE the value for GTW_CHANNEL.APPROVED_DATE
     */
    public void setAPPROVED_DATE(Date APPROVED_DATE) {
        this.APPROVED_DATE = APPROVED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.DATE_UPDATED
     *
     * @return the value of GTW_CHANNEL.DATE_UPDATED
     */
    public Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.DATE_UPDATED
     *
     * @param DATE_UPDATED the value for GTW_CHANNEL.DATE_UPDATED
     */
    public void setDATE_UPDATED(Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.DELETED_BY
     *
     * @return the value of GTW_CHANNEL.DELETED_BY
     */
    public String getDELETED_BY() {
        return DELETED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.DELETED_BY
     *
     * @param DELETED_BY the value for GTW_CHANNEL.DELETED_BY
     */
    public void setDELETED_BY(String DELETED_BY) {
        this.DELETED_BY = DELETED_BY == null ? null : DELETED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.DELETED_DATE
     *
     * @return the value of GTW_CHANNEL.DELETED_DATE
     */
    public Date getDELETED_DATE() {
        return DELETED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.DELETED_DATE
     *
     * @param DELETED_DATE the value for GTW_CHANNEL.DELETED_DATE
     */
    public void setDELETED_DATE(Date DELETED_DATE) {
        this.DELETED_DATE = DELETED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.REACTIVATED_BY
     *
     * @return the value of GTW_CHANNEL.REACTIVATED_BY
     */
    public String getREACTIVATED_BY() {
        return REACTIVATED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.REACTIVATED_BY
     *
     * @param REACTIVATED_BY the value for GTW_CHANNEL.REACTIVATED_BY
     */
    public void setREACTIVATED_BY(String REACTIVATED_BY) {
        this.REACTIVATED_BY = REACTIVATED_BY == null ? null : REACTIVATED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.REACTIVATED_DATE
     *
     * @return the value of GTW_CHANNEL.REACTIVATED_DATE
     */
    public Date getREACTIVATED_DATE() {
        return REACTIVATED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.REACTIVATED_DATE
     *
     * @param REACTIVATED_DATE the value for GTW_CHANNEL.REACTIVATED_DATE
     */
    public void setREACTIVATED_DATE(Date REACTIVATED_DATE) {
        this.REACTIVATED_DATE = REACTIVATED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SERVER_APPROVED_DATE
     *
     * @return the value of GTW_CHANNEL.SERVER_APPROVED_DATE
     */
    public Date getSERVER_APPROVED_DATE() {
        return SERVER_APPROVED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SERVER_APPROVED_DATE
     *
     * @param SERVER_APPROVED_DATE the value for GTW_CHANNEL.SERVER_APPROVED_DATE
     */
    public void setSERVER_APPROVED_DATE(Date SERVER_APPROVED_DATE) {
        this.SERVER_APPROVED_DATE = SERVER_APPROVED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SERVER_CREATED_DATE
     *
     * @return the value of GTW_CHANNEL.SERVER_CREATED_DATE
     */
    public Date getSERVER_CREATED_DATE() {
        return SERVER_CREATED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SERVER_CREATED_DATE
     *
     * @param SERVER_CREATED_DATE the value for GTW_CHANNEL.SERVER_CREATED_DATE
     */
    public void setSERVER_CREATED_DATE(Date SERVER_CREATED_DATE) {
        this.SERVER_CREATED_DATE = SERVER_CREATED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SERVER_DELETED_DATE
     *
     * @return the value of GTW_CHANNEL.SERVER_DELETED_DATE
     */
    public Date getSERVER_DELETED_DATE() {
        return SERVER_DELETED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SERVER_DELETED_DATE
     *
     * @param SERVER_DELETED_DATE the value for GTW_CHANNEL.SERVER_DELETED_DATE
     */
    public void setSERVER_DELETED_DATE(Date SERVER_DELETED_DATE) {
        this.SERVER_DELETED_DATE = SERVER_DELETED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SERVER_MODIFIED_DATE
     *
     * @return the value of GTW_CHANNEL.SERVER_MODIFIED_DATE
     */
    public Date getSERVER_MODIFIED_DATE() {
        return SERVER_MODIFIED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SERVER_MODIFIED_DATE
     *
     * @param SERVER_MODIFIED_DATE the value for GTW_CHANNEL.SERVER_MODIFIED_DATE
     */
    public void setSERVER_MODIFIED_DATE(Date SERVER_MODIFIED_DATE) {
        this.SERVER_MODIFIED_DATE = SERVER_MODIFIED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SERVER_REACTIVATED_DATE
     *
     * @return the value of GTW_CHANNEL.SERVER_REACTIVATED_DATE
     */
    public Date getSERVER_REACTIVATED_DATE() {
        return SERVER_REACTIVATED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SERVER_REACTIVATED_DATE
     *
     * @param SERVER_REACTIVATED_DATE the value for GTW_CHANNEL.SERVER_REACTIVATED_DATE
     */
    public void setSERVER_REACTIVATED_DATE(Date SERVER_REACTIVATED_DATE) {
        this.SERVER_REACTIVATED_DATE = SERVER_REACTIVATED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SERVER_SUSPENDED_DATE
     *
     * @return the value of GTW_CHANNEL.SERVER_SUSPENDED_DATE
     */
    public Date getSERVER_SUSPENDED_DATE() {
        return SERVER_SUSPENDED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SERVER_SUSPENDED_DATE
     *
     * @param SERVER_SUSPENDED_DATE the value for GTW_CHANNEL.SERVER_SUSPENDED_DATE
     */
    public void setSERVER_SUSPENDED_DATE(Date SERVER_SUSPENDED_DATE) {
        this.SERVER_SUSPENDED_DATE = SERVER_SUSPENDED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.STATUS
     *
     * @return the value of GTW_CHANNEL.STATUS
     */
    public String getSTATUS() {
        return STATUS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.STATUS
     *
     * @param STATUS the value for GTW_CHANNEL.STATUS
     */
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS == null ? null : STATUS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SUSPENDED_BY
     *
     * @return the value of GTW_CHANNEL.SUSPENDED_BY
     */
    public String getSUSPENDED_BY() {
        return SUSPENDED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SUSPENDED_BY
     *
     * @param SUSPENDED_BY the value for GTW_CHANNEL.SUSPENDED_BY
     */
    public void setSUSPENDED_BY(String SUSPENDED_BY) {
        this.SUSPENDED_BY = SUSPENDED_BY == null ? null : SUSPENDED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SUSPENDED_DATE
     *
     * @return the value of GTW_CHANNEL.SUSPENDED_DATE
     */
    public Date getSUSPENDED_DATE() {
        return SUSPENDED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SUSPENDED_DATE
     *
     * @param SUSPENDED_DATE the value for GTW_CHANNEL.SUSPENDED_DATE
     */
    public void setSUSPENDED_DATE(Date SUSPENDED_DATE) {
        this.SUSPENDED_DATE = SUSPENDED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.ACTIVE_QUEUE_YN
     *
     * @return the value of GTW_CHANNEL.ACTIVE_QUEUE_YN
     */
    public String getACTIVE_QUEUE_YN() {
        return ACTIVE_QUEUE_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.ACTIVE_QUEUE_YN
     *
     * @param ACTIVE_QUEUE_YN the value for GTW_CHANNEL.ACTIVE_QUEUE_YN
     */
    public void setACTIVE_QUEUE_YN(String ACTIVE_QUEUE_YN) {
        this.ACTIVE_QUEUE_YN = ACTIVE_QUEUE_YN == null ? null : ACTIVE_QUEUE_YN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.COMMUNICATION_PROTOCOL
     *
     * @return the value of GTW_CHANNEL.COMMUNICATION_PROTOCOL
     */
    public String getCOMMUNICATION_PROTOCOL() {
        return COMMUNICATION_PROTOCOL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.COMMUNICATION_PROTOCOL
     *
     * @param COMMUNICATION_PROTOCOL the value for GTW_CHANNEL.COMMUNICATION_PROTOCOL
     */
    public void setCOMMUNICATION_PROTOCOL(String COMMUNICATION_PROTOCOL) {
        this.COMMUNICATION_PROTOCOL = COMMUNICATION_PROTOCOL == null ? null : COMMUNICATION_PROTOCOL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.HTTP_PASSWORD
     *
     * @return the value of GTW_CHANNEL.HTTP_PASSWORD
     */
    public String getHTTP_PASSWORD() {
        return HTTP_PASSWORD;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.HTTP_PASSWORD
     *
     * @param HTTP_PASSWORD the value for GTW_CHANNEL.HTTP_PASSWORD
     */
    public void setHTTP_PASSWORD(String HTTP_PASSWORD) {
        this.HTTP_PASSWORD = HTTP_PASSWORD == null ? null : HTTP_PASSWORD.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.HTTP_REQUEST_TIME_OUT
     *
     * @return the value of GTW_CHANNEL.HTTP_REQUEST_TIME_OUT
     */
    public BigDecimal getHTTP_REQUEST_TIME_OUT() {
        return HTTP_REQUEST_TIME_OUT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.HTTP_REQUEST_TIME_OUT
     *
     * @param HTTP_REQUEST_TIME_OUT the value for GTW_CHANNEL.HTTP_REQUEST_TIME_OUT
     */
    public void setHTTP_REQUEST_TIME_OUT(BigDecimal HTTP_REQUEST_TIME_OUT) {
        this.HTTP_REQUEST_TIME_OUT = HTTP_REQUEST_TIME_OUT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.HTTP_USER
     *
     * @return the value of GTW_CHANNEL.HTTP_USER
     */
    public String getHTTP_USER() {
        return HTTP_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.HTTP_USER
     *
     * @param HTTP_USER the value for GTW_CHANNEL.HTTP_USER
     */
    public void setHTTP_USER(String HTTP_USER) {
        this.HTTP_USER = HTTP_USER == null ? null : HTTP_USER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.INTERFACE_CODE
     *
     * @return the value of GTW_CHANNEL.INTERFACE_CODE
     */
    public BigDecimal getINTERFACE_CODE() {
        return INTERFACE_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.INTERFACE_CODE
     *
     * @param INTERFACE_CODE the value for GTW_CHANNEL.INTERFACE_CODE
     */
    public void setINTERFACE_CODE(BigDecimal INTERFACE_CODE) {
        this.INTERFACE_CODE = INTERFACE_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.IP_ADDRESS
     *
     * @return the value of GTW_CHANNEL.IP_ADDRESS
     */
    public String getIP_ADDRESS() {
        return IP_ADDRESS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.IP_ADDRESS
     *
     * @param IP_ADDRESS the value for GTW_CHANNEL.IP_ADDRESS
     */
    public void setIP_ADDRESS(String IP_ADDRESS) {
        this.IP_ADDRESS = IP_ADDRESS == null ? null : IP_ADDRESS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.PARALLELISM_CONTROL
     *
     * @return the value of GTW_CHANNEL.PARALLELISM_CONTROL
     */
    public BigDecimal getPARALLELISM_CONTROL() {
        return PARALLELISM_CONTROL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.PARALLELISM_CONTROL
     *
     * @param PARALLELISM_CONTROL the value for GTW_CHANNEL.PARALLELISM_CONTROL
     */
    public void setPARALLELISM_CONTROL(BigDecimal PARALLELISM_CONTROL) {
        this.PARALLELISM_CONTROL = PARALLELISM_CONTROL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.PORT
     *
     * @return the value of GTW_CHANNEL.PORT
     */
    public String getPORT() {
        return PORT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.PORT
     *
     * @param PORT the value for GTW_CHANNEL.PORT
     */
    public void setPORT(String PORT) {
        this.PORT = PORT == null ? null : PORT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SERVER_TYPE
     *
     * @return the value of GTW_CHANNEL.SERVER_TYPE
     */
    public String getSERVER_TYPE() {
        return SERVER_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SERVER_TYPE
     *
     * @param SERVER_TYPE the value for GTW_CHANNEL.SERVER_TYPE
     */
    public void setSERVER_TYPE(String SERVER_TYPE) {
        this.SERVER_TYPE = SERVER_TYPE == null ? null : SERVER_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.SOCKET_RESTART_INTERVAL
     *
     * @return the value of GTW_CHANNEL.SOCKET_RESTART_INTERVAL
     */
    public BigDecimal getSOCKET_RESTART_INTERVAL() {
        return SOCKET_RESTART_INTERVAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.SOCKET_RESTART_INTERVAL
     *
     * @param SOCKET_RESTART_INTERVAL the value for GTW_CHANNEL.SOCKET_RESTART_INTERVAL
     */
    public void setSOCKET_RESTART_INTERVAL(BigDecimal SOCKET_RESTART_INTERVAL) {
        this.SOCKET_RESTART_INTERVAL = SOCKET_RESTART_INTERVAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.END_POINT
     *
     * @return the value of GTW_CHANNEL.END_POINT
     */
    public String getEND_POINT() {
        return END_POINT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.END_POINT
     *
     * @param END_POINT the value for GTW_CHANNEL.END_POINT
     */
    public void setEND_POINT(String END_POINT) {
        this.END_POINT = END_POINT == null ? null : END_POINT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.TO_BE_STATUS
     *
     * @return the value of GTW_CHANNEL.TO_BE_STATUS
     */
    public String getTO_BE_STATUS() {
        return TO_BE_STATUS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.TO_BE_STATUS
     *
     * @param TO_BE_STATUS the value for GTW_CHANNEL.TO_BE_STATUS
     */
    public void setTO_BE_STATUS(String TO_BE_STATUS) {
        this.TO_BE_STATUS = TO_BE_STATUS == null ? null : TO_BE_STATUS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.TO_BE_STATUS_BY
     *
     * @return the value of GTW_CHANNEL.TO_BE_STATUS_BY
     */
    public String getTO_BE_STATUS_BY() {
        return TO_BE_STATUS_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.TO_BE_STATUS_BY
     *
     * @param TO_BE_STATUS_BY the value for GTW_CHANNEL.TO_BE_STATUS_BY
     */
    public void setTO_BE_STATUS_BY(String TO_BE_STATUS_BY) {
        this.TO_BE_STATUS_BY = TO_BE_STATUS_BY == null ? null : TO_BE_STATUS_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GTW_CHANNEL.TO_BE_STATUS_DATE
     *
     * @return the value of GTW_CHANNEL.TO_BE_STATUS_DATE
     */
    public Date getTO_BE_STATUS_DATE() {
        return TO_BE_STATUS_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GTW_CHANNEL.TO_BE_STATUS_DATE
     *
     * @param TO_BE_STATUS_DATE the value for GTW_CHANNEL.TO_BE_STATUS_DATE
     */
    public void setTO_BE_STATUS_DATE(Date TO_BE_STATUS_DATE) {
        this.TO_BE_STATUS_DATE = TO_BE_STATUS_DATE;
    }
}