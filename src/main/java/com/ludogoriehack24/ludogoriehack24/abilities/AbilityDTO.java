package com.ludogoriehack24.ludogoriehack24.abilities;

import com.ludogoriehack24.ludogoriehack24.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbilityDTO {
    private Long id;
    private String name;
    private List<User> users;
    private List<User> usersInNeed;
}
