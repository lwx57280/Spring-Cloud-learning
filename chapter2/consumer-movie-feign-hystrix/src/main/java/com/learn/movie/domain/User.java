package com.learn.movie.domain;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String username;

    private String name;

    private Integer age;

    private Double balance;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                '}';
    }
}
