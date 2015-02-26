package com.sge.clear.admin.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;


public class ClearStepDO implements Comparable<ClearStepDO>{
    
	@Range(min=1,max=500,message="清算场次号请输入1-500间的整数")
	private Integer 	step;

    @NotEmpty(message="清算场次名称不能为空")
	private String 		name;

    private String 		ip;

    @NotEmpty(message="清算场次应用路径不能为空")
    private String 		url;

    private String 		logfile;

    private String 		param;

    private Integer 	isEnable;

    private String 		comt;

    private Date 		gmtCreated;

    private Date 		gmtModified;

    private String 		userModified;
	
	private String 		status = "Waiting";		//步骤状态

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getLogfile() {
        return logfile;
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile == null ? null : logfile.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getComt() {
        return comt;
    }

    public void setComt(String comt) {
        this.comt = comt == null ? null : comt.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getUserModified() {
        return userModified;
    }

    public void setUserModified(String userModified) {
        this.userModified = userModified == null ? null : userModified.trim();
    }
	
		public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(ClearStepDO other) {
		if(this.step>other.step) return +1;
		if(this.step<other.step) return -1;
		return 0;
	}
	
	@Override
	public String toString() {
		return String.format("name=%s,step=%d,ip=%s,url=%s,logfile=%s,param=%s,enable=%d,comt=%s,createTime=%s,lastTime=%s,lastOper=%s",
				name,step,ip,url,logfile,param,isEnable,comt,gmtCreated,gmtModified,userModified);
	}
}