package com.inventory_service._rd.microservice;

import com.inventory_service._rd.microservice.model.Inventory;
import com.inventory_service._rd.microservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory =new Inventory();
			inventory.setSkuCode("ayush");
			inventory.setQuantity(0);

			Inventory inventory1=new Inventory();
			inventory1.setSkuCode("arjun");
			inventory1.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}
