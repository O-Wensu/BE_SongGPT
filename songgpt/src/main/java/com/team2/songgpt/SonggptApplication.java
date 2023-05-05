package com.team2.songgpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SonggptApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonggptApplication.class, args);
    }

}
