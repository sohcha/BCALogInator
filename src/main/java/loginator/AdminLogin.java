package loginator;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// import javafx.scene.Scene;

public class AdminLogin {
    private VBox layout;
    
    public AdminLogin(Stage window) {
        layout = new VBox(10);
        layout.setSpacing(20);
        
        Label usernameLabel = new Label("Enter Admin Username:");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Enter Admin Password:");
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Continue");
        
        loginButton.setOnAction(e -> {
            if (validateAdmin(usernameInput.getText(), passwordInput.getText())) {
                AdminDashboard.showAdminDashboard(window);
            } else {
                Label errorLabel = new Label("Sorry, the credentials are incorrect. Please try again.");
                layout.getChildren().add(errorLabel);
            }
        });
        
        layout.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton);
    }
    
    public VBox getLayout() {
        return layout;
    }

    private boolean validateAdmin(String username, String password) {
        return username.equals("admin") && password.equals("password");
    }
}
