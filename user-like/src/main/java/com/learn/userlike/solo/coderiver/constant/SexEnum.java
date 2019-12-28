package com.learn.userlike.solo.coderiver.constant;

import lombok.Getter;

@Getter
public enum SexEnum {

    UN_KNOW(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女"),
    ;

    private Integer code;

    private String name;

    SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
