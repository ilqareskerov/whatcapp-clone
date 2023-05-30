package com.company.whatcapp.controller;

import com.company.whatcapp.modal.User;
import com.company.whatcapp.request.UpdateUserRequest;
import com.company.whatcapp.response.ApiResponse;
import com.company.whatcapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfile(jwt);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/search/{query}")
    public ResponseEntity<List<User>> searchUser(@PathVariable("query") String query) {
        return ResponseEntity.ok(userService.searchUser(query));
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody UpdateUserRequest updateUserRequest){
        User user = userService.findUserProfile(jwt);
        userService.updateUser(user.getId(), updateUserRequest);
        return ResponseEntity.ok(new ApiResponse( "User Updated Successfully",true));
    }
}
