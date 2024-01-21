package com.meiken.springbootmeikendatasourcejpa.controller;

import com.meiken.springbootmeikendatasourcejpa.entity.Person;
import com.meiken.springbootmeikendatasourcejpa.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JpaController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/person")
    @ResponseBody
    public Person getPerson(){
        return personRepository.findById(1L).get();
    }
}
