package com.ludogoriehack24.ludogoriehack24.user;

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
    @NotEmpty(message = "Field must not be empty!")
    private String fullName;
    @NotEmpty(message = "Field must not be empty!")
    @Size(min = 4, max = 20, message = "Username must be between 6 and 20 characters!")
    private String username;
    @NotEmpty(message = "Field must not be empty!")
    @Size(min = 8, message = "Password must be at least 8 characters!")
    private String password;
    @NotEmpty(message = "Field must not be empty!")
    private String repeatPassword;
    @NotEmpty(message = "Field must not be empty!")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits!")
    private String mobile;
    @NotEmpty(message = "Field must not be empty!")
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
}
