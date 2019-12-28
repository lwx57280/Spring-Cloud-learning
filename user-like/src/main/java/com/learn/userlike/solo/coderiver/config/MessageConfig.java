package com.learn.userlike.solo.coderiver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@Configuration
public class MessageConfig {



    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setCacheSeconds(0);
        messageBundle.setBasenames("classpath:messages/user","classpath:messages/clean");
//        messageBundle.setBasenames("/messages/user","/messages/clean");
        messageBundle.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageBundle;
    }
}
