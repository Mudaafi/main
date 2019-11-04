package executor.command;

import interpreter.Parser;
import ui.ReceiptTracker;
import ui.gui.MainWindow;


public class CommandDateList extends Command {
    private String date;

    //Constructor
    /**
     * Constructor for CommandListMonYear subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandDateList(String userInput) {
        this.userInput = userInput;
        this.description = "Lists receipts based on date input. Format: datelist <date>";
        this.commandType = CommandType.DATELIST;
        this.date = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(MainWindow gui) {
        ReceiptTracker dateReceipts = gui.getWallet().getReceipts().findReceiptsByDate(this.date);
        gui.dukeSays("You have the following receipts for" + " " + date);
        gui.printSeparator();
        gui.printToDisplay(dateReceipts.getPrintableReceipts());
        gui.printSeparator();
    }

}

