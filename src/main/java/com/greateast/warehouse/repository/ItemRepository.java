
package com.greateast.warehouse.repository;

import com.greateast.warehouse.model.request.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Item entity.
 * Provides CRUD operations via Spring Data JPA.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findById(int id);
   void deleteById(int id);

   boolean existsById(int id);
}
