package executor.command;

import interpreter.Parser;
import ui.ReceiptTracker;
import ui.gui.MainWindow;

public class CommandGetSpendingByYear extends Command {
    private String userInput;

    /**
     * Constructor to explain about the method.
     * @param userInput userInput from CLI
     */
    public CommandGetSpendingByYear(String userInput) {
        this.commandType = CommandType.EXPENDEDYEAR;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the year stated. "
                + "Format : expendedyear <year>";
    }

    @Override
    public void execute(MainWindow gui) {
        ReceiptTracker receiptsInYear = new ReceiptTracker();
        String yearStr = Parser.parseForPrimaryInput(CommandType.EXPENDEDYEAR, userInput);
        int year = Integer.parseInt(yearStr);
        receiptsInYear = gui.getWallet().getReceipts().findReceiptByYear(year);
        Double totalMoney = receiptsInYear.getTotalCashSpent();
        gui.displayToast("The total amount of money spent in " + year + " : " + totalMoney);
    }
}

