package loginator;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPage {
    private VBox layout;

    public MainPage(Stage window) {
        layout = new VBox(30); // Adjust spacing for a cleaner look
        layout.setAlignment(Pos.CENTER); // Center-align all elements
        layout.setStyle("-fx-padding: 40; -fx-background-color: #f7f7f7;"); // Add padding and background color

        // Title label
        Label titleLabel = new Label("BCALogInator 2.0");
        titleLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Subtitle label
        Label welcomeLabel = new Label("Welcome! Please select an option below:");
        welcomeLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #555;");

        // ID input and buttons
        Label idScanLabel = new Label("Enter Your ID:");
        idScanLabel.setStyle("-fx-font-size: 14;");
        TextField idScanInput = new TextField();
        idScanInput.setPromptText("Enter or scan your ID");
        idScanInput.setStyle("-fx-pref-width: 300;");

        // Sign-in and Sign-out buttons
        Button signInButton = new Button("Sign In");
        signInButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button signOutButton = new Button("Sign Out");
        signOutButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #f44336; -fx-text-fill: white;");

        // HBox for sign-in and sign-out buttons
        HBox signInOutBox = new HBox(20);
        signInOutBox.setAlignment(Pos.CENTER);
        signInOutBox.getChildren().addAll(signInButton, signOutButton);

        // Admin Login and Kiosk Mode buttons
        Button adminLoginButton = new Button("Administrator Login");
        adminLoginButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #808080; -fx-text-fill: white;");
        Button kioskModeButton = new Button("Kiosk Mode");
        kioskModeButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #808080; -fx-text-fill: white;");

        // HBox for admin and kiosk mode buttons
        HBox adminKioskBox = new HBox(20);
        adminKioskBox.setAlignment(Pos.CENTER);
        adminKioskBox.getChildren().addAll(adminLoginButton, kioskModeButton);

        // Button actions
        adminLoginButton.setOnAction(e -> {
            AdminLogin adminLogin = new AdminLogin(window);
            window.setScene(new Scene(adminLogin.getLayout(), 400, 400));
        });

        kioskModeButton.setOnAction(e -> new KioskMode(window));

        signInButton.setOnAction(e -> {
            Student s = DB.loadStudent(idScanInput.getText());
            if (s != null) {
                DB.recordAction(idScanInput.getText(), "SIGNED_IN");
                showTemporaryNotification("Signed In Successfully", "Welcome, " + s.getStudentFirstName() + "!", 10);

            } else {
                showTemporaryNotification("Error", "Invalid ID. Please try again.", 10);
            }
        });

        signOutButton.setOnAction(e -> {
            Student s = DB.loadStudent(idScanInput.getText());
            if (s != null) {
                DB.recordAction(idScanInput.getText(), "SIGNED_OUT");
                showTemporaryNotification("Signed Out Successfully", "Goodbye, " + s.getStudentFirstName() + "!", 10);
            } else {
                showTemporaryNotification("Error", "Invalid ID. Please try again.", 10);
            }
        });

        // Adding all elements to the layout
        layout.getChildren().addAll(titleLabel, welcomeLabel, idScanLabel, idScanInput, signInOutBox, adminKioskBox);
    }

    public VBox getLayout() {
        return layout;
    }

    public void showTemporaryNotification(String title, String message, int durationInSeconds) {
        Label notificationLabel = new Label(title + ": " + message);
        notificationLabel.setStyle(
                "-fx-font-size: 16; -fx-text-fill: black; -fx-background-color: pink; -fx-padding: 10; -fx-max-width: 400px; -fx-wrap-text: true;");
        notificationLabel.setAlignment(Pos.CENTER);

        layout.getChildren().add(notificationLabel);

        PauseTransition delay = new PauseTransition(Duration.seconds(durationInSeconds));
        delay.setOnFinished(event -> layout.getChildren().remove(notificationLabel));
        delay.play();
    }
}
