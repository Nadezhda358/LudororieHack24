package com.ludogoriehack24.ludogoriehack24.Chat;

import com.ludogoriehack24.ludogoriehack24.Exceptions.ApiRequestException;
import com.ludogoriehack24.ludogoriehack24.user.User;
import com.ludogoriehack24.ludogoriehack24.user.UserRepository;
import com.ludogoriehack24.ludogoriehack24.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private ChatRepository chatRepository;
    public Chat startChat(Long userReceiverId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        Optional<User> optionalUser = userRepository.findById(userReceiverId);
        if (optionalUser.isPresent()) {
            Chat chat = new Chat();
            chat.setUser1(user);
            chat.setUser2(optionalUser.get());
            chatRepository.save(chat);
            return chat;
        }
        throw new ApiRequestException("User not found");
    }
}