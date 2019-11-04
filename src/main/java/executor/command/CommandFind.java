package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.gui.MainWindow;

public class CommandFind extends Command {
    protected String userInput;

    /**
     * Constructor for CommandFind subCommand Class.
     * @param userInput The User Input to be translated into a Command
     */
    public CommandFind(String userInput) {
        this.userInput = userInput;
        this.description = "Parses input and loops through list of entries and checks if input matches any of them";
        this.commandType = CommandType.FIND;
    }

    @Override
    public void execute(MainWindow gui) {
        try {
            String queryInput = Parser.removeStr("find", this.userInput);
            queryInput = queryInput.toLowerCase();
            gui.printToDisplay("Here are the Tasks matching your query '"
                    + queryInput
                    + "'."
            );
            gui.printToDisplay(getPrintableTasksByName(queryInput, gui.getTaskList()));
        } catch (Exception e) {
            gui.displayToast("No such task found.");
        }
        gui.printSeparator();
    }

    /**
     * Finds and prints each task that contains the string.
     * @param name     The substring to be found
     * @param taskList The TaskList containing the Tasks.
     * @return String representing the output
     */
    private String getPrintableTasksByName(String name, TaskList taskList) {
        StringBuilder outputStr = new StringBuilder();
        for (int index = 0; index < taskList.getSize(); ++index) {
            try {
                if (taskList.getList().get(index).getTaskName().toLowerCase().contains(name)) {
                    outputStr.append(taskList.getPrintableTasksByIndex(index));
                }
            } catch (Exception e) {
                outputStr.append("Read invalid taskName");
            }
        }
        return outputStr.toString();
    }
}
