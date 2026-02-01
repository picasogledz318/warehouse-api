
package com.greateast.warehouse.repository;

import com.greateast.warehouse.model.request.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Item entity.
 * Provides CRUD operations via Spring Data JPA.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findById(long id);
   void deleteById(long id);

   boolean existsById(long id);
}
