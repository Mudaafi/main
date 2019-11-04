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
            Stage stageTwo = new Stage();
            FXMLLoader loaderCliDisplay = new FXMLLoader(MainGui.class
                    .getResource("/view/CommandLineDisplay.fxml"));
            AnchorPane cliDisplayRoot = loaderCliDisplay.load();
            CommandLineDisplay cliDisplayController = loaderCliDisplay.<CommandLineDisplay>getController();
            cliDisplayController.setStyle();
            Scene sceneCliDisplay = new Scene(cliDisplayRoot);
            stageTwo.setScene(sceneCliDisplay);
            stageTwo.setTitle("Command Line Display");
            stageTwo.show();

            FXMLLoader loaderMain = new FXMLLoader(MainGui.class
                    .getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            loaderMain.<MainWindow>getController().initialize(this.taskPath, this.walletPath, cliDisplayController);
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setTitle("Duke$$$");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
