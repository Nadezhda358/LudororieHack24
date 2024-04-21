package com.ludogoriehack24.ludogoriehack24.userFriend;

import com.ludogoriehack24.ludogoriehack24.exceptions.ApiRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/user-friends")
public class UserFriendController {
    private final UserFriendService userFriendService;

    @PostMapping("/friend-request/{friendId}")
    public String sendFriendRequest(@PathVariable("friendId") Long friendId) {
        try {
            userFriendService.sendFriendRequest(friendId);
        } catch (Exception e) {
            throw new ApiRequestException("Friend request not sent");
        }
        return "redirect:/users/view-profile/" + friendId;
    }

    @GetMapping("/delete/{userFriendId}")
    public String deleteUserFriend(@PathVariable("userFriendId") Long userFriendId, Model model) {
        Long id = userFriendService.getUserFriendById(userFriendId).getFriend().getId();
        userFriendService.deleteUserFriend(userFriendId);
        return "redirect:/users/view-profile/" + id;
    }
    @PostMapping("/accept/{userFriendId}")
    public String acceptUserFriend(@PathVariable("userFriendId") Long userFriendId, Model model) {
        Long id = userFriendService.getUserFriendById(userFriendId).getFriend().getId();
        userFriendService.acceptUserFriend(userFriendId);
        return "redirect:/users/view-profile/" + id;
    }
}
