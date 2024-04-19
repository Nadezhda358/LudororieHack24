package com.ludogoriehack24.ludogoriehack24.event;

import com.ludogoriehack24.ludogoriehack24.user.User;
import com.ludogoriehack24.ludogoriehack24.user.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private ModelMapper modelMapper;

    private EventRepository eventRepository;

    private UserRepository userRepository;
    public EventDTO eventToEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    public Event eventDTOToEvent(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }

    public boolean checkIfEndDateIsAfterStart(EventDTO eventDTO) {
        return eventDTO.getStartDate().isBefore(eventDTO.getEndDate());
    }

    public List<EventDTO> findAllEventDTOs() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(this::eventToEventDTO).toList();
    }

    public void setOrganiser(EventDTO eventDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        eventDTO.setUser(user);
    }
}
