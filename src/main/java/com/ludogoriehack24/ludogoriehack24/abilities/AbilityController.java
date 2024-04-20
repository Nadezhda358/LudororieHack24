package com.ludogoriehack24.ludogoriehack24.abilities;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/abilities")
@AllArgsConstructor
public class AbilityController {
    private AbilityRepository abilityRepository;
    private AbilityService abilityService;
    @GetMapping("/add-ability")
    public String addAbility(Model model){
        model.addAttribute("abilityDTO",new AbilityDTO());
        return "/ability/add";
    }
    @GetMapping("/get-abilities")
    public String getAbilities(Model model){
        model.addAttribute("allAbilities",abilityRepository.findAll());
        return "/ability/all";
    }
    @PostMapping("/submit-ability")
    public String submitAbility(@Valid AbilityDTO abilityDTO, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()){
            return "/ability/add";
        }
        abilityRepository.save(abilityService.abilityDTOToAbility(abilityDTO));
        model.addAttribute("allAbilities",abilityRepository.findAll());
        return "/ability/all";
    }
}
