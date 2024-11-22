package loginator;

import javafx.geometry.Pos;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SignedInTodayPage {
    private VBox layout;

    public SignedInTodayPage(Stage window) {
        layout = new VBox(10);
        layout.setSpacing(20);
        layout.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Students Signed In Today");

        // Fetch the list of students who signed in today
        ArrayList<String[]> students = DB.loadSignedInStudentsToday();

        GridPane studentListGrid = new GridPane();
        studentListGrid.setAlignment(Pos.TOP_CENTER);
        studentListGrid.setHgap(20);
        studentListGrid.setVgap(10);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(studentListGrid);
        scrollPane.setFitToWidth(true); // Ensures the grid fits the ScrollPane's width

        // Add column headers
        Label headerId = new Label("Student ID");
        Label headerName = new Label("Name");
        Label headerDateTime = new Label("Sign-In Time");
        studentListGrid.add(headerId, 0, 0);
        studentListGrid.add(headerName, 1, 0);
        studentListGrid.add(headerDateTime, 2, 0);

        // put student data in grid
        for (int i = 0; i < students.size(); i++) {
            String[] studentData = students.get(i);

            Label idLabel = new Label(studentData[0]);
            Label nameLabel = new Label(studentData[1]);
            Label dateTimeLabel = new Label(studentData[2]); 

            studentListGrid.add(idLabel, 0, i + 1);
            studentListGrid.add(nameLabel, 1, i + 1);
            studentListGrid.add(dateTimeLabel, 2, i + 1);
        }

        Button backButton = new Button("Back to Admin Dashboard");
        backButton.setOnAction(e -> AdminDashboard.showAdminDashboard(window));

        layout.getChildren().addAll(titleLabel, scrollPane, backButton);
    }

    public VBox getLayout() {
        return layout;
    }
}