package com.ludogoriehack24.ludogoriehack24.abilities;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/abilities")
public class AbilityController {
    private AbilityService abilityService;
}
