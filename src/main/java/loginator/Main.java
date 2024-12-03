package loginator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import javafx.scene.layout.VBox;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;

public class Main extends Application {
    private Stage window;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("BCALogInator 2.0");

        MainPage mainPage = new MainPage(window);
        window.setScene(new Scene(mainPage.getLayout(), 600, 600));
        window.show();
    }
}