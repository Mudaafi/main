package ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



public class CommandLineDisplay {
    @FXML
    AnchorPane cliDisplayRoot;
    @FXML
    ScrollPane cliDisplay;
    @FXML
    VBox container;

    public void display(String outputStr) {
        Text newOutput = new Text(outputStr);
        container.getChildren().add(newOutput);
    }

    public void setStyle() {
        String css = this.getClass().getResource("/css/ImportedBox.css").toExternalForm();
        this.container.getStylesheets().add(css);
    }
}
