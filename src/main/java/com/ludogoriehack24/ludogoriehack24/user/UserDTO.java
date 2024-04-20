
package com.ludogoriehack24.ludogoriehack24.user;

import com.ludogoriehack24.ludogoriehack24.Message.Message;
import com.ludogoriehack24.ludogoriehack24.abilities.Ability;
import com.ludogoriehack24.ludogoriehack24.constants.Role;
import com.ludogoriehack24.ludogoriehack24.event.Event;
import com.ludogoriehack24.ludogoriehack24.userFriend.UserFriend;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String fullName;
    @NotEmpty
    @Size(min = 4, max = 20, message = "Username must be between 6 and 20 characters!")
    private String username;
    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 characters!")
    private String password;
    @NotEmpty
    private String repeatPassword;
    @NotEmpty
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits!")
    private String mobile;
    @NotEmpty
    private String email;
    @Size(max = 1000,message = "Education description must not exceed 1000 characters!")
    private String education;
    @Size(max = 1000,message = "Education description must not exceed 1000 characters!")
    private String workplace;
    @Size(max = 1000,message = "Education description must not exceed 1000 characters!")
    private String workExperience;
    @Enumerated(EnumType.STRING)
    private Role role;
    private List<UserFriend> friends;
    private List<Event> events;
    private String profileImageName;
    private List<Ability> userAbilities;
    private List<Ability> neededAbilities;
    private List<Message> receivedMessages;
    private List<Message> sentMessages;
}
