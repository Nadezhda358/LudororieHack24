package com.ludogoriehack24.ludogoriehack24.abilities;

import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
@Table(name = "abilities")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Field must not be empty!")
    @Size(min=3, message = "Size must be at least 3 characters!")
    private String name;

    @ManyToMany(mappedBy = "userAbilities")
    private List<User> users;

    @ManyToMany(mappedBy = "neededAbilities")
    private List<User> usersInNeed;
}
