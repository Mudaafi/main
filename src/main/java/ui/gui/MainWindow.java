package ui.gui;

import executor.task.Task;
import executor.task.TaskList;
import interpreter.Interpreter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Duke;
import storage.Storage;
import ui.Receipt;
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
    private DonutChart balanceChart;
    @FXML
    private StackedBarChart<String, Number> breakdownChart;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField userInput;
    @FXML
    private VBox taskContainerLeft;
    @FXML
    private VBox taskContainerRight;

    private Boolean exitRequest;
    private Storage storage;
    private TaskList taskList;
    private Wallet wallet;
    private ArrayList<String> userInputHistory;
    private ObservableList<PieChart.Data> pieChartData;

    @FXML
    public void initialize() {
        this.exitRequest = false;
        this.userInputHistory = new ArrayList<>();
        this.taskList = new TaskList();
        this.wallet = new Wallet();
        this.displayTasks(taskList);
        //test
        this.wallet.setBalance(500.0);
        Receipt receipt = new Receipt(100.0);
        this.wallet.addReceipt(receipt);
        //end test
        pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Expenses",
                                this.wallet.getTotalExpenses()),
                        new PieChart.Data("Balance",
                                this.wallet.getBalance() - this.wallet.getTotalExpenses()));
        this.displayBalancePieChart();
        this.fetchStoredImages();

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("1800");
        series1.getData().add(new XYChart.Data<>("Food", 100));
        series1.getData().add(new XYChart.Data<>("Water", 50));
        series1.getData().add(new XYChart.Data<>("Transport", 100));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("1900");
        series2.getData().add(new XYChart.Data<>("Food", 100));
        series2.getData().add(new XYChart.Data<>("Water", 100));
        series2.getData().add(new XYChart.Data<>("Transport", 50));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.getData().add(new XYChart.Data<>("Food", 100));

        this.breakdownChart.getData().add(series1);
        this.breakdownChart.getData().add(series2);
        this.breakdownChart.getData().add(series3);
        String css = this.getClass().getResource("/css/BreakdownChart.css").toExternalForm();
        this.breakdownChart.getStylesheets().add(css);


    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        this.userInputHistory.add(input);
        this.exitRequest = Interpreter.interpret(this.taskList, this.wallet, input);
        this.updateBalanceChart(this.wallet);
        this.displayTasks(this.taskList);
        userInput.clear();
    }

    /**
     * Fetches Images stored in application for display in slots for features yet to be developed.
     */
    @FXML
    private void fetchStoredImages() {
        Image headerBackgroundPic = new Image(this.getClass().getResourceAsStream("/images/headerBackground.png"));
        this.headerBackground.setImage(headerBackgroundPic);

        //Image balanceChartPic = new Image(this.getClass().getResourceAsStream("/images/balanceChartExample.png"));
        //this.balanceChartPic.setImage(balanceChartPic);

        // Image breakdownChartPic = new Image(this.getClass().getResourceAsStream("/images/breakdownExample.png"));
        // this.breakdownChartPic.setImage(breakdownChartPic);
    }

    private void updateBalanceChart(Wallet wallet) {
        this.pieChartData.get(0).setPieValue(wallet.getTotalExpenses());
        this.pieChartData.get(1).setPieValue(wallet.getBalance() - wallet.getTotalExpenses());
    }

    private void displayBalancePieChart() {
        this.balanceChart.setData(this.pieChartData);
        this.balanceChart.setLegendVisible(false);
        this.balanceChart.setLabelsVisible(false);
        this.balanceChart.setStartAngle(90.0);
        String css = this.getClass().getResource("/css/PieChart.css").toExternalForm();
        this.balanceChart.getStylesheets().add(css);
    }

    private void displayTasks(TaskList taskList) {
        this.taskContainerRight.getChildren().clear();
        this.taskContainerLeft.getChildren().clear();
        for (Task task : taskList.getList()) {
            int leftListLength = this.taskContainerLeft.getChildren().size();
            int rightListLength = this.taskContainerRight.getChildren().size();
            if (leftListLength > rightListLength) {
                this.taskContainerRight.getChildren().add(TaskBox.getNewTaskBox(task));
            } else {
                this.taskContainerLeft.getChildren().add(TaskBox.getNewTaskBox(task));
            }
        }
    }
}
