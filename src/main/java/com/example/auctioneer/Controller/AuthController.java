package com.example.auctioneer.Controller;

import com.example.auctioneer.Model.Users;
import com.example.auctioneer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@RequestParam String email, @RequestParam String name, @RequestParam String password, @RequestParam String address, @RequestParam String phoneNr, @RequestParam String postNr){
        Users user = new Users(email, password, name, phoneNr, address, postNr);

        userService.save(user);
        return "login";
    }

    @GetMapping("login")
    public String login(Model model, String error, String logout){
        if (error != null){
            model.addAttribute("error", "Your username or password is invalid.");
        }

        if (logout != null){
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }
}
