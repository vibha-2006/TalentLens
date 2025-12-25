package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllAISettingsDTO {
    private AISettingsDTO openai;
    private AISettingsDTO gemini;
    private AISettingsDTO groq;
}

