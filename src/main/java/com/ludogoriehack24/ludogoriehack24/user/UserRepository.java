package com.ludogoriehack24.ludogoriehack24.user;

import com.ludogoriehack24.ludogoriehack24.abilities.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User getUserByUsername(@Param("username") String username);
    User getUserByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u WHERE u.id <> :userId")
    List<User> findAllExceptUser(@Param("userId") Long userId);
    @Query("SELECT u FROM User u " +
            "JOIN u.userAbilities a " +
            "WHERE a IN :neededAbilities " +
            "AND u.id != :userId " +
            "GROUP BY u.id " +
            "ORDER BY COUNT(a) DESC")
    List<User> findUsersWithMatchingAbilities(@Param("neededAbilities") List<Ability> neededAbilities,
                                              @Param("userId") Long userId);

    @Query("SELECT e.id FROM User u JOIN u.events e WHERE u.id = :userId")
    List<Long> findEventIdsByUserId(Long userId);
}

