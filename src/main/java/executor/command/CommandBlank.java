package executor.command;

import ui.gui.MainWindow;

public class CommandBlank extends Command {

    // Constructor
    /**
     * Constructor for CommandBlank subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandBlank(String userInput) {
        this.userInput = userInput;
        this.description = "Does Nothing";
        this.commandType = CommandType.BLANK;
    }

    /**
     * Executes a particular Command.
     */
    @Override
    public void execute(MainWindow gui) {
        gui.displayToast("No Command Detected");
    }
}
