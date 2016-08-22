package com.youmeek.common.sso.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class UserRole implements Serializable {

	/****/
	private String id;

	/****/
	private String roleId;

	/****/
	private String userId;

	/**主体类型1=U,2=R**/
	private Integer linkType;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	public String getRoleId(){
		return this.roleId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setLinkType(Integer linkType){
		this.linkType = linkType;
	}

	public Integer getLinkType(){
		return this.linkType;
	}

}
