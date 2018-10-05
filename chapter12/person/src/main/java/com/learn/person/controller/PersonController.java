package com.learn.person.controller;

import com.learn.person.dao.PersonRepository;
import com.learn.person.domain.Person;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController{

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public List<Person> savePerson(@RequestBody String personName){
        Person p = new Person(personName);
        List<Person> people = personRepository.findAll(new PageRequest(0, 10)).getContent();
        return people;
    }
}
