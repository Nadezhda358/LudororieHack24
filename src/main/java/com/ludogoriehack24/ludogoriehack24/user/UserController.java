package com.ludogoriehack24.ludogoriehack24.user;

import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @GetMapping("/home-page")
    public String showHomePage(Model model){
        List<UserDTO> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "home_page.html";
    }
    @GetMapping("/all")
    public String showAllUsers(Model model){
        List<UserDTO> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all_users.html";
    }
}
