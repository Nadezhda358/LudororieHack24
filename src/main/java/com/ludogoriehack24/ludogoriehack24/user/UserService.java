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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addChanges(UserDTO userDTO, BindingResult bindingResult, Model model, @RequestParam("selectedAbilitiesIds") List<Long> selectedAbilitiesIds,@RequestParam("searchedAbilitiesIds") List<Long> searchedAbilitiesIds) {
        List<Ability> abilities = abilityRepository.findAllByIdIn(selectedAbilitiesIds);
        List<Ability> searched = abilityRepository.findAllByIdIn(searchedAbilitiesIds);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            return "/user/edit";
        }
        if (!comparePasswords(userDTO.getPassword(), userDTO.getRepeatPassword())) {
            model.addAttribute("passwordsDoNotMatch", "Passwords do not match!");
            return "/user/registration";
        }
        User user = userDTOToUser(userDTO);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
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
        return "redirect:/users/login";
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
}
