package com.wangzaiplus.test.util;

import com.wangzaiplus.test.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 设值
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        try {
            Jedis jedis = getJedis();
            return jedis.set(key, value);
        } catch (Exception e) {
            throw new RuntimeException("set异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 设值
     *
     * @param key
     * @param value
     * @param expireTime 过期时间, 单位: s
     * @return
     */
    public String set(String key, String value, int expireTime) {
        try {
            Jedis jedis = getJedis();
            String result = jedis.set(key, value);
            if (Constant.Redis.OK.equals(result)) {
                jedis.expire(key, expireTime);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("set-expireTime异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 取值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            Jedis jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            throw new RuntimeException("get异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 设值-object
     *
     * @param key
     * @param value
     * @return
     */
    public String setObject(String key, Object value) {
        try {
            Jedis jedis = getJedis();
            return jedis.set(key.getBytes(), SerializableUtil.serializable(value));
        } catch (Exception e) {
            throw new RuntimeException("setObject异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 设值-object-expireTime
     *
     * @param key
     * @param value
     * @param expireTime 过期时间, 单位: s
     * @return
     */
    public String setObject(String key, Object value, int expireTime) {
        try {
            Jedis jedis = getJedis();
            String result = jedis.set(key.getBytes(), SerializableUtil.serializable(value));
            if (Constant.Redis.OK.equals(result)) {
                jedis.expire(key.getBytes(), expireTime);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("setObject-expireTime异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 取值-object
     *
     * @param key
     * @return
     */
    public Object getObject(String key) {
        try {
            Jedis jedis = getJedis();
            byte[] bytes = jedis.get(key.getBytes());
            if (null == bytes || bytes.length <= 0) {
                return null;
            }
            return SerializableUtil.unserializable(bytes);
        } catch (Exception e) {
            throw new RuntimeException("getObject异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        try {
            Jedis jedis = getJedis();
            return jedis.del(key.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("del异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        try {
            Jedis jedis = getJedis();
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("exists异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 设值key过期时间
     *
     * @param key
     * @param seconds
     * @return
     */
    public long expire(String key, int seconds) {
        try {
            Jedis jedis = getJedis();
            return jedis.expire(key.getBytes(), seconds);
        } catch (Exception e) {
            throw new RuntimeException("keys异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 模糊查询获取key集合
     *
     * @param key
     * @return
     */
    public Set<String> keys(String key) {
        try {
            Jedis jedis = getJedis();
            return jedis.keys(key);
        } catch (Exception e) {
            throw new RuntimeException("keys异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

    /**
     * 获取剩余时间
     *
     * @param key
     * @return
     */
    public Long ttl(String key) {
        try {
            Jedis jedis = getJedis();
            return jedis.ttl(key);
        } catch (Exception e) {
            throw new RuntimeException("ttl异常: key: " + key + ", cause: " + e.getMessage());
        }
    }

}
