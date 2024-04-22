package dev.Innocent.controller;

import dev.Innocent.Service.Impl.CustomUserDetailsImpl;
import dev.Innocent.config.JwtProvider;
import dev.Innocent.model.User;
import dev.Innocent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailsImpl customUserDetails;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          CustomUserDetailsImpl customUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetails = customUserDetails;
    }
    @PostMapping("/signup")
    public ResponseEntity<User> createUserHandler(@RequestBody User user) throws Exception {
        User isUserExist = userRepository.findByEmail(user.getEmail());

        if(isUserExist != null){
            throw new Exception("Email already exist with another account");
        }

        User createUser = new User();
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(createUser);

        // Generate Token
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
