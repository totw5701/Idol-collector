package com.idolcollector.idolcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class IdolCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdolCollectorApplication.class, args);
    }

}
