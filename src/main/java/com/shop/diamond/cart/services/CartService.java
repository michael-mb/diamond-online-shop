package com.shop.diamond.cart.services;

import com.shop.diamond.cart.entities.Cart;
import com.shop.diamond.cart.repositories.CartRepository;
import com.shop.diamond.item.entities.Item;
import com.shop.diamond.item.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemService itemService;

    public void createCart(Long userId){
        if(userId == null) throw new NullPointerException("UserId should not be null");
        cartRepository.save(new Cart(userId));
    }

    public boolean hasUserCart(Long userId){
        if(userId == null) throw new NullPointerException("UserId should not be null");
        for(Cart c : cartRepository.findAll()){
            if(c.getUserId().equals(userId))
                return true;
        }
        return false;
    }

    public Optional<Cart> getUserCart(Long userId){
        if(userId == null) throw new NullPointerException("UserId should not be null");
        return cartRepository.findCartByUserId(userId);
    }

    public void addItemToCart(Long userId, Long itemId , Long quantity){
        if(userId == null) throw new NullPointerException("UserId should not be null");
        if(itemId == null) throw new NullPointerException("itemId should not be null");

        Optional<Cart> cartOptional = getUserCart(userId);
        if(cartOptional.isEmpty())
            throw new NullPointerException("Cart is empty");
        Cart cart = cartOptional.get();
        if(cart.getItemMap().containsKey(itemId)){
            cart.getItemMap().put(itemId, (cart.getItemMap().get(itemId) + quantity)) ;
        }else{
            cart.getItemMap().put(itemId, quantity) ;
        }

        cartRepository.save(cart);
    }

    public void removeItemToCart(Long userId, Long itemId){
        if(userId == null) throw new NullPointerException("UserId should not be null");
        if(itemId == null) throw new NullPointerException("itemId should not be null");

        Optional<Cart> cartOptional = getUserCart(userId);
        if(cartOptional.isEmpty())
            throw new NullPointerException("Cart is empty");
        Cart cart = cartOptional.get();

        cart.getItemMap().remove(itemId);
        cartRepository.save(cart);
    }

    public void removeItemFromStock(Long cartId){
        if(cartId == null) throw new NullPointerException("cartId should not be null");

        Map<Item, Long> itemMap = getItemsFromCart(cartId);
        for(Item i : itemMap.keySet()){
            i.setStock(i.getStock() - itemMap.get(i));
            itemService.save(i);
        }
        resetCart(cartId);
    }

    public void resetCart(Long cartId){
        if(cartId == null) throw new NullPointerException("cartId should not be null");
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        Cart cart = cartOptional.get();
        cart.setItemMap(new HashMap<>());
        cartRepository.save(cart);
    }
    public Map<Item, Long> getItemsFromCart(Long cartId){
        if(cartId == null) throw new NullPointerException("cartId should not be null");
        Map<Item, Long> itemsMap = new HashMap<>();

        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if(cartOptional.isEmpty())
            throw new NullPointerException("CartOptional should not be null");

        Cart cart = cartOptional.get();

        for(Long id : cart.getItemMap().keySet()){
            Item item = itemService.getItem(id).get();
            itemsMap.put(item, cart.getItemMap().get(id));
        }

        return itemsMap;
    }

    public Long calculateTotalPrice(Map<Item, Long> itemMap){
        long price = 0L;
        for(Item i : itemMap.keySet()){
            price += i.getPrice() * itemMap.get(i);
        }
        return price;
    }
}
