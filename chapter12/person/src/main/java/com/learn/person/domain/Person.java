package com.learn.person.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)  // AUTO应当换成你所使用的主键生成方式
    private Long id;

    private String personName;

    public Person() {
    }

    public Person(String personName) {
        this.personName = personName;
    }
}
