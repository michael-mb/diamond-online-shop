package com.shop.diamond.cart.repositories;

import com.shop.diamond.cart.entities.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart,Long> {

    public Optional<Cart> findCartByUserId(Long userId);
}
