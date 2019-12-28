package com.learn.userlike.solo.coderiver.controller;

import com.learn.userlike.solo.coderiver.dto.UserInfoDTO;
import com.learn.userlike.solo.coderiver.exception.ResultEnum;
import com.learn.userlike.solo.coderiver.exception.UserException;
import com.learn.userlike.solo.coderiver.service.RedisService;
import com.learn.userlike.solo.coderiver.service.UserService;
import com.learn.userlike.solo.coderiver.util.KeyUtils;
import com.learn.userlike.solo.coderiver.util.ResultVOUtils;
import com.learn.userlike.solo.coderiver.vo.RegisterVO;
import com.learn.userlike.solo.coderiver.vo.ResultVO;
import com.learn.userlike.solo.coderiver.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController
@Api(description = "用户服务")
@RequestMapping("/user")
public class UserLikeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/register")
    @ApiOperation(value = "通过邮箱注册用户")
    public ResultVO registerByEmail(@RequestBody @Valid RegisterVO registerVO, BindingResult bindingResult) {
        //校验不通过
        if (bindingResult.hasErrors()) {
            log.error("【用户注册】参数错误，form=${}", registerVO.toString());
            throw new UserException(ResultEnum.PARAMS_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        //赋值唯一的id
        userInfoDTO.setId(KeyUtils.genUniqueKey());
        BeanUtils.copyProperties(registerVO,userInfoDTO);
        userService.registerByEmail(userInfoDTO);
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "通过邮箱登录")
    @PostMapping("/login")
    public ResultVO loginByEmail(@RequestParam("email") String email,
                                 @RequestParam("password") String password) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new UserException(ResultEnum.EMAIL_PASSWORD_EMPTY);
        }
        UserInfoDTO dto = userService.loginByEmail(email, password);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(dto, userInfoVO);

        return ResultVOUtils.success(userInfoVO);
    }

    @RequestMapping("/like")
    @ApiOperation("点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "likedUserId", value = "被点赞的用户id",dataType = "string"),
            @ApiImplicitParam(name = "likedPostId", value = "点赞的用户id",dataType = "string")
    })
    public ResultVO like(@RequestParam("likedUserId") String likedUserId,
                         @RequestParam("likedPostId") String likedPostId) {
        redisService.saveLiked2Redis(likedUserId, likedPostId);
        redisService.incrementLikedCount(likedUserId);
        return ResultVOUtils.success();
    }


    @PostMapping("/unlike")
    @ApiOperation("取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "likedUserId", value = "被点赞的用户id",dataType = "string"),
            @ApiImplicitParam(name = "likedPostId", value = "点赞的用户id",dataType = "string")
    })
    public ResultVO unlike(@RequestParam("likedUserId") String likedUserId,
                           @RequestParam("likedPostId") String likedPostId) {
        redisService.unlikeFromRedis(likedUserId, likedPostId);
        redisService.decrementLikedCount(likedUserId);
        return ResultVOUtils.success();
    }

}
