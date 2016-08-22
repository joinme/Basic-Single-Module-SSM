package com.youmeek.common.sso.mapper;

import com.youmeek.common.sso.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * TUserMapper数据库操作接口类
 * 
 **/
@Repository
public interface UserMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	User selectByPrimaryKey ( String id );

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
	int insert( User record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( User record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( User record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( User record );
	
	List<User> selectByObject ( User record );
	
	User queryUser ( User record );

}