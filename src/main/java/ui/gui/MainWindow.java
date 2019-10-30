package ui.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import main.Duke;


public class MainWindow extends AnchorPane {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private ImageView headerBackground;
    //@FXML
    //private ImageView balanceChartPic;
    @FXML
    private DonutChart balanceChart;
    @FXML
    private ImageView breakdownChartPic;
    @FXML
    private TextField userInput;
    @FXML
    private VBox taskContainerLeft;
    @FXML
    private VBox taskContainerRight;

    private Duke duke;

    @FXML
    public void setDuke(Duke d) {
        this.duke = d;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        int leftListLength = taskContainerLeft.getChildren().size();
        int rightListLength = taskContainerRight.getChildren().size();
        if (leftListLength > rightListLength) {
            taskContainerRight.getChildren().add(TaskBox.getNewTaskBox(input));
        } else {
            taskContainerLeft.getChildren().add(TaskBox.getNewTaskBox(input));
        }
        userInput.clear();
    }

    @FXML
    public void fetchStoredImages() {
        Image headerBackgroundPic = new Image(this.getClass().getResourceAsStream("/images/headerBackground.png"));
        this.headerBackground.setImage(headerBackgroundPic);

        //Image balanceChartPic = new Image(this.getClass().getResourceAsStream("/images/balanceChartExample.png"));
        //this.balanceChartPic.setImage(balanceChartPic);

        Image breakdownChartPic = new Image(this.getClass().getResourceAsStream("/images/breakdownExample.png"));
        this.breakdownChartPic.setImage(breakdownChartPic);
    }

    @FXML
    public void displayBalancePieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Budget", 13),
                        new PieChart.Data("Expenses", 25));
        balanceChart.setData(pieChartData);
        balanceChart.setLegendVisible(false);
        balanceChart.setLabelsVisible(false);
        balanceChart.setStartAngle(90.0);
        String css = this.getClass().getResource("/css/PieChart.css").toExternalForm();
        balanceChart.getStylesheets().add(css);

    }
}
