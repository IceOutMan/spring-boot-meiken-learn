package com.meiken.springbootmeikenredisredistemplate.entity;

import java.util.Date;

/**
 * @Author glf
 * @Date 2020/11/5
 */
public class Person {

    private String name;
    private Integer age;
    private Address address;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
