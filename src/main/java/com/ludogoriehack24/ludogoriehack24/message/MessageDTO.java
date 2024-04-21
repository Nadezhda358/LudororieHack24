package com.ludogoriehack24.ludogoriehack24.message;

import com.ludogoriehack24.ludogoriehack24.chat.Chat;
import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;

    private User senderUser;

    private User receiverUser;

    @NotEmpty(message = "Message must be at least 1 char!")
    private String text;

    private Chat chat;

    private LocalDateTime time;
}
