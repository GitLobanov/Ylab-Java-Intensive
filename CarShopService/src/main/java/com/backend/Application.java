package com.backend;

import com.backend.util.MigrateLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        MigrateLiquibase migrateLiquibase = new MigrateLiquibase();
        migrateLiquibase.migrate();
        SpringApplication.run(Application.class, args);


    }
}