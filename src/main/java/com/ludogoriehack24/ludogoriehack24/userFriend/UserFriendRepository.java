package com.ludogoriehack24.ludogoriehack24.userFriend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {
    List<UserFriend> findByIsAcceptedAndUserId(boolean isAccepted, Long userId);
    List<UserFriend> findByIsAcceptedAndFriendId(boolean isAccepted, Long friendId);

    @Query("SELECT uf FROM UserFriend uf WHERE (uf.isAccepted = :status AND uf.friend.id = :id) OR (uf.isAccepted = :status AND uf.user.id = :id)")
    List<UserFriend> findByStatusAndFriendIdOrUserId(boolean status, Long id);

    @Query("SELECT uf FROM UserFriend uf WHERE (uf.user.id = :firstId AND uf.friend.id = :secondId) OR (uf.user.id = :secondId AND uf.friend.id = :firstId)")
    List<UserFriend> findByIds(Long firstId, Long secondId);
    List<UserFriend> findByUserIdAndFriendId(Long userId, Long friendId);

}
