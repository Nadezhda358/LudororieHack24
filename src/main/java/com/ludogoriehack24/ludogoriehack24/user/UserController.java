
package com.ludogoriehack24.ludogoriehack24.user;
import com.ludogoriehack24.ludogoriehack24.abilities.Ability;
import com.ludogoriehack24.ludogoriehack24.abilities.AbilityDTO;
import com.ludogoriehack24.ludogoriehack24.abilities.AbilityRepository;
import com.ludogoriehack24.ludogoriehack24.abilities.AbilityService;
import com.ludogoriehack24.ludogoriehack24.constants.Role;
import com.ludogoriehack24.ludogoriehack24.userFriend.UserFriend;
import com.ludogoriehack24.ludogoriehack24.userFriend.UserFriendDTO;
import com.ludogoriehack24.ludogoriehack24.userFriend.UserFriendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private AbilityRepository abilityRepository;
    private AbilityService abilityService;
    private final UserFriendService userFriendService;
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @GetMapping("/edit-profile/{userId}")
    public String editProfile(@PathVariable("userId") Long userId, Model model, HttpServletRequest request){
        return userService.editProfile(userId,model,request);
    }
    @PostMapping("/add-changes")
    public String addChanges(UserDTO userDTO, BindingResult bindingResult, Model model,@RequestParam(name = "selectedAbilitiesIds", required = false) List<Long> selectedAbilitiesIds,
                             @RequestParam(name = "searchedAbilitiesIds", required = false) List<Long> searchedAbilitiesIds,@RequestParam("profilePicture") MultipartFile profilePicture){
        return userService.addChanges(userDTO,bindingResult,model,selectedAbilitiesIds,searchedAbilitiesIds,profilePicture);
    }
   @GetMapping("/view-profile/{userId}")
    public String viewProfile(@PathVariable("userId") Long userId, Model model,HttpServletRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        User currentUser = userService.getLoggedUser();
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("user",user);
        if (user.equals(currentUser)){
            model.addAttribute("friendRequests", userFriendService.getUnapprovedFriendRequestsByFriendId(currentUser.getId()));
        }
        List<UserFriendDTO> userFriends = userFriendService.getFriendsById(userId);
        model.addAttribute("userFriends", userFriends);
        return "/user/view_profile";
    }

    @GetMapping("/home-page")
    public String showHomePage(Model model){
        List<UserDTO> allUsers = userService.getAllUsersExceptLogged();
        List<UserDTO> recommendedUsers = userService.getRecommendedUsers();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("recommendedUsers", recommendedUsers);
        return "home_page.html";
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
    public String saveUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model, @RequestParam(name = "selectedAbilitiesIds", required = false) List<Long> selectedAbilitiesIds,
                           @RequestParam(name = "searchedAbilitiesIds", required = false) List<Long> searchedAbilitiesIds,@RequestParam("profilePicture") MultipartFile profilePicture) {
        List<Ability> abilities = abilityRepository.findAllByIdIn(selectedAbilitiesIds);
        List<Ability> searched = abilityRepository.findAllByIdIn(searchedAbilitiesIds);
        if (bindingResult.hasErrors()) {
            model.addAttribute("abilities",abilityRepository.findAll());
            return "/user/registration";
        }
        if (!comparePasswords(userDTO.getPassword(), userDTO.getRepeatPassword())) {
            model.addAttribute("passwordsDoNotMatch", "Passwords do not match!");
            model.addAttribute("abilities",abilityRepository.findAll());
            return "/user/registration";
        }
        User user = modelMapper.map(userDTO, User.class);
        List<Object> userPresentObj = isUserPresent(user);
        if ((Boolean) userPresentObj.get(0)) {
            model.addAttribute("abilities",abilityRepository.findAll());
            return "/user/registration";
        }
        if (!profilePicture.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString();
                String originalFileName = profilePicture.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                fileName += fileExtension;
                Path uploadDir = Paths.get("src/main/resources/static/img");
                Files.createDirectories(uploadDir);
                try (InputStream inputStream = profilePicture.getInputStream()) {
                    Files.copy(inputStream, uploadDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                }
                user.setProfileImageName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    @GetMapping("/all")
    public String showAllUsers(Model model){
        List<UserDTO> allUsers = userService.getAllUsersExceptLogged();
        List<AbilityDTO> abilities = abilityService.getAbilities();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("allAbilities", abilities);
        return "/user/all_users.html";
    }
}
