package com.ludogoriehack24.ludogoriehack24.Message;

import com.ludogoriehack24.ludogoriehack24.Chat.Chat;
import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User senderUser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private User receiverUser;

    @NotEmpty(message = "Message must be at least 1 char!")
    private String text;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private LocalDateTime time;
}
