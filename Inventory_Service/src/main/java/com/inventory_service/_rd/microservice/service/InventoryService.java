//22)
// This class belongs to the 'service' layer of the application — it's responsible for business logic.
package com.inventory_service._rd.microservice.service;

// Imports the DTO used to return a response from this service
import com.inventory_service._rd.microservice.dto.InventoryResponse;
// Imports the repository interface that talks to the database
import com.inventory_service._rd.microservice.repository.InventoryRepository;

// Lombok annotations to auto-generate boilerplate code like constructor and logger
import lombok.RequiredArgsConstructor; // Generates constructor for final fields
import lombok.SneakyThrows;            // Lets us skip try-catch for checked exceptions
import lombok.extern.slf4j.Slf4j;      // Enables use of 'log' for logging

// Spring annotations to mark this class as a service and handle transactions
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Allows us to work with lists of data
import java.util.List;

// Marks this class as a Spring-managed service (it's part of the business logic layer)
@Service
// Tells Lombok to generate a constructor for 'final' fields (like 'inventoryRepository')
@RequiredArgsConstructor
// Adds a logger so we can write log messages like 'log.info(...)'
@Slf4j
public class InventoryService {

    // This repository is used to fetch data from the database.
    // Spring injects it automatically because of @RequiredArgsConstructor.
    private final InventoryRepository inventoryRepository;

    // This method is only reading data from the database — it won’t modify anything.
    // Spring uses @Transactional to wrap this in a safe DB read operation.
    @Transactional(readOnly = true)
    // Tells Java to allow exceptions without forcing us to catch them here.
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        // Writes a message to the logs (useful for debugging or monitoring)
        log.info("Checking Inventory");

        // Here's where the database call happens!
        // We're using a method called 'findBySkuCodeIn', which we declared in InventoryRepository.
        // ⚠️ IMPORTANT:
        // We did NOT implement 'findBySkuCodeIn' ourselves — Spring Data JPA generates the method for us
        // based on its name. It knows:
        //   - 'findBy' = SELECT query
        //   - 'SkuCodeIn' = WHERE sku_code IN (list)
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()

                // Now we map each Inventory object from the database to our custom response (InventoryResponse)
                .map(inventory ->
                        InventoryResponse.builder()
                                // Set the SKU code in the response
                                .skuCode(inventory.getSkuCode())
                                // Set stock status based on quantity (in stock if quantity > 0)
                                .isInStock(inventory.getQuantity() > 0)
                                // Finish building the response object
                                .build()
                )

                // Collect all response objects into a list and return
                .toList();
    }
}
