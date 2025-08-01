// 8)
package com.allinone.Product_Service.controller;

// Lombok annotation: generates a constructor for all final fields (like productService)
import com.allinone.Product_Service.dto.ProductRequest;
import com.allinone.Product_Service.dto.ProductResponse;
import com.allinone.Product_Service.service.ProductService;
import lombok.RequiredArgsConstructor;

// Used to set custom HTTP status codes
import org.springframework.http.HttpStatus;

// Spring annotations for REST controllers and HTTP mapping
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as a REST controller (Spring will auto-detect and handle HTTP requests)
@RestController

// Sets the base URL path for all endpoints in this controller to /api/product
@RequestMapping("/api/product")

// Lombok annotation: creates a constructor for final fields (here: productService), so no need for @Autowired
@RequiredArgsConstructor
public class ProductController {

    // Final field to hold the service layer dependency
    private final ProductService productService;

    // Handles HTTP POST requests to /api/product
    // Accepts a JSON body, converts it to ProductRequest, and calls the service layer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Returns HTTP 201 if successful
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    // Handles HTTP GET requests to /api/product
    // Returns a list of ProductResponse DTOs to the client
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // Returns HTTP 200
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}

//Sure, Ayush! Let's compare the **same Spring Boot controller** class in **two versions**:
//
//---
//
//## ✅ 1. **With `@RequiredArgsConstructor` (using Lombok)**
//
//```java
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/product")
//@RequiredArgsConstructor  // Lombok auto-generates constructor for final fields
//public class ProductController {
//
//    private final ProductService productService;  // final field, required by constructor
//
//    @PostMapping
//    public void createProduct(@RequestBody ProductRequest request) {
//        productService.createProduct(request);
//    }
//}
//```
//
//### 🔍 What happens here:
//
//* `@RequiredArgsConstructor` creates this behind the scenes:
//
//  ```java
//  public ProductController(ProductService productService) {
//      this.productService = productService;
//  }
//  ```
//* This constructor is used by Spring to inject the `ProductService` bean.
//* Clean, less boilerplate.
//
//---
//
//## ❌ 2. **Without Lombok — Manual Constructor**
//
//```java
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/product")
//public class ProductController {
//
//    private final ProductService productService;
//
//    // Manually write constructor
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @PostMapping
//    public void createProduct(@RequestBody ProductRequest request) {
//        productService.createProduct(request);
//    }
//}
//```
//
//---
//
//## 🆚 Summary of Differences
//
//| Feature                      | With `@RequiredArgsConstructor` | Without Lombok         |
//| ---------------------------- | ------------------------------- | ---------------------- |
//| Constructor written manually | ❌ No                            | ✅ Yes                  |
//| Less boilerplate             | ✅ Yes                           | ❌ No                   |
//| Requires Lombok dependency   | ✅ Yes                           | ❌ No                   |
//| Cleaner and modern approach  | ✅ Yes                           | ✅ (but with more code) |
//
//---
//
//Let me know if you'd like me to show this with a `@Service` class too or compare `@Autowired` vs constructor injection.