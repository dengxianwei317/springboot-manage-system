package com.spring.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * redis 访问操作工具类
 */
@Component
@Slf4j
public class RedisUtils {
    /*@Autowired
    private redisTemplate<String, Object> redisTemplate;

    //@Resource(name = "redisTemplate")
    private ValueOperations<String, Object> valOpsStr = redisTemplate.opsForValue();

    //@Resource(name = "redisTemplate")
    private SetOperations<String, Object> valOpsSet = redisTemplate.opsForSet();

    //@Resource(name = "redisTemplate")
    private ZSetOperations<String, Object> valOpsZSet = redisTemplate.opsForZSet();

    //@Resource(name = "redisTemplate")
    ListOperations<String, Object> valOpsList = redisTemplate.opsForList();

    //@Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> valOpsHash = redisTemplate.opsForHash();

    private static final ObjectMapper mapper = new ObjectMapper();

    *//**
     * 将数据存入缓存
     *//*
    public void saveString(String key, String val) {
        valOpsStr.set(key, val);
    }

    *//**
     * 将数据存入缓存的集合中
     *//*
    public void saveToSet(String key, String val) {
        valOpsSet.add(key, val);
    }

    *//**
     * 从Set中获取数据
     *//*
    public Object getFromSet(String key) {
        return valOpsSet.pop(key);
    }

    *//**
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * SETNX 是『SETif Not eXists』(如果不存在，则 SET)的简写。
     * 保存成功，返回 true
     * 保存失败，返回 false
     *//*
    public boolean saveNX(String key, String val) {
        return valOpsStr.setIfAbsent(key, val);
    }

    *//**
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * SETNX 是『SETif Not eXists』(如果不存在，则 SET)的简写。
     * 保存成功，返回 true
     * 保存失败，返回 false
     * expire 单位：秒
     * 保存成功，返回 true 否则返回 false
     *//*
    public boolean saveNX(String key, String val, int expire) {
        boolean ret = saveNX(key, val);
        if (ret) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return ret;
    }

    *//**
     * 将数据存入缓存（并设置失效时间）
     *//*
    public void saveString(String key, String val, int seconds) {
        valOpsStr.set(key, val, seconds, TimeUnit.SECONDS);
    }

    *//**
     * 将自增变量存入缓存
     *//*
    public void saveSeq(String key, long seqNo) {
        redisTemplate.delete(key);
        valOpsStr.increment(key, seqNo);
    }

    *//**
     * 将递增浮点数存入缓存
     *//*
    public void saveFloat(String key, float data) {
        redisTemplate.delete(key);
        valOpsStr.increment(key, data);
    }

    *//**
     * 保存复杂类型数据到缓存
     *//*
    public void saveObject(String key, Object obj) {
        try {
            redisTemplate.opsForValue().set(key, obj);
        } catch (Exception e) {
            logger.error("遇到错误", e);
        }
    }

    *//**
     * 保存复杂类型数据到缓存（并设置失效时间）
     *//*
    public void saveObject(String key, Object value, int seconds) {
        if (seconds > 0) {
            redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        } else {
            this.saveObject(key, value);
        }
    }

    *//**
     * 功能: 存到指定的队列中，不限制队列大小
     * 左进右出
     *//*
    public void saveToQueue(String key, String val) {
        saveToQueue(key, val, 0);
    }

    *//**
     * 功能: 存到指定的队列中
     * 左进右出
     * size 队列大小限制 0：不限制
     *//*
    public void saveToQueue(String key, String val, long size) {
        if (size > 0 && valOpsList.size(key) >= size) {
            valOpsList.rightPop(key);
        }
        valOpsList.leftPush(key, val);
    }

    *//**
     * 保存到hash集合中
     * hName 集合名
     *//*
    public void hashSet(String hName, String key, String value) {
        valOpsHash.put(hName, key, value);
    }

    *//**
     * 保存到hash集合中
     * hName 集合名
     *//*
    public void hashSet(String hName, Map<String, String> hashMap) {
        valOpsHash.putAll(hName, hashMap);
    }

    *//**
     * 根据key获取所以值
     *//*
    public Map<String, Object> getAll(String key) {
        return valOpsHash.entries(key);
    }

    *//**
     * 保存到hash集合中
     * hName 集合名ProcessingException
     *//*
    public <T> void hashSet(String hName, String key, T t) throws JsonProcessingException {
        hashSet(hName, key, mapper.writeValueAsString(t));
    }

    *//**
     * 保存到hash集合中 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与
     * key 关联。如果字段已存在，该操作无效果。
     * hName集合名
     *//*
    public void hSetnx(String hName, String key, String value) {
        valOpsHash.putIfAbsent(hName, key, value);
    }

    *//**
     * 保存到hash集合中 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与
     * key关联。如果字段已存在，该操作无效果。
     * hName集合名
     *//*
    public <T> void hSetnx(String hName, String key, T t) throws JsonProcessingException {
        hSetnx(hName, key, mapper.writeValueAsString(t));
    }

    *//**
     * 删除Hash中的key项
     *//*
    public void deleteKey(String hName, String key) {
        valOpsHash.delete(hName, key);
    }

    *//**
     * 取得复杂类型数据
     *//*
    public Object getObject(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    *//**
     * 从缓存中取得字符串数据
     *//*
    public Object get(String key) {
        return valOpsStr.get(key);
    }

    *//**
     * 功能: 从指定队列里取得数据
     * size 数据长度
     *//*
    public List<Object> getFromQueue(String key) {
        return getFromQueue(key, 0);
    }

    public List<Object> getFromQueue(String key, long size) {
        boolean flag = redisTemplate.execute((redisTemplateCallback<Boolean>) connection -> {
            return connection.exists(key.getBytes());
        });
        if (flag) {
            return new ArrayList<>();
        }
        if (size > 0) {
            return valOpsList.range(key, 0, size - 1);
        } else {
            return valOpsList.range(key, 0, valOpsList.size(key) - 1);
        }
    }

    *//**
     * 功能: 从指定队列里取得数据
     *//*
    public Object popQueue(String key) {
        return valOpsList.rightPop(key);
    }

    *//**
     * 取得序列值的下一个
     *//*
    public Long getSeqNext(String key) {
        return redisTemplate.execute((redisTemplateCallback<Long>) connection -> {
            return connection.incr(key.getBytes());
        });
    }

    *//**
     * 取得序列值的下一个，增加 value
     *//*
    public Long getSeqNext(String key, long value) {
        return redisTemplate.execute((redisTemplateCallback<Long>) connection -> {
            return connection.incrBy(key.getBytes(), value);
        });

    }

    *//**
     * 将序列值回退一个
     *//*
    public void getSeqBack(String key) {
        redisTemplate.execute((redisTemplateCallback<Long>) connection -> connection.decr(key.getBytes()));
    }

    *//**
     * 增加浮点数的值
     *//*
    public Double incrFloat(String key, double incrBy) {
        return redisTemplate.execute((redisTemplateCallback<Double>) connection -> {
            return connection.incrBy(key.getBytes(), incrBy);
        });
    }

    *//**
     * 从hash集合里取得
     *//*
    public Object getHash(String hName, String key) {
        return valOpsHash.get(hName, key);
    }

    public <T> T getHash(String hName, String key, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue((String) getHash(hName, key), clazz);
    }

    *//**
     * 判断是否缓存了数据
     *//*
    public boolean exists(String key) {
        return redisTemplate.execute((redisTemplateCallback<Boolean>) connection -> {
            return connection.exists(key.getBytes());
        });
    }

    *//**
     * 判断hash集合中是否缓存了数据, 有问题
     *//*
    public boolean hashExists(String hName, String key) {
        return valOpsHash.hasKey(hName, key);
    }

    *//**
     * 判断是否缓存在指定的集合中
     *//*
    public boolean isMember(String key, String val) {
        return valOpsSet.isMember(key, val);
    }

    *//**
     * 从缓存中删除数据
     *//*
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    *//**
     * 设置超时时间
     *//*
    public void expire(String key, int seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    *//**
     * 列出set中所有成员
     *//*
    public Set<Object> listSet(String setName) {
        return valOpsSet.members(setName);
    }

    *//**
     * 向set中追加一个值
     *//*
    public void appendSet(String setName, String value) {
        valOpsSet.add(setName, value);
    }

    *//**
     * 向sorted set中追加一个值
     *//*
    public void saveToSortedSet(String key, String member, Double score) {
        valOpsZSet.add(key, member, score);
    }

    *//**
     * 根据成员名取得sorted sort分数
     * key set名
     * member 成员名
     *
     * @return 分数
     *//*
    public Double getMemberScore(String key, String member) {
        return valOpsZSet.score(key, member);
    }

    *//**
     * 从sorted set删除一个值
     *//*
    public void deleteSortedSet(String key, String member) {
        valOpsZSet.remove(key, member);
    }

    *//**
     * 逆序列出sorted set包括分数的set列表
     * key set名
     * start 开始位置
     * end  结束位置
     *//*
    public Set<TypedTuple<Object>> getReverseRangeWith(String key, int start, int end) {
        return valOpsZSet.reverseRangeWithScores(key, start, end);
    }

    *//**
     * 逆序取得sorted sort排名
     * key    set名
     * member 成员名
     *//*
    public Long getReverseRank(String key, String member) {
        return valOpsZSet.reverseRank(key, member);
    }

    *//**
     * 从hashMap中删除一个值
     *//*
    public void deleteHashMap(String key, String field) {
        valOpsHash.delete(key, field);
    }

    */
    /**
     * 将所有指定的值插入到存于 key 的列表的头部。如果 key 不存在，那么在进行 push 操作前会创建一个空列表
     *//*
    public <T> Long leftPush(String key, T value) throws JsonProcessingException {
        return valOpsList.leftPush(key, mapper.writeValueAsString(value));
    }

    /**
     * 只有当 key 已经存在并且存着一个 list 的时候，在这个 key 下面的 list 的头部插入 value。 与 LPUSH 相反，当
     * key 不存在的时候不会进行任何操作
     *//*
    public <T> Long leftPushIfPresent(String key, T value) throws JsonProcessingException {
        return valOpsList.leftPushIfPresent(key, mapper.writeValueAsString(value));
    }

    /**
     * 返回存储在 key 里的list的长度。 如果 key 不存在，那么就被看作是空list，并且返回长度为 0
     /*
    public Long size(String key) {
        return valOpsList.size(key);
    }

    /**
     * 返回存储在 key 的列表里指定范围内的元素。 start 和 end
     * 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推
     /*
    public List<Object> listRange(String key, long start, long end) {
        return valOpsList.range(key, start, end);
    }*/

    @Autowired
    RedisTemplate redisTemplate;

    static RedisTemplate redis;

    @PostConstruct
    public void init() {
        redis = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redis.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("获取key的失效时间异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return redis.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断是否缓存了数据
     *
     * @param key 数据KEY
     * @return 判断是否缓存了
     */
    public static boolean exists(String key) {

        try {
            return (boolean) redis.execute((RedisCallback<Boolean>) connection -> {
                return connection.exists(key.getBytes());
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return redis.hasKey(key);
        } catch (Exception e) {
            log.error("判断key是否存在异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public static void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redis.delete(key[0]);
            } else {
                redis.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        if (key == null) {
            return null;
        }
        return redis.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        try {
            redis.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置String类型异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0)
                redis.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            else
                set(key, value);

            return true;
        } catch (Exception e) {
            log.error("设置String类型及有效期异常" + e.getMessage(), e);
            return false;
        }
    }


    /**
     * 递增 1
     *
     * @param key 键
     * @return
     */
    public static long incr(String key) {
        return redis.opsForValue().increment(key);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redis.opsForValue().increment(key, delta);
    }

    /**
     * 递减 1
     *
     * @param key 键
     * @return
     */
    public static long decr(String key) {
        return redis.opsForValue().decrement(key);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redis.opsForValue().decrement(key, delta);
    }

    /**
     * 取得序列值的下一个
     *
     * @param key
     * @return
     */
    public static Long getSeqNext(String key) {
        return (Long) redis.execute((RedisCallback<Long>) connection -> {
            return connection.incr(key.getBytes());
        });
    }

    /**
     * 取得序列值的下一个，增加 value
     *
     * @param key
     * @param value
     * @return
     */
    public static Long getSeqNext(String key, long value) {
        return (Long) redis.execute((RedisCallback<Long>) connection -> {
            return connection.incrBy(key.getBytes(), value);
        });
    }

    /**
     * 将序列值回退一个
     *
     * @param key
     * @return
     */
    public static void getSeqBack(String key) {
        redis.execute((RedisCallback<Long>) connection -> connection.decr(key.getBytes()));
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object getMapValue(String key, String item) {
        return redis.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> getMap(String key) {
        return redis.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean setMap(String key, Map<String, Object> map) {
        try {
            redis.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("批量设置Map类型异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean setMap(String key, Map<String, Object> map, long time) {
        try {
            redis.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("批量设置Map类型及有效期异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean setMapValue(String key, String item, Object value) {
        try {
            redis.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("设置单个哈希表异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean setMapValue(String key, String item, Object value, long time) {
        try {
            redis.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("设置单个哈希表及有效期异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void deleteMapValue(String key, Object... item) {
        redis.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return redis.opsForHash().hasKey(key, item);
    }

    /**
     * 269
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hIncr(String key, String item, double by) {
        return redis.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hDecr(String key, String item, double by) {
        return redis.opsForHash().increment(key, item, -by);
    }


    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> getSet(String key) {
        try {
            return redis.opsForSet().members(key);
        } catch (Exception e) {
            log.error("获取set异常" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean isMember(String key, Object value) {
        try {
            return redis.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("获取set中是否存在某个值异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long addSet(String key, Object... values) {
        try {
            return redis.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("设置set异常" + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long addSet(String key, long time, Object... values) {
        try {
            Long count = redis.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("设置set及有效期异常" + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long getSetSize(String key) {
        try {
            return redis.opsForSet().size(key);
        } catch (Exception e) {
            log.error("获取set的长度异常" + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long removeSet(String key, Object... values) {
        try {
            Long count = redis.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("移除set中某个值异常" + e.getMessage(), e);
            return 0;
        }
    }


    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public static List<Object> getList(String key, long start, long end) {
        try {
            return redis.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("获取list异常" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long getListSize(String key) {
        try {
            return redis.opsForList().size(key);
        } catch (Exception e) {
            log.error("获取list长度异常" + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object getListIndex(String key, long index) {
        try {
            return redis.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("通过索引获取list中的值异常" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean addList(String key, Object value) {
        try {
            redis.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置list异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean addList(String key, Object value, long time) {
        try {
            redis.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("设置list及有效期异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean addList(String key, List<Object> value) {
        try {
            redis.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("批量设置list异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean addList(String key, List<Object> value, long time) {
        try {
            redis.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("批量设置list及有效期异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean updateListIndex(String key, long index, Object value) {
        try {
            redis.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("根据索引修改list中的某条数据异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long removeList(String key, long count, Object value) {
        try {
            Long remove = redis.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("移除list中N个值为value异常" + e.getMessage(), e);
            return 0;
        }
    }

}
