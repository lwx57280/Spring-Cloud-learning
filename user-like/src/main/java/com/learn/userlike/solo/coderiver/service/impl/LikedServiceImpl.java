package com.learn.userlike.solo.coderiver.service.impl;

import com.learn.userlike.solo.coderiver.constant.LikedStatusEnum;
import com.learn.userlike.solo.coderiver.dataobject.UserInfo;
import com.learn.userlike.solo.coderiver.dataobject.UserLike;
import com.learn.userlike.solo.coderiver.dto.LikedCountDTO;
import com.learn.userlike.solo.coderiver.repository.UserLikeRepository;
import com.learn.userlike.solo.coderiver.service.LikedService;
import com.learn.userlike.solo.coderiver.service.RedisService;
import com.learn.userlike.solo.coderiver.service.UserService;
import com.learn.userlike.solo.coderiver.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class LikedServiceImpl implements LikedService {

    @Autowired
    UserLikeRepository userLikeRepository;

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;


    @Override
    @Transactional
    public UserLike save(UserLike like) {
        return userLikeRepository.save(like);
    }

    @Override
    @Transactional
    public List<UserLike> saveAll(List<UserLike> list) {
        return userLikeRepository.saveAll(list);
    }

    @Override
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return userLikeRepository.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        return userLikeRepository.findByLikedPostIdAndStatus(likedPostId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
        return userLikeRepository.findByLikedUserIdAndLikedPostId(likedUserId, likedPostId);
    }

    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<UserLike> likedCountFromRedis = redisService.getLikedDataFromRedis();

        likedCountFromRedis.forEach(like -> {
            UserLike user = getByLikedUserIdAndLikedPostId(like.getLikedUserId()
                    , like.getLikedPostId());
            if (ObjectUtils.isNull(user)) {
                //没有记录，直接存入
                save(like);
            } else {
                //有记录，需要更新
                user.setStatus(like.getStatus());
                save(user);
            }
        });
    }

    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> likedCountFromRedis = redisService.getLikedCountFromRedis();
        likedCountFromRedis.forEach(likedCountDTO -> {
            UserInfo userInfo = userService.findById(likedCountDTO.getId());
            // 点赞数量属于无关紧要的操作，出错无需抛异常
            if (ObjectUtils.isNotNull(userInfo)) {
                int likeNum = userInfo.getLikeNum() + likedCountDTO.getCount();
                userInfo.setLikeNum(likeNum);
                // 更新点赞数量
                userService.updateInfo(userInfo);
            }
        });
    }
}
