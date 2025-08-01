// 13) first page after product service

package com.OrderService.order.model;

// 🪄 These are Lombok tools that generate common methods like getters and setters automatically
import lombok.AllArgsConstructor;  // ✅ Makes a constructor that takes all fields
import lombok.Getter;              // ✅ Makes "get" methods (e.g., getId())
import lombok.NoArgsConstructor;   // ✅ Makes a constructor with no inputs
import lombok.Setter;              // ✅ Makes "set" methods (e.g., setId())

// 🏛️ These tools are for working with databases using JPA (Java Persistence API)
import jakarta.persistence.*;

// 📋 This lets us use List, like a group of things (items in an order)
import java.util.List;

// 🏠 This class represents a table in the database
@Entity
// 📄 The table in the database will be called "t_orders"
@Table(name = "t_orders")
// 🛠️ Use Lombok to auto-generate getters, setters, and constructors
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    // 🪪 This is the primary key (unique ID) for each order
    @Id
    // 🎲 The ID will be generated automatically (like 1, 2, 3...)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🧾 A human-readable order number, like "ORD1234"
    private String orderNumber;

    // 👪 One order has many items. When we save/delete the order,
    //     we also save/delete its items automatically (cascade = ALL).
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList; // 📦📦📦 A list of items in this order
}