//20)
package com.inventory_service._rd.microservice.repository;

import com.inventory_service._rd.microservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}

//Sure Ayush! Let’s break it down line by line like I’m explaining to a 10-year-old 👦🧠
//
//---
//
//```java
//public interface InventoryRepository extends JpaRepository<Inventory, Long> {
//```
//
//➡️ This line is like saying:
//**“Hey, I'm making a new helper called `InventoryRepository`, and it knows how to talk to the database for me.”**
//
//* `public interface` just means: “This is a rulebook or helper that others can use.”
//* `InventoryRepository` is the name of this helper.
//* `extends JpaRepository<Inventory, Long>` means:
//
//  > “This helper can already do lots of useful things like saving, updating, deleting, and finding stuff from the database for things called `Inventory` (like items in a store), and it uses a number (called `Long`) as the ID for each item.”
//
//---
//
//```java
//List<Inventory> findBySkuCodeIn(List<String> skuCode);
//```
//
//➡️ This line is like saying:
//**“Give me a list of inventory items whose `skuCode` matches any of the codes in this list.”**
//
//* `List<Inventory>` means: “I will give you back a bunch of inventory items.”
//* `findBySkuCodeIn(...)` is a magic method name that Spring understands. It means:
//
//  > “Find all items where the **SKU code** is **in** this list.”
//* `List<String> skuCode` means: “Here’s a bunch of product codes I want to search for.”
//
//---
//
//### A simple example:
//
//Let’s say your store has items like:
//
//* Apple 🍎 with code `"A1"`
//* Banana 🍌 with code `"B2"`
//* Carrot 🥕 with code `"C3"`
//
//If you say:
//
//```java
//findBySkuCodeIn(List.of("A1", "C3"))
//```
//
//Spring will go find the Apple and Carrot for you — because their codes match.
//
//---
//
//### In short:
//
//📦 This interface helps you *automatically* do smart searches in your database for store items (`Inventory`) using product codes — no need to write SQL manually! Magic, right?
