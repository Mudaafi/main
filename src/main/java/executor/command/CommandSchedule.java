package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.gui.MainWindow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandSchedule extends Command {
    protected String userInput;

    /**
     * Constructor for CommandSchedule subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandSchedule(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.VIEWSCHEDULE;
        this.description = "Prints the schedule for the input date";
    }

    @Override
    public void execute(MainWindow gui) {
        String dateInput = Parser.removeStr(this.commandType.toString(), this.userInput);
        gui.printToDisplay("Here is your schedule for the following date '"
                + dateInput
                + "'."
        );
        try {
            gui.printToDisplay(getPrintableSchedule(dateInput, taskList));
        } catch (Exception e) {
            gui.displayToast("Read invalid input");
        }
    }

    private String getPrintableSchedule(String dateInput, TaskList taskList) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date userDate = format.parse(dateInput);
        userDate.setTime(0);
        StringBuilder outputStr = new StringBuilder();
        for (int index = 0; index < taskList.getSize(); ++index) {
            try {
                Date taskDate = taskList.getList().get(index).getDatetime();//creates of copy of datetime in the task
                if (taskDate != null) {
                    taskDate.setTime(0);//this sets the time to zero
                    if (taskDate.equals(userDate)) {
                        outputStr.append(taskList.getPrintableTasksByIndex(index));
                    }
                }
            } catch (Exception e) {
                outputStr.append("Invalid inputs received");
            }
        }
        return outputStr.toString();
    }
}
