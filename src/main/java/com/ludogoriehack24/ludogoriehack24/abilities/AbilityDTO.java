package com.ludogoriehack24.ludogoriehack24.abilities;

import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.persistence.ManyToMany;
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
public class AbilityDTO {
    @NotEmpty(message = "Field must not be empty!")
    @Size(min=3, message = "Size must be at least 3 characters!")
    private String name;
    private List<User> users;
    private List<User> usersInNeed;
}
