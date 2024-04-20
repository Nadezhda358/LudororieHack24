package com.ludogoriehack24.ludogoriehack24.userFriend;

import com.ludogoriehack24.ludogoriehack24.user.User;
import com.ludogoriehack24.ludogoriehack24.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserFriendService {
    private final UserFriendRepository userFriendRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserFriendDTO userFriendToUserFriendDTO(UserFriend userFriend) {
        return modelMapper.map(userFriend, UserFriendDTO.class);
    }
    public UserFriend userFriendDTOToUserFriend(UserFriendDTO userFriendDTO) {
        return modelMapper.map(userFriendDTO, UserFriend.class);
    }
    public UserFriendDTO sendFriendRequest(Long friendId) throws Exception {
        User friend = userService.userDTOToUser(userService.getUserById(friendId));
        User user = userService.getLoggedUser();
        List<UserFriend> userFriendsFound = userFriendRepository.findByUserIdAndFriendId(user.getId(), friendId);
        if(!userFriendsFound.isEmpty()){
            return userFriendToUserFriendDTO(userFriendsFound.get(0));
        }
        UserFriend userFriend = new UserFriend(null, user, friend, false);
        return userFriendToUserFriendDTO(userFriendRepository.save(userFriend));
    }
    public List<UserFriendDTO> getUnapprovedFriendRequestsByFriendId(Long id){
        List<UserFriend> userFriends = userFriendRepository.findByIsAcceptedAndFriendId(false, id);
        return userFriends.stream()
                .map(this::userFriendToUserFriendDTO)
                .toList();
    }
    public List<UserFriendDTO> getFriendsById(Long id){
        List<UserFriend> userFriends = userFriendRepository.findByStatusAndFriendIdOrUserId(true, id);
        return userFriends.stream()
                .map(this::userFriendToUserFriendDTO)
                .toList();
    }
    public void deleteUserFriend(Long id){
        Optional<UserFriend> userFriend = userFriendRepository.findById(id);
        if (userFriend.isPresent()){
            userFriendRepository.deleteById(id);
        }
    }

    public void acceptUserFriend(Long id) {
        Optional<UserFriend> userFriendOptional = userFriendRepository.findById(id);
        userFriendOptional.ifPresent(userFriend -> {
            userFriend.setAccepted(true);
            userFriendRepository.save(userFriend);

            List<UserFriend> userFriendsFound = userFriendRepository.findByUserIdAndFriendId(userFriend.getFriend().getId(), userFriend.getUser().getId());
            userFriendRepository.deleteAll(userFriendsFound);
        });
    }
    public UserFriendDTO getUserFriendById(Long id) throws Exception {
        Optional<UserFriend> userFriend = userFriendRepository.findById(id);
        return userFriend.map(this::userFriendToUserFriendDTO).orElseThrow(() -> new Exception("UserFriend not Found"));
    }
}
