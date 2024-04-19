package com.ludogoriehack24.ludogoriehack24.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    public User getUserByUsername(@Param("username") String username);
    public User getUserByEmail(@Param("email") String email);

}
