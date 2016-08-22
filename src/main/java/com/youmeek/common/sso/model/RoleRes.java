package com.youmeek.common.sso.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class RoleRes implements Serializable {

	/****/
	private String id;

	/**主体类型1=U,2=R**/
	private Integer linkType;

	/****/
	private String resId;

	/****/
	private String roleId;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setLinkType(Integer linkType){
		this.linkType = linkType;
	}

	public Integer getLinkType(){
		return this.linkType;
	}

	public void setResId(String resId){
		this.resId = resId;
	}

	public String getResId(){
		return this.resId;
	}

	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	public String getRoleId(){
		return this.roleId;
	}

}
