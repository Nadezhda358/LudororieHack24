package com.ludogoriehack24.ludogoriehack24.user;

import com.ludogoriehack24.ludogoriehack24.abilities.Ability;
import com.ludogoriehack24.ludogoriehack24.abilities.AbilityDTO;
import com.ludogoriehack24.ludogoriehack24.abilities.AbilityRepository;
import com.ludogoriehack24.ludogoriehack24.constants.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private AbilityRepository abilityRepository;
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDTO userToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    public User userDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::userToUserDTO)
                .toList();
    }
    public List<UserDTO> getAllUsersExceptLogged() {
        List<User> users = userRepository.findAllExceptUser(getLoggedUser().getId());
        return users.stream()
                .map(this::userToUserDTO)
                .toList();
    }

    public List<UserDTO> getRecommendedUsers(){
        User loggedUser = getLoggedUser();
        List<User> users = userRepository.findUsersWithMatchingAbilities(loggedUser.getNeededAbilities(), loggedUser.getId());
        return users.stream()
                .map(this::userToUserDTO)
                .toList();
    }
    public String editProfile(Long id, Model model, HttpServletRequest request){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = userToUserDTO(user);
            model.addAttribute("userDTO", userDTO);
            model.addAttribute("abilities",abilityRepository.findAll());
            model.addAttribute("selectedAbilities",new ArrayList<AbilityDTO>());
            model.addAttribute("selectedAbilities2",new ArrayList<AbilityDTO>());
            return "/user/edit";
        }
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }
    public String addChanges(UserDTO userDTO, BindingResult bindingResult, Model model,@RequestParam(name = "selectedAbilitiesIds", required = false) List<Long> selectedAbilitiesIds,
                             @RequestParam(name = "searchedAbilitiesIds", required = false) List<Long> searchedAbilitiesIds,@RequestParam("profilePicture") MultipartFile profilePicture) {
        List<Ability> abilities = abilityRepository.findAllByIdIn(selectedAbilitiesIds);
        List<Ability> searched = abilityRepository.findAllByIdIn(searchedAbilitiesIds);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            model.addAttribute("abilities",abilityRepository.findAll());
            return "/user/edit";
        }
        if (!comparePasswords(userDTO.getPassword(), userDTO.getRepeatPassword())) {
            model.addAttribute("passwordsDoNotMatch", "Passwords do not match!");
            model.addAttribute("abilities",abilityRepository.findAll());
            return "/user/registration";
        }
        User user = userDTOToUser(userDTO);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        if (!profilePicture.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString();
                String originalFileName = profilePicture.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                fileName += fileExtension;
                Path uploadDir = Paths.get("src/main/resources/static/img");
                Files.createDirectories(uploadDir); // Create directories if they don't exist
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
        user.setEnabled(true);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return "redirect:/users/home-page";
    }
    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.getUserByUsername(username);
    }
    public boolean comparePasswords(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }
}
