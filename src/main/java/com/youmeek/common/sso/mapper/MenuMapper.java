package com.youmeek.common.sso.mapper;

import com.youmeek.common.sso.model.Menu;
import java.util.List;

/**
 * 
 * TMenuMapper数据库操作接口类
 * 
 **/

public interface MenuMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Menu selectByPrimaryKey ( String id );

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey (String id );

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert( Menu record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Menu record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Menu record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Menu record );
	List<Menu> selectByObject ( Menu record );

}