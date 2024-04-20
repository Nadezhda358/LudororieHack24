package com.ludogoriehack24.ludogoriehack24.abilities;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AbilityRepository extends JpaRepository<Ability,Long> {
    List<Ability> findAllByIdIn(List<Long> ids);

}
