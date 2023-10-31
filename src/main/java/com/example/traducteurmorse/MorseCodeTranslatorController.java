package com.example.traducteurmorse;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class MorseCodeTranslatorController {
    @FXML
    private TextField inputField;

    @FXML
    private Label outputLabel;

    @FXML
    private Circle led;

    private int dotDuration = 500; // Durée d'un point en ms
    private int dashDuration = 3 * dotDuration; // Durée d'un tiret en ms
    private int spaceDuration = 7 * dotDuration; // Durée de l'espace entre les mots en ms
    private int slashDuration = 3 * dotDuration; // Durée d'un slash en ms


    @FXML
    private void translateButtonAction() {
        String text = inputField.getText().toUpperCase();
        if (!text.matches("^[A-Za-z0-9\\s]+$")) {
            // Show an alert if the text contains special characters
            showAlert("Le texte ne doit contenir que des chiffres, des lettres majuscules et des lettres minuscules.");
        } else {
            String translatedText = translateToMorse(text);
            outputLabel.setText("Morse Code: " + translatedText);
            new Thread(() -> flashMorse(translatedText)).start();
        }
    }

    private void flashMorse(String morseCode) {
        for (char c : morseCode.toCharArray()) {
            if (c == '.') {
                // LED allumée pour un point
                led.setFill(Color.GREEN);
                try {
                    Thread.sleep(dotDuration); // Temps d'allumage pour un point (ajustez selon vos préférences)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                led.setFill(Color.BLACK); // Éteindre la LED
            } else if (c == '-') {
                // LED allumée pour un tiret
                led.setFill(Color.GREEN);
                try {
                    Thread.sleep(slashDuration); // Temps d'allumage pour un tiret (ajustez selon vos préférences)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                led.setFill(Color.BLACK); // Éteindre la LED
            } else if (c == ' ') {
                // Espace entre les caractères
                try {
                    Thread.sleep(spaceDuration); // Temps d'attente entre les caractères (ajustez selon vos préférences)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (c == '/') {
                // Espace entre les mots
                try {
                    Thread.sleep(slashDuration); // Temps d'attente entre les mots (ajustez selon vos préférences)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
            } else{
                int index = -1;
                for (int i = 0; i < alphabet.length; i++) {
                    if (alphabet[i].charAt(0) == c) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    morseText.append(morseCode[index]).append(" / ");
                }
            }
        }

        return morseText.toString();
    }

}