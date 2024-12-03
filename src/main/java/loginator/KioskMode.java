package loginator;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class KioskMode {
    private VBox layout;

    public KioskMode(Stage window) {
        layout = new VBox(20); 
        layout.setAlignment(Pos.CENTER); 
        layout.setStyle("-fx-background-color: #000; -fx-padding: 50;"); 

        Label titleLabel = new Label("Kiosk Mode");
        titleLabel.setStyle("-fx-font-size: 36; -fx-text-fill: white; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        Label instructionLabel = new Label("Scan Your ID Below:");
        instructionLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white;");
        instructionLabel.setAlignment(Pos.CENTER);

        TextField idInput = new TextField();
        idInput.setPromptText("Enter ID or Scan...");
        idInput.setStyle("-fx-font-size: 16; -fx-max-width: 300px;"); // Limit the width of the input box
        idInput.setAlignment(Pos.CENTER);

        Button signInButton = new Button("Sign In");
        signInButton.setStyle("-fx-font-size: 18; -fx-padding: 10 20;");

        Button signOutButton = new Button("Sign Out");
        signOutButton.setStyle("-fx-font-size: 18; -fx-padding: 10 20;");

        signInButton.setOnAction(e -> {
            Student s = DB.loadStudent(idInput.getText());
            if (s != null) {
                DB.recordAction(idInput.getText(), "SIGNED_IN");
                idInput.clear();
                showTemporaryNotification("Signed In Successfully", "Welcome, " + s.getStudentFirstName() + "!", 10);
            } else {
                showTemporaryNotification("Error", "Invalid ID. Please try again.", 10);
            }
        });

        signOutButton.setOnAction(e -> {
            Student s = DB.loadStudent(idInput.getText());
            if (s != null) {
                DB.recordAction(idInput.getText(), "SIGNED_OUT");
                idInput.clear();
                showTemporaryNotification("Signed Out Successfully", "Goodbye, " + s.getStudentFirstName() + "!", 10);
            } else {
                showTemporaryNotification("Error", "Invalid ID. Please try again.", 10);
            }
        });

        layout.getChildren().addAll(titleLabel, instructionLabel, idInput, signInButton, signOutButton);

        Scene kioskScene = new Scene(layout, 800, 600);
        kioskScene.setOnKeyPressed((KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.Q) {
                MainPage mainPage = new MainPage(window);
                window.setScene(new Scene(mainPage.getLayout(), 600, 600));
                window.setFullScreen(false); // Exit fullscreen
            }
        });

        window.setScene(kioskScene);
        window.setFullScreen(true); 
    }

    public VBox getLayout() {
        return layout;
    }

    public void showTemporaryNotification(String title, String message, int durationInSeconds) {
        Label notificationLabel = new Label(title + ": " + message);
        notificationLabel.setStyle(
                "-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: green; -fx-padding: 10; -fx-max-width: 400px; -fx-wrap-text: true;");
        notificationLabel.setAlignment(Pos.CENTER);

        layout.getChildren().add(notificationLabel);

        PauseTransition delay = new PauseTransition(Duration.seconds(durationInSeconds));
        delay.setOnFinished(event -> layout.getChildren().remove(notificationLabel));
        delay.play();
    }
}
