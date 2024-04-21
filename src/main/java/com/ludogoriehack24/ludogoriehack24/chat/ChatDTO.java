package com.ludogoriehack24.ludogoriehack24.chat;

import com.ludogoriehack24.ludogoriehack24.message.Message;
import com.ludogoriehack24.ludogoriehack24.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private Long id;
    private User user1;

    private User user2;

    private List<Message> messages;
}
