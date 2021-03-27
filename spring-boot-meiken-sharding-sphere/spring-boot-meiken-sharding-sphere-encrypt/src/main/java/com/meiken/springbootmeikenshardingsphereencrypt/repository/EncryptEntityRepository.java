package com.meiken.springbootmeikenshardingsphereencrypt.repository;

import com.meiken.springbootmeikenshardingsphereencrypt.entity.EncryptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author glf
 * @Date 2020/9/21
 */
@Repository
public interface EncryptEntityRepository extends JpaRepository<EncryptEntity, Long> {


    List<EncryptEntity> findAllByMobileIsNull();

    List<EncryptEntity> findAllByMobile(String psw);


}
