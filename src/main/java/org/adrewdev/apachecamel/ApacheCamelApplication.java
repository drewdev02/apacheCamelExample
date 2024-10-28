package org.adrewdev.apachecamel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ApacheCamelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApacheCamelApplication.class, args);
    }
}
