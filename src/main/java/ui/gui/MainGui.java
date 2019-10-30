package ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Duke;

import java.io.IOException;

public class MainGui extends Application {

    @Override
    public void start(Stage stage) {
        try {
            //String[] fakeArgs = {};
            //Duke.main(fakeArgs);
            FXMLLoader fxmlLoader = new FXMLLoader(MainGui.class
                    .getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
