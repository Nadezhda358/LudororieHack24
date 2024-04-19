package com.ludogoriehack24.ludogoriehack24.user;

import lombok.AllArgsConstructor;
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
        List<User> villages = userRepository.findAll();
        return villages.stream()
                .map(this::userToUserDTO)
                .toList();
    }
}
