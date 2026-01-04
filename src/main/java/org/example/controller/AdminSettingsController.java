package org.example.controller;

import org.example.dto.AllAISettingsDTO;
import org.example.service.AISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/settings")
public class AdminSettingsController {

    @Autowired
    private AISettingsService aiSettingsService;

    @GetMapping
    public ResponseEntity<AllAISettingsDTO> getAllSettings() {
        try {
            AllAISettingsDTO settings = aiSettingsService.getAllSettings();
            return ResponseEntity.ok(settings);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody AllAISettingsDTO settings) {
        try {
            aiSettingsService.updateSettings(settings);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Settings updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update settings: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/test/{provider}")
    public ResponseEntity<?> testConnection(@PathVariable String provider) {
        try {
            boolean isConnected = aiSettingsService.testConnection(provider);
            Map<String, Object> response = new HashMap<>();
            response.put("provider", provider);
            response.put("connected", isConnected);
            response.put("message", isConnected ? "API key is configured" : "API key is missing");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to test connection: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}

