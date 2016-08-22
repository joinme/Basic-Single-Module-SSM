package com.youmeek.common.sso.manager;

import com.youmeek.common.sso.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/8/8 16:27
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Service
public class ResourceManager {
	@Resource
	private ResourceMapper resourceMapper;
	
	public List<String> getResourcesByUserId(String userId) {
		return resourceMapper.selectResourcesByUserId(userId);
	}
	
	
}
