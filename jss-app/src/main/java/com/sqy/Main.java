package com.sqy;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }


    @Override
    public void run(String... args) throws Exception {
    }
}
