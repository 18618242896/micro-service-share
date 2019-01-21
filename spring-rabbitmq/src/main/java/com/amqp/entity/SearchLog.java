package com.amqp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志实体类，需要实现序列化接口
 */
public class SearchLog implements Serializable{

    //日志时间
    private Date logDate;
    //查询词
    private String searchValue;
    //所属IP
    private String ipAddress;
    //账户
    private String account;

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return logDate+"\t"+account+"\t"+searchValue+"\t"+ipAddress;
    }
}
