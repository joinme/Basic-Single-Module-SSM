package com.youmeek.common.sso.manager;

import com.youmeek.common.base.model.BaseModel;
import com.youmeek.common.sso.model.User;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/8/9 18:16
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
public interface UserManager {
	
	BaseModel queryLoginUser(User user);
	
}
