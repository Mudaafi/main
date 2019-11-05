import javafx.stage.Stage;
import ui.MainGuiTest;
import ui.Wallet;
import ui.Receipt;
import org.junit.jupiter.api.Test;
import ui.gui.MainGui;
import ui.gui.MainWindow;

import java.io.File;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageWalletTest {

    @Test
    void loadData() {
//        MainGuiTest rootGui = new MainGuiTest();
//        rootGui.initialize("testDataLoad.txt","testWalletDataLoad.txt");
//        Wallet wallet = rootGui.getMainWindowController().getWallet();
//
//        //In 5.00 /date 2019-01-29 /tags street
//        Receipt firstReceipt = wallet.getReceipts().get(0);
//        assertEquals(-5.00, firstReceipt.getCashSpent(),
//                "Loaded wrong cashSpent");
//        assertEquals("2019-08-30", firstReceipt.getDate().toString(),
//                "Loaded wrong date");
//        assertEquals("street", firstReceipt.getTags().get(0),
//                "Loaded wrong tag");
//
//        //out $10 /date 2001-08-25
//        Receipt secondReceipt = wallet.getReceipts().get(1);
//        assertEquals(10, secondReceipt.getCashSpent(),
//                "Loaded wrong cashSpent");
//        assertEquals("2001-08-25", secondReceipt.getDate().toString(),
//                "Loaded wrong date");
//
//        //Out $15.00 /date 2019-12-10 /tags dogfood puppy monthly
//        Receipt thirdReceipt = wallet.getReceipts().get(2);
//        assertEquals(15, thirdReceipt.getCashSpent(),
//                "Loaded wrong cashSpent");
//        assertEquals("2019-12-10", thirdReceipt.getDate().toString(),
//                "Loaded wrong date");
//        assertEquals("dogfood", thirdReceipt.getTags().get(0),
//                "Loaded wrong tag");
//        assertEquals("puppy", thirdReceipt.getTags().get(1),
//                        "Loaded wrong tag");
//        assertEquals("monthly", thirdReceipt.getTags().get(2),
//                        "Loaded wrong tag");
//
//        //in $3.00 /date 2018-03-14 /tags refund clothes
//        Receipt fourthReceipt = wallet.getReceipts().get(3);
//        assertEquals(-3.00, fourthReceipt.getCashSpent(),
//                "Loaded wrong cashSpent");
//        assertEquals("2018-03-14", fourthReceipt.getDate().toString(),
//                "Loaded wrong date");
//        assertEquals("refund", fourthReceipt.getTags().get(0),
//                "Loaded wrong tag");
//        assertEquals("clothes", fourthReceipt.getTags().get(1),
//                        "Loaded wrong tag");
    }

    @Test
    void saveData() {
//        MainWindow guiTest = new MainWindow();
//        Stage stageTest = new Stage();
//        guiTest.initialize(stageTest, "testDataLoad.txt", "testWalletDataLoad.txt");
//        guiTest.saveAllData();
//
//        File fileExpected = new File("testWalletDataLoad.txt");
//        File fileSaved = new File("testWalletDataSave.txt");
//        try {
//            Scanner scannerExpected = new Scanner(fileExpected);
//            Scanner scannerSaved = new Scanner(fileSaved);
//            while (scannerExpected.hasNextLine()) {
//                assertEquals(scannerExpected.nextLine(), scannerSaved.nextLine());
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
