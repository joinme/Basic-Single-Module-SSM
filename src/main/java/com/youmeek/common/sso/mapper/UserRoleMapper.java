package com.youmeek.common.sso.mapper;

import com.youmeek.common.sso.model.UserRole;
import java.util.List;

/**
 * 
 * TUserRoleMapper数据库操作接口类
 * 
 **/

public interface UserRoleMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	UserRole selectByPrimaryKey ( String id );

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
	int insert( UserRole record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( UserRole record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( UserRole record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( UserRole record );
	List<UserRole> selectByObject ( UserRole record );

}