package loginator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboard {
    private VBox layout;
    
    public AdminDashboard(Stage window) {
        layout = new VBox(10);
        layout.setSpacing(20);
        
        Label dashboardLabel = new Label("Admin Dashboard");
        Label activityLogLabel = new Label("Activity Log:");
        
        // For now, display a simple log (this should be replaced by real log data)
        Label activityLog = new Label("John Doe signed in at 08:00");
        
        Button backButton = new Button("Back to Main Page");
        backButton.setOnAction(e -> {
            MainPage mainPage = new MainPage(window);
            window.setScene(new Scene(mainPage.getLayout(), 400, 400));
        });
        
        layout.getChildren().addAll(dashboardLabel, activityLogLabel, activityLog, backButton);
    }
    
    public VBox getLayout() {
        return layout;
    }
}
