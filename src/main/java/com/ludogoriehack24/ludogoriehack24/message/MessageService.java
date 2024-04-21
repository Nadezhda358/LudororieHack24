package com.ludogoriehack24.ludogoriehack24.message;

import com.ludogoriehack24.ludogoriehack24.exceptions.ApiRequestException;
import com.ludogoriehack24.ludogoriehack24.user.User;
import com.ludogoriehack24.ludogoriehack24.user.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private ModelMapper modelMapper;

    public Message messageDTOToMessage(MessageDTO messageDTO) {
        return modelMapper.map(messageDTO, Message.class);
    }

    public MessageDTO messageToMessageDTO(Message message) {
        return modelMapper.map(message, MessageDTO.class);
    }

    public void setMessageParams(MessageDTO messageDTO) {
        messageDTO.setTime(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        messageDTO.setSenderUser(user);
        User userReceiver = getUserReceiver(messageDTO);
        messageDTO.setReceiverUser(userReceiver);
        messageRepository.save(messageDTOToMessage(messageDTO));
    }

    public User getUserReceiver(MessageDTO messageDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        List<Long> userIds = new java.util.ArrayList<>(List.of(messageDTO.getChat().getUser1().getId(), messageDTO.getChat().getUser2().getId()));
        userIds.remove(user.getId());
        Long receiverId = userIds.get(0);
        Optional<User> optionalReceiverUser = userRepository.findById(receiverId);
        if (optionalReceiverUser.isPresent()) return optionalReceiverUser.get();
        throw new ApiRequestException("User not found!");
    }
}
