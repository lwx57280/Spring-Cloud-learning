package com.learn.userlike.solo.coderiver.constant;


import lombok.Getter;

/**
 * 用户点赞的状态
 */
@Getter
public enum LikedStatusEnum {
    /**
     * 点赞
     */
    LIKE(1, "点赞"),
    /**
     * 取消
     */
    UN_LIKE(0, "取消点赞/未点赞")
    ;
    private Integer code;

    private String msg;

    LikedStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
