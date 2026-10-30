package com.secureship.notes_api;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotesController {

    private final JdbcTemplate jdbc;

    public NotesController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/api/notes")
    @ResponseBody
    public Map<String, String> add(@RequestBody Map<String, String> request) {
        String owner = request.get("owner");
        String title = request.get("title");
        String content = request.get("content");
        
        jdbc.update("INSERT INTO notes(owner, title, content) VALUES (?, ?, ?)", owner, title, content);
        return Map.of("status", "success");
    }

    @GetMapping("/api/notes/search")
    @ResponseBody
    public List<Map<String, Object>> search(@RequestParam(required = false, defaultValue = "") String q) {
        // FIXED VULN 2: Parameterized query prevents SQL injection
        String sql = "SELECT id, owner, title, content FROM notes WHERE title LIKE ?";
        return jdbc.queryForList(sql, "%" + q + "%");
    }
}