package com.discovery_server.microservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
//this creates our eureka server
public class DiscoveryServerApplication{

    public static void main(String[] args){
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }

}