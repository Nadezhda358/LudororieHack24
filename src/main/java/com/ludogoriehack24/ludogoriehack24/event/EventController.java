package com.ludogoriehack24.ludogoriehack24.event;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {
    private EventService eventService;

    private EventRepository eventRepository;

    @GetMapping("/add")
    private String eventForm(Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        return "/event/add";
    }

    @PostMapping("/submit")
    private String eventSubmit(@Valid EventDTO eventDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/event/add";
        }
        if (!eventService.checkIfDateIsValid(eventDTO)) {
            model.addAttribute("dateMessage", "Invalid dates!");
            return "/event/add";
        }
        eventService.setOrganiser(eventDTO);
        eventRepository.save(eventService.eventDTOToEvent(eventDTO));
        model.addAttribute("allEventDTOs", eventService.findAllEventDTOs());
        return "/event/all";
    }

    @GetMapping("/all")
    private String allEvents(Model model) {
        model.addAttribute("allEventDTOs", eventService.findAllEventDTOs());
        return "/event/all";
    }
}
