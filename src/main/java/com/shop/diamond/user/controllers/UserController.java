package com.shop.diamond.user.controllers;


import com.shop.diamond.user.entities.User;
import com.shop.diamond.user.entities.UserRole;
import com.shop.diamond.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;


    @GetMapping("/user")
    public String handleUserPage(Model model , @AuthenticationPrincipal User user){
        if(user == null)
            return "redirect:/shop";
        model.addAttribute("authUser" , user);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/users")
    public String handleUsersPage(Model model , @AuthenticationPrincipal User user){

        if(user == null)
            return "redirect:/shop";

        if(!user.hasAuthority(UserRole.ADMIN))
            return "redirect:/";

        model.addAttribute("user", user);
        model.addAttribute("authUser" , user);
        model.addAttribute("users", userService.getUsers());
        return "usersPage";
    }


}
