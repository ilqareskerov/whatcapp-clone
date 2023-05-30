package com.company.whatcapp.controller;

import com.company.whatcapp.modal.User;
import com.company.whatcapp.repository.UserRepository;
import com.company.whatcapp.request.LoginRequest;
import com.company.whatcapp.response.AuthResponse;
import com.company.whatcapp.service.CustomUserService;
import com.company.whatcapp.service.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CustomUserService customUserService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, CustomUserService customUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.customUserService = customUserService;
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFull_name();
        User isUser = userRepository.findByEmail(email);
        if (isUser != null) {
            return ResponseEntity.ok(new AuthResponse("User Already Exist", false));
        }
        User createUser = new User();
        createUser.setEmail(email);
        createUser.setPassword(passwordEncoder.encode(password));
        createUser.setFull_name(fullName);
        userRepository.save(createUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUser.getEmail(), createUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=tokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwt, true);
        return ResponseEntity.ok(authResponse);
        }

            @PostMapping(value = "/login",produces = "application/json")
            public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest req) {
            String email = req.getEmail();
            String password = req.getPassword();
            Authentication authentication = authentication(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse(jwt, true);
            return ResponseEntity.ok(authResponse);
            }
        public Authentication authentication(String email, String password) {
            UserDetails userDetails = customUserService.loadUserByUsername(email);
            if (userDetails == null){
                throw new BadCredentialsException("User Not Found");
            }
            if(!passwordEncoder.matches(password, userDetails.getPassword())){
                throw new BadCredentialsException("Password is Incorrect");
            }
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
    }
