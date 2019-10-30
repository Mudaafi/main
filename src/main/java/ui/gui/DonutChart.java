package ui.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DonutChart extends PieChart {
    private Circle hole;

    public DonutChart() {
        super(FXCollections.observableArrayList());
        this.hole = new Circle(50);
        this.hole.setFill(Color.valueOf("#ede7d1"));
    }

    private DonutChart(ObservableList<Data> pieChartData) {
        super(pieChartData);
        this.hole = new Circle(50);
        this.hole.setFill(Color.valueOf("#ede7d1"));
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        super.layoutChartChildren(top, left, contentWidth, contentHeight);
        if (this.getData().size() > 0) {
            Node pie = this.getData().get(0).getNode();
            Pane parentChart = (Pane) pie.getParent();
            if (!parentChart.getChildren().contains(hole)) {
                matchHoleToChart(parentChart);
                parentChart.getChildren().add(hole);
            }
        }
    }

    // @@author {Mudaafi}-reused
    // Solution below adapted from:
    // https://stackoverflow.com/questions/24121580/can-piechart-from-javafx-be-displayed-as-a-doughnut
    private void matchHoleToChart(Pane chart) {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (PieChart.Data data: getData()) {
            Node node = data.getNode();
            Bounds bounds = node.getBoundsInParent();
            if (bounds.getMinX() < minX) {
                minX = bounds.getMinX();
            }
            if (bounds.getMinY() < minY) {
                minY = bounds.getMinY();
            }
            if (bounds.getMaxX() > maxX) {
                maxX = bounds.getMaxX();
            }
            if (bounds.getMaxY() > maxY) {
                maxY = bounds.getMaxY();
            }
        }
        hole.setCenterX(minX + (maxX - minX) / 2);
        hole.setCenterY(minY + (maxY - minY) / 2);
        hole.setRadius((maxX - minX) / 2.6);
    }

    public static DonutChart createBalanceChart(double budgetedAmt, double expenditure) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Budget left", budgetedAmt - expenditure),
                        new PieChart.Data("Expenses", expenditure));
        return new DonutChart(pieChartData);
    }
}
