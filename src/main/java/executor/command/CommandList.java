package executor.command;

import ui.gui.MainWindow;

public class CommandList extends Command {
    // Constructor
    /**
     * Constructor for CommandList subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandList(String userInput) {
        this.userInput = userInput;
        this.description = "Lists all tasks and receipts added by user";
        this.commandType = CommandType.LIST;
    }

    @Override
    public void execute(MainWindow gui) {
        gui.printToDisplay("You have ("
                + String.valueOf(gui.getTaskList().getSize())
                + ") Tasks in your list!"
        );
        gui.printToDisplay(gui.getTaskList().getPrintableTasks());
        gui.printToDisplay("\n");

        gui.dukeSays("You have ("
                + gui.getWallet().getReceipts().size()
                + ") receipts!"
        );
        gui.printToDisplay(gui.getWallet().getReceipts().getPrintableReceipts());
        gui.printSeparator();
    }
}
