package com.company.whatcapp.service;

import com.company.whatcapp.dto.ErrorCode;
import com.company.whatcapp.exception.GenericException;
import com.company.whatcapp.modal.User;
import com.company.whatcapp.repository.UserRepository;
import com.company.whatcapp.request.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }
    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new GenericException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND));
    }
    public User findUserProfile(String jwt){
        String email = tokenProvider.getEmailFromToken(jwt);
        if(email == null){
            throw new BadCredentialsException("Invalid Token");
        }
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new GenericException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND);
        }
            return user;
    }
    public User updateUser(Long userId, UpdateUserRequest updateUserRequest){
        User user = findUserById(userId);
        if (updateUserRequest.getFull_name() != null){
            user.setFull_name(updateUserRequest.getFull_name());
        }
        if (updateUserRequest.getProfile_picture() != null){
            user.setProfile_picture(updateUserRequest.getProfile_picture());
        }
        return userRepository.save(user);
    }
    public List<User> searchUser(String query){
        return userRepository.searchUser(query);
    }
}
