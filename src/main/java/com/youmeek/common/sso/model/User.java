package com.youmeek.common.sso.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 10:38
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class User {
	private String id;
	private String userId;
	private String userName;
	@JSONField(
			serialize = false
	)
	private String passWord;
	private String nickName;
	private int sex;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	@JSONField(
			format = "yyyy-MM-dd"
	)
	private Date birthday;
	private int registerType;
	private String mobile;
	private String telephone;
	private String email;
	private String realName;
	private int idType;
	private String idNumber;
	private String picGroupName;
	private String picFileName;
	private int registResource;
	private int status;
	private int activeFlag;
	private int userType;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JSONField(
			format = "yyyy-MM-dd HH:mm:ss"
	)
	private Date lastloginTime;
	private int channelType = 1;
	private String recommendCode;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	@JSONField(
			format = "yyyy-MM-dd"
	)
	private Date registTime;
	
	private Date createTime;
	private Date updateTime;
	private String createUser;
	private String updateUser;
	private String lastLoginTime;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public int getSex() {
		return sex;
	}
	
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public int getRegisterType() {
		return registerType;
	}
	
	public void setRegisterType(int registerType) {
		this.registerType = registerType;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public int getIdType() {
		return idType;
	}
	
	public void setIdType(int idType) {
		this.idType = idType;
	}
	
	public String getIdNumber() {
		return idNumber;
	}
	
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPicGroupName() {
		return picGroupName;
	}
	
	public void setPicGroupName(String picGroupName) {
		this.picGroupName = picGroupName;
	}
	
	public String getPicFileName() {
		return picFileName;
	}
	
	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}
	
	public int getRegistResource() {
		return registResource;
	}
	
	public void setRegistResource(int registResource) {
		this.registResource = registResource;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getActiveFlag() {
		return activeFlag;
	}
	
	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public int getUserType() {
		return userType;
	}
	
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	public Date getLastloginTime() {
		return lastloginTime;
	}
	
	public void setLastloginTime(Date lastloginTime) {
		this.lastloginTime = lastloginTime;
	}
	
	public int getChannelType() {
		return channelType;
	}
	
	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}
	
	public String getRecommendCode() {
		return recommendCode;
	}
	
	public void setRecommendCode(String recommendCode) {
		this.recommendCode = recommendCode;
	}
	
	public Date getRegistTime() {
		return registTime;
	}
	
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getUpdateUser() {
		return updateUser;
	}
	
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}
