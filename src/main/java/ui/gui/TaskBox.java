package ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class TaskBox extends HBox {
    @FXML
    private TextFlow taskText;

    private TaskBox(String text) {
        try {
            FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("/view/TaskBox.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Text boldText = new Text(text);
        boldText.setStyle("-fx-font-weight: bold");
        this.taskText.getChildren().addAll(boldText, new Text(text));
    }

    public static TaskBox getNewTaskBox(String text) {
        TaskBox newTaskBox = new TaskBox(text);
        newTaskBox.setAlignment(Pos.CENTER_LEFT);
        return newTaskBox;
    }
}
