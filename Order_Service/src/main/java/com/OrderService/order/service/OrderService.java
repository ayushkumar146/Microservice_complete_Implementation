// This class is responsible for placing orders in our system.
package com.OrderService.order.service;

import com.OrderService.order.dto.InventoryResponse;
import com.OrderService.order.dto.OrderLineItemsDto;
import com.OrderService.order.dto.OrderRequest;
import com.OrderService.order.model.Order;
import com.OrderService.order.model.OrderLineItems;
import com.OrderService.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// @Service tells Spring this is a service class that contains business logic.
// @RequiredArgsConstructor automatically creates a constructor with all 'final' fields.
// @Transactional ensures everything inside a method runs in a transaction (all-or-nothing).
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    // We need to save orders to the database, so we need OrderRepository.
    private final OrderRepository orderRepository;

    // We use WebClient to call another service (Inventory service) over HTTP.
    private final WebClient.Builder webClientBuilder;

    // This is the main method to place an order.
    public void placeOrder(OrderRequest orderRequest) {
        // Create a new empty order object.
        Order order = new Order();

        // Set a random unique number for the order (like a secret name).
        order.setOrderNumber(UUID.randomUUID().toString());

        // Convert the list of OrderLineItemsDto (from the request) to real OrderLineItems.
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto) // convert each item
                .toList();

        // Put the converted items into the order.
        order.setOrderLineItemsList(orderLineItems);

        // Extract only the SKU codes (product IDs) from each order line item.
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // 🔄 Now we call the Inventory Service to check if these products are in stock.

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build()  // Build the WebClient
                .get() // We are making a GET request
                .uri( // Set the URL and query params
                        "http://localhost:8083/api/inventory", // Base URL + path
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build() // Add all SKU codes as query params
                )
                .retrieve() // Fetch the response
                .bodyToMono(InventoryResponse[].class) // Convert the response into an array of InventoryResponse
                .block(); // Wait (block) until we get the response (not async anymore)
        System.out.println("all products stock " + inventoryResponseArray);

        // ✅ Check if ALL products are in stock
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock); // Check if each one is true
        System.out.println("al prod "+ allProductsInStock);
        if (allProductsInStock) {
            // ✅ All good, save the order to the database
            orderRepository.save(order);
        } else {
            // ❌ Some products are missing, throw an error
            throw new IllegalArgumentException("Product is not there");
        }
    }

    // This helper method converts one OrderLineItemsDto into OrderLineItems.
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice()); // Copy the price
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity()); // Copy the quantity
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode()); // Copy the SKU code
        return orderLineItems;
    }
}
