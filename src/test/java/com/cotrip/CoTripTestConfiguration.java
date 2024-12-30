package com.cotrip;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class CoTripTestConfiguration {

  @Bean
  @ServiceConnection
  PostgreSQLContainer<?> postgreSQLContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.15-alpine3.21"))
        .withDatabaseName("cotrip")
        .withUsername("username")
        .withPassword("password");
  }

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
