package com.shop.diamond.user.services;


import com.shop.diamond.authentication.forms.RegisterDto;
import com.shop.diamond.general.TokenServiceImpl;
import com.shop.diamond.user.entities.User;
import com.shop.diamond.user.entities.UserRole;
import com.shop.diamond.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public TokenServiceImpl tokenService;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        if (email == null) throw new NullPointerException("EMail must not be null.");
        if (email.isEmpty()) throw new NullPointerException("EMail must not be empty.");

        return userRepository.findFirstByEmail(email.toLowerCase().trim());
    }

    public void registerUser(RegisterDto registerDto){
        if(registerDto == null) throw new NullPointerException("registerDto must not be null");
        User user = new User(registerDto.firstName , registerDto.lastName , registerDto.email , registerDto.password , new HashSet<UserRole>(Collections.singletonList(UserRole.USER)));
        rehashPassword(registerDto.password,user);
    }

    public void rehashPassword(final String password, final User user) {
        this.updatePassword(password, user);
    }

    public void updatePassword(final String newPassword, final User user) {
        if (newPassword == null) throw new NullPointerException("NewPassword must not be null.");
        if (newPassword.isEmpty()) throw new IllegalArgumentException("NewPassword must not be empty.");
        if (user == null) throw new NullPointerException("User must not be null.");

        user.hashedPassword = passwordEncoder.encode(newPassword);
        saveUser(user);
    }

    public void saveUser(final User user){
        if(user == null) throw new NullPointerException("User must not be null");

        userRepository.save(user);
    }

    public void generateAndSaveNewValidationTokenForUser(final User user) {
        if (user == null) throw new NullPointerException("User must not be null.");
        String token = tokenService.createToken(user.getEmail());
        user.setValidationToken(token);
        saveUser(user);
    }

    public boolean doesEmailAlreadyExists(final String email) {
        if (email == null) throw new NullPointerException("Email must not be null.");
        if (email.isEmpty()) throw new NullPointerException("Email must not be empty.");

        return findUserByEmail(email).isPresent();
    }

}
