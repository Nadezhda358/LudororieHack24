package com.ludogoriehack24.ludogoriehack24.userFriend;

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
            throw new RuntimeException(e);
        }
        return "redirect:/users/view-profile/" + friendId;
    }

    @GetMapping("/delete/{userFriendId}")
    public String deleteUserFriend(@PathVariable("userFriendId") Long userFriendId, Model model) {
        Long id;
        try {
            id = userFriendService.getUserFriendById(userFriendId).getFriend().getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        userFriendService.deleteUserFriend(userFriendId);
        return "redirect:/users/view-profile/" + id;
    }
    @PostMapping("/accept/{userFriendId}")
    public String acceptUserFriend(@PathVariable("userFriendId") Long userFriendId, Model model) {
        Long id;
        try {
            id = userFriendService.getUserFriendById(userFriendId).getFriend().getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        userFriendService.acceptUserFriend(userFriendId);
        return "redirect:/users/view-profile/" + id;
    }
}
