package ui.gui;

import executor.task.Task;
import executor.task.TaskList;
import interpreter.Interpreter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ui.ReceiptTracker;
import ui.Wallet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeWindow extends AnchorPane {
    @FXML
    private TextField userInput;
    @FXML
    private DonutChart balanceChart;
    @FXML
    private Label balanceFigure;
    @FXML
    private StackedBarChart<String, Double> breakdownChart;
    @FXML
    private VBox taskContainerLeft;
    @FXML
    private VBox taskContainerRight;

    private Boolean exitRequest = false;
    private ArrayList<String> userInputHistory;
    private TaskList taskList;
    private Wallet wallet;
    private ObservableList<PieChart.Data> pieChartData;

    void initialize(ArrayList<String> userInputHistory, TaskList taskList, Wallet wallet) {
        this.exitRequest = false;
        this.userInputHistory = userInputHistory;
        this.taskList = taskList;
        this.wallet = wallet;

        this.displayTasks(taskList);
        this.extractPieChartData();
        this.displayBalanceChart();
        this.displayBreakdownChart();
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

    void updateBalanceChart(Wallet wallet) {
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

    void displayTasks(TaskList taskList) {
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

}
