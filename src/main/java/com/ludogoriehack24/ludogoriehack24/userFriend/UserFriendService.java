package com.ludogoriehack24.ludogoriehack24.userFriend;

import com.ludogoriehack24.ludogoriehack24.user.User;
import com.ludogoriehack24.ludogoriehack24.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserFriendService {
    private final UserFriendRepository userFriendRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserFriendDTO userFriendToUserFriendDTO(UserFriend userFriend) {
        return modelMapper.map(userFriend, UserFriendDTO.class);
    }

    public UserFriend sendFriendRequest(Long friendId) throws Exception {
        User friend = userService.userDTOToUser(userService.getUserById(friendId));
        User user = userService.getLoggedUser();
        List<UserFriend> userFriendsFound = userFriendRepository.findByIds(friendId, user.getId());
        if(!userFriendsFound.isEmpty()){
            return userFriendsFound.get(0);
        }
        UserFriend userFriend = new UserFriend(null, user, friend, false);
        return userFriendRepository.save(userFriend);
    }
    public List<UserFriendDTO> getUnapprovedFriendRequestsByUSerId(Long id){
        List<UserFriend> userFriends = userFriendRepository.findByIsAcceptedAndUserId(false, id);
        return userFriends.stream()
                .map(this::userFriendToUserFriendDTO)
                .toList();
    }
    public List<UserFriendDTO> getFriendsByUserId(Long id){
        List<UserFriend> userFriends = userFriendRepository.findByStatusAndFriendIdOrUserId(true, id);
        return userFriends.stream()
                .map(this::userFriendToUserFriendDTO)
                .toList();
    }

}
