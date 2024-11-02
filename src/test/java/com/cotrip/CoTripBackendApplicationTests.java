package com.cotrip;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CoTripTestConfiguration.class)
@SpringBootTest
class CoTripBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
