package com.learn.userlike.solo.coderiver.exception;

import com.learn.userlike.solo.coderiver.croe.PropertyConfig;
import lombok.Getter;

@Getter
public enum ResultEnum {

    PARAMS_ERROR(101),
    EMAIL_ALREADY_REGISTER(102),
    REGISTER_FAIL(103),
    EMAIL_PASSWORD_EMPTY(201),
    EMAIL_NOT_EXIST(211),
    PASSWORD_ERROR(22),
    RESET_PASSWORD_FAIL(223),
    SAVE_EDUCATION_EXPERIENCE_FAIL(301),
    FIND_EDUCATION_EXPERIENCE_FAIL(302),
    USER_ID_CANNOT_EMPTY(303),
    MAIN_ID_NOT_NULL(311),
    SAVE_WORK_EXPERIENCE_FAIL(401),
    PROJECT_ID_EMPTY(502),
    CANNOT_FIND_USER_INFO(601),
    CANNOT_FIND_PROJECT_INFO(611),
    LIKE_FAIL(701),
    UNLIKE_FAIL(702),
    ;

    private Integer code;

    ResultEnum(Integer code) {
        this.code = code;
    }

    /**
     * 这里会从Spring中容器去取到配置文件
     *
     * @return
     */
    public String getMessage() {
        String message = PropertyConfig.getProperty(String.valueOf(this.code));
        return message == null ? "" : message;
    }
}
