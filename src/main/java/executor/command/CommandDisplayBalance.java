package executor.command;

import ui.gui.MainWindow;

import java.text.DecimalFormat;

public class CommandDisplayBalance extends Command {
    // Constructor
    /**
     * Constructor for CommandDisplayBalance subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDisplayBalance(String userInput) {
        this.userInput = userInput;
        this.description = "Shows the current balance available in the wallet";
        this.commandType = CommandType.BALANCE;
    }

    @Override
    public void execute(MainWindow gui) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        gui.printToDisplay("Your Balance: $"
                + decimalFormat.format(gui.getWallet().getBalance())
        );
    }
}
