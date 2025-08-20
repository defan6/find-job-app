package my.ddos.user_ms.controller;

import lombok.RequiredArgsConstructor;
import my.ddos.user_ms.config.JwtUtils;
import my.ddos.user_ms.dto.JwtResponse;
import my.ddos.user_ms.dto.LoginRequest;
import my.ddos.user_ms.dto.RegisterRequest;
import my.ddos.user_ms.entity.User;
import my.ddos.user_ms.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {


    @Value("${jwt.expiration}")
    private Long expiration;

    private final JwtUtils jwtUtils;


    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(userService.authenticateAndGenerateToken(request.getUsername(), request.getPassword()));
    }


    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerAndGenerateToken(request));
    }


    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody String token){
        return ResponseEntity.ok(jwtUtils.validateToken(token));
    }
}
