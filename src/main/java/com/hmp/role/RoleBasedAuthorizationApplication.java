package com.hmp.role;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoleBasedAuthorizationApplication {
	private static final Logger log = LogManager.getLogger(RoleBasedAuthorizationApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RoleBasedAuthorizationApplication.class, args);
		log.fatal("print date {}", System.currentTimeMillis());
	}
}
