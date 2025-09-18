package com.inditex.price.config;

import com.inditex.price.PriceApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = PriceApplication.class)
public class CucumberSpringConfiguration {
    // Empty on purpose. Just hooks Cucumber with Spring Boot context.
}
