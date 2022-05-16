package com.example.springbootmeikendatasourcemybatis.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserTest {
    private Integer id;

    private String name;

    private Integer age;

    private Long pay;

    private BigDecimal odds;

    private BigDecimal payResult;

    private Integer flag;

    private Date time;

    public UserTest(Integer id, String name, Integer age, Long pay, BigDecimal odds, BigDecimal payResult, Integer flag, Date time) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.pay = pay;
        this.odds = odds;
        this.payResult = payResult;
        this.flag = flag;
        this.time = time;
    }

    public UserTest() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getPay() {
        return pay;
    }

    public void setPay(Long pay) {
        this.pay = pay;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public BigDecimal getPayResult() {
        return payResult;
    }

    public void setPayResult(BigDecimal payResult) {
        this.payResult = payResult;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}