package loginator;
// import loginator.Action;
// import loginator.DB;
// import loginator.Student;
// import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// import java.sql.DriverManager;
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

        Button viewSignedInTodayButton = new Button("View Today's Signed-In Students");
        viewSignedInTodayButton.setOnAction(e -> {
            SignedInTodayPage signedInTodayPage = new SignedInTodayPage(window);
            window.setScene(new Scene(signedInTodayPage.getLayout(), 600, 400));
        });


        HBox topPart = new HBox();
        GridPane searchPerson = new GridPane();
        TextField searchInput = new TextField();
        Button searchButton = new Button("Search For Student (Name or SID)");
        searchPerson.add(searchInput, 0, 0);
        searchPerson.add(searchButton, 0, 1);
        topPart.getChildren().addAll(dashboardLabel, searchPerson, viewSignedInTodayButton);

        searchButton.setOnAction(e -> {
            loadGridPane(gp, action, searchInput.getText());
        });

        loadGridPane(gp, action, "");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gp);
        scrollPane.setFitToWidth(true); // Ensures the grid fits the ScrollPane's width
     
        Button backButton = new Button("Back to Main Page");
        backButton.setOnAction(e -> {
            MainPage mainPage = new MainPage(window);
            window.setScene(new Scene(mainPage.getLayout(), 400, 400));
        });

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(topPart, scrollPane, backButton);


        Scene scene = new Scene(vb, 600, 400);

        window.setScene(scene);
        window.show();
        
    }


    private static void loadGridPane(GridPane gp, ArrayList<Action> action, String input){
        gp.getChildren().clear();

        int mode = 0;
        int j = 0;

        if (input != null && !input.isEmpty()) {
            char firstChar = input.charAt(0);
            if (Character.isDigit(firstChar)) {
                mode = 1;
            } else if (Character.isLetter(firstChar)) {
                mode = 2;
            }
        } else {
            mode = 0;
            }

        for(int i = 0; i<action.size(); i++){
            Action action1 = action.get(i);

            if (mode == 0 || 
                    (mode == 1 && action1.getStudentId().contains(input)) ||
                        (mode == 2 && action1.getStudentName().contains(input)) ){
                            Label actIdBody = new Label("" + action1.getActionId());
                            Label stuIdBody = new Label("" + action1.getStudentId());
                            Label nameBody = new Label("" + action1.getStudentName()); 
                            Label actTypeBody = new Label("" + action1.getActionTypeId());
                            Label timeBody = new Label("" + action1.getActDateTime());
                
                            gp.add(actIdBody, 0, j+1);
                            gp.add(stuIdBody, 1, j+1);
                            gp.add(nameBody, 2, j+1);
                            gp.add(actTypeBody, 3, j+1);
                            gp.add(timeBody, 4, j+1);
                            j++;
                        }
            
        }
    }        
}


