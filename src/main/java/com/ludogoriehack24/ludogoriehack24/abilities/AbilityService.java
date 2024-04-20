package com.ludogoriehack24.ludogoriehack24.abilities;
import com.ludogoriehack24.ludogoriehack24.event.Event;
import com.ludogoriehack24.ludogoriehack24.event.EventDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
public class AbilityService {
    private AbilityRepository abilityRepository;
    private ModelMapper modelMapper;

    public AbilityDTO abilityToAbilityDTO(Ability ability) {
        return modelMapper.map(ability, AbilityDTO.class);
    }
  
    public Ability abilityDTOToAbility(AbilityDTO abilityDTO) {
        return modelMapper.map(abilityDTO, Ability.class);
    }
    public List<AbilityDTO> getAbilities(){
        List<Ability> abilities = abilityRepository.findAll();
        return abilities.stream()
                .map(this::abilityToAbilityDTO)
                .toList();
    }
}
