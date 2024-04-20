package com.ludogoriehack24.ludogoriehack24.user;

import com.ludogoriehack24.ludogoriehack24.config.AppConfig;
import com.ludogoriehack24.ludogoriehack24.constants.Role;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/home-page")
    public String showHomePage(Model model){
        List<UserDTO> allUsers = userService.getAllUsers();
        List<UserDTO> recommendedUsers = userService.getRecommendedUsers();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("recommendedUsers", recommendedUsers);
        return "home_page.html";
    }
    @GetMapping("/login")
    public String login(){
        return "/user/login";
    }
    @GetMapping("/registration")
    public String register(Model model){
        model.addAttribute("userDTO",new UserDTO());
        return "/user/registration";
    }
    @PostMapping("submit-registration")
    public String saveUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "/user/registration";
        }
        if (!comparePasswords(userDTO.getPassword(),userDTO.getRepeatPassword())){
            model.addAttribute("passwordsDoNotMatch","Passwords do not match!");
            return "/user/registration";
        }
        User user = modelMapper.map(userDTO, User.class);
        List<Object> userPresentObj = isUserPresent(user);
        if((Boolean) userPresentObj.get(0)){

            return "/user/registration";
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return "redirect:/users/login";
    }
    public List<Object> isUserPresent(User user) {
        boolean userExists = false;
        String message = null;
        User u  = userRepository.getUserByUsername(user.getUsername());
        if(u != null){
            userExists = true;
            message = "Username Already Present!";
        }
        return Arrays.asList(userExists, message);
    }
    public boolean comparePasswords(String password,String repeatPassword){
        return password.equals(repeatPassword);
    }

    @GetMapping("/all")
    public String showAllUsers(Model model){
        List<UserDTO> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all_users.html";
    }
}
