import executor.command.CommandQueue;
import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import org.junit.jupiter.api.Test;
import ui.gui.MainWindow;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandQueueTest {

    @Test
    void execute() {
        MainWindow gui = new MainWindow();
        TaskList taskList = gui.getTaskList();
        Task testTask = TaskList.createTask(TaskType.EVENT,"something/by somewhen");
        taskList.addTask(testTask);
        CommandQueue testCommand = new CommandQueue("Queue 1 EventIce Cream Party / Tomorrow");
        testCommand.execute(gui);

        Task mainTask = taskList.getList().get(0);
        assertEquals(true, mainTask.isQueuedTasks());
        Task queuedTask = mainTask.getQueuedTasks().getList().get(0);
        assertEquals(TaskType.EVENT, queuedTask.getTaskType());
        assertEquals("Ice Cream Party", queuedTask.getTaskName());
        assertEquals("", queuedTask.getDetailDesc());
        assertEquals("Tomorrow", queuedTask.getTaskDetails());
    }
}