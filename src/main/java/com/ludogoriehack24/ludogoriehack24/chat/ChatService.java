package com.ludogoriehack24.ludogoriehack24.chat;

import com.ludogoriehack24.ludogoriehack24.exceptions.ApiRequestException;
import com.ludogoriehack24.ludogoriehack24.user.User;
import com.ludogoriehack24.ludogoriehack24.user.UserRepository;
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

    public ChatDTO chatToChatDTO(Chat chat) {
        return modelMapper.map(chat, ChatDTO.class);
    }

    public Chat chatDTOToChat(ChatDTO chatDTO) {
        return modelMapper.map(chatDTO, Chat.class);
    }
    public ChatDTO startChat(Long userReceiverId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        Optional<User> optionalUser = userRepository.findById(userReceiverId);
        if (optionalUser.isPresent()) {
            User user2 = optionalUser.get();
            Chat chat = new Chat();
            chat.setUser1(user);
            chat.setUser2(user2);
            if (checkIfChatCanBeStarted(user2)) chatRepository.save(chat);
            else {
                return chatToChatDTO(chatRepository.findChatByUserIds(user.getId(), user2.getId()));
            }
            return chatToChatDTO(chat);
        }
        throw new ApiRequestException("User not found");
    }

    public boolean checkIfChatCanBeStarted(User user2) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        Chat optionalChat = chatRepository.findChatByUserIds(user.getId(), user2.getId());
        if (optionalChat != null) return false;
        return true;
    }
}
