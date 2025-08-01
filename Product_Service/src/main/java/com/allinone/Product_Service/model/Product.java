// 3) created this model

package com.allinone.Product_Service.model;

// Lombok annotations to reduce boilerplate code
import lombok.AllArgsConstructor;   // Automatically generates constructor with all fields
import lombok.Builder;              // Enables builder pattern for object creation
import lombok.Data;                 // Generates getters, setters, equals, hashCode, toString
import lombok.NoArgsConstructor;    // Generates a no-args constructor

// Annotation to map this class to a MongoDB collection
import org.springframework.data.mongodb.core.mapping.Document;



// Importing BigDecimal to accurately handle currency/price values
import java.math.BigDecimal;

@Document(value = "product")

// Lombok annotation that includes @Getter, @Setter, @ToString, @EqualsAndHashCode
@Data

// Lombok annotation to generate a constructor with all fields
@AllArgsConstructor

// Lombok annotation to generate a no-argument constructor
@NoArgsConstructor

// Lombok annotation to use the Builder pattern to create instances
@Builder

// This is a POJO (Plain Old Java Object) representing a Product
public class Product {
    // ID field used by MongoDB as the unique identifier (_id)
    private String id;

    // Name of the product
    private String name;

    // Description of the product
    private String description;

    // SKU (Stock Keeping Unit) Code - uniquely identifies the product type
    private String skuCode;

    // Price of the product, using BigDecimal for precision in currency
    private BigDecimal price;
}


// why do we use @builder

// Great question!
//
//When we say **"create instances"** using the `@Builder` annotation in your `Product` class, we’re talking about creating **objects of the `Product` class** using the **Builder pattern** — which is a flexible, readable way to construct complex objects.
//
//---
//
//### 📦 What Is an “Instance”?
//
//An **instance** is simply an **object created from a class**.
//For example:
//
//```java
//Product product = new Product();
//```
//
//Here, `product` is an **instance** of the `Product` class.
//
//---
//
//### 💡 Why Use `@Builder`?
//
//Instead of writing:
//
//```java
//Product p = new Product("id123", "Mouse", "Wireless Mouse", "MS100", new BigDecimal("1299.99"));
//```
//
//You can do:
//
//```java
//Product p = Product.builder()
//    .id("id123")
//    .name("Mouse")
//    .description("Wireless Mouse")
//    .skuCode("MS100")
//    .price(new BigDecimal("1299.99"))
//    .build();
//```
//
//This way:
//
//* You **don’t need to remember constructor order**.
//* It makes the code **more readable and maintainable**.
//* Optional fields can be skipped easily.
//
//---
//
//### 🔨 Behind the Scenes (What `@Builder` does):
//
//Lombok generates a `ProductBuilder` inner class for you. So when you call:
//
//```java
//Product.builder().name("Fan").price(BigDecimal.valueOf(4000)).build();
//```
//
//You're calling methods on this generated builder class, which then returns a new instance of `Product`.
//
//---
//
//### 🧪 Example in a Spring Boot Controller:
//
//```java
//@PostMapping("/product")
//public ResponseEntity<String> createProduct() {
//    Product newProduct = Product.builder()
//        .name("Monitor")
//        .description("27-inch 4K Monitor")
//        .skuCode("MON123")
//        .price(new BigDecimal("22000.00"))
//        .build();
//
//    productRepository.save(newProduct); // Saving the instance
//    return ResponseEntity.ok("Product saved!");
//}
//```
//
//---
//
