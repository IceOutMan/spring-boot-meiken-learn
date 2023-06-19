package com.example.springbootmeikenshardingspheretables;

import com.example.springbootmeikenshardingspheretables.dao.CourseMapper;
import com.example.springbootmeikenshardingspheretables.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SpringBootMeikenShardingSphereTablesApplicationTests {

    @Autowired
    private CourseMapper courseMapper;
    @Test
    void insertCourseTest() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("sharding sphere");
            course.setUserId(Long.valueOf(1000 + i));
            course.setCstatus("1");

            courseMapper.insert(course);
        }

    }

}
