package com.learn.userlike.solo.coderiver.croe;

import com.learn.userlike.solo.coderiver.util.LocaleUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class PropertyConfig  implements ApplicationContextAware {

    private static MessageSource messageSource;

    /**
     *
     * 服务器启动，Spring容器初始化时，当加载了当前类为bean组件后，
     * 将会调用下面方法注入ApplicationContext实例
     */
    private static ApplicationContext applicationContextFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContextFactory = applicationContext;
    }
    private static void init() {
        if (messageSource == null) {
            synchronized (PropertyConfig.class) {
                messageSource = (MessageSource) applicationContextFactory.getBean("messageSource");
            }
        }
    }

    public static String getProperty(String id) {
        return getMessage(id, new Object[0]);
    }

    public static String getProperty(String code, Object[] param) {
        init();
        return messageSource.getMessage(code, param, LocaleUtils.getCurrentLocale());
    }

    public static String getMessage(String code, Object[] args) {
        init();
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
    public static String getProperty(String code, Object[] args, String defaultMessage) {
        init();
        return messageSource.getMessage(code, args, defaultMessage, LocaleUtils.getCurrentLocale());
    }
}
