package com.ludogoriehack24.ludogoriehack24.abilities;

import com.ludogoriehack24.ludogoriehack24.event.Event;
import com.ludogoriehack24.ludogoriehack24.event.EventDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AbilityService {
    private ModelMapper modelMapper;
    public AbilityDTO abilityToAbilityDTO(Ability ability) {
        return modelMapper.map(ability, AbilityDTO.class);
    }

    public Ability abilityDTOToAbility(AbilityDTO abilityDTO) {
        return modelMapper.map(abilityDTO, Ability.class);
    }
}
