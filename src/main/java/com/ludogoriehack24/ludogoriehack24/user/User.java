package com.ludogoriehack24.ludogoriehack24.user;

import com.ludogoriehack24.ludogoriehack24.constants.Role;
import com.ludogoriehack24.ludogoriehack24.abilities.Ability;
import com.ludogoriehack24.ludogoriehack24.event.Event;
import com.ludogoriehack24.ludogoriehack24.userFriend.UserFriend;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotEmpty
    private String fullName;
    @NotEmpty
    @Size(min = 4, max = 20, message = "Username must be between 6 and 20 characters!")
    private String username;
    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 characters!")
    private String password;
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
    @ManyToMany
    @JoinTable(name = "event_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
    @Column(name = "profile_image_path")
    private String profileImageName;
    @Column(columnDefinition = "boolean DEFAULT '1'")
    private boolean enabled;
    public void setProfileImagePath(String profileImagePath) {
        this.profileImageName = profileImagePath;
    }



    //role
}
