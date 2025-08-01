// 2) Created this page to check mongo DB.

package com.allinone.Product_Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoCheck implements CommandLineRunner {

    @Autowired
    private MongoClient mongoClient;

    @Override
    public void run(String... args) {
        try {
            // Get database names as a test
            MongoIterable<String> dbs = mongoClient.listDatabaseNames();
            System.out.println("✅ MongoDB Connected! Available databases:");
            for (String dbName : dbs) {
                System.out.println(" - " + dbName);
            }
        } catch (Exception e) {
            System.out.println("❌ MongoDB Connection Failed:");
            e.printStackTrace();
        }
    }
}
