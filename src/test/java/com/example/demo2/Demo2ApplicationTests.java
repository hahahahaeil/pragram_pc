package com.example.demo2;

import com.example.demo2.entity.Myorder;
import com.example.demo2.mapper.MyorderMapper;
import com.example.demo2.service.IMyorderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootTest
class Demo2ApplicationTests {



    @Autowired
    MyorderMapper myorderMapper;
    @Test
    void contextLoads1() {
        Myorder myorder = new Myorder();
        myorder.setName("中是国");
        myorderMapper.insert(myorder);
    }

    @Autowired
    IMyorderService myorderService;

    @Test
    void contextLoads() {
        Myorder myorder = new Myorder();
        myorder.setName("中国是");
        myorderService.save(myorder);
    }


}
