//package com.ludogoriehack24.ludogoriehack24.user;
//
//import com.ludogoriehack24.ludogoriehack24.config.AppConfig;
//import com.ludogoriehack24.ludogoriehack24.constants.Role;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.Banner;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//@Controller
//@AllArgsConstructor
//@RequestMapping("/users")
//public class UserController {
//    private UserService userService;
//    private UserRepository userRepository;
//    private ModelMapper modelMapper;
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @GetMapping("/home-page")
//    public String showHomePage(Model model){
//        List<UserDTO> allUsers = userService.getAllUsers();
//        model.addAttribute("allUsers", allUsers);
//        return "home_page.html";
//    }
//    @GetMapping("/login")
//    public String login(){
//        return "/user/login";
//    }
//    @GetMapping("/view-profile")
//    public String viewProfile(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.getUserByUsername(authentication.getName());
//        model.addAttribute("currentUser",user);
//        return "/user/view_profile";
//    }
//    @GetMapping("/registration")
//    public String register(Model model){
//        model.addAttribute("userDTO",new UserDTO());
//        return "/user/registration";
//    }
//    @PostMapping("/submit-registration")
//    public String saveUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model){
//        System.out.println(1234);
//        if (bindingResult.hasErrors()){
//            model.addAttribute("userDTO",new UserDTO());
//            return "/user/registration";
//        }
//        if (!comparePasswords(userDTO.getPassword(),userDTO.getRepeatPassword())){
//            model.addAttribute("passwordsDoNotMatch","Passwords do not match!");
//            return "/user/registration";
//        }
//        User user = modelMapper.map(userDTO, User.class);
//        List<Object> userPresentObj = isUserPresent(user);
//        if((Boolean) userPresentObj.get(0)){
//
//            return "/user/registration";
//        }
//        user.setPassword(passwordEncoder().encode(user.getPassword()));
//        user.setEnabled(true);
//        user.setRole(Role.ROLE_USER);
////        if (!profileImage.isEmpty()) {
////            try {
////                String uploadDir = "src/main/resources/static/img"; // Path to static/img directory
////                String fileName = UUID.randomUUID().toString() + "-" + profileImage.getOriginalFilename();
////                Path filePath = Paths.get(uploadDir, fileName);
////                Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
////                userDTO.setProfileImageName("/img/" + fileName); // Path relative to the project root
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
//        userRepository.save(user);
//        return "redirect:/users/login";
//    }
//@GetMapping("/registration")
//public String register(Model model){
//    model.addAttribute("userDTO", new UserDTO());
//    return "/user/registration";
//}
//
//    @PostMapping("/submit-registration")
//    public String saveUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model){
//        System.out.println(1234);
//        if (bindingResult.hasErrors()){
//            // Добавяне на userDTO към модела за да запазим въведените данни
//            model.addAttribute("userDTO", userDTO);
//            return "/user/registration";
//        }
//        if (!comparePasswords(userDTO.getPassword(), userDTO.getRepeatPassword())){
//            model.addAttribute("passwordsDoNotMatch", "Passwords do not match!");
//            return "/user/registration";
//        }
//        User user = modelMapper.map(userDTO, User.class);
//        List<Object> userPresentObj = isUserPresent(user);
//        if ((Boolean) userPresentObj.get(0)){
//            // Ако потребителят вече съществува, може да направите нещо
//            return "/user/registration";
//        }
//        user.setPassword(passwordEncoder().encode(user.getPassword()));
//        user.setEnabled(true);
//        user.setRole(Role.ROLE_USER);
//        userRepository.save(user);
//        return "redirect:/users/login";
//    }
//    public List<Object> isUserPresent(User user) {
//        boolean userExists = false;
//        String message = null;
//        User u  = userRepository.getUserByUsername(user.getUsername());
//        if(u != null){
//            userExists = true;
//            message = "Username Already Present!";
//        }
//        return Arrays.asList(userExists, message);
//    }
//    public boolean comparePasswords(String password,String repeatPassword){
//        return password.equals(repeatPassword);
//    }
//
//}
package com.ludogoriehack24.ludogoriehack24.user;

import com.ludogoriehack24.ludogoriehack24.abilities.Ability;
import com.ludogoriehack24.ludogoriehack24.abilities.AbilityDTO;
import com.ludogoriehack24.ludogoriehack24.abilities.AbilityRepository;
import com.ludogoriehack24.ludogoriehack24.config.AppConfig;
import com.ludogoriehack24.ludogoriehack24.constants.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private AbilityRepository abilityRepository;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @GetMapping("/home-page")
//    public String showHomePage(Model model) {
//        List<UserDTO> allUsers = userService.getAllUsers();
//        List<UserDTO> recommendedUsers = userService.getRecommendedUsers();
//        model.addAttribute("allUsers", allUsers);
//        model.addAttribute("recommendedUsers", recommendedUsers);
//        return "home_page.html";
//    }
    @GetMapping("/edit-profile/{userId}")
    public String editProfile(@PathVariable("userId") Long userId, Model model, HttpServletRequest request){
        return userService.editProfile(userId,model,request);
    }
    @PostMapping("/add-changes")
    public String addChanges(UserDTO userDTO, BindingResult bindingResult, Model model,@RequestParam("selectedAbilitiesIds") List<Long> selectedAbilitiesIds,@RequestParam("searchedAbilitiesIds") List<Long> searchedAbilitiesIds){
        return userService.addChanges(userDTO,bindingResult,model,selectedAbilitiesIds,searchedAbilitiesIds);
    }
   @GetMapping("/view-profile/{userId}")
    public String viewProfile(@PathVariable("userId") Long userId, Model model,HttpServletRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        User currentUser = userService.getLoggedUser();
//        if (currentUser.getId().equals(user.getId())){
//            return "redirect:/users/edit-profile/"+userId;
//        }
       model.addAttribute("currentUser",currentUser);
        model.addAttribute("user",user);
        return "/user/view_profile";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("abilities",abilityRepository.findAll());
        model.addAttribute("selectedAbilities",new ArrayList<AbilityDTO>());
        model.addAttribute("selectedAbilities2",new ArrayList<AbilityDTO>());
        return "/user/registration";
    }

    @PostMapping("submit-registration")
    public String saveUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model, @RequestParam("selectedAbilitiesIds") List<Long> selectedAbilitiesIds,@RequestParam("searchedAbilitiesIds") List<Long> searchedAbilitiesIds) {
        List<Ability> abilities = abilityRepository.findAllByIdIn(selectedAbilitiesIds);
        List<Ability> searched = abilityRepository.findAllByIdIn(searchedAbilitiesIds);
        if (bindingResult.hasErrors()) {
            return "/user/registration";
        }
        if (!comparePasswords(userDTO.getPassword(), userDTO.getRepeatPassword())) {
            model.addAttribute("passwordsDoNotMatch", "Passwords do not match!");
            return "/user/registration";
        }
        User user = modelMapper.map(userDTO, User.class);
        List<Object> userPresentObj = isUserPresent(user);
        if ((Boolean) userPresentObj.get(0)) {

            return "/user/registration";
        }
        List<Ability> abilityList = new ArrayList<>();
        for (int i = 0; i < abilities.size(); i++) {
            abilityList.add(modelMapper.map(abilities.get(i),Ability.class));
        }
        List<Ability> searchedAbilityList = new ArrayList<>();
        for (int i = 0; i < searched.size(); i++) {
           searchedAbilityList.add(modelMapper.map(searched.get(i),Ability.class));
        }
        user.setUserAbilities(abilityList);
        user.setNeededAbilities(searchedAbilityList);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return "redirect:/users/login";
    }

    public List<Object> isUserPresent(User user) {
        boolean userExists = false;
        String message = null;
        User u = userRepository.getUserByUsername(user.getUsername());
        if (u != null) {
            userExists = true;
            message = "Username Already Present!";
        }
        return Arrays.asList(userExists, message);
    }

    public boolean comparePasswords(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }
}
