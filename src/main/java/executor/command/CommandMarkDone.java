package executor.command;

import executor.task.Task;
import executor.task.TaskList;
import interpreter.Parser;
import ui.gui.MainWindow;

public class CommandMarkDone extends Command {
    private String userInput;
    private MainWindow gui;

    // Constructor
    /**
     * Constructor for CommandMarkDone subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandMarkDone(String userInput) {
        this.userInput = userInput;
        this.description = "Marks a certain task as done";
        this.commandType = CommandType.DONE;
    }

    @Override
    public void execute(MainWindow gui) {
        try {
            int index = Integer.parseInt(Parser.removeStr("done", this.userInput)) - 1;
            Task mainTask = gui.getTaskList().getList().get(index);
            mainTask.markDone();
            loadQueuedTasks(gui.getTaskList(), mainTask);
            gui.displayToast(genMarkDoneReply(index, gui.getTaskList()));
        } catch (Exception e) {
            gui.displayToast("Invalid 'done' statement. Please indicate the index of the task you wish to mark done.");
        }
    }

    /**
     * Generates the standard duke reply to inform user that the Task is marked done.
     * @param index The index of the Task in the TaskList
     * @param taskList The TaskList containing all tasks
     * @return Standard duke reply for user
     */
    private String genMarkDoneReply(int index, TaskList taskList) {
        return "Alrighty, I've marked task '"
                + String.valueOf(index + 1)
                + ") "
                + taskList.getList().get(index).getTaskName()
                + "' as done!";
    }

    /**
     * Loads all queued Tasks from the now-done Task to the main TaskList.
     * @param taskList The Main TaskList for Tasks to be added to
     * @param mainTask The Task that has been marked done
     */
    private void loadQueuedTasks(TaskList taskList, Task mainTask) {
        TaskList queuedTasks = mainTask.getQueuedTasks();
        if (queuedTasks == null) {
            return;
        }
        for (Task newTask : queuedTasks.getList()) {
            taskList.addTask(newTask);
        }
        mainTask.setQueuedTasks(null);
    }
}
