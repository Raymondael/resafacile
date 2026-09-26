package com.isninfo.resafacile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ResafacileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResafacileApplication.class, args);
       // System.out.println("Bonjour depuis Spring Boot");
    }


}
