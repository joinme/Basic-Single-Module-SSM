package com.youmeek.common.sso.mapper;

import com.youmeek.common.sso.model.RoleRes;
import java.util.List;

/**
 * 
 * TRoleResMapper数据库操作接口类
 * 
 **/

public interface RoleResMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	RoleRes selectByPrimaryKey ( String id );

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
	int insert( RoleRes record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( RoleRes record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( RoleRes record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( RoleRes record );
	List<RoleRes> selectByObject ( RoleRes record );

}