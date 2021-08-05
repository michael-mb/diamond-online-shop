package com.shop.diamond.item.controllers;

import com.shop.diamond.item.entities.Brand;
import com.shop.diamond.item.entities.Item;
import com.shop.diamond.item.services.ItemService;
import com.shop.diamond.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ShopController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/shop")
    public String showShopPage(Model model ,
                               @RequestParam Optional<String> brand,
                               @RequestParam("min") Optional<Long> min ,
                               @RequestParam("max") Optional<Long> max,
                               @AuthenticationPrincipal User user){

        if(max.isPresent() && min.isPresent()){
            List<Item> items = itemService.getItems().stream().filter(
                    item -> item.getPrice() <= max.get() && item.getPrice()>=min.get()).collect(Collectors.toList());

            if(items.toArray().length <= 0)
                model.addAttribute("items", new ArrayList<>());
            else
                model.addAttribute("items", items);
        }else if(brand.isPresent()){
            List<Item> items = itemService.getItems().stream().filter(
                    item -> item.getBrand().toString().equals(brand.get())).collect(Collectors.toList());

            if(items.toArray().length <= 0)
                model.addAttribute("items", new ArrayList<>());
            else
                model.addAttribute("items", items);
        } else{
            List<Item> items = itemService.getItems();
            model.addAttribute("items", items);
        }

        model.addAttribute("user", user);
        model.addAttribute("brands", Brand.values());
        return "shop";
    }

    @GetMapping("/detail")
    public String showDetail(@RequestParam("id") Optional<Long> id , Model model ,@AuthenticationPrincipal User user){
        if(id.isEmpty())
            return "redirect:/shop";

        Optional<Item> item = itemService.getItem(id.get());

        if(item.isEmpty())
            return "redirect:/shop";
        model.addAttribute("user", user);
        model.addAttribute("item", item.get());
        return "productDetails";
    }
}
