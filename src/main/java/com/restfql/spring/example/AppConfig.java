package com.restfql.spring.example;

import com.restfql.spring.RestFQLConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestFQLConfiguration.class)
public class AppConfig {}
