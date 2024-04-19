package com.ludogoriehack24.ludogoriehack24.user;

import com.ludogoriehack24.ludogoriehack24.abilities.Ability;
import com.ludogoriehack24.ludogoriehack24.userFriend.UserFriend;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String education;
    private String workplace;
    private String workExperience;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFriend> friends;

    @ManyToMany
    @JoinTable(name = "ability_user", joinColumns = @JoinColumn(name = "ability_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Ability> userAbilities;

    @ManyToMany
    @JoinTable(name = "ability_user_needed", joinColumns = @JoinColumn(name = "needed_ability_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Ability> neededAbilities;
    //role
}
