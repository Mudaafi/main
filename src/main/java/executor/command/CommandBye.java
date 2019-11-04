package executor.command;

import ui.gui.MainWindow;

public class CommandBye extends Command {

    // Constructor
    /**
     * Constructor for CommandBye subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandBye(String userInput) {
        this.userInput = userInput;
        this.description = "Exits the program";
        this.commandType =  CommandType.BYE;
    }

    @Override
    public void execute(MainWindow gui) {
        this.exitRequest = true;
        gui.displayToast("Bye. Hope to see you again soon!");
    }
}
