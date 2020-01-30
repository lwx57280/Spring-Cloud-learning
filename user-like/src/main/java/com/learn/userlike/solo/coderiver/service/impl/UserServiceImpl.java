package com.learn.userlike.solo.coderiver.service.impl;

import com.learn.userlike.solo.coderiver.constant.RedisConstans;
import com.learn.userlike.solo.coderiver.croe.PropertyConfig;
import com.learn.userlike.solo.coderiver.dataobject.UserInfo;
import com.learn.userlike.solo.coderiver.dto.ExperienceDTO;
import com.learn.userlike.solo.coderiver.dto.RoleDTO;
import com.learn.userlike.solo.coderiver.dto.UserInfoDTO;
import com.learn.userlike.solo.coderiver.exception.ResultEnum;
import com.learn.userlike.solo.coderiver.exception.UserException;
import com.learn.userlike.solo.coderiver.repository.UserInfoRepository;
import com.learn.userlike.solo.coderiver.service.UserService;
import com.learn.userlike.solo.coderiver.util.KeyUtils;
import com.learn.userlike.solo.coderiver.util.ObjectUtils;
import com.learn.userlike.solo.coderiver.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userInfoRepository")
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
            String property = PropertyConfig.getProperty(ResultEnum.EMAIL_ALREADY_REGISTER.getCode().toString(), new Object[]{"请"});
            log.error("============property============" + property);
            throw new UserException(ResultEnum.EMAIL_ALREADY_REGISTER);
        }
        //  没有被注册过，则存入数据库
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(userInfo, user);
        UserInfo saveUserInfo = userInfoRepository.save(user);
        if (ObjectUtils.isNull(saveUserInfo)) {
            throw new UserException(ResultEnum.REGISTER_FAIL.getCode()
                    , ResultEnum.REGISTER_FAIL.getMessage());
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(saveUserInfo, userInfoDTO);

        return userInfoDTO;
    }

    @Override
    @Transactional
    public UserInfo updateInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    private UserInfoDTO convertToDTO(UserInfo user) {

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        if (ObjectUtils.isNotNull(user)) {
            BeanUtils.copyProperties(user, userInfoDTO);
            return userInfoDTO;
        }
        return userInfoDTO;
    }

    @Override
    public UserInfoDTO loginByEmail(String email, String password) {
        UserInfo userInfo = userInfoRepository.findUserInfoByEmail(email);
        if (ObjectUtils.isNull(userInfo)) {
            throw new UserException(ResultEnum.EMAIL_NOT_EXIST);
        }
        if (!userInfo.getPassword().equals(password)) {
            throw new UserException(ResultEnum.PASSWORD_ERROR);
        }
        // 生成 token 并保存在 Redis 中
        String token = KeyUtils.genUniqueKey();
        // 将token存储在 Redis 中。键是 TOKEN_用户id, 值是token
        redisUtils.setString(String.format(RedisConstans.TOKEN_TEMPLATE, userInfo.getId()), token, 2L, TimeUnit.HOURS);
        UserInfoDTO userInfoDTO = convertToDTO(userInfo);
        userInfoDTO.setToken(token);
        return userInfoDTO;
    }

    @Override
    public UserInfo loginByTel(String tel, String password) {
        return null;
    }

    @Override
    public void logout(String userId) {
        // 从 Redis 中删除当前用户对应的key
        String key = String.format(RedisConstans.TOKEN_TEMPLATE, userId);
        redisUtils.deleteString(key);
    }

    @Override
    public UserInfo findById(String id) {
        return userInfoRepository.findUserInfoById(id);
    }

    @Override
    @Transactional
    public UserInfo resetPassword(String id, String oldPassword, String newPassword) {
        UserInfo userInfo = userInfoRepository.findUserInfoById(id);
        if (ObjectUtils.isNull(userInfo)) {
            throw new UserException(ResultEnum.CANNOT_FIND_USER_INFO);
        }
        String password = userInfo.getPassword();
        //先校验旧密码
        if (!password.equalsIgnoreCase(oldPassword)) {
            throw new UserException(ResultEnum.PASSWORD_ERROR);
        }
        //密码校验通过保存新密码
        userInfo.setPassword(newPassword);
        UserInfo user = updateInfo(userInfo);
        if (ObjectUtils.isNull(user)) {
            throw new UserException(ResultEnum.RESET_PASSWORD_FAIL);
        }
        return user;
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
