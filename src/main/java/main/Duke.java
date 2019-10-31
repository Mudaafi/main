package main;

import executor.task.TaskList;
import javafx.application.Application;
import ui.Ui;
import ui.gui.MainGui;

public class Duke {
    protected static Ui ui;
    protected static MainGui gui;
    protected static TaskList taskList;

    /**
     * The Main method by which main.Duke will be launched.
     */
    public static void main(String[] args) {
        initializeGui(args);
    }

    private static void initializeUi() {
        ui = new Ui("savedTask.txt", "savedWallet.txt");
        ui.initialize();
    }

    private static void initializeGui(String[] args) {
        gui = new MainGui();
        gui.initialize(args);
    }
}
