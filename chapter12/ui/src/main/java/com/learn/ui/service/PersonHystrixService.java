package com.learn.ui.service;

import com.google.common.collect.Lists;

import com.learn.ui.domain.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调用　Person Service断路器
 */
@Service
public class PersonHystrixService {

    @Autowired
    PersonService personService;

    // 使用@HystrixCommand的fallbackMethod参数指定，当本方法调用失败时，调用后备方法fallbackSave
    @HystrixCommand(fallbackMethod = "fallbackSave")
    public List<Person> save(String name) {
        return personService.save(name);
    }

    public List<Person> fallbackSave(String name) {
        List<Person> list = Lists.newArrayList();
        Person p = new Person(name + "没有保存成功，Person Service 故障");
        list.add(p);
        return list;
    }
}
