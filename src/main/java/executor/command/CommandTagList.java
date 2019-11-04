package executor.command;

import interpreter.Parser;
import ui.ReceiptTracker;
import ui.gui.MainWindow;

import java.text.DecimalFormat;

public class CommandTagList extends CommandList {
    private String tag;

    //Constructor
    /**
     * Constructor for CommandListTag subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandTagList(String userInput) {
        super(userInput);
        this.userInput = userInput;
        this.description = "Lists based on tag";
        this.commandType = CommandType.TAGLIST;
        this.tag = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
       public void execute(MainWindow gui) {
        ReceiptTracker taggedReceipts = gui.getWallet().getReceipts().findReceiptsByTag(this.tag);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        gui.dukeSays("You spent a total of $"
                +
                decimalFormat.format(taggedReceipts.getTotalCashSpent())
                + " "
                + "on"
                + " "
                + tag
        );
        gui.printSeparator();
        gui.printToDisplay(taggedReceipts.getPrintableReceipts());
        gui.printSeparator();
    }
}
