package com.youmeek.common.sso.manager.impl;

import com.youmeek.common.Constant;
import com.youmeek.common.base.model.BaseModel;
import com.youmeek.common.sso.manager.UserManager;
import com.youmeek.common.sso.mapper.UserMapper;
import com.youmeek.common.sso.model.User;
import com.youmeek.common.utils.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/8/10 17:11
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Service
public class UserManagerImpl implements UserManager {
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public BaseModel queryLoginUser(User user) {
		String userName = user.getUserName();
		if (Util.isNullOrBlank(userName)) {
			return new BaseModel(Constant.FAIL, "用户名不能为空");
		}
		String passWord = user.getPassWord();
		if (Util.isNullOrBlank(passWord)) {
			return new BaseModel(Constant.FAIL, "密码不能为空");
		}
		user = userMapper.queryUser(user);
		if (user == null) {
			return new BaseModel(Constant.FAIL, "用户名或者密码错误");
		}
		return new BaseModel(Constant.SUCCESS, user);
	}
}
