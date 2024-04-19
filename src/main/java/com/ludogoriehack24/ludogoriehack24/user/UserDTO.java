package com.ludogoriehack24.ludogoriehack24.user;

import com.ludogoriehack24.ludogoriehack24.abilities.Ability;
import com.ludogoriehack24.ludogoriehack24.event.Event;
import com.ludogoriehack24.ludogoriehack24.userFriend.UserFriend;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String education;
    private String workplace;
    private String workExperience;
    private List<UserFriend> friends;
    private List<Event> events;
    private String profileImageName;
    private List<Ability> userAbilities;
    private List<Ability> neededAbilities;
    //role
}