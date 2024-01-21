package com.meiken.springbootmeikendatasourcejpa.repository;

import com.meiken.springbootmeikendatasourcejpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person,Long> {

    /**
     * 查询根据参数位置
     * @param name
     * @return
     */
    @Query(value = "select * from person  where name = ?1",nativeQuery = true)
    Person findPersonByName(String Name);

    /**
     * 查询根据Param注解
     * @param name
     * @return
     */
    @Query(value = "select p from person p where p.uname = :name")
    Person findPersonByNameTwo(@Param("name") String name);
}
