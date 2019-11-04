package executor.command;

import executor.task.TaskList;
import ui.Wallet;
import ui.gui.MainWindow;

public abstract class Command {
    protected Boolean exitRequest = false;
    protected String userInput = null;
    protected CommandType commandType;
    protected String description = "NO DESCRIPTION";
    protected TaskList taskList;

    // Constructor
    public Command() {
    }

    /**
     * Returns True if the command requests for the Ui to exit.
     *
     * @return Boolean
     */
    public Boolean getExitRequest() {
        return exitRequest;
    }

    /**
     * Executes a particular Command.
     */
    public abstract void execute(MainWindow gui);

    public String getDescription() {
        return this.description;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }
}
