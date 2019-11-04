package ui.gui;

import executor.task.Task;
import executor.task.TaskList;
import interpreter.Interpreter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField userInput;
    @FXML
    private VBox taskContainerLeft;
    @FXML
    private VBox taskContainerRight;

    private CommandLineDisplay cliDisplayController;
    private Boolean exitRequest;
    private StorageTask taskStore;
    private StorageWallet walletStore;
    private TaskList taskList;
    private Wallet wallet;
    private ArrayList<String> userInputHistory;
    private ObservableList<PieChart.Data> pieChartData;

    void initialize(String taskPath, String walletPath, CommandLineDisplay cliDisplayController) {
        this.exitRequest = false;
        this.userInputHistory = new ArrayList<>();
        this.taskStore = new StorageTask(taskPath);
        this.walletStore = new StorageWallet(walletPath);
        this.taskList = this.taskStore.loadData();
        this.wallet = this.walletStore.loadData();
        this.cliDisplayController = cliDisplayController;

        this.displayTasks(taskList);
        this.extractPieChartData();
        this.displayBalanceChart();
        this.fetchStoredImages();
        this.displayBreakdownChart();

    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        this.userInputHistory.add(input);
        this.cliDisplayController.display(input);
        this.exitRequest = Interpreter.interpret(this.taskList, this.wallet, input);
        this.updateBalanceChart(this.wallet);
        this.displayTasks(this.taskList);
        this.userInput.clear();
        this.taskStore.saveData(this.taskList);
        this.walletStore.saveData(this.wallet);
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

    private void extractPieChartData() {
        if (this.wallet.getBalance() == 0) {
            this.wallet.setBalance(0.1);
        }
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Expenses",
                        this.wallet.getTotalExpenses()),
                new PieChart.Data("Balance",
                        this.wallet.getBalance() - this.wallet.getTotalExpenses()));
    }

    private void updateBalanceChart(Wallet wallet) {
        this.pieChartData.get(0).setPieValue(wallet.getTotalExpenses());
        this.pieChartData.get(1).setPieValue(wallet.getBalance() - wallet.getTotalExpenses());

        DecimalFormat decimalFormat = new DecimalFormat("$#0");
        this.balanceFigure.setText(decimalFormat.format(this.wallet.getBalance()));
    }

    private void displayBalanceChart() {
        this.balanceChart.setData(this.pieChartData);
        this.balanceChart.setLegendVisible(false);
        this.balanceChart.setLabelsVisible(false);
        this.balanceChart.setStartAngle(90.0);
        String css = this.getClass().getResource("/css/PieChart.css").toExternalForm();
        this.balanceChart.getStylesheets().add(css);

        DecimalFormat decimalFormat = new DecimalFormat("$#0");
        this.balanceFigure.setText(decimalFormat.format(this.wallet.getBalance()));

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

    private void displayBreakdownChart() {
        XYChart.Series<String, Double> expenditureSeries = new XYChart.Series<>();
        expenditureSeries.setName("Expenditure");
        XYChart.Series<String, Double> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        updateBreakdownData(expenditureSeries, incomeSeries);

        XYChart.Series<String, Double> backdrop = new XYChart.Series<>();
        backdrop.setName("Backdrop");
        Double backdropValue = this.getBackdropValue(this.wallet);
        HashMap<String, Double> backdropData = this.getBackdropData(backdropValue, expenditureSeries, incomeSeries);

        for (Map.Entry<String, Double> data : backdropData.entrySet()) {
            backdrop.getData().add(new XYChart.Data<String, Double>(data.getKey(), data.getValue()));
        }

        this.breakdownChart.getData().add(expenditureSeries);
        this.breakdownChart.getData().add(incomeSeries);
        this.breakdownChart.getData().add(backdrop);
        this.breakdownChart.setScaleY(1.1);
        String css = this.getClass().getResource("/css/BreakdownChart.css").toExternalForm();
        this.breakdownChart.getStylesheets().add(css);
    }

    private void updateBreakdownData(XYChart.Series<String, Double> expenditureSeries,
                                     XYChart.Series<String, Double> incomeSeries) {
        for (Map.Entry<String, ReceiptTracker> folder : this.wallet.getFolders().entrySet()) {
            if (folder.getValue().getTotalCashSpent() > 0) {
                expenditureSeries.getData().add(new XYChart.Data<>(
                        folder.getKey(),
                        folder.getValue().getTotalCashSpent())
                );
            } else if (-folder.getValue().getTotalCashSpent() > 0) {
                incomeSeries.getData().add(new XYChart.Data<>(
                        folder.getKey(),
                        -folder.getValue().getTotalCashSpent())
                );
            }
        }
    }

    private Double getBackdropValue(Wallet wallet) {
        double maxValue = 100.0;
        for (ReceiptTracker folderContent : wallet.getFolders().values()) {
            if (folderContent.getTotalCashSpent() > maxValue) {
                maxValue = folderContent.getTotalCashSpent();
            } else if (-folderContent.getTotalCashSpent() > maxValue) {
                maxValue = -folderContent.getTotalCashSpent();
            }
        }
        return maxValue;
    }

    private HashMap<String, Double> getBackdropData(Double backdropValue,
                                                    XYChart.Series<String, Double> expenditureSeries,
                                                    XYChart.Series<String, Double> incomeSeries) {
        HashMap<String, Double> backdropData = new HashMap<>();
        for (XYChart.Data<String, Double> data : expenditureSeries.getData()) {
            backdropData.put(data.getXValue(), backdropValue - data.getYValue());
        }
        for (XYChart.Data<String, Double> data : incomeSeries.getData()) {
            if (backdropData.containsKey(data.getXValue())) {
                backdropData.replace(data.getXValue(), backdropData.get(data.getXValue()) - data.getYValue());
            } else {
                backdropData.put(data.getXValue(), backdropValue - data.getYValue());
            }
        }
        return backdropData;
    }

    private boolean containsXValue(String xValue, XYChart.Series<String, Double> series) {
        for (XYChart.Data<String, Double> data : series.getData()) {
            if (data.getXValue().equals(xValue)) {
                return true;
            }
        }
        return false;
    }

}
