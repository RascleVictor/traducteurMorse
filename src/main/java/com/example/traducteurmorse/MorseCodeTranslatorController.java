package com.example.traducteurmorse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


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
    private Timeline blinkingTimeline;

    @FXML
    private void initialize() {
        blinkingTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> led.setFill(javafx.scene.paint.Color.RED))
        );
        blinkingTimeline.setOnFinished(event -> led.setFill(javafx.scene.paint.Color.RED)); // Arrêter la LED à la fin de l'animation
    }

    @FXML
    private void translateButtonAction() {
        String text = inputField.getText().toUpperCase();
        if (!text.matches("^[A-Z0-9]+$")) {
            // Afficher une alerte si le texte contient des caractères spéciaux
            showAlert("Le texte ne doit contenir que des chiffres, des lettres majuscules et des lettres minuscules.");
        } else {
            String translatedText = translateToMorse(text);
            outputLabel.setText("Morse Code: " + translatedText);

            // Commencer l'animation de la LED lors de la traduction
            playMorseAnimation(translatedText);
        }
    }

    @FXML
    private void playMorseAnimation(String morseText) {
        blinkingTimeline.stop();
        blinkingTimeline.getKeyFrames().clear();

        for (char c : morseText.toCharArray()) {
            if (c == '.') {
                addBlinkKeyFrames(dotDuration);
            } else if (c == '-') {
                addBlinkKeyFrames(dashDuration);
            } else if (c == '/') {
                addBlinkKeyFrames(slashDuration);
            } else if (c == ' ') {
                addSpaceKeyFrames(spaceDuration);
            }
        }

        blinkingTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkingTimeline.play();
    }

    private void addBlinkKeyFrames(int duration) {
        blinkingTimeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, e -> led.setFill(javafx.scene.paint.Color.RED)),
                new KeyFrame(Duration.millis(duration / 2), e -> led.setFill(javafx.scene.paint.Color.GREEN)),
                new KeyFrame(Duration.millis(duration), e -> led.setFill(javafx.scene.paint.Color.RED))
        );
    }

    private void addSpaceKeyFrames(int duration) {
        blinkingTimeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, e -> led.setFill(javafx.scene.paint.Color.RED)),
                new KeyFrame(Duration.millis(duration), e -> led.setFill(javafx.scene.paint.Color.RED))
        );
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
            } else {
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