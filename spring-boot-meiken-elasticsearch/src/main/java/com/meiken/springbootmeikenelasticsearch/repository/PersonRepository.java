package com.meiken.springbootmeikenelasticsearch.repository;

import com.meiken.springbootmeikenelasticsearch.entity.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @Author glf
 * @Date 2020/9/16
 */
public interface PersonRepository extends ElasticsearchRepository<Person, Integer> {

    List<Person> findAll();

}
