package com.cookBook.cookbook_api.Services;

import com.cookBook.cookbook_api.DTOS.SignInRequestDTO;
import com.cookBook.cookbook_api.DTOS.SignUpRequestDTO;
import com.cookBook.cookbook_api.Exceptions.InvalidCredentialsException;
import com.cookBook.cookbook_api.Exceptions.UserAlreadyExistsException;
import com.cookBook.cookbook_api.Models.User;
import com.cookBook.cookbook_api.Repositories.UserRepository;
import com.cookBook.cookbook_api.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    // Sign-in method for JWT generation
    public String signIn(SignInRequestDTO dto) throws Exception {
        // Find user by username
        User user = userRepository.findByUserName(dto.getUserName());
        if (user == null) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        // Check password match
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Generate JWT token
        return jwtUtil.generateToken(user.getUserName(), user.getEmail(), user.getRole().name());
    }

    public void signUp(SignUpRequestDTO dto) throws Exception {
        if (userRepository.findByUserName(dto.getUserName()) != null) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUserName(dto.getUserName());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

    }
}