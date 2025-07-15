package com.sekolah.project_sekolah.controller;

import com.sekolah.project_sekolah.dto.LoginRequest;
import com.sekolah.project_sekolah.dto.LoginResponse;
import com.sekolah.project_sekolah.dto.RegisterRequest;
import com.sekolah.project_sekolah.model.User;
import com.sekolah.project_sekolah.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import com.sekolah.project_sekolah.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/request") // login
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (userService.checkPassword(request.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
                return ResponseEntity.ok(
                        new LoginResponse("success", user.getRole().name(), user.getName(), token, user.getEmail()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login gagal");
    }

    @PostMapping("/register") // register
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        String result = userService.register(request);
        if (result.equals("Registrasi berhasil")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/protected") // protected
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("Berhasil akses endpoint dilindungi dengan token JWT");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Object> profile = new HashMap<>();
            profile.put("name", user.getName());
            profile.put("email", user.getEmail());
            profile.put("role", user.getRole().name());
            return ResponseEntity.ok(profile);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User tidak ditemukan");
    }

}
