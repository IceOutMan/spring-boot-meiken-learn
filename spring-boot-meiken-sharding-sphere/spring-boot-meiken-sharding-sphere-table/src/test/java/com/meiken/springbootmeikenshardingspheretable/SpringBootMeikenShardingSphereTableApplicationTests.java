package com.meiken.springbootmeikenshardingspheretable;

import com.meiken.springbootmeikenshardingspheretable.dao.OrderDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class SpringBootMeikenShardingSphereTableApplicationTests {

    @Autowired
    private OrderDao orderDao;

    @Test
    void orderDaoTest() {
        for(int i=0;i<100;i++){
            orderDao.insertOrder(BigDecimal.valueOf(i),Long.valueOf(i),i+"");
        }
    }

}
