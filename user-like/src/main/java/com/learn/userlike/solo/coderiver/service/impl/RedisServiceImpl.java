package com.learn.userlike.solo.coderiver.service.impl;

import com.google.common.collect.Lists;
import com.learn.userlike.solo.coderiver.constant.LikedStatusEnum;
import com.learn.userlike.solo.coderiver.dataobject.UserLike;
import com.learn.userlike.solo.coderiver.dto.LikedCountDTO;
import com.learn.userlike.solo.coderiver.service.RedisService;
import com.learn.userlike.solo.coderiver.util.RedisKeyUtils;
import com.learn.userlike.solo.coderiver.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UN_LIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    @Override
    public void incrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, 1);
    }

    @Override
    public void decrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, -1);
    }

    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = Lists.newArrayList();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            // 分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();

            // 组装成 UserLike 对象
            UserLike userLike = new UserLike(likedUserId, likedPostId, value);
            list.add(userLike);
            // 存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }
        return list;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = Lists.newArrayList();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            // 将点赞数量存储在 LikedCountDT
            String key = (String) map.getKey();
            LikedCountDTO likedCountDTO = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(likedCountDTO);
            // 从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return list;
    }

    @Override
    public void distributedLock(String lock,int realStock) {
        redisTemplate.opsForValue().set("stock",realStock);
    }

    @Override
    public void deleteLock(String lockKey) {
        redisUtils.deleteString(lockKey);
    }

    @Override
    public Boolean abSent(String lockKey, String tag) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey,tag);
    }

    @Override
    public void expire(String lockKey, String param,long timeOut, TimeUnit unit) {
        redisUtils.setString(lockKey,param,timeOut,unit);
    }

    @Override
    public void lockTimeOut(String lockKey, long timeOut, TimeUnit unit) {
        redisTemplate.expire(lockKey, timeOut, unit);
    }

    @Override
    public String getLock(String key) {
        return redisUtils.getString(key);
    }

    @Override
    public int getDistributedLock(String lock) {
        return Integer.parseInt(redisTemplate.opsForValue().get(lock).toString());
    }
}
