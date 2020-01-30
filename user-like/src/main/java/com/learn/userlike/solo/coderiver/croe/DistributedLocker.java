package com.learn.userlike.solo.coderiver.croe;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface DistributedLocker {
    /**
     * lock(), 拿不到lock就不罢休，不然线程就一直block
     * @author      li cong zhi
     * @param       lockKey 锁
     * @return
     * @exception
     * @date        2020/1/30 17:17
     */
    RLock lock(String lockKey);
    /**
     * leaseTime为加锁时间，单位为秒
     * @author      li cong zhi
     * @param       lockKey 锁
     * @param       timeout 加锁时间，单位为秒
     * @return
     * @exception
     * @date        2020/1/30 17:18
     */
    RLock lock(String lockKey, long timeout);
    /**
     * timeout为加锁时间，时间单位由unit确定
     * @author      li cong zhi
     * @param       unit 时间单位由unit
     * @param       timeout 加锁时间
     * @return
     * @exception
     * @date        2020/1/30 17:20
     */
    RLock lock(String lockKey, TimeUnit unit, long timeout);
    /**
     * tryLock()，马上返回，拿到lock就返回true，不然返回false
     * 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
     * @author      li cong zhi
     * @param
     * @return
     * @exception
     * @date        2020/1/30 17:21
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

}
