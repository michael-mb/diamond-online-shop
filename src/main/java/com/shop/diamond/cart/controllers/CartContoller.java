package com.shop.diamond.cart.controllers;

import com.shop.diamond.cart.entities.Cart;
import com.shop.diamond.cart.services.CartService;
import com.shop.diamond.item.entities.Item;
import com.shop.diamond.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class CartContoller {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String showCart(Model model, @AuthenticationPrincipal User user , @RequestParam("itemId")Optional<Long> itemId){
        if(user == null)
            return "redirect:/login";

        Cart cart;
        if(itemId.isEmpty()){
            if(cartService.hasUserCart(user.getId())){
                cart = cartService.getUserCart(user.getId()).get();
                Map<Item,Long> itemMap = cartService.getItemsFromCart(cart.getId());
                model.addAttribute("itemMap", itemMap);
                model.addAttribute("total", cartService.calculateTotalPrice(itemMap));
                model.addAttribute("cartId", cart.getId());
            }else{
                cartService.createCart(user.getId());
                cart = cartService.getUserCart(user.getId()).get();Map<Item,Long> itemMap = cartService.getItemsFromCart(cart.getId());
                model.addAttribute("itemMap", itemMap);
                model.addAttribute("total", cartService.calculateTotalPrice(itemMap));
                model.addAttribute("cartId", cart.getId());
            }
        }
        else{
            if(cartService.hasUserCart(user.getId())){
                cartService.addItemToCart(user.getId(), itemId.get(), 1L);
                cart = cartService.getUserCart(user.getId()).get();
                Map<Item,Long> itemMap = cartService.getItemsFromCart(cart.getId());
                model.addAttribute("itemMap", itemMap);
                model.addAttribute("total", cartService.calculateTotalPrice(itemMap));
                model.addAttribute("cartId", cart.getId());
            }else{
                cartService.createCart(user.getId());
                cartService.addItemToCart(user.getId(), itemId.get(), 1L);
                cart = cartService.getUserCart(user.getId()).get();
                Map<Item,Long> itemMap = cartService.getItemsFromCart(cart.getId());
                model.addAttribute("itemMap", itemMap);
                model.addAttribute("total", cartService.calculateTotalPrice(itemMap));
                model.addAttribute("cartId", cart.getId());
            }
        }

        model.addAttribute("user", user);
        return "cart";
    }

    @GetMapping("/payment")
    public String showPayment(Model model, @AuthenticationPrincipal User user, @RequestParam("cartId")Optional<Long> cartId ){
        model.addAttribute("user", user);
        cartId.ifPresent(aLong -> cartService.removeItemFromStock(aLong));

        return "payment";
    }

    @GetMapping("/removeFromCart")
    public String removeFromCart(@AuthenticationPrincipal User user,@RequestParam("itemId")Optional<Long> itemId){
        if(itemId.isEmpty())
            return "redirect:/cart";

        cartService.removeItemToCart(user.getId(), itemId.get());
        return "redirect:/cart";
    }

}
