package executor.command;

import executor.task.Task;
import ui.gui.MainWindow;

import java.util.Calendar;
import java.util.Date;

public class CommandReminder extends Command {
    protected Date currentDate = Calendar.getInstance().getTime();

    /**
     * Constructor for CommandReminder subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandReminder(String userInput) {
        this.userInput = userInput;
        this.currentDate.setTime(0);
        this.commandType = CommandType.REMINDER;
        this.description = "Loops through list and checks if current date matches date linked with task and prints it";
    }

    @Override
    public void execute(MainWindow gui) {
        try {
            for (Task task : taskList.getList()) {
                Date dateCopy = task.getDatetime();
                if (dateCopy != null) {
                    dateCopy.setTime(0);
                    if (dateCopy.equals(this.currentDate)) {
                        gui.displayToast(task.genTaskDesc());
                        gui.printSeparator();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("sorry");
        }
    }
}
