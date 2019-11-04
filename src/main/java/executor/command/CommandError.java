package executor.command;

import ui.gui.MainWindow;

public class CommandError extends Command {
    // Constructor
    /**
     * Constructor for CommandError subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandError(String userInput) {
        this.userInput = userInput;
        this.description = "Prints error message when program encounters an error";
        this.commandType = CommandType.ERROR;
    }

    /**
     * Executes a particular Command.
     */
    @Override
    public void execute(MainWindow gui) {
        gui.displayToast("Please enter a valid Command. Type 'help' to see the list of Commands");
        gui.printSeparator();
    }
}
