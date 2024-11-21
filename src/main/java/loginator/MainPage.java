package loginator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPage {
    private VBox layout;
    
    public MainPage(Stage window) {
        layout = new VBox(10);
        layout.setSpacing(20);
        layout.setAlignment(Pos.TOP_CENTER);
        
        Label titleLabel = new Label("BCALogInator 2.0");
        Label welcomeLabel = new Label("Scan Your ID Below!");
        Button adminLoginButton = new Button("Administrator Login");
        Label idScanLabel = new Label("SID:");
        TextField idScanInput = new TextField();
        Button signInButton = new Button("Sign In");
        Button signOutButton = new Button("Sign Out");
        adminLoginButton.setOnAction(e -> {
            AdminLogin adminLogin = new AdminLogin(window);
            window.setScene(new Scene(adminLogin.getLayout(), 300, 400));
        });

        signInButton.setOnAction(e -> {
            Student s = DB.loadStudent(idScanInput.getText());
            if(s != null) {
                DB.recordAction(idScanInput.getText(),"SIGNED_IN");
                SignedIn signedIn = new SignedIn(window, s);
                window.setScene(new Scene(signedIn.getLayout(), 300, 200));
            }
            else{
                Label errorLabel = new Label("Sorry, the credentials are incorrect. Please try again.");
                layout.getChildren().add(errorLabel);
            }
            
        });

        signOutButton.setOnAction(e -> {
            Student s = DB.loadStudent(idScanInput.getText());
            if(s != null) {
                DB.recordAction(idScanInput.getText(),"SIGNED_OUT");
                SignedOut signedOut = new SignedOut(window, s);
                window.setScene(new Scene(signedOut.getLayout(), 300, 200));
            }
            else{
                Label errorLabel = new Label("Sorry, the credentials are incorrect. Please try again.");
                layout.getChildren().add(errorLabel);
            }
            
        });

        // Add more logic for handling the scanning of student ID (SID)
        
        layout.getChildren().addAll(titleLabel, welcomeLabel, adminLoginButton, idScanLabel, idScanInput, signInButton, signOutButton);
    }
    
    public VBox getLayout() {
        return layout;
    }
}