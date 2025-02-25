package kr.co.codenation.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.codenation.entity.User;
import kr.co.codenation.repository.UserRepository;
import kr.co.codenation.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        System.out.println("existingUser.getPassword(): " + existingUser.getPassword());
        System.out.println("user.getPassword(): " + user.getPassword());
    
        boolean isPasswordMatch = passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
        System.out.println("Password Matches: " + isPasswordMatch);
    
        if (!isPasswordMatch) {
            throw new RuntimeException("Invalid password");
        }
    
        CustomUserDetails userDetails = new CustomUserDetails(existingUser);
        return jwtTokenProvider.createToken(userDetails);
    }
}
