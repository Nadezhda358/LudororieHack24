package com.ludogoriehack24.ludogoriehack24.Chat;

import com.ludogoriehack24.ludogoriehack24.Message.MessageDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private ChatService chatService;
    @GetMapping("/{userReceiverId}")
    public String startChat(@PathVariable("userReceiverId") Long userReceiverId, Model model) {
        ChatDTO chatDTO = chatService.startChat(userReceiverId);
        model.addAttribute("chatDTO", chatDTO);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setChat(chatService.chatDTOToChat(chatDTO));
        model.addAttribute("messageDTO", messageDTO);
        return "/chat/conversation";
    }

}
