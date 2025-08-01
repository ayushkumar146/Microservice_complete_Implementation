//23) First page after inventory service.
package com.OrderService.order.config;

// This line says: "I'm using a special tool called LoadBalanced from Spring Cloud
// It helps me find which service to call when I use multiple servers"
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;

// This line means: "I want to use Spring's tool to define beans (things Spring manages for me)"
import org.springframework.context.annotation.Bean;

// This line lets me mark this class as a configuration file —
// like a settings or setup file for how things work in Spring.
import org.springframework.context.annotation.Configuration;

// This is Spring's WebClient — it's like a smart robot that can send and receive messages (HTTP requests).
import org.springframework.web.reactive.function.client.WebClient;

// This says: "Hey Spring! This is a config class, please look at it during app startup"
@Configuration
public class WebClientConfig {

    // This says: "I’m making a bean — something Spring can give me wherever I need it"
    @Bean

    // This makes sure the WebClient uses service names instead of hardcoded URLs.
    // It works with tools like Eureka or Consul to find services.
//    @LoadBalanced

    // This method creates a WebClient builder — a helper that makes WebClient objects.
    // We return the builder so other parts of the app can use it to make API calls.
    public WebClient.Builder webClientBuilder() {
        // This returns a fresh WebClient builder with default settings.
        // You can later customize this builder to add headers, timeouts, etc.
        return WebClient.builder();
    }
}
