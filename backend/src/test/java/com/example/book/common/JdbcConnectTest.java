package com.example.book.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@SpringBootTest
public class JdbcConnectTest {
    
    @Autowired
    DataSource dataSource;
    
    @Test
    void jdbcConnectTest() {
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("metaData.getUserName() = " + metaData.getUserName());
            System.out.println("metaData.getURL() = " + metaData.getURL());
        } catch (SQLException e) {
            System.out.println("e = " + e);
        }
    }
}
