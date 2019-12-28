package com.learn.userlike.solo.coderiver.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikedCountDTO implements Serializable {


    private static final long serialVersionUID = 7625447357994834143L;


    private String id;

    private Integer count;

    public LikedCountDTO() {
    }

    public LikedCountDTO(String id, Integer count) {
        this.id = id;
        this.count = count;
    }
}
