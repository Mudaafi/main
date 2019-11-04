package ui.gui;

import executor.task.Task;
import executor.task.TaskList;
import interpreter.Interpreter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import storage.StorageTask;
import storage.StorageWallet;
import ui.ReceiptTracker;
import ui.Wallet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends AnchorPane {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private ImageView headerBackground;
    @FXML
    private Label balanceChartDesc;
    @FXML
    private DonutChart balanceChart;
    @FXML
    private Label balanceFigure;
    @FXML
    private StackedBarChart<String, Double> breakdownChart;

    @FXML
    private TextField userInput;
    @FXML
    private VBox taskContainerLeft;
    @FXML
    private VBox taskContainerRight;

    private Boolean exitRequest;
    private StorageTask taskStore;
    private StorageWallet walletStore;
    private TaskList taskList;
    private Wallet wallet;
    private ArrayList<String> userInputHistory;
    private DisplayType displayType;
    private CommandLineDisplay cliController;
    private HomeWindow homeController;

    void initialize(String taskPath, String walletPath) {
        this.exitRequest = false;
        this.displayType = DisplayType.NONE;
        this.userInputHistory = new ArrayList<>();
        this.taskStore = new StorageTask(taskPath);
        this.walletStore = new StorageWallet(walletPath);
        this.taskList = this.taskStore.loadData();
        this.wallet = this.walletStore.loadData();

        this.fetchStoredImages();
        this.showHomeDisplay();

    }

    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        this.userInputHistory.add(input);
        this.exitRequest = Interpreter.interpret(this.taskList, this.wallet, input);
        if (this.displayType == DisplayType.HOME) {
            if (input.equals("cli")) {
                showCliDisplay();
            } else {
                this.homeController.updateBalanceChart(this.wallet);
                this.homeController.displayTasks(this.taskList);
            }
        } else if (this.displayType == DisplayType.CLI && input.equals("home")) {
            this.showHomeDisplay();
        }
        this.userInput.clear();
        if (this.exitRequest) {
            Platform.exit();
        }
    }



    /**
     * Fetches Images stored in application for display in slots for features yet to be developed.
     */
    @FXML
    private void fetchStoredImages() {
        Image headerBackgroundPic = new Image(this.getClass().getResourceAsStream("/images/headerBackground.png"));
        this.headerBackground.setImage(headerBackgroundPic);
    }

    private boolean containsXValue(String xValue, XYChart.Series<String, Double> series) {
        for (XYChart.Data<String, Double> data : series.getData()) {
            if (data.getXValue().equals(xValue)) {
                return true;
            }
        }
        return false;
    }

    private void showHomeDisplay() {
        try {
            FXMLLoader loaderHomeDisplay = new FXMLLoader(MainGui.class
                    .getResource("/view/HomeWindow.fxml"));
            AnchorPane homeDisplayRoot = loaderHomeDisplay.load();
            HomeWindow homeDisplayController = loaderHomeDisplay.<HomeWindow>getController();
            homeDisplayController.initialize(this.userInputHistory, this.taskList, this.wallet);
            this.contentPane.getChildren().add(homeDisplayRoot);
            this.displayType = DisplayType.HOME;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (this.contentPane.getChildren().size() > 1) {
            this.contentPane.getChildren().remove(0);
        }
    }

    private void showCliDisplay() {
        try {
            FXMLLoader loaderCliDisplay = new FXMLLoader(MainGui.class
                .getResource("/view/CommandLineDisplay.fxml"));
            AnchorPane cliDisplayRoot = loaderCliDisplay.load();
            CommandLineDisplay cliDisplayController = loaderCliDisplay.<CommandLineDisplay>getController();
            cliDisplayController.setStyle();
            this.contentPane.getChildren().add(cliDisplayRoot);
            this.displayType = DisplayType.CLI;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (this.contentPane.getChildren().size() > 1) {
            this.contentPane.getChildren().remove(0);
        }
    }

}
