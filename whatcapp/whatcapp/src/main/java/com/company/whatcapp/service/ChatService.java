package com.company.whatcapp.service;

import com.company.whatcapp.exception.GenericException;
import com.company.whatcapp.modal.Chat;
import com.company.whatcapp.modal.User;
import com.company.whatcapp.repository.ChatRepository;
import com.company.whatcapp.request.GroupChatRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;

    public ChatService(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public Chat createChat(User reqUser, Long userId){
        User user = userService.findUserById(userId);
        Chat chat = chatRepository.findSingleChatByUserIds(user,reqUser);
        if(chat!=null)
            return chat;
        chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        return chatRepository.save(chat);
    }
    public Chat findChatById(Long chatId){
        Chat chat = chatRepository.findById(chatId).orElseThrow(()-> new GenericException("Chat not found"));
        return chat;
    }
    public List<Chat> findAllChatByUserId(Long userId){
        User user = userService.findUserById(userId);
        List<Chat> chats = chatRepository.findChatByUserId(userId);
        return chats;
    }
    public Chat createGroup(GroupChatRequest req , User reqUser){
        Chat group = new Chat();
        group.setCreatedBy(reqUser);
        group.setGroup(true);
        group.setChat_name(req.getChat_name());
        group.setChat_image(req.getChat_image());
        group.getAdmins().add(reqUser);
        for(Long userId : req.getUserIds()){
            User user = userService.findUserById(userId);
            group.getUsers().add(user);
        }
        return chatRepository.save(group);
    }
    public Chat addUserToGroup( Long userId, Long chatId,User reqUser) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new GenericException("Chat not found"));
        User user = userService.findUserById(userId);
        if (chat.getAdmins().contains(reqUser)) {
            chat.getUsers().add(user);
            return chatRepository.save(chat);
        }
        throw new GenericException("You are not admin of this group");
    }

    public Chat renameGroup(Long chatId, String newName, User reqUser){
        Chat chat = chatRepository.findById(chatId).orElseThrow(()-> new GenericException("Chat not found"));
        if(chat.getAdmins().contains(reqUser)){
            chat.setChat_name(newName);
            return chatRepository.save(chat);
        }
        throw new GenericException("You are not admin of this group");
    }
    public Chat removeUserFromGroup(Long chatId,Long userId, User reUser){
        Chat chat = chatRepository.findById(chatId).orElseThrow(()-> new GenericException("Chat not found"));
        User user = userService.findUserById(userId);
        if(chat.getAdmins().contains(reUser)){
            chat.getUsers().remove(user);
            return chatRepository.save(chat);
        } else if (chat.getUsers().contains(reUser)){
            if (user.getId()==reUser.getId()){
                throw new GenericException("You can't remove yourself");
            }
            throw new GenericException("You are not admin of this group");
        }
            throw new GenericException("You are not member of this group");
    }
    public void deleteChat(Long chatId, Long reqUserId){
        Chat chat = chatRepository.findById(chatId).orElseThrow(()-> new GenericException("Chat not found"));
        chatRepository.deleteById(chat.getId());
    }
}
