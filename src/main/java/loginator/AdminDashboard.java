package loginator;
import loginator.Action;
import loginator.DB;
import loginator.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.DriverManager;
import java.util.ArrayList;


public class AdminDashboard {
  
    
    public static void showAdminDashboard(Stage window) {

        
        ArrayList<Action> action = DB.loadActions();
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(50);
        gp.setVgap(10);

        Label dashboardLabel = new Label("Admin Dashboard");
        Label actionIDLabel = new Label("Action ID");
        Label studentIDLabel = new Label("Student ID");
        Label studentNameLabel = new Label("Student Name");
        Label actionTypeLabel = new Label("Action Type");
        Label timeOfAction = new Label("Time Of Action");
        gp.add(actionIDLabel, 0, 0);
        gp.add(studentIDLabel, 1, 0);
        gp.add(studentNameLabel, 2, 0);
        gp.add(actionTypeLabel, 3, 0);
        gp.add(timeOfAction, 4, 0);

        for(int i = 0; i<action.size(); i++){
            Action action1 = action.get(i);
            Label actIdBody = new Label("" + action1.getActionId());
            Label stuIdBody = new Label("" + action1.getStudentId());
            Label nameBody = new Label("" + action1.getStudentName()); 
            Label actTypeBody = new Label("" + action1.getActionTypeId());
            Label timeBody = new Label("" + action1.getActDateTime());

            gp.add(actIdBody, 0, i+1);
            gp.add(stuIdBody, 1, i+1);
            gp.add(nameBody, 2, i+1);
            gp.add(actTypeBody, 3, i+1);
            gp.add(timeBody, 4, i+1);
        }
     
        Button backButton = new Button("Back to Main Page");
        backButton.setOnAction(e -> {
            MainPage mainPage = new MainPage(window);
            window.setScene(new Scene(mainPage.getLayout(), 400, 400));
        });

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(dashboardLabel, gp, backButton);


        Scene scene = new Scene(vb);

        window.setScene(scene);
        window.show();
        
    }
    
}
