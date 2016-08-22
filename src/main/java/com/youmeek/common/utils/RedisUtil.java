package com.youmeek.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/27 14:34
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Component
public class RedisUtil {
	public static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	public static final int MAX_EXPIRE_SECONES = 2592000;
	@Resource
	private JedisPool jedisPool = null;
	
	public RedisUtil() {
	}
	
	public Set<String> keys(String key) throws Exception {
		if(Util.isNullOrBlank(key)) {
			return null;
		} else {
			Jedis shardJedis = this.getRedisClient();
			if(shardJedis == null) {
				return null;
			} else {
				Set keys = shardJedis.keys(key);
				this.returnResource(shardJedis, false);
				return keys;
			}
		}
	}
	
	public boolean exists(String key) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return false;
		} else {
			boolean value = shardJedis.exists(key).booleanValue();
			this.returnResource(shardJedis, false);
			return value;
		}
	}
	
	public long expire(String key, int seconds) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return 0L;
		} else {
			long result = shardJedis.expire(key, seconds).longValue();
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public long delCache(String key) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return 0L;
		} else {
			Long result = shardJedis.del(key);
			this.returnResource(shardJedis, false);
			return result.longValue();
		}
	}
	
	public String getSet(String key, String value) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		return shardJedis == null?null:shardJedis.getSet(key, value);
	}
	
	public long setNX(String key, String value) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		return shardJedis == null?0L:shardJedis.setnx(key, value).longValue();
	}
	
	public String getString(String key) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return null;
		} else {
			String value = shardJedis.get(key);
			this.returnResource(shardJedis, false);
			return value;
		}
	}
	
	public String saveOrUpdateString(String key, String value, int seconds) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return null;
		} else {
			if(seconds > 2592000) {
				seconds = 2592000;
			}
			
			String result = shardJedis.set(key, value);
			shardJedis.expire(key, seconds);
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public long lPush(String key, Integer seconds, String... values) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return 0L;
		} else {
			long result = shardJedis.lpush(key, values).longValue();
			if(seconds != null) {
				if(seconds.intValue() > 2592000) {
					seconds = Integer.valueOf(2592000);
				}
				
				shardJedis.expire(key, seconds.intValue());
			}
			
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public long rPush(String key, String[] values, Integer seconds) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return 0L;
		} else {
			long result = shardJedis.rpush(key, values).longValue();
			if(seconds != null) {
				if(seconds.intValue() > 2592000) {
					seconds = Integer.valueOf(2592000);
				}
				
				shardJedis.expire(key, seconds.intValue());
			}
			
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public String rPop(String key, Integer seconds) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return null;
		} else {
			String result = shardJedis.rpop(key);
			if(seconds != null) {
				if(seconds.intValue() > 2592000) {
					seconds = Integer.valueOf(2592000);
				}
				
				shardJedis.expire(key, seconds.intValue());
			}
			
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public String lPop(String key, Integer seconds) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return null;
		} else {
			String result = shardJedis.lpop(key);
			if(seconds != null) {
				if(seconds.intValue() > 2592000) {
					seconds = Integer.valueOf(2592000);
				}
				
				shardJedis.expire(key, seconds.intValue());
			}
			
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public List<String> bLPop(String key, Integer blockTime, Integer seconds) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return null;
		} else {
			List result = shardJedis.blpop(blockTime.intValue(), new String[]{key});
			if(seconds != null) {
				if(seconds.intValue() > 2592000) {
					seconds = Integer.valueOf(2592000);
				}
				
				shardJedis.expire(key, seconds.intValue());
			}
			
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public List<String> bRPop(String key, Integer blockTime, Integer seconds) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return null;
		} else {
			List result = shardJedis.brpop(blockTime.intValue(), new String[]{key});
			if(seconds != null) {
				if(seconds.intValue() > 2592000) {
					seconds = Integer.valueOf(2592000);
				}
				
				shardJedis.expire(key, seconds.intValue());
			}
			
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public long linsert(String key, String index, String value) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return 0L;
		} else {
			long result = shardJedis.linsert(key, BinaryClient.LIST_POSITION.AFTER, index, value).longValue();
			this.returnResource(shardJedis, false);
			return result;
		}
	}
	
	public long size(String key) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return 0L;
		} else {
			long value = shardJedis.llen(key).longValue();
			this.returnResource(shardJedis, false);
			return value;
		}
	}
	
	public List<String> getLRange(String key, long start, long end) throws Exception {
		Jedis shardJedis = this.getRedisClient();
		if(shardJedis == null) {
			return null;
		} else {
			List value = shardJedis.lrange(key, start, end);
			this.returnResource(shardJedis, false);
			return value;
		}
	}
	
	public Jedis getRedisClient() throws Exception {
		try {
			return this.jedisPool.getResource();
		} catch (Exception var2) {
			logger.error("获取Redis链接异常。getRedisClent error", var2);
			throw new Exception(var2.getMessage());
		}
	}
	
	public void returnResource(Jedis redis, boolean broken) {
		if(broken) {
			this.jedisPool.returnBrokenResource(redis);
		} else {
			this.jedisPool.returnResource(redis);
		}
		
	}
	
	
}
