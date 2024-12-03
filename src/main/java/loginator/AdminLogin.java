package loginator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminLogin {
    private VBox layout;

    public AdminLogin(Stage window) {
        layout = new VBox(20); // Adjust spacing for a cleaner look
        layout.setAlignment(Pos.CENTER); // Center-align all elements
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;"); // Add padding and background color

        // Title label
        Label titleLabel = new Label("Admin Login");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        // Username input
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 14;");
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Enter your username");
        usernameInput.setStyle("-fx-pref-width: 300;");

        // Password input
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 14;");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");
        passwordInput.setStyle("-fx-pref-width: 300;");

        // Login and cancel buttons
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #f44336; -fx-text-fill: white;");

        // Button actions
        loginButton.setOnAction(e -> {
            if (validateAdmin(usernameInput.getText(), passwordInput.getText())) {
                AdminDashboard.showAdminDashboard(window);
            } else {
                Label errorLabel = new Label("Incorrect credentials. Please try again.");
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12;");
                if (!layout.getChildren().contains(errorLabel)) {
                    layout.getChildren().add(errorLabel);
                }
            }
        });

        cancelButton.setOnAction(e -> {
            MainPage mainPage = new MainPage(window);
            window.setScene(new Scene(mainPage.getLayout(), 600, 600));
        });

        // Horizontal box for buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, cancelButton);

        // Adding all elements to the layout
        layout.getChildren().addAll(titleLabel, usernameLabel, usernameInput, passwordLabel, passwordInput, buttonBox);
    }

    public VBox getLayout() {
        return layout;
    }

    private boolean validateAdmin(String username, String password) {
        return username.equals("admin") && password.equals("password");
    }
}
