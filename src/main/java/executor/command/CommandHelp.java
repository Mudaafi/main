package executor.command;

import ui.gui.MainWindow;

public class CommandHelp extends Command {

    /**
     * Constructor to provide the user with the details about the commands available.
     */
    public CommandHelp(String userInput) {
        this.userInput = userInput;
        this.description = "Provides the user with all the available commands and descriptions.";
        this.commandType = CommandType.HELP;
    }

    @Override
    public void execute(MainWindow gui) {
        for (String s : CommandType.getNames()) {
            if (!s.equals("ERROR") && !s.equals("TASK") && !s.equals("BLANK")) {
                CommandType commandType = CommandType.valueOf(s);
                Command c = Executor.createCommand(commandType, "null");
                String commandDesc = c.getDescription();

                gui.printToDisplay(s.toUpperCase() + " - " + commandDesc);
            }
        }
        gui.printSeparator();
    }
}
