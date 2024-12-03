package loginator;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AdminDashboard {

    public static void showAdminDashboard(Stage window) {
        ArrayList<Action> actions = DB.loadActions();
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(50);
        gp.setVgap(10);

        // Top title
        Label dashboardLabel = new Label("Admin Dashboard");
        dashboardLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #00509e;");

        // Search bar and button
        TextField searchInput = new TextField();
        searchInput.setPromptText("Enter Student Name or ID");
        searchInput.setStyle("-fx-pref-width: 300;");
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        VBox search = new VBox(10);
        search.getChildren().addAll(searchInput, searchButton);
        search.setAlignment(Pos.CENTER);

        // Button for today's signed-in students
        Button viewSignedInTodayButton = new Button("View Today's Signed-In Students");
        viewSignedInTodayButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #007bb5; -fx-text-fill: white;");



        // HBox for search and today's signed-in button
        HBox searchAndViewBox = new HBox(20);
        searchAndViewBox.setAlignment(Pos.CENTER);
        searchAndViewBox.getChildren().addAll(search, viewSignedInTodayButton);

        VBox topLayout = new VBox(20);
        topLayout.setAlignment(Pos.CENTER);
        topLayout.getChildren().addAll(dashboardLabel, searchAndViewBox);

        searchButton.setOnAction(e -> loadGridPane(gp, actions, searchInput.getText()));

        viewSignedInTodayButton.setOnAction(e -> {
            SignedInTodayPage signedInTodayPage = new SignedInTodayPage(window);
            window.setScene(new Scene(signedInTodayPage.getLayout(), 600, 400));
        });

        loadGridPane(gp, actions, "");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gp);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #e8f4fc;");

        // Export and Back buttons
        Button exportButton = new Button("Export to Excel");
        exportButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        exportButton.setOnAction(e -> exportGridPaneToExcel(DB.loadActions()));

        Button backButton = new Button("Back to Main Page");
        backButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            MainPage mainPage = new MainPage(window);
            window.setScene(new Scene(mainPage.getLayout(), 600, 600));
        });

        // Clear History button
        Button clearHistoryButton = new Button("Clear History");
        clearHistoryButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20; -fx-background-color: #FF5722; -fx-text-fill: white;");
        clearHistoryButton.setOnAction(e -> {
            DB.clearAllActions();
            loadGridPane(gp, new ArrayList<>(), ""); // Clear the grid
            System.out.println("History cleared!");
        });
        
        HBox bottomBox = new HBox(20);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(backButton, exportButton, clearHistoryButton);

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-padding: 30; -fx-background-color: #e8f4fc;"); // Match MainPage background
        mainLayout.getChildren().addAll(topLayout, scrollPane, bottomBox);

        Scene scene = new Scene(mainLayout, 800, 600);

        window.setScene(scene);
        window.show();
    }

    private static void loadGridPane(GridPane gp, ArrayList<Action> actions, String input) {
        gp.getChildren().clear();

        Label actionIDHeader = new Label("Action ID");
        Label studentIDHeader = new Label("Student ID");
        Label studentNameHeader = new Label("Student Name");
        Label actionTypeHeader = new Label("Action Type");
        Label timeOfActionHeader = new Label("Time Of Action");

        actionIDHeader.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        studentIDHeader.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        studentNameHeader.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        actionTypeHeader.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        timeOfActionHeader.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        gp.add(actionIDHeader, 0, 0);
        gp.add(studentIDHeader, 1, 0);
        gp.add(studentNameHeader, 2, 0);
        gp.add(actionTypeHeader, 3, 0);
        gp.add(timeOfActionHeader, 4, 0);

        int mode = input == null || input.isEmpty() ? 0 : (Character.isDigit(input.charAt(0)) ? 1 : 2);
        int j = 0;

        for (Action action : actions) {
            if (mode == 0 || 
                (mode == 1 && action.getStudentId().contains(input)) || 
                (mode == 2 && action.getStudentName().contains(input))) {
                gp.add(new Label(String.valueOf(action.getActionId())), 0, j + 1);
                gp.add(new Label(action.getStudentId()), 1, j + 1);
                gp.add(new Label(action.getStudentName()), 2, j + 1);
                gp.add(new Label(action.getActionTypeId()), 3, j + 1);
                gp.add(new Label(action.getActDateTime()), 4, j + 1);
                j++;
            }
        }
    }

    private static void exportGridPaneToExcel(ArrayList<Action> actions) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Admin Dashboard Data");

        String[] headers = {"Action ID", "Student ID", "Student Name", "Action Type", "Time Of Action"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        int rowIndex = 1;
        for (Action action : actions) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(action.getActionId());
            row.createCell(1).setCellValue(action.getStudentId());
            row.createCell(2).setCellValue(action.getStudentName());
            row.createCell(3).setCellValue(action.getActionTypeId());
            row.createCell(4).setCellValue(action.getActDateTime());
        }

        try (FileOutputStream fileOut = new FileOutputStream("AdminDashboardData.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
