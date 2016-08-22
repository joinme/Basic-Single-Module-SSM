package com.youmeek.common.sso.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Resource implements Serializable {

	/****/
	private String id;

	/**资源描述**/
	private String resDesc;

	/**资源类型 1、链接 2 、菜单**/
	private Integer resType;

	/**资源地址**/
	private String resUrl;

	/****/
	private String resName;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setResDesc(String resDesc){
		this.resDesc = resDesc;
	}

	public String getResDesc(){
		return this.resDesc;
	}

	public void setResType(Integer resType){
		this.resType = resType;
	}

	public Integer getResType(){
		return this.resType;
	}

	public void setResUrl(String resUrl){
		this.resUrl = resUrl;
	}

	public String getResUrl(){
		return this.resUrl;
	}

	public void setResName(String resName){
		this.resName = resName;
	}

	public String getResName(){
		return this.resName;
	}

}
