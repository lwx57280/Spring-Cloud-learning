package com.learn.userlike.solo.coderiver.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExperienceDTO implements Serializable {

    private static final long serialVersionUID = 3471004434079661696L;

    private Integer code;

    private String name;

    public ExperienceDTO() {
    }

    public ExperienceDTO(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
