package com.shop.diamond.item.repositories;

import com.shop.diamond.item.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    @Override
    List<Item> findAll();
}
