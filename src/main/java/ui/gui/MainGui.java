package ui.gui;

import executor.task.TaskList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Duke;
import storage.StorageTask;
import storage.StorageWallet;
import ui.Wallet;

import java.io.IOException;

public class MainGui extends Application {
    private String taskPath = "savedTask.txt";
    private String walletPath = "savedWallet.txt";

    public void initialize(String[] args) {
        Application.launch(MainGui.class, args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainGui.class
                    .getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            fxmlLoader.<MainWindow>getController().initialize(this.taskPath, this.walletPath);
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
