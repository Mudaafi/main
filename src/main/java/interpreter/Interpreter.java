package interpreter;

import executor.command.CommandType;
import executor.command.Executor;
import executor.task.TaskList;
import ui.Wallet;
import ui.gui.MainWindow;

public class Interpreter {

    /**
     * Interprets the userInput relative to the TaskList provided and executes the Command.
     * @param gui The caller's Graphical User Interface
     * @param userInput The userInput taken from the User Interface
     * @return True if the Command executed calls for an ExitRequest, false otherwise
     */
    public static boolean interpret(MainWindow gui, String userInput) {
        CommandType commandType = Parser.parseForCommandType(userInput);
        boolean exitRequest = Executor.runCommand(gui, commandType, userInput);
        return exitRequest;
    }
}
