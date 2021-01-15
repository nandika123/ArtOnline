package com.ArtOnline.ArtOnline.controller;


import java.security.Principal;

import com.ArtOnline.ArtOnline.model.User;
import com.ArtOnline.ArtOnline.repository.UserDao;

import com.ArtOnline.ArtOnline.service.UserService;
import com.ArtOnline.ArtOnline.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

   

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserDao userdao;

    @RequestMapping("")
    public String home(Model m, Principal p) {
        //m.addAttribute("user_email" , p.getName());
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.save(user);
        return "redirect:/login";
    }


    @GetMapping("/welcome")
    public String welcome(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        System.out.println(user.getRole());
        if ("user".equals(user.getRole()))
            return "redirect:/self/profile";
        else if("gallery".equals(user.getRole()))
            return "redirect:/self/galleries";
        else
            return "redirect:/admin";
    }
}
