package com.learn.userlike.solo.coderiver.service.impl;

import com.learn.userlike.solo.coderiver.constant.RedisConsts;
import com.learn.userlike.solo.coderiver.croe.PropertyConfig;
import com.learn.userlike.solo.coderiver.dataobject.UserInfo;
import com.learn.userlike.solo.coderiver.dto.ExperienceDTO;
import com.learn.userlike.solo.coderiver.dto.RoleDTO;
import com.learn.userlike.solo.coderiver.dto.UserInfoDTO;
import com.learn.userlike.solo.coderiver.exception.ResultEnum;
import com.learn.userlike.solo.coderiver.exception.UserException;
import com.learn.userlike.solo.coderiver.repository.UserInfoRepository;
import com.learn.userlike.solo.coderiver.service.UserService;
import com.learn.userlike.solo.coderiver.util.ObjectUtils;
import com.learn.userlike.solo.coderiver.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public UserInfoDTO registerByEmail(UserInfoDTO userInfo) {
        //校验邮箱是否被注册过
        UserInfo info = userInfoRepository.findUserInfoByEmail(userInfo.getEmail());
        if (ObjectUtils.isNotNull(info)) {
            //  已经注册过了,抛出已注册异常
            throw new UserException(ResultEnum.EMAIL_ALREADY_REGISTER.getCode());
        }
        //  没有被注册过，则存入数据库
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(userInfo, user);
        UserInfo saveUserInfo = userInfoRepository.save(user);
        if (ObjectUtils.isNull(saveUserInfo)) {
            throw new UserException(ResultEnum.REGISTER_FAIL.getCode()
                    ,ResultEnum.REGISTER_FAIL.getMessage());
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(saveUserInfo, userInfoDTO);

        return userInfoDTO;
    }

    @Override
    public UserInfo updateInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfoDTO loginByEmail(String email, String password) {
        return null;
    }

    @Override
    public UserInfo loginByTel(String tel, String password) {
        return null;
    }

    @Override
    public void logout(String userId) {
        // 从 Redis 中删除当前用户对应的key
        String key = String.format(RedisConsts.TOKEN_TEMPLATE,userId);
        redisUtils.deleteString(key);
    }

    @Override
    public UserInfo findById(String id) {
        return null;
    }

    @Override
    public UserInfo resetPassword(String id, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public List<UserInfo> findAll(Integer page, Integer size, Integer sort) {
        return null;
    }

    @Override
    public List<UserInfo> findAllByRole(Integer page, Integer size, Integer sort, Integer role) {
        return null;
    }

    @Override
    public List<UserInfo> findAllByExperience(Integer page, Integer size, Integer sort, Integer experience) {
        return null;
    }

    @Override
    public List<UserInfo> findAllByRoleAndExperience(Integer page, Integer size, Integer sort, Integer role, Integer experience) {
        return null;
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        return null;
    }

    @Override
    public List<ExperienceDTO> findAllExperience() {
        return null;
    }
}
