package com.company.whatcapp.controller;

import com.company.whatcapp.modal.Chat;
import com.company.whatcapp.modal.User;
import com.company.whatcapp.request.GroupChatRequest;
import com.company.whatcapp.request.SingleChatRequest;
import com.company.whatcapp.response.ApiResponse;
import com.company.whatcapp.service.ChatService;
import com.company.whatcapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }
    @PostMapping("/single")
    public ResponseEntity<Chat> createChat(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        Chat chat = chatService.createChat(user, singleChatRequest.getUserId());
        return ResponseEntity.ok(chat);
    }
    @PostMapping("/group")
    public ResponseEntity<Chat> createGroup(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        Chat chat = chatService.createGroup(groupChatRequest, user);
        return ResponseEntity.ok(chat);
    }
    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatById(@PathVariable("chatId") Long chatId, @RequestHeader("Authorization") String token){
        Chat chat = chatService.findChatById(chatId);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatByUserId(@RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        List<Chat> chat = chatService.findAllChatByUserId(user.getId());
        return ResponseEntity.ok(chat);
    }
    @PutMapping("{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroup(@PathVariable Long chatId,@PathVariable Long userId,@RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        Chat chat = chatService.addUserToGroup(userId,chatId,user);
        return ResponseEntity.ok(chat);
    }
    @PutMapping("{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromGroup(@PathVariable Long chatId,@PathVariable Long userId,@RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        Chat chat = chatService.removeUserFromGroup(userId,chatId,user);
        return ResponseEntity.ok(chat);
    }

    @DeleteMapping("delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChat(@PathVariable Long chatId, @RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        chatService.deleteChat(chatId,user.getId());
        ApiResponse apiResponse = new ApiResponse("Chat is deleted successfully",false);
        return ResponseEntity.ok(apiResponse);
    }
}
