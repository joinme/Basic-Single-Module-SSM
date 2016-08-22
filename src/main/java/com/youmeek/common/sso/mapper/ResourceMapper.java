package com.youmeek.common.sso.mapper;

import com.youmeek.common.sso.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * TResourceMapper数据库操作接口类
 * 
 **/
@Repository
public interface ResourceMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Resource selectByPrimaryKey ( String id );

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
	int insert( Resource record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Resource record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Resource record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Resource record );
	List<Resource> selectByObject ( Resource record );
	
	List<String> selectResourcesByUserId ( String userId );

}