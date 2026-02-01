package com.greateast.warehouse.config;

import com.greateast.warehouse.model.entity.Item;
import com.greateast.warehouse.model.entity.Variant;
import com.greateast.warehouse.repository.ItemRepository;
import com.greateast.warehouse.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialLoader implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final VariantRepository variantRepository;

    @Override
    public void run(String... args) {
        Item item = new Item();
        item.setName("T-Shirt");
        item.setDescription("Cotton T-Shirt");
        itemRepository.save(item);

        Variant v1 = new Variant();
        v1.setItemId(item.getId());
        v1.setSku("TS-RED-M");
        v1.setColor("Red");
        v1.setSize("M");
        v1.setCapitalPrice(new BigDecimal("79.99"));
        v1.setSalesPrice(new BigDecimal("99.99"));
        v1.setStock(10);

        Variant v2 = new Variant();
        v2.setItemId(item.getId());
        v2.setSku("TS-BLUE-L");
        v2.setColor("Blue");
        v2.setSize("L");
        v1.setCapitalPrice(new BigDecimal("95.20"));
        v2.setCapitalPrice(new BigDecimal("109.99"));
        v2.setStock(5);

        variantRepository.saveAll(List.of(v1, v2));
    }
}