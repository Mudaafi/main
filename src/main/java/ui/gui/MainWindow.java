package ui.gui;

import executor.task.TaskList;
import interpreter.Interpreter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import storage.StorageTask;
import storage.StorageWallet;
import ui.Wallet;
import java.util.ArrayList;

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
    private TextField userInput;

    private Boolean exitRequest;
    private StorageTask taskStore;
    private StorageWallet walletStore;
    private TaskList taskList = new TaskList();
    private Wallet wallet = new Wallet();
    private ArrayList<String> userInputHistory;
    private Stage mainStage;
    private DisplayType displayType;
    private CommandLineDisplay cliController;
    private HomeWindow homeController;

    void initialize(Stage stage, String taskPath, String walletPath) {
        this.exitRequest = false;
        this.mainStage = stage;
        this.displayType = DisplayType.NONE;
        this.userInputHistory = new ArrayList<>();
        this.taskStore = new StorageTask(taskPath);
        this.walletStore = new StorageWallet(walletPath);
        this.taskList = this.taskStore.loadData();
        this.walletStore.loadData(this);

        this.fetchStoredImages();
        this.showHomeDisplay();
        this.displayToast("test");

    }

    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        this.userInputHistory.add(input);
        this.exitRequest = Interpreter.interpret(this, input);
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

    /**
     * Set the Graphical User Interface to the Home Display.
     */
    public void showHomeDisplay() {
        if (this.displayType == DisplayType.HOME) {
            return;
        }
        try {
            FXMLLoader loaderHomeDisplay = new FXMLLoader(MainGui.class
                    .getResource("/view/HomeWindow.fxml"));
            AnchorPane homeDisplayRoot = loaderHomeDisplay.load();
            this.homeController = loaderHomeDisplay.<HomeWindow>getController();
            this.homeController.initialize(this.userInputHistory, this.taskList, this.wallet);
            this.contentPane.getChildren().add(homeDisplayRoot);
            this.displayType = DisplayType.HOME;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (this.contentPane.getChildren().size() > 1) {
            this.contentPane.getChildren().remove(0);
        }
    }

    /**
     * Set the Graphical User Interface to the CLI Display.
     */
    public void showCliDisplay() {
        if (this.displayType == DisplayType.CLI) {
            return;
        }
        try {
            FXMLLoader loaderCliDisplay = new FXMLLoader(MainGui.class
                .getResource("/view/CommandLineDisplay.fxml"));
            AnchorPane cliDisplayRoot = loaderCliDisplay.load();
            this.cliController = loaderCliDisplay.<CommandLineDisplay>getController();
            this.cliController.setStyle();
            this.contentPane.getChildren().add(cliDisplayRoot);
            this.displayType = DisplayType.CLI;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (this.contentPane.getChildren().size() > 1) {
            this.contentPane.getChildren().remove(0);
        }
    }

    public void displayToast(String string) {
        Toast.makeText(this.mainStage, string);
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public TaskList getTaskList() {
        return this.taskList;
    }

    public void dukeSays(String string) {
        this.showCliDisplay();
        this.cliController.dukeSays(string);
    }

    public void printToDisplay(String string) {
        this.showCliDisplay();
        this.cliController.printToDisplay(string);
    }

    public void printSeparator() {
        this.showCliDisplay();
        this.cliController.printSeparator();
    }

    public void saveAllData() {
        this.taskStore.saveData(this.taskList);
        this.walletStore.saveData(this);
    }
}
