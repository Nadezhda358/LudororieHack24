package com.ludogoriehack24.ludogoriehack24.event;

import com.ludogoriehack24.ludogoriehack24.user.User;
import com.ludogoriehack24.ludogoriehack24.user.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public boolean checkIfDateIsValid(EventDTO eventDTO) {
        if (eventDTO.getStartDate().isBefore(LocalDateTime.now())) return false;
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

    public void setParticipant(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.getUserByUsername(authentication.getName());
            List<Event> eventList = user.getEvents();
            if (checkIfUserIsAlreadyParticipating(eventId, user.getId())) {
                eventList.add(event);
                user.setEvents(eventList);
                userRepository.save(user);
            }
        }
    }

    public boolean checkIfUserIsAlreadyParticipating(Long eventId, Long userId) {
        List<Long> eventIds = userRepository.findEventIdsByUserId(userId);
        if (eventIds.contains(eventId)) return false;
        return true;
    }

    public List<EventDTO> getEventDTOsParticipatedByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        List<Long> eventIdsList = userRepository.findEventIdsByUserId(user.getId());
        List<Event> eventList = eventRepository.findAllById(eventIdsList);
        return eventList.stream().map(this::eventToEventDTO).collect(Collectors.toList());
    }

    public List<EventDTO> getEventDTOsCreatedByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        List<Event> eventList = eventRepository.findAllByUserId(user.getId());
        return eventList.stream().map(this::eventToEventDTO).collect(Collectors.toList());
    }

    public void deleteEvent(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            List<User> userList = event.getUsers();
            for (User user : userList) {
                List<Event> participatingEvents = user.getEvents();
                participatingEvents.remove(event);
                user.setEvents(participatingEvents);
                userRepository.save(user);
            }
            eventRepository.deleteById(eventId);
        }
    }


}
