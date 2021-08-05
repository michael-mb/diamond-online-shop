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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ModeratorController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/editItem")
    public String showEditItem(@RequestParam("itemId") Optional<Long> itemId , Model model , @AuthenticationPrincipal User user){
        if(itemId.isEmpty())
            return "redirect:/shop";

        Optional<Item> item = itemService.getItem(itemId.get());

        if(item.isEmpty())
            return "redirect:/shop";

        model.addAttribute("user", user);
        model.addAttribute("item", item.get());
        return "editItem";
    }

    @PostMapping("editItem")
    public String handlePostEditItem(@ModelAttribute("item") Item formItem ,@RequestParam("itemId") Optional<Long> itemId){

        if(itemId.isEmpty())
            return "redirect:/shop";

        Optional<Item> item = itemService.getItem(itemId.get());
        if(item.isEmpty())
            return "redirect:/shop";

        item.get().setName(formItem.getName());
        item.get().setDescription(formItem.getDescription());
        item.get().setPrice(formItem.getPrice());
        item.get().setImagePath(formItem.getImagePath());
        item.get().setStock(formItem.getStock());
        item.get().setSale(formItem.isSale());
        itemService.save(item.get());
        return "redirect:/detail?id="+item.get().getId();
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("itemId") Optional<Long> itemId ,@AuthenticationPrincipal User user){
        if(itemId.isEmpty())
            return "redirect:/shop";

        Optional<Item> item = itemService.getItem(itemId.get());

        if(item.isEmpty())
            return "redirect:/shop";

        itemService.deleteItem(itemId.get());
        return "redirect:/shop";

    }

    @GetMapping("/addItem")
    public String addItem(Model model , @AuthenticationPrincipal User user){

        model.addAttribute("brands" , Brand.values());
        model.addAttribute("user", user);
        model.addAttribute("item", new Item());
        return "addItem";
    }

    @PostMapping("/addItem")
    public String handlePostAddItem(Model model, @ModelAttribute("item") Item formItem){

        formItem.setImagePath("images/shop/"+formItem.getImagePath());
        itemService.save(formItem);
        return "redirect:/shop";
    }
}
