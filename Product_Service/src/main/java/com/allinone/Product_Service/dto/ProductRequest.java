// 5) created this dto

package com.allinone.Product_Service.dto;

// Lombok annotation to generate getters, setters, toString, equals, and hashCode
import lombok.AllArgsConstructor;   // Generates constructor with all fields
import lombok.Builder;              // Enables the builder pattern
import lombok.Data;                 // Combines @Getter, @Setter, @ToString, etc.
import lombok.NoArgsConstructor;    // Generates a no-argument constructor

// Import BigDecimal for accurate currency representation (better than float/double)
import java.math.BigDecimal;

// Lombok annotation that adds getters, setters, toString, equals, hashCode
@Data

// Lombok annotation that provides a builder pattern for creating objects
@Builder

// Lombok annotation that generates a constructor with all fields
@AllArgsConstructor

// Lombok annotation that generates a default no-args constructor
@NoArgsConstructor

// This is a plain Java class used as a DTO (Data Transfer Object)
public class ProductRequest {

    // Name of the product
    private String name;

    // Description of the product
    private String description;

    // Price of the product (using BigDecimal for precision)
    private BigDecimal price;
}

//📦 What is a DTO (Data Transfer Object)?
//A DTO is a simple Java object used to carry data between different parts of a program, especially:
//
//Between client and server (like from your frontend to backend API)
//
//Between controller and service layers
//
//✅ Why Use a DTO?
//Keeps your API contracts clean (don’t expose internal entity structure)
//
//Allows validation and transformation of input data
//
//Improves security by avoiding exposing internal DB models directly
//
//💡 Real-Life Analogy:
//Your Product entity might have fields like id, createdAt, updatedAt, etc.
//
//But the client (frontend or API consumer) only needs to send:
//
//name
//
//description
//
//price
//
//So you create a DTO like ProductRequest to only accept the needed data.
