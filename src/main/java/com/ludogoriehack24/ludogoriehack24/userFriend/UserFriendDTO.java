package com.ludogoriehack24.ludogoriehack24.userFriend;

import com.ludogoriehack24.ludogoriehack24.user.User;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendDTO {
    private Long id;
    private User user;
    private User friend;
    private boolean isAccepted;
}
