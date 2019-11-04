package ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



public class CommandLineDisplay {
    @FXML
    AnchorPane cliDisplayRoot;
    @FXML
    ScrollPane cliDisplay;
    @FXML
    VBox container;

    public void printToDisplay(String outputStr) {
        Text newOutput = new Text(outputStr);
        container.getChildren().add(newOutput);
    }

    public void setStyle() {
    }

    public void dukeSays(String string) {
        printToDisplay("Duke: " + string + "\n");
    }
}
