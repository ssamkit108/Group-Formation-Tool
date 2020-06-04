package com.dal.catmeclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "com.dal.catmeclone")
public class Group12CatMeCloneApplication {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(Group12CatMeCloneApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(Group12CatMeCloneApplication.class, args);
		
	}
	
	

	
	
}
