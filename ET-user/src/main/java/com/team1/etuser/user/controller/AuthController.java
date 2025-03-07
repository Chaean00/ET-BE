package com.team1.etuser.user.controller;

import com.team1.etuser.user.dto.AuthResponseDto;
import com.team1.etuser.user.dto.LoginRequestDto;
import com.team1.etuser.user.dto.SignUpRequestDto;
import com.team1.etuser.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().stream().findFirst().ifPresent(error -> {
                errorMessage.append(error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().stream().findFirst().ifPresent(error -> {
                errorMessage.append(error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}

