package com.dal.catmeclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Group12CatMeCloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(Group12CatMeCloneApplication.class, args);
    }
}
