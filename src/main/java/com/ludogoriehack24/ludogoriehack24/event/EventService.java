package com.ludogoriehack24.ludogoriehack24.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {
    public boolean checkIfEndDateIsAfterStart(EventDTO eventDTO) {
        return !eventDTO.getEndDate().isBefore(eventDTO.getStartDate());
    }
}
