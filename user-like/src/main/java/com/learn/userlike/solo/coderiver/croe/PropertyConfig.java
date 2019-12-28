package com.learn.userlike.solo.coderiver.croe;

import com.learn.userlike.solo.coderiver.util.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class PropertyConfig  {

    @Autowired
    private static MessageSource messageSource;


    /**
     * 系统语言环境，默认为中文zh
     */
    public static final String LANGUAGE = "zh";

    /**
     * 系统国家环境，默认为中国CN
     */
    public static final String COUNTRY = "CN";
    private static Locale getLocale() {
        Locale locale = new Locale(LANGUAGE, COUNTRY);
        return locale;
    }

    public static String getProperty(String id) {
        return getMessage(id, null);
    }

    public static String getProperty(String code, Object[] param) {
        return messageSource.getMessage(code, param, LocaleUtils.getCurrentLocale());
    }

    public static String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleUtils.getCurrentLocale());
    }


    /**
     * 获取配置文件内容
     * @author      li cong zhi
     * @param       code        信息的键，properties中的key
     * @param       args        系统运行时参数，可为空
     * @param       defaultMessage  默认信息，可为空
     * @return
     * @exception   
     * @date        2019/12/24 20:42
     */
    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleUtils.getCurrentLocale());
    }
}
