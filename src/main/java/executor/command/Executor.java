package executor.command;

import ui.gui.MainWindow;

public class Executor {

    /**
     * Constructor for 'Executor' Class.
     */
    public Executor() {
    }

    /**
     * Parses the user input and executes the Command specified.
     * @param userInput User input from the CLI
     * @return True if the Command executed calls for an ExitRequest, false otherwise
     */
    public static boolean runCommand(MainWindow gui, CommandType commandType, String userInput) {
        Command c = createCommand(commandType, userInput);
        gui.displayToast("Executing Command: " + c.commandType.toString());
        c.execute(gui);
        return c.getExitRequest();
    }

    /**
     * Creates a specific Command sub-Object given the CommandType.
     * @param commandType The type of command to be created
     * @param userInput The input to be initialized with the command
     * @return The generated Command
     */
    public static Command createCommand(CommandType commandType, String userInput) {
        Command c;
        try {
            c = (Command) commandType.getCommandClass().getDeclaredConstructor(String.class).newInstance(
                    userInput);
        } catch (Exception e) {
            c = new CommandError(userInput);
        }
        return c;
    }


}
