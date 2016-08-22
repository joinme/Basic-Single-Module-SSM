package com.youmeek.common.sso.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Role implements Serializable {

	/****/
	private String id;

	/**资源描述**/
	private String roleName;

	/**资源描述**/
	private String roleDesc;

	/**1=C端，2=B端，缺省为2**/
	private Integer roleType;

	/**1=有效，2=已禁用，9=已删除**/
	private Integer roleStatus;

	/**角色权重**/
	private Integer roleWeight;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	public String getRoleName(){
		return this.roleName;
	}

	public void setRoleDesc(String roleDesc){
		this.roleDesc = roleDesc;
	}

	public String getRoleDesc(){
		return this.roleDesc;
	}

	public void setRoleType(Integer roleType){
		this.roleType = roleType;
	}

	public Integer getRoleType(){
		return this.roleType;
	}

	public void setRoleStatus(Integer roleStatus){
		this.roleStatus = roleStatus;
	}

	public Integer getRoleStatus(){
		return this.roleStatus;
	}

	public void setRoleWeight(Integer roleWeight){
		this.roleWeight = roleWeight;
	}

	public Integer getRoleWeight(){
		return this.roleWeight;
	}

}
