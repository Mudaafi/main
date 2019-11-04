package executor.command;

import executor.task.Task;
import executor.task.TaskList;
import interpreter.Parser;
import ui.gui.MainWindow;

public class CommandQueue extends Command {
    protected String userInput;

    /**
     * Constructor for CommandQueue subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandQueue(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.QUEUE;
        this.description = "Queues user-inputted task in the list. Syntax: queue [taskindex] [taskdescription]";
    }

    @Override
    public void execute(MainWindow gui) {
        TaskList throwaway = new TaskList();
        String[] parsedInput = Parser.removeStr(this.commandType.toString(), this.userInput)
                .split(" ", 2);
        int mainTaskIndex = Integer.parseInt(parsedInput[0]) - 1;
        Task mainTask = gui.getTaskList().getList().get(mainTaskIndex);
        String taskString = parsedInput[1].trim();
        CommandType commandType = Parser.parseForCommandType(taskString);
        Command createNewTaskCommand = Executor.createCommand(commandType, taskString);
        if (createNewTaskCommand.commandType != CommandType.TASK) {
            gui.displayToast("No Task detected after 'Queue'.");
            return;
        }
        initializeQueue(mainTask);
        createNewTaskCommand.setTaskList(mainTask.getQueuedTasks());
        createNewTaskCommand.execute(gui);
    }

    /**
     * Initializes the Queue if it hasn't been initialized.
     * @param task The task to initialize the Queue in.
     */
    private void initializeQueue(Task task) {
        if (task.isQueuedTasks()) {
            return;
        }
        task.setQueuedTasks(new TaskList());
    }
}
