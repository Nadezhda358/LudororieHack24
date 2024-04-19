package com.ludogoriehack24.ludogoriehack24.userFriend;

import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_friends")
public class UserFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;
    private boolean isAccepted;

}
