package com.learn.userlike.solo.coderiver.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private static final long serialVersionUID = 7650719229311661868L;

    private Integer code;


    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Integer code) {
        super(code.toString());
    }
    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Integer code, String massage) {
        super(code + massage);
    }

    public UserException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    // https://www.jianshu.com/p/d222de8c4475

}
