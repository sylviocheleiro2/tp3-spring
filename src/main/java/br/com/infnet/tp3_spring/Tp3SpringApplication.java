package br.com.infnet.tp3_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Tp3SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp3SpringApplication.class, args);
    }
}