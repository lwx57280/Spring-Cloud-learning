package com.learn.userlike.solo.coderiver.controller;

import com.learn.userlike.solo.coderiver.constant.RedisConstans;
import com.learn.userlike.solo.coderiver.croe.DistributedLocker;
import com.learn.userlike.solo.coderiver.dto.UserInfoDTO;
import com.learn.userlike.solo.coderiver.exception.ResultEnum;
import com.learn.userlike.solo.coderiver.exception.UserException;
import com.learn.userlike.solo.coderiver.service.ProductService;
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
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@Api(description = "用户服务")
@RequestMapping("/user")
public class UserLikeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DistributedLocker distributedLocker;

//    @Autowired
//    private Redisson redisson;

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
        BeanUtils.copyProperties(registerVO, userInfoDTO);
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
            @ApiImplicitParam(name = "likedUserId", value = "被点赞的用户id", dataType = "string"),
            @ApiImplicitParam(name = "likedPostId", value = "点赞的用户id", dataType = "string")
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
            @ApiImplicitParam(name = "likedUserId", value = "被点赞的用户id", dataType = "string"),
            @ApiImplicitParam(name = "likedPostId", value = "点赞的用户id", dataType = "string")
    })
    public ResultVO unlike(@RequestParam("likedUserId") String likedUserId,
                           @RequestParam("likedPostId") String likedPostId) {
        redisService.unlikeFromRedis(likedUserId, likedPostId);
        redisService.decrementLikedCount(likedUserId);
        return ResultVOUtils.success();
    }


//    @RequestMapping("/deduct_stock")
////    @ApiOperation("分布式锁")
////    public ResultVO deductStock() {
////        String lockKey = "lockKey";
////        String clientId = UUID.randomUUID().toString();
////        RLock redissonLock = redisson.getLock(lockKey);
////        try {
////            // 多个线程过来  只有一个线程会将num值设置为1  其余的线程都不可能为1
////            //Boolean result = redisService.abSent(lockKey, "zhang");
////            // 超时策略（由于运维kill进程导致finally 部分代码未被执行，因此加超时策略）
//////            redisService.lockTimeOut(lockKey,10, TimeUnit.SECONDS);
//////            redisService.expire(lockKey, clientId, 10, TimeUnit.SECONDS);
//////            if (!result) {
//////                return ResultVOUtils.error("Error");
//////            }
////            //如果使用默认的午餐lock方法 默认超时时间设置的是30秒 可以传入自定义的超时时间
////            redissonLock.lock();
////            int stock = redisService.getDistributedLock(RedisConstans.stock);
////            if (stock > 0) {
////                int realStock = stock - 1;
////                redisService.distributedLock(RedisConstans.stock, realStock);
////                log.info("扣减成功，剩余库存" + realStock);
////            } else {
////                log.info("扣减失败，库存不足");
////            }
////        } finally {
////            redissonLock.unlock();
//////            if (clientId.equalsIgnoreCase(redisService.getLock(lockKey))) {
//////                // 释放锁
//////                redisService.deleteLock(lockKey);
//////            }
////        }
////        return ResultVOUtils.success();
////    }

    @RequestMapping("/red_lock")
    @ApiOperation("分布式锁")
    public ResultVO redLock() {

        redisService.distributedLock(RedisConstans.stock, 59);
        return ResultVOUtils.success();
    }

    /**
     * 根据商品ID进行抢购
     * @param productId
     * @return 商品抢购详情
     */
    @GetMapping("/order/{productId}")
    public ResultVO sellGoods(@PathVariable String productId){
        String result = productService.orderGoods(productId);
        return ResultVOUtils.success(result);
    }

    /**
     * 根据商品ID进行查询余量
     * @param productId
     * @return 商品抢购详情
     */
    @GetMapping("/query/{productId}")
    public ResultVO queryGoods(@PathVariable String productId){
        String result = productService.queryGoods(productId);
        return ResultVOUtils.success(result);
    }

    @RequestMapping("/redisson_lock")
    public ResultVO redissonLock() {
        String key = "redisson_key";
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                log.info("=============线程开启============"+Thread.currentThread().getName());
                boolean isGetLock  = distributedLocker.tryLock(key, TimeUnit.SECONDS, 5L, 10L);
                if (isGetLock) {
                    try {
                        Thread.sleep(100); //获得锁之后可以进行相应的处理
                        log.info("======获得锁后进行相应的操作======"+Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"lock").start();
        }
        return ResultVOUtils.success();
    }
}
