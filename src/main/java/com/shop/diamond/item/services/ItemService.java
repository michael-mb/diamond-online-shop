package com.shop.diamond.item.services;

import com.shop.diamond.item.entities.Item;
import com.shop.diamond.item.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems(){
        return itemRepository.findAll();
    }

    public void save(Item item){
        if(item == null) throw new NullPointerException("Item should not be null");
        itemRepository.save(item);
    }

    public Optional<Item> getItem(Long id){
        if(id == null) throw new NullPointerException("id should not be null");
        return itemRepository.findById(id);
    }

    public void deleteItem(Long id){
        if(id == null) throw new NullPointerException("id should not be null");
        itemRepository.deleteById(id);
    }
}
