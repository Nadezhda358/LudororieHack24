package com.ludogoriehack24.ludogoriehack24.Chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private ChatService chatService;

    @PostMapping("/add/{userReceiverId}")
    public String startChat(@PathVariable("userReceiverId") Long userReceiverId, Model model) {
        Chat chat = chatService.startChat(userReceiverId);
        model.addAttribute("chat", chat);
        return "/chat/conversation";
    }
}
