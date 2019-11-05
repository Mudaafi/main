package executor.command;

import ui.gui.MainWindow;

public class CommandCliDisplay extends Command {

    /**
     * Constructor for CommandCliDisplay subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandCliDisplay(String userInput) {
        this.userInput = userInput;
        this.description = "Displays the Command Line Display";
        this.commandType = CommandType.CLI;
    }

    @Override
    public void execute(MainWindow gui) {
        try {
            gui.showCliDisplay();
        } catch (Exception e) {
            gui.displayToast("Unable to show CLI Display");
        }
    }
}
