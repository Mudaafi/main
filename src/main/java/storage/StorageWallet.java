package storage;

import ui.Receipt;
import interpreter.Parser;
import executor.command.Executor;
import executor.command.CommandType;
import ui.gui.MainWindow;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class StorageWallet {
    protected String filePath;

    /**
     * * Constrctor for the 'StorageWallet' Class.
     *
     * @param filePath The file path to be used to store and load data
     */
    public StorageWallet(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Method to save the current list of receipts.
     * @param gui Graphical User Interface that Contains a Wallet Object
     */
    public void saveData(MainWindow gui) {
        try {
            FileWriter writer = new FileWriter(this.filePath);
            writer.write(gui.getWallet().getBalance().toString() + "\n");
            for (Receipt receipt : gui.getWallet().getReceipts()) {
                String strSave = Parser.encodeReceipt(receipt);
                writer.write(strSave);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Method to load previously saved list of receipts.
     * @param gui Graphical User Interface that Contains a Wallet Object
     */
    public void loadData(MainWindow gui) {
        try {
            File file = new File(this.filePath);
            Scanner scanner = new Scanner(file);
            String storedBalanceStr = scanner.nextLine();
            Double storedBalanceDouble = 0.10;
            try {
                storedBalanceDouble = Double.parseDouble(storedBalanceStr);
            } catch (Exception e) {
                System.out.println("Balance cannot be read");
            }
            gui.getWallet().setBalance(storedBalanceDouble);

            while (scanner.hasNextLine()) {
                String loadedInput = scanner.nextLine();
                if (loadedInput.equals("")) {
                    break;
                }
                try {
                    parseAddReceiptFromStorageString(gui, loadedInput);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("No Previously saved wallet Data.");
        }
    }

    /**
     * Converts saved String in StorageWallet to actual Receipt object and saves in Wallet Object.
     * @param gui The Graphical User Interface that contains a Wallet Object.
     * @param loadedInput The saved String to be converted
     */
    public void parseAddReceiptFromStorageString(MainWindow gui, String loadedInput) {
        CommandType commandtype = Parser.parseForCommandType(loadedInput);
        Executor.runCommand(gui, commandtype, loadedInput);
    }
}
