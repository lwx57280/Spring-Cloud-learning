package com.learn.userlike.solo.coderiver.service;

import com.learn.userlike.solo.coderiver.dataobject.UserLike;
import com.learn.userlike.solo.coderiver.dto.LikedCountDTO;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    /**
     * 点赞。状态为1
     * @param likedUserId
     * @param likedPostId
     */
    void saveLiked2Redis(String likedUserId, String likedPostId);

    /**
     * 取消点赞。将状态改变为0
     * @param likedUserId
     * @param likedPostId
     */
    void unlikeFromRedis(String likedUserId, String likedPostId);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId);

    /**
     * 该用户的点赞数加1
     * @param likedUserId
     */
    void incrementLikedCount(String likedUserId);

    /**
     * 该用户的点赞数减1
     * @param likedUserId
     */
    void decrementLikedCount(String likedUserId);
    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<LikedCountDTO> getLikedCountFromRedis();

    /**
     * 分布式锁
     * @param lock
     * @return
     */
    void distributedLock(String lock,int realStock);


    int getDistributedLock(String lock);

    Boolean abSent(String lockKey, String tag);


    void deleteLock(String lockKey);
    /**
     * 超时策略
     * @author      li cong zhi
     * @param       key 键值
     * @return
     * @exception
     * @date        2020/1/30 12:51
     */
    void lockTimeOut(String lockKey, long timeOut, TimeUnit unit);

    void expire(String lockKey,String param, long timeOut, TimeUnit unit);


    String getLock(String key);
}
