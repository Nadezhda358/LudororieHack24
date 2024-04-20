package com.ludogoriehack24.ludogoriehack24.Message;

import com.ludogoriehack24.ludogoriehack24.Chat.ChatService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private MessageService messageService;
    private ChatService chatService;
    @PostMapping("/send")
    public String sendMessage(@Valid MessageDTO messageDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("chatDTO", chatService.chatToChatDTO(messageDTO.getChat()));
            return "/chat/conversation";
        }
        messageService.setMessageParams(messageDTO);
        return "redirect:/chat/" + messageService.getUserReceiver(messageDTO).getId();
    }

}
