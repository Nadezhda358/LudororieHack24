package com.ludogoriehack24.ludogoriehack24.event;

import com.ludogoriehack24.ludogoriehack24.user.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    @NotEmpty
    private LocalDateTime startDate;

    // след дата на начало
    @NotEmpty
    private LocalDateTime endDate;

    @Size(min = 4, max = 250, message = "Size must be between 4 and 250 characters!")
    private String location;

    // повече от 255 символа
    @Column(columnDefinition = "TEXT")
    @Size(min = 10, message = "Size must be at least 10 characters!")
    private String description;

    private List<User> users;

    private User user;
}