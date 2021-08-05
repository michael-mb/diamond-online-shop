package com.shop.diamond.authentication.controllers;

import com.shop.diamond.authentication.forms.LoginDto;
import com.shop.diamond.authentication.forms.RegisterDto;
import com.shop.diamond.security.StaticUtils;
import com.shop.diamond.user.entities.User;
import com.shop.diamond.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String handleLoginPage(Model model, @AuthenticationPrincipal User user ){

        if(user != null)
            return "redirect:/";

        model.addAttribute("error", "");
        model.addAttribute("loginDto" , new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String handleLoginPost(Model model,@ModelAttribute("loginDto") LoginDto loginDto , HttpServletRequest httpServletRequest){
        try {
            httpServletRequest.login(loginDto.email.toLowerCase().trim(), loginDto.password);
        } catch (ServletException e){
            logger.error("Exception on logout after attempted login with bad credentials.");
            try {
                // invalidate session to clean up context in tomcat
                StaticUtils.logoutAndInvalidateSession();
            } catch (ServletException e2) {
                logger.error("Exception on logout after attempted login with bad credentials.", e2);
            }
            model.addAttribute("error", "Password or Email ist not correct");
            return "login";
        }
        /* Here login was successful */
        final Optional<User> user = userService.findUserByEmail(loginDto.email);
        if (!user.isPresent()) {
            throw new RuntimeException("Login succeeded but user could not be found in repository.");
        }
        userService.rehashPassword(loginDto.password, user.get());

        return "redirect:/";
    }

    @GetMapping("/register")
    public String handleRegisterPage(Model model, @AuthenticationPrincipal User user){
        if(user != null)
            return "redirect:/";

        model.addAttribute("registerDto" , new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegisterPost(@ModelAttribute("registerDto") RegisterDto registerDto,
                                     HttpServletRequest httpServletRequest, Model model){

        if(userService.doesEmailAlreadyExists(registerDto.email)){
            model.addAttribute("error", "This email is already in our database");
            return "register";
        }

        userService.registerUser(registerDto);
        return autologin(httpServletRequest , registerDto);
    }

    private String autologin(HttpServletRequest httpServletRequest , RegisterDto registerDto ){
        try {
            httpServletRequest.login(registerDto.email, registerDto.password);
        } catch (ServletException e){
            try {
                // invalidate session to clean up context in tomcat
                StaticUtils.logoutAndInvalidateSession();
            } catch (ServletException e2) {
                logger.error("Exception on logout after attempted login with bad credentials.", e2);
            }
            return "redirect:/login";
        }
        /* Here login was successful */
        final Optional<User> user = userService.findUserByEmail(registerDto.email);
        if (!user.isPresent()) {
            throw new RuntimeException("Login succeeded but user could not be found in repository.");
        }
        userService.rehashPassword(registerDto.password, user.get());

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String handleLogout(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                StaticUtils.logoutAndInvalidateSession();
            } catch (ServletException e) {
                logger.error("Error at log out of '" + authentication.getName() + "'.", e);
            }
        }
        return "redirect:/";
    }

}
