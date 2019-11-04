package executor.command;

import interpreter.Parser;
import ui.gui.MainWindow;

import java.text.DecimalFormat;

public class CommandUpdateBalance extends Command {

    private Double newBalance;

    /**
     * Constructor for the CommandUpdateBalance class.
     * @param userInput The user Input from the CLI
     */
    public CommandUpdateBalance(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.SETBALANCE;
        this.newBalance = extractAmount();
        this.description = "Updates current balance to new balance in the wallet";
    }

    @Override
    public void execute(MainWindow gui) {
        gui.getWallet().setBalance(this.newBalance);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        gui.displayToast("Balance updated to: $" + decimalFormat.format(this.newBalance));
    }

    private Double extractAmount() {
        String incomeStr = Parser.parseForPrimaryInput(this.commandType, this.userInput);
        incomeStr = incomeStr.trim().replace("$", "");
        try {
            return Double.parseDouble(incomeStr);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
