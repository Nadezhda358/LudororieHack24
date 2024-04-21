package com.ludogoriehack24.ludogoriehack24.Chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c " +
            "WHERE (c.user1.id = :user1Id AND c.user2.id = :user2Id) " +
            "OR (c.user1.id = :user2Id AND c.user2.id = :user1Id)")
    Chat findChatByUserIds(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
}
