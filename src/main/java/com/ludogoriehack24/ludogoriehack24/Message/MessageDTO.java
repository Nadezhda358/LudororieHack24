package com.ludogoriehack24.ludogoriehack24.Message;

import com.ludogoriehack24.ludogoriehack24.Chat.Chat;
import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
