package executor.command;

import ui.gui.MainWindow;

public class CommandDelete extends Command {
    protected String userInput;

    // Constructor
    /**
     * Constructor for CommandDelete subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDelete(String userInput) {
        this.userInput = userInput;
        this.description = "Deletes the specific entry that the user wants to remove. FORMAT: delete <Index_of_Entry>";
        this.commandType = CommandType.DELETE;
    }

    @Override
    public void execute(MainWindow gui) {
        try {
            int index = Integer.parseInt(userInput.replace("delete", "").trim()) - 1;
            gui.displayToast("Task '"
                    + String.valueOf(index + 1)
                    + ") "
                    + gui.getTaskList().getList().get(index).getTaskName()
                    + "' deleted"
            );
            gui.getTaskList().deleteTaskByIndex(index);
        } catch (Exception e) {
            gui.displayToast("Invalid 'delete' statement. "
                    + "Please indicate the index of the task you wish to mark delete.");
        }
    }
}