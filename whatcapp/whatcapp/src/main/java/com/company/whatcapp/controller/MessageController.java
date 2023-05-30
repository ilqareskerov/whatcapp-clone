package com.company.whatcapp.controller;

import com.company.whatcapp.modal.Message;
import com.company.whatcapp.modal.User;
import com.company.whatcapp.request.SendMessageRequest;
import com.company.whatcapp.response.ApiResponse;
import com.company.whatcapp.service.MessageService;
import com.company.whatcapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/messages/")
@RestController
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }
    @PostMapping("send")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest sendMessageRequest,@RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        sendMessageRequest.setUserId(user.getId());
        Message message = messageService.sendMessage(sendMessageRequest);
        return ResponseEntity.ok(message);
    }
    @GetMapping("chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessages(@PathVariable("chatId") Long chatId,@RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        List<Message> messages = messageService.getChatMessages(chatId,user);
        return ResponseEntity.ok(messages);
    }
    @DeleteMapping("{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(@PathVariable("messageId") Long messageId,@RequestHeader("Authorization") String token){
        User user = userService.findUserProfile(token);
        messageService.deleteMessage(messageId,user);
        return ResponseEntity.ok(new ApiResponse("Message deleted",true));
    }
}
