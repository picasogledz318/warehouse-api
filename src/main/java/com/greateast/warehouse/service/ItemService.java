
package com.greateast.warehouse.service;

import com.greateast.warehouse.model.request.Item;
import com.greateast.warehouse.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service layer for Item business logic.
 */
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * Save or update item.
     */
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    /**
     * Retrieve all items.
     */
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(int id) {
        return itemRepository.findById(id);
    }

    public Boolean existsById(int id) {
        return itemRepository.existsById(id);
    }
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

}
