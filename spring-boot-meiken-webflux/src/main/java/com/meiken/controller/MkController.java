package com.meiken.controller;

import com.meiken.entity.Person;
import com.meiken.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mk")
public class MkController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Person> hello() {
        List<Person> personList = personRepository.findAll();
        return Flux.fromIterable(personList).delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Person> findPerson() {
        Person person = personRepository.findById(1L).get();
        Mono<Person> personMono = Mono.just(person);
        return personMono;
    }

}