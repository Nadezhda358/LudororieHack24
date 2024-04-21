package com.ludogoriehack24.ludogoriehack24.Chat;

import com.ludogoriehack24.ludogoriehack24.Message.Message;
import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_user_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "second_user_id")
    private User user2;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
}
