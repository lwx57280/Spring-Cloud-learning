package com.learn.ui.domain;

import lombok.Data;

@Data
public class Person {
    private Long id;

    private String personName;

    public Person() {
    }

    public Person(String personName) {
        this.personName = personName;
    }
}
