package com.ludogoriehack24.ludogoriehack24.userFriend;

import com.ludogoriehack24.ludogoriehack24.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendDTO {
    private Long id;
    private User user;
    private User friend;
    private boolean isAccepted;
}
