package com.cotrip;

import org.springframework.boot.SpringApplication;

public class TestCotripBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(CotripBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
