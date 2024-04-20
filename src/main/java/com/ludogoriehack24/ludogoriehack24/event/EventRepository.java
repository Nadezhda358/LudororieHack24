package com.ludogoriehack24.ludogoriehack24.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    //    List<Event> findAllByUserId(@Param("userId") Long userId);
    List<Event> findAllByUserId(@Param("userId") Long userId);
}
