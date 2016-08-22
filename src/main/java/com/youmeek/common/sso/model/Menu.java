package com.youmeek.common.sso.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Menu implements Serializable {

	/****/
	private String id;

	/****/
	private String menuName;

	/****/
	private String pid;

	/****/
	private String resId;

	/****/
	private String menuUrl;

	/****/
	private String imageUrl;

	/**排序 顺序**/
	private Integer order;

	/****/
	private String menuDesc;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setMenuName(String menuName){
		this.menuName = menuName;
	}

	public String getMenuName(){
		return this.menuName;
	}

	public void setPid(String pid){
		this.pid = pid;
	}

	public String getPid(){
		return this.pid;
	}

	public void setResId(String resId){
		this.resId = resId;
	}

	public String getResId(){
		return this.resId;
	}

	public void setMenuUrl(String menuUrl){
		this.menuUrl = menuUrl;
	}

	public String getMenuUrl(){
		return this.menuUrl;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return this.imageUrl;
	}

	public void setOrder(Integer order){
		this.order = order;
	}

	public Integer getOrder(){
		return this.order;
	}

	public void setMenuDesc(String menuDesc){
		this.menuDesc = menuDesc;
	}

	public String getMenuDesc(){
		return this.menuDesc;
	}

}
