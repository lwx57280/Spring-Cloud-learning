package com.learn.userlike.solo.coderiver.handle;

import com.learn.userlike.solo.coderiver.exception.UserException;
import com.learn.userlike.solo.coderiver.util.ResultVOUtils;
import com.learn.userlike.solo.coderiver.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("请求有参数才进来"+binder.getFieldDefaultPrefix());
    }

    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public ResultVO handle(HttpServletRequest req, Exception e) {
        // 业务异常
        if (e instanceof UserException) {
            UserException userException = (UserException) e;

            return ResultVOUtils.error(userException.getCode(), userException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            return ResultVOUtils.error(-1, "未知错误");
        }

    }

}
