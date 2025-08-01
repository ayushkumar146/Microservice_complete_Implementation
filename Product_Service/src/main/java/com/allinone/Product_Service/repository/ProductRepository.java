// 4) Created this repository

package com.allinone.Product_Service.repository;

import com.allinone.Product_Service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}

//Great follow-up!
//
//The `<Product, String>` in:
//
//```java
//public interface ProductRepository extends MongoRepository<Product, String>
//```
//
//is a **generic type declaration**, and here’s what each part means:
//
//---
//
//### ✅ `Product`:
//
//* This is the **type of the document (model)** you're working with — in this case, the `Product` class.
//* It tells Spring Data MongoDB:
//
//  > "You're working with documents of type `Product`."
//
//---
//
//### ✅ `String`:
//
//* This is the **type of the ID field** in the `Product` class.
//* Since the `Product` class has:
//
//  ```java
//  private String id;
//  ```
//
//  the ID type is `String`.
//
//---
//
//### 💡 In simple terms:
//
//You're telling Spring:
//
//> “Create a repository for `Product` documents, where the ID is a `String`.”
//
//---
//
//If your model had:
//
//```java
//private Integer id;
//```
//
//Then you'd write:
//
//```java
//MongoRepository<Product, Integer>
//```
//
// is to enable automatic CRUD operations (like save, find, delete, update) for the Product collection in MongoDB without writing any implementation code.