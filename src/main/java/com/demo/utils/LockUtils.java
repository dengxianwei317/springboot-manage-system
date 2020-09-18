package com.demo.utils;

import com.demo.constant.ExpireTimeConstant;
import lombok.extern.slf4j.Slf4j;

/*
 * 加锁工具类
 * */
@Slf4j
public class LockUtils {

    /**
     * 获取redis中key的锁，乐观锁实现
     * lockName
     * expireTime 锁的失效时间
     */
    public static Boolean getLock(String lockName, int expireTime) {
        Boolean result = false;
        try {
            boolean isExist = RedisUtils.exists(lockName);
            if (!isExist) {
                RedisUtils.getSeqNext(lockName, 0);
                RedisUtils.expire(lockName, expireTime <= 0 ? ExpireTimeConstant.ONE_MINUTE : expireTime);
            }
            long reVal = RedisUtils.getSeqNext(lockName, 1);
            if (1l == reVal) {
                //获取锁
                result = true;
                log.info("获取redis锁:" + lockName + ",成功");
            } else {
                log.info("获取redis锁:" + lockName + ",失败" + reVal);
            }
        } catch (Exception e) {
            log.error("获取redis锁失败:" + lockName, e);
        }
        return result;
    }

    /**
     * 释放锁(直接删除会导致任务重复执行，所以释放锁机制设为超时10s)
     */
    public static Boolean releaseLock(String lockName) {
        Boolean result = false;
        try {
            RedisUtils.expire(lockName, ExpireTimeConstant.TEN_SECOND);
            log.info("释放redis锁:" + lockName + ",成功");
        } catch (Exception e) {
            log.error("释放redis锁失败:" + lockName, e);
        }
        return result;
    }
}
