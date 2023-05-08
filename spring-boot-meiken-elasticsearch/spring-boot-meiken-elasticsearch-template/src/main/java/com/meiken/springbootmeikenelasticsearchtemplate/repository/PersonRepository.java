package com.meiken.springbootmeikenelasticsearchtemplate.repository;

import com.meiken.springbootmeikenelasticsearch.entity.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author glf
 * @Date 2020/9/16
 */
public interface PersonRepository extends ElasticsearchRepository<Person, Integer> {

    List<Person> findAll();

}
