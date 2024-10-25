package loginator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignedOut {
    private VBox layout;
    
    public SignedOut(Stage window, Student s) {
        layout = new VBox(10);
        layout.setSpacing(20);
        layout.setAlignment(Pos.TOP_CENTER);
        
        Label label = new Label("Successfuly Signed Out!");
        
        Button backButton = new Button("Back to Main Page");
        backButton.setOnAction(e -> {
            MainPage mainPage = new MainPage(window);
            window.setScene(new Scene(mainPage.getLayout(), 400, 400));
        });
        
        layout.getChildren().addAll(label, backButton);
    }
    
    public VBox getLayout() {
        return layout;
    }
}
