package com.learn.userlike.solo.coderiver.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 7955268844114453887L;

    private Integer code;

    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
