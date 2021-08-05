package com.shop.diamond.general;

import com.shop.diamond.item.entities.Item;
import com.shop.diamond.item.services.ItemService;
import com.shop.diamond.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ItemService itemService;


    @GetMapping("/")
    public String showIndexPage(Model model , @AuthenticationPrincipal User user){
        List<Item> items = new ArrayList<>();

        for(Item i  : itemService.getItems()){
            if(items.size() < 6){
                items.add(i);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping("/contact")
    public String showContactPage(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        return "contact";
    }
}
