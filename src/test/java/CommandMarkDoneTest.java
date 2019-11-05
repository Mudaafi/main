import com.sun.tools.javac.Main;
import executor.command.CommandMarkDone;
import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import org.junit.jupiter.api.Test;
import ui.gui.MainWindow;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandMarkDoneTest {

    @Test
    void loadQueuedTasks() {
        MainWindow gui = new MainWindow();
        TaskList taskList = gui.getTaskList();
        TaskList queuedTaskList = new TaskList();
        Task mainTask = TaskList.createTask(TaskType.EVENT,"something/by somewhen");
        Task queuedTask = TaskList.createTask(TaskType.DEADLINE, "Something Else / rly");
        queuedTaskList.addTask(queuedTask);
        mainTask.setQueuedTasks(queuedTaskList);
        taskList.addTask(mainTask);
        CommandMarkDone c = new CommandMarkDone("Done1");
        c.execute(gui);

        Task loadedTask = taskList.getList().get(1);
        assertEquals(true, mainTask.getIsDone());
        assertEquals(false, mainTask.isQueuedTasks());
        assertEquals(TaskType.DEADLINE, loadedTask.getTaskType());
        assertEquals("Something Else", loadedTask.getTaskName());
        assertEquals("", loadedTask.getDetailDesc());
        assertEquals("rly", loadedTask.getTaskDetails());
    }
}