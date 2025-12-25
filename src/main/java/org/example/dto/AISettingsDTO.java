package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AISettingsDTO {
    private String provider;
    private String apiKey;
    private String model;
    private String apiUrl;
    private boolean enabled;
}
