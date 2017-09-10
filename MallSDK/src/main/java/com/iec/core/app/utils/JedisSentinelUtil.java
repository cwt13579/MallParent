package com.iec.core.app.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Response;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import com.google.gson.Gson;
import com.iec.core.app.utils.PropsConfig;

public class JedisSentinelUtil {
	private static final Logger log = Logger.getLogger(JedisSentinelUtil.class);
	private static JedisSentinelPool pool = null;
	public static final Gson gson = new Gson();
    public static Object lock=new Object();
	private static JedisSentinelPool getPool() {
		if (pool == null || pool.isClosed()) {
			synchronized (lock) {
				try {
					if(pool!=null&&!pool.isClosed()){
						return pool;
					}
					
					@SuppressWarnings("rawtypes")
					Set sentinels = new HashSet();
					String masterName = PropsConfig.getPropValue("redis_masterName");
					String serversString = PropsConfig.getPropValue("redis_servers");
					String password = PropsConfig.getPropValue("redis_password");

					String[] servers = serversString.split(",");
					for (int i = 0; i < servers.length; i++) {
						sentinels.add(servers[i]);
					}

					JedisPoolConfig config = new JedisPoolConfig();
					// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
					config.setBlockWhenExhausted(true);
					// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
					config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
					// 是否启用pool的jmx管理功能, 默认true
					config.setJmxEnabled(true);
					// MBean ObjectName = new
					// ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name="
					// + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
					config.setJmxNamePrefix("pool");
					// 是否启用后进先出, 默认true
					config.setLifo(true);
					// 最大空闲连接数, 默认8个
					config.setMaxIdle(100);
					// 最大连接数, 默认8个
					config.setMaxTotal(100);
					// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常,
					// 小于零:阻塞不确定的时间, 默认-1
					config.setMaxWaitMillis(-1);
					// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
					config.setMinEvictableIdleTimeMillis(1800000);
					// 最小空闲连接数, 默认0
					config.setMinIdle(0);
					// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
					config.setNumTestsPerEvictionRun(3);
					// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
					// 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
					config.setSoftMinEvictableIdleTimeMillis(1800000);
					// 在获取连接的时候检查有效性, 默认false
					config.setTestOnBorrow(true);
					// 在空闲时检查有效性, 默认false
					config.setTestWhileIdle(false);
					// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
					config.setTimeBetweenEvictionRunsMillis(-1);
					// timeout 读取超时30秒
					int timeout = 30000;
					if (null != password && !"".equals(password)) {
						pool = new JedisSentinelPool(masterName, sentinels, config, timeout, password,
								Protocol.DEFAULT_DATABASE);
					} else {
						pool = new JedisSentinelPool(masterName, sentinels, config, timeout);
					}
					if (log.isDebugEnabled()) {
						log.debug("初始化cache:====================" + PropsConfig.getPropValue("redis_servers"));
					}
				} catch (Exception e) {
					String msg = "============初始化redis服务器失败，请检查redis服务器配置";
					log.error(msg, e);
					throw new RuntimeException(msg, e);
				}
			}
			
		}
		return pool;
	}
	public static boolean hexists(String key, String field){
		Jedis jedis = null;
		
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.hexists(key, field);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			e.printStackTrace();
			return false;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
		
	}
	
	public static boolean set(String key, Object obj) {
		Jedis jedis = null;
		
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			if (obj instanceof String) {
				jedis.set(key.getBytes(), ((String) obj).getBytes());
				
			} else {
				jedis.set(key.getBytes(), gson.toJson(obj).getBytes());
			}
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			e.printStackTrace();
			return false;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}

		return true;
	}

	public static void set(String key, Object obj, Integer minute) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			if (obj instanceof String) {
				jedis.set(key.getBytes(), ((String) obj).getBytes());
			} else {
				jedis.set(key.getBytes(), gson.toJson(obj).getBytes());
			}
			jedis.expire(key.getBytes(), 60 * minute);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	
	public static void replace(String key, Object obj, Integer minute) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			if (obj instanceof String) {
				jedis.set(key.getBytes(), ((String) obj).getBytes());
			} else {
				jedis.set(key.getBytes(), gson.toJson(obj).getBytes());
			}
			jedis.expire(key.getBytes(), 60 * minute);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 删除key 对应的数据
	 * @param key
	 */
	public static void delete(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			jedis.del(key);
			log.info("delete___cache:" + key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 批量删除
	 * @param key
	 */
	public static void delete(String ... key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			jedis.del(key);
			log.info("delete___cache:" + key);
			System.out.println("delete___cache:" + key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 递减(数据为整型)
	 * @param key
	 * @return
	 */
	public static Long decr(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.decr(key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return null;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 减number值返回值(数据为整型)
	 * @param key
	 * @param number
	 * @return
	 */
	public static long decr(String key, int number) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.decrBy(key, number);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new RuntimeException("redisExcpetion");
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 设置失效时间key 对应的数据
	 * @param key
	 * @param sec
	 * @return
	 */
	public static long expire(String key, int sec) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.expire(key, sec);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new RuntimeException("redisExcpetion");
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 递增(数据为整型)
	 * @param key
	 * @return
	 */
	public static Long incr(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.incr(key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return null;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 增number值返回值(数据为整型)
	 * @param key
	 * @param number
	 * @return
	 */
	public static long incr(String key, int number) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.incrBy(key, number);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new RuntimeException("redisExcpetion");
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * set String 数据
	 * @param key
	 * @param val
	 */
	public static void set(String key, String val) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			jedis.set(key, val);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new RuntimeException("redisExcpetion");
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * get 取key值对应的数据
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		Jedis jedis = null;
		boolean broken = false;
		String result = null;
		try {
			jedis = getPool().getResource();
			result = jedis.get(key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new RuntimeException("redisExcpetion");
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
		return result;
	}

	/**
	 * 设置值并同时获取旧的值,如果旧的值不存在，同时设置过期时间
	 * 该方法适用于在多线程情况下的一些操作逻辑，保障在多线程同时调用该api时，只有第一个set会设置超时时间
	 * 一般用于控制某些操作的重复调用的问题，比如订单提交时的频率控制
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getSetAndExpireIfNew(String key, String value, int second) throws Exception {
		Jedis jedis = null;
		boolean broken = false;
		String result = null;
		try {
			jedis = getPool().getResource();
			result = jedis.getSet(key, value);
			if (result == null) {
				jedis.expire(key, second);
			}
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new Exception("redis-error", e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
		return result;
	}
	/**
	 * 设值String类型并且设置时间
	 * @param key
	 * @param val
	 * @param minute
	 */
	public static void setStringMin(String key, String val, Integer minute) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			jedis.set(key, val);
			jedis.expire(key, 60 * minute);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	
	public static void setStringSec(String key, String val, int second) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			// jedis.set(key,val);
			// jedis.expire(key, second);
			jedis.setex(key, second, val);// modified by chender
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new RuntimeException("redisExcpetion");
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	
	/**
	 * 查询是否存在key且设置了超时时间
	 * 下单专用
	 * @param key
	 * @return
	 */
	public static boolean existsAndHasExpireTime(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			long expireTime = jedis.ttl(key);//已过期返回-2，没设置过期时间返回-1
			if(expireTime==-1){//如果由于写的时候异常(两步操作不是原子的)，导致没有设置超时间，直接del掉，避免用户永远都下不了单
				jedis.del(key);
				return false;
			}
			return expireTime!=-2;
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new RuntimeException("redisExcpetion");
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	
	/**
	 * 查询是否存在key 对应的数据
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.exists(key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			throw new RuntimeException("redisExcpetion");
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}

	public static Jedis getConnection() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return null;
		}
		return jedis;
	}
	/**
	 * 在指定Key所关联的List Value的头部插入参数中给出的所有Values。
	 * 如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的头部插入。
	 * 如果该键的Value不是链表类型，该命令将返回相关的错误信息。 	
	 * @param key
	 * @param value
	 * @return 插入后链表中元素的数量。
	 */
	public static Long lpush(String key, String value) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.lpush(key, value);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return null;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	
	
	/**
	 * 在指定Key所关联的List Value的尾部插入参数中给出的所有Values。
	 * 如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的头部插入。
	 * 如果该键的Value不是链表类型，该命令将返回相关的错误信息。 	
	 * @param key
	 * @param value
	 * @return 插入后链表中元素的数量。
	 */
	public static Long rpush(String key, String value) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.rpush(key, value);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return null;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 返回并弹出指定Key关联的链表中的最后，即尾部元素，。如果该Key不存，返回nil。
	 * @param key
	 * @return
	 */
	public static String rpop(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.rpop(key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return null;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 返回并弹出指定Key关联的链表中的第一个元素，即头部元素，。如果该Key不存，返回nil。
	 * @param key
	 * @return
	 */
	public static String lpop(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.lpop(key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return null;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hset(String key, String field, String value) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			jedis.hset(key, field, value);
			
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * hash自增 在名字为key的map里面为field自增step
	 * @param key map的名字
	 * @param field 字段
	 * @param step 自增值
	 * @return 自增后的值
	 */
	public static Long hincrBy(String key, String field, long step) {
		Jedis jedis = null;
		long res=0l;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
		res=	jedis.hincrBy(key, field, step);
			
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
		return res;
	}
	
	
	/**
	 * 命令用于设置指定字段各自的值，在存储于键的散列。此命令将覆盖哈希任何现有字段。如果键不存在，新的key由哈希创建。
	 * @param key
	 * @param hash
	 */
	public static void hmset(String key, Map<String,String> hash) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			jedis.hmset(key, hash) ;
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
 
	public static String hget(String key, String field) {
		Jedis jedis = null;
		String value = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			value = jedis.hget(key, field);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
		return value;
	}
	
	public static Map<String, String> hgetAll(String key) {
		Jedis jedis = null;
		Map<String, String> value = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			value = jedis.hgetAll(key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
		return value;
	}

	/**
	 * 
	 * <li>@Description:获取一个list
	 * <li>@param key
	 * <li>@param start 开始 从0开始
	 * <li>@param stop 结束 正数表示第N个 负数表示倒数第N个
	 * <li>@return
	 * <li>创建人：韩啸
	 * <li>创建时间：2016年8月10日
	 * <li>修改人：
	 * <li>修改时间：
	 */
	public static List<String> lrange(String key, int start, int stop) {
		Jedis jedis = null;
		List<String> value = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			value = jedis.lrange(key, start, stop);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
		return value;
	}

	/**
	 * 
	 * <li>@Description:批量获取map
	 * <li>@param keys
	 * <li>@return
	 * <li>创建人：韩啸
	 * <li>创建时间：2016年8月18日
	 * <li>修改人：
	 * <li>修改时间：
	 */
	public static List<Map<String, String>> hgetallBatch(List<String> keys) {
		Jedis jedis = null;
		List<Map<String, String>> value = new ArrayList<Map<String, String>>();
		boolean broken = false;
		try {
			Map<String, Response<Map<String, String>>> responses = new HashMap<String, Response<Map<String, String>>>(
					keys.size());
			jedis = getPool().getResource();
			Pipeline p = jedis.pipelined();
			for (String key : keys) {
				responses.put(key, p.hgetAll(key));
			}
			p.sync();

			for (String k : responses.keySet()) {
				value.add(responses.get(k).get());
			}
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
		return value;
	}

	public static void hdel(String key, String field) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			jedis.hdel(key, field);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * @param key
	 * @param count
	 * @param field
	 * @return
	 */
	public static long lrem(String key, int count, String field) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.lrem(key, count, field);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return 0;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}
	/**
	 * Redis LLEN命令将返回存储在key列表的长度。
	 * 如果key不存在，它被解释为一个空列表，则返回0。
	 * 当存储在关key的值不是一个列表，则会返回错误
	 * @param key
	 * @return
	 */
	public static Long llen(String key) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getPool().getResource();
			return jedis.llen(key);
		} catch (JedisException e) {
			log.error(e);
			broken = handleJedisException(e);
			return null;
		} finally {
			// 返还到连接池
			closeResource(jedis, broken);
		}
	}

	public static void returnResource(Jedis jedis) {
		try {
			getPool().returnResource(jedis);
		} catch (Exception e) {
			log.error("return back jedis failed, will fore close the jedis.", e);
		}
	}

	protected static boolean handleJedisException(JedisException jedisException) {
		if (jedisException instanceof JedisConnectionException) {
			log.error("Redis connection " + " lost.", jedisException);
		} else if (jedisException instanceof JedisDataException) {
			if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
				log.error("Redis connection " + " are read-only slave.", jedisException);
			} else {
				// dataException, isBroken=false
				return false;
			}
		} else {
			log.error("Jedis exception happen.", jedisException);
		}
		return true;
	}

	protected static void closeResource(Jedis jedis, boolean conectionBroken) {
		try {
			if (conectionBroken) {
				getPool().returnBrokenResource(jedis);
			} else {
				getPool().returnResource(jedis);
			}
		} catch (Exception e) {
			log.error("return back jedis failed, will fore close the jedis.", e);
			getPool().destroy();
		}
	}
}