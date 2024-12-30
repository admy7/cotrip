package com.cotrip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@Import(CoTripTestConfiguration.class)
@SpringBootTest
public class CoTripBackendApplicationTests {

  @Autowired private PostgreSQLContainer<?> postgreSQLContainer;

  @Test
  void contextLoads() {
    Assertions.assertTrue(postgreSQLContainer.isCreated());
    Assertions.assertTrue(postgreSQLContainer.isRunning());
  }
}
