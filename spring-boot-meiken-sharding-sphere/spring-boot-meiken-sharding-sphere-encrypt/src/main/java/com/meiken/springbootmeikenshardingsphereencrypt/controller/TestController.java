package com.meiken.springbootmeikenshardingsphereencrypt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meiken.springbootmeikenshardingsphereencrypt.entity.EncryptEntity;
import com.meiken.springbootmeikenshardingsphereencrypt.repository.EncryptEntityRepository;
import com.meiken.springbootmeikenshardingsphereencrypt.service.EncryptService;
import com.meiken.springbootmeikenshardingsphereencrypt.util.SensitiveEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author glf
 * @Date 2020/9/21
 */
@RestController
@RequestMapping
public class TestController {


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EncryptService encryptService;

    @Autowired
    private EncryptEntityRepository encryptEntityRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public EncryptEntity saveEntity(@RequestBody EncryptEntity encryptEntity) {
        encryptEntity = encryptEntityRepository.saveAndFlush(encryptEntity);
        return encryptEntity;
    }


    @RequestMapping(value = "/getByNull", method = RequestMethod.GET)
    @ResponseBody
    public String findByPwdIsNull() {
        List<EncryptEntity> resultList = encryptEntityRepository.findAllByMobileIsNull();
        return JSON.toJSONString(resultList);
    }


    @RequestMapping(value = "/getByNullValue", method = RequestMethod.GET)
    @ResponseBody
    public String findByNullValue() {
        List<EncryptEntity> resultList = encryptEntityRepository.findAllByMobile("");
        return JSON.toJSONString(resultList);
    }

    @RequestMapping(value = "/encrypt", method = RequestMethod.POST)
    public void encrypt(@RequestBody JSONArray jsonArray) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject encryptJsonObject = jsonArray.getJSONObject(i);
            encryptService.encrypt(encryptJsonObject);
        }
    }

    @RequestMapping(value = "/decrypt", method = RequestMethod.POST)
    public void decrypt(@RequestBody JSONArray jsonArray) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject encryptJsonObject = jsonArray.getJSONObject(i);
            encryptService.encrypt(encryptJsonObject);
        }
    }


    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        System.out.println(SensitiveEncryptUtil.encrypt(""));
    }
}
