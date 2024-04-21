package com.ludogoriehack24.ludogoriehack24.event;

import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Field must not be empty!")
    @Size(min=3, message = "Size must be at least 3 characters!")
    private String name;

    @NotNull(message = "Field must not be empty!")
    private LocalDateTime startDate;
    @NotNull(message = "Field must not be empty!")
    private LocalDateTime endDate;

    @Size(min = 4, max = 250, message = "Size must be between 4 and 250 characters!")
    private String location;

    @Column(columnDefinition = "TEXT")
    @Size(min = 10, message = "Size must be at least 10 characters!")
    private String description;

    @ManyToMany(mappedBy = "events")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "organiser_id")
    private User user;
}
