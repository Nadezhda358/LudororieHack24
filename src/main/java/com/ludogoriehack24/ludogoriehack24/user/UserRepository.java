package com.ludogoriehack24.ludogoriehack24.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    public User getUserByUsername(@Param("username") String username);
    public User getUserByEmail(@Param("email") String email);

    @Query("SELECT e.id FROM User u JOIN u.events e WHERE u.id = :userId")
    List<Long> findEventIdsByUserId(Long userId);

}
