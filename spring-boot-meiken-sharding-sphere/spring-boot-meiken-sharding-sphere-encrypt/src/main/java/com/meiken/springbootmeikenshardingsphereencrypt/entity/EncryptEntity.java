package com.meiken.springbootmeikenshardingsphereencrypt.entity;

import javax.persistence.*;

/**
 * @Author glf
 * @Date 2020/9/21
 */
@Table(name = "encrypt_entity")
@Entity
public class EncryptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_name",columnDefinition = "varchar(50) comment '用户名'")
    private String userName;

    @Column(name = "mobile",columnDefinition = "varchar(50) comment '手机号'")
    private String mobile ="";


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
