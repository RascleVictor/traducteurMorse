package com.example.traducteurmorse;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class MorseCodeTranslatorController {
    @FXML
    private TextField inputField;

    @FXML
    private Label outputLabel;

    @FXML
    private void translateButtonAction() {
        String text = inputField.getText().toUpperCase();
        String translatedText = translateToMorse(text);
        outputLabel.setText("Morse Code: " + translatedText);
    }

    private String translateToMorse(String text) {
        StringBuilder morseText = new StringBuilder();

        String[] alphabet = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };

        String[] morseCode = {
                ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."
        };

        for (char c : text.toCharArray()) {
            if (c == ' ') {
                morseText.append("   "); // 3 spaces for word separation
            } else {
                int index = -1;
                for (int i = 0; i < alphabet.length; i++) {
                    if (alphabet[i].charAt(0) == c) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    morseText.append(morseCode[index]).append(" ");
                }
            }
        }

        return morseText.toString();
    }
}