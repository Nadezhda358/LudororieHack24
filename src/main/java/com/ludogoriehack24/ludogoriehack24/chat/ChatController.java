package com.ludogoriehack24.ludogoriehack24.chat;

import com.ludogoriehack24.ludogoriehack24.message.MessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
