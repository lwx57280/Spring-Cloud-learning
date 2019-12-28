package com.learn.userlike.solo.coderiver.constant;

import lombok.Getter;

@Getter
public enum RoleEnum {
    UN_KNOW(0, "未知"),
    DESIGNER(1, "设计师"),
    FRONT_END(2, "前端工程师"),
    BACK_END(3, "后端工程师"),
    MOBILE(4, "移动端工程师"),
    GAME(5, "游戏工程师"),
    PRODUCT_MANAGER(6, "产品经理"),
    ;
    private Integer code;

    private String roleName;

    RoleEnum(Integer code, String roleName) {
        this.code = code;
        this.roleName = roleName;
    }
}
