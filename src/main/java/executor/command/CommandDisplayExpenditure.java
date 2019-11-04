package executor.command;

import ui.gui.MainWindow;

import java.text.DecimalFormat;

public class CommandDisplayExpenditure extends Command {
    /**
     * Constructor for CommandDisplayExpenditure subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDisplayExpenditure(String userInput) {
        this.userInput = userInput;
        this.description = "Shows the total amount of money spent";
        this.commandType = CommandType.EXPENSES;
    }

    @Override
    public void execute(MainWindow gui) {
        double totalExpenses = gui.getWallet().getTotalExpenses();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        gui.printToDisplay("Total Expenditure: $"
                + decimalFormat.format(totalExpenses)
        );
    }
}
