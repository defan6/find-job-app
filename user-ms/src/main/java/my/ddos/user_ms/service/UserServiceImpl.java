package my.ddos.user_ms.service;

import lombok.RequiredArgsConstructor;
import my.ddos.user_ms.config.JwtUtils;
import my.ddos.user_ms.dto.JwtResponse;
import my.ddos.user_ms.dto.LoginRequest;
import my.ddos.user_ms.dto.RegisterRequest;
import my.ddos.user_ms.entity.User;
import my.ddos.user_ms.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    private final JwtUtils jwtUtils;


    @Override
    public JwtResponse authenticateAndGenerateToken(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User with username " + username + " not found."));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = this.generateToken(username);


        return new JwtResponse(token, username, jwtUtils.getExpirationTime());
    }


    @Override
    public JwtResponse registerAndGenerateToken(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("User with username " + registerRequest.getUsername() + " already exists");
        }
        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        User saveduser = userRepository.save(user);
        String username = saveduser.getUsername();
        String token = this.generateToken(username);
        return new JwtResponse(token, username, jwtUtils.getExpirationTime());
    }

    private String generateToken(String username){
        return jwtUtils.generateToken(username);
    }
}
