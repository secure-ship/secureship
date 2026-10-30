package com.secureship.notes_api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JdbcTemplate jdbc;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(JdbcTemplate jdbc, JwtUtil jwtUtil) {
        this.jdbc = jdbc;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        String sql = "SELECT password FROM users WHERE username = ?";
        try {
            String dbPasswordHash = jdbc.queryForObject(sql, String.class, username);
            if (passwordEncoder.matches(password, dbPasswordHash)) {
                String token = jwtUtil.generateToken(username);
                return Map.of("token", token);
            }
        } catch (Exception e) {
            // User not found
        }
        throw new RuntimeException("Invalid credentials");
    }
}