package com.ludogoriehack24.ludogoriehack24.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import org.modelmapper.ModelMapper;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserDTO userToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
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
}
