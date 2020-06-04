package com.dal.catmeclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "com.dal.catmeclone")

public class Group12CatMeCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(Group12CatMeCloneApplication.class, args);
	}

}
