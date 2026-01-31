
package com.greateast.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Warehouse API application.
 * Spring Boot will scan all sub-packages under com.greateast.warehouse.
 */
@SpringBootApplication
public class WarehouseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApiApplication.class, args);
    }
}
