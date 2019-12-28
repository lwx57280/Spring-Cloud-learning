package com.learn.userlike.solo.coderiver.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class LocaleUtils {

    private LocaleUtils() {
    }
    // https://www.jianshu.com/p/46eda1f96abe

    /**
     * 这里使用比较方便的方法，不依赖request.
     * @return
     */
    public static Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
}
