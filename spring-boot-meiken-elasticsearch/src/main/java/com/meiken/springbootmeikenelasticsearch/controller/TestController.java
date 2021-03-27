package com.meiken.springbootmeikenelasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.meiken.springbootmeikenelasticsearch.entity.Person;
import com.meiken.springbootmeikenelasticsearch.repository.PersonRepository;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.elasticsearch.action.support.WriteRequest.RefreshPolicy.IMMEDIATE;


/**
 * @Author glf
 * @Date 2020/9/16
 */
@RestController
@RequestMapping("/elasticsearch")
public class TestController {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    RestHighLevelClient elasticsearchClient;


    @Autowired
    private PersonRepository personRepository;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() throws IOException {

        HashMap<String, Object> personMap = new HashMap<>();
        personMap.put("id", "10");
        personMap.put("name", "王五");

        IndexRequest request = new IndexRequest("spring-boot-elastic")
                .source(personMap)
                .setRefreshPolicy(IMMEDIATE);

        IndexResponse response = elasticsearchClient.index(request, RequestOptions.DEFAULT);

        return "df";
    }


    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public String save() {

        Person newPerson = new Person();
        newPerson.setId(3);
        newPerson.setName("李四");

        Person person = elasticsearchOperations.save(newPerson);

        return JSON.toJSONString(person);

    }


    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public String query() {

        Person newPerson = new Person();
        newPerson.setId(2);
        newPerson.setName("张三");

        Person person = elasticsearchOperations.get("2", Person.class);

        return JSON.toJSONString(person);
    }

    @PostMapping(value = "/findByName")
    @ResponseBody
    public String findByName(@RequestBody Person person) {

        List<Person> personList = personRepository.findAll();

        return JSON.toJSONString(personList);
    }

    @PostMapping(value = "/savePerson")
    @ResponseBody
    public String savePerson(@RequestBody Person person) {

        person = personRepository.save(person);

        return JSON.toJSONString(person);
    }

}
