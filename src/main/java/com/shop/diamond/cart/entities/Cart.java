package com.shop.diamond.cart.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @ElementCollection
    // itemId , quantity
    private Map<Long,Long> itemMap = new HashMap<>();

    public Cart() { }

    public Cart(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<Long, Long> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Long, Long> itemMap) {
        this.itemMap = itemMap;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemMap=" + itemMap +
                '}';
    }
}