package my.ddos.user_ms.service;

import my.ddos.user_ms.dto.JwtResponse;
import my.ddos.user_ms.dto.LoginRequest;
import my.ddos.user_ms.dto.RegisterRequest;
import my.ddos.user_ms.entity.User;

import java.util.Optional;

public interface UserService {
    JwtResponse authenticateAndGenerateToken(String username, String password);

    JwtResponse registerAndGenerateToken(RegisterRequest registerRequest);
}
