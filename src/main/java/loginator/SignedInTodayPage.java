package loginator;

import javafx.geometry.Pos;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
// import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SignedInTodayPage {
    private VBox layout;

    public SignedInTodayPage(Stage window) {
        layout = new VBox(20); // Increased spacing for a cleaner look
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-padding: 30; -fx-background-color: #e8f4fc;"); // Add padding and background color

        // Title label
        Label titleLabel = new Label("Students Signed In Today");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #00509e;");

        // Fetch signed-in students
        ArrayList<String[]> students = DB.loadSignedInStudentsToday();

        // GridPane for displaying the data
        GridPane studentListGrid = new GridPane();
        studentListGrid.setAlignment(Pos.TOP_CENTER);
        studentListGrid.setHgap(50);
        studentListGrid.setVgap(10);

        // Headers
        Label headerId = new Label("Student ID");
        headerId.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Label headerName = new Label("Name");
        headerName.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Label headerDateTime = new Label("Sign-In Time");
        headerDateTime.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        studentListGrid.add(headerId, 0, 0);
        studentListGrid.add(headerName, 1, 0);
        studentListGrid.add(headerDateTime, 2, 0);

        // Populate rows with student data
        for (int i = 0; i < students.size(); i++) {
            String[] studentData = students.get(i);

            Label idLabel = new Label(studentData[0]);
            idLabel.setStyle("-fx-font-size: 12;");
            Label nameLabel = new Label(studentData[1]);
            nameLabel.setStyle("-fx-font-size: 12;");
            Label dateTimeLabel = new Label(studentData[2]);
            dateTimeLabel.setStyle("-fx-font-size: 12;");

            studentListGrid.add(idLabel, 0, i + 1);
            studentListGrid.add(nameLabel, 1, i + 1);
            studentListGrid.add(dateTimeLabel, 2, i + 1);
        }

        // ScrollPane for the grid
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(studentListGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #e8f4fc;");

        // Back button
        Button backButton = new Button("Back to Admin Dashboard");
        backButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #007bb5; -fx-text-fill: white;");
        backButton.setOnAction(e -> AdminDashboard.showAdminDashboard(window));

        // Layout assembly
        layout.getChildren().addAll(titleLabel, scrollPane, backButton);
    }

    public VBox getLayout() {
        return layout;
    }
}
