package com.learn.userlike.solo.coderiver.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private static final long serialVersionUID = 7650719229311661868L;

    private Integer code;

    private String massage;

    public UserException() {
        super();
    }

    public UserException(Integer code) {
        super();
        this.code = code;
    }
    public UserException(Integer code, String massage) {
        super(massage);
        this.code = code;
        this.massage =massage;
    }

    public UserException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.massage=resultEnum.getMessage();
    }

    public UserException(ResultEnum resultEnum,Throwable cause) {
        super(cause);
        this.code = resultEnum.getCode();
    }
    public UserException(Integer code,Throwable cause) {
        super(cause);
        this.code = code;
    }

    public UserException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.massage = message;
    }
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
