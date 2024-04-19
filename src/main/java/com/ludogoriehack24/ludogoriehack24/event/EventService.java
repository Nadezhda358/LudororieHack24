package com.ludogoriehack24.ludogoriehack24.event;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {
    private ModelMapper modelMapper;
    public EventDTO eventToEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    public Event eventDTOToEvent(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }
    public boolean checkIfEndDateIsAfterStart(EventDTO eventDTO) {
        return eventDTO.getStartDate().isBefore(eventDTO.getEndDate());
    }
}
