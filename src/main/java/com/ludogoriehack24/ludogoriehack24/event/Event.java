package com.ludogoriehack24.ludogoriehack24.event;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime startDate;
    // след дата на начало
    private LocalDateTime endDate;
    private String location;
    // повече от 255 символа
    private String description;
}
