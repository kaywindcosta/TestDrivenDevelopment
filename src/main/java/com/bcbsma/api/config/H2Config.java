package com.bcbsma.api.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.bcbsma.api.h2.H2Application;
@Configuration
@EnableAutoConfiguration
public class H2Config extends H2Application{

}
