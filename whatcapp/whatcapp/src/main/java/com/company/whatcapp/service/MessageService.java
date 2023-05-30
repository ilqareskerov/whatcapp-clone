package com.company.whatcapp.service;

import com.company.whatcapp.exception.GenericException;
import com.company.whatcapp.modal.Chat;
import com.company.whatcapp.modal.Message;
import com.company.whatcapp.modal.User;
import com.company.whatcapp.repository.ChatRepository;
import com.company.whatcapp.repository.MessageRepository;
import com.company.whatcapp.request.SendMessageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private ChatService chatService;
    private final ChatRepository chatRepository;

    public MessageService(MessageRepository messageRepository, UserService userService, ChatService chatService,
                          ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
        this.chatRepository = chatRepository;
    }
    public Message sendMessage(SendMessageRequest sendMessageRequest){
        User user=userService.findUserById(sendMessageRequest.getUserId());
        Chat chat =chatService.findChatById(sendMessageRequest.getChatId());
        Message message = new Message();
        message.setChat(chat);
        message.setContent(sendMessageRequest.getContent());
        message.setSender(user);
        message.setTimestamp(LocalDateTime.now());
        chat.getMessages().add(message);
        chatRepository.save(chat);
        return messageRepository.save(message);
    }
    public List<Message> getChatMessages(Long chatId,User reqUser){
        Chat chat = chatService.findChatById(chatId);
        if(!chat.getUsers().contains(reqUser)){
            throw new GenericException("You are not a member of this chat");
        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return messages;
    }
    public Message findMessageById(Long messageId){
        Message opt =messageRepository.findById(messageId).orElseThrow(()->new GenericException("Message not found"));
        return opt;
    }
    public void deleteMessage(Long messageId,User req){
         Message message = findMessageById(messageId);
          if(message.getSender().getId()!=req.getId()){
                throw new GenericException("You are not the sender of this message");
          }
          messageRepository.delete(message);
    }

}
