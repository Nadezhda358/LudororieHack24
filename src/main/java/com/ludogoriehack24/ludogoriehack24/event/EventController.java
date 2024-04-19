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
@RequestMapping(name = "/templates/event")
@AllArgsConstructor
public class EventController {
    private EventService eventService;

    private EventRepository eventRepository;

    @GetMapping("/add")
    private String eventForm(Model model) {
        model.addAttribute("templates/event", new EventDTO());
        return "/events/add";
    }

    @PostMapping("/submit")
    private String eventSubmit(@Valid EventDTO eventDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || eventService.checkIfEndDateIsAfterStart(eventDTO)) {
            return "redirect:/event/add";
        }
        //
        return "/event/all";
    }
}