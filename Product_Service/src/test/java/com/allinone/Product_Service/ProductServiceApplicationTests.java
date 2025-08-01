// 12)

package com.allinone.Product_Service;

// These lines are like bringing in tools you'll use
import com.allinone.Product_Service.dto.ProductRequest;
import com.allinone.Product_Service.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions; // To check if something is true or correct
import org.junit.jupiter.api.Test; // To say "this is a test"
import org.springframework.beans.factory.annotation.Autowired; // Helps Spring automatically give you objects
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Lets us test web APIs without starting a real server
import org.springframework.boot.test.context.SpringBootTest; // Starts the full Spring Boot app during test
import org.springframework.http.MediaType; // Tells what kind of content we're sending (e.g., JSON)
import org.springframework.test.context.DynamicPropertyRegistry; // Allows changing Spring properties (like DB connection) during test
import org.springframework.test.context.DynamicPropertySource; // Helps us provide those dynamic properties
import org.springframework.test.web.servlet.MockMvc; // A magic tool to send fake HTTP requests to our API
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders; // Helps build those fake HTTP requests
import org.testcontainers.containers.MongoDBContainer; // Lets us spin up a real MongoDB in a Docker container
import org.testcontainers.junit.jupiter.Container; // Marks a Testcontainer as special

import java.math.BigDecimal; // Used for money values

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Used to check response status (like 200, 201, etc.)

// This tells Spring to load the full app for testing
@SpringBootTest
@AutoConfigureMockMvc // This sets up MockMvc for sending fake HTTP requests
class ProductServiceApplicationTests {

	// This starts a real MongoDB server inside a Docker container
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	// Ask Spring to give us these objects automatically
	@Autowired
	private MockMvc mockMvc; // Helps us test APIs

	@Autowired
	private ObjectMapper objectMapper; // Converts Java object to JSON

	@Autowired
	private ProductRepository productRepository; // Allows us to talk to the database

	// This code runs when the test starts. It starts the MongoDB container.
	static {
		mongoDBContainer.start();
	}

	// This tells Spring: "Hey, use this MongoDB instead of the one in the config file"
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	// This is our test method: It checks if creating a product works
	@Test
	void shouldCreateProduct() throws Exception {
		// Step 1: Create a fake product object
		ProductRequest productRequest = getProductRequest();

		// Step 2: Convert the product to JSON string
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		// Step 3: Send a fake POST request to our /api/product endpoint
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON) // We are sending JSON
						.content(productRequestString)) // The actual product data
				.andExpect(status().isCreated()); // We expect HTTP 201 (Created)

		// Step 4: Check that the product was saved in the database
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	// This helper method returns a sample product to test with
	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iPhone 13")
				.description("iPhone 13")
				.price(BigDecimal.valueOf(1200)) // Price in rupees or dollars
				.build();
	}
}

//Great! Let’s break this down **like you’re 10 years old**. You’ve built a Spring Boot application with APIs, and now you're wondering:
//
//* What is **JUnit**?
//* What is **ObjectMapper** doing?
//* What is **MockMvc**?
//* What are we actually testing here?
//* And what is the **TestContainer** doing?
//
//---
//
//## 🧪 What Are We Testing?
//
//You made an API like:
//
//```
//POST /api/product
//```
//
//This saves a product (like an iPhone) into your MongoDB database.
//
//Now you want to check:
//
//> "If someone sends this API call, will my code really save it in the database?"
//
//That’s what your **test class** is doing. It's like a **robot clicking buttons** and checking if things work.
//
//---
//
//## 🔍 What is JUnit?
//
//* **JUnit** is a Java library that helps you **test your code**.
//* You write small methods called `@Test` that check if something works or breaks.
//* JUnit says:
//
//  > "Run this piece of code and tell me if the result is what I expected."
//
//---
//
//## 👷 What is `MockMvc`?
//
//Imagine a fake user that makes API calls without opening a browser.
//
//* `MockMvc` = **fake browser** that can send requests to your API like:
//
//  ```java
//  mockMvc.perform(post("/api/product")...)
//  ```
//
//It lets you **test your API** as if a user is calling it.
//
//---
//
//## 📦 What is `ObjectMapper`?
//
//* Your API takes **JSON**, like:
//
//```json
//{
//  "name": "iPhone",
//  "price": 1200
//}
//```
//
//* `ObjectMapper` is a helper from Jackson that converts:
//
//  ✅ Java ➡️ JSON
//  ✅ JSON ➡️ Java
//
//So:
//
//```java
//objectMapper.writeValueAsString(productRequest)
//```
//
//turns the Java object `ProductRequest` into a JSON string.
//
//---
//
//## 🐳 What is Testcontainers?
//
//Let’s say your app saves data into **MongoDB**.
//
//But in real testing, you don’t want to mess up your **real database**. So, Testcontainers gives you:
//
//> A **temporary MongoDB running in Docker**, just for testing.
//
//* You start it in Java like:
//
//```java
//MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
//```
//
//Then your app saves the test product into this temporary MongoDB.
//Once the test is done, the MongoDB is thrown away. Clean and safe!
//
//---
//
//## 🧠 So What’s Happening in Your Test?
//
//Let's connect the dots from your test method:
//
//```java
//@Test
//void shouldCreateProduct() throws Exception {
//    // Create a fake ProductRequest (like sending a form)
//    ProductRequest productRequest = getProductRequest();
//
//    // Convert it to JSON using ObjectMapper
//    String json = objectMapper.writeValueAsString(productRequest);
//
//    // Make a POST call using MockMvc to your API
//    mockMvc.perform(post("/api/product")
//            .contentType(JSON)
//            .content(json))
//        .andExpect(status().isCreated());
//
//    // Finally, check if the product was saved in MongoDB
//    assertEquals(1, productRepository.findAll().size());
//}
//```
//
//### So this test is checking 3 things:
//
//1. ✅ Can my API `/api/product` be called?
//2. ✅ Does my service save it to the database?
//3. ✅ Is the product really there in the DB after saving?
//
//And this is done using:
//
//| Tool             | Job                                                                 |
//| ---------------- | ------------------------------------------------------------------- |
//| `JUnit`          | Runs the test and checks results                                    |
//| `MockMvc`        | Pretends to call the API like a browser                             |
//| `ObjectMapper`   | Converts Java → JSON for the API request                            |
//| `Testcontainers` | Gives a fake MongoDB just for testing (so your real DB stays clean) |
//
//---
//
//## 🎁 Final Analogy:
//
//Imagine you're building a vending machine (your app).
//
//* You make buttons (APIs)
//* You write software (services)
//* And you connect it to a money box (MongoDB)
//
//Then you use:
//
//* **JUnit** = A robot pressing buttons and checking if it works
//* **MockMvc** = A fake person pressing those buttons
//* **ObjectMapper** = Translates the person's input into machine language
//* **TestContainers** = A fake money box for testing, so you don’t use real money
//
//---
//

