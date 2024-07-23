package org.example;

import quickfix.*;
import quickfix.field.*;
import quickfix.fix42.OrderCancelRequest;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ConfigError, InterruptedException {

        /*String username = "your_username";
        String password = "your_password";*/

        // Load configuration file
        String configFile = "quickfix.cfg";
        SessionSettings settings = new SessionSettings(configFile);

        // Create an application implementation
        Application application = new MyFIXApplication(); // Implement this class

//        Application application = new MyFIXApplication(username, password);

        // Create a message store factory
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);

        // Create a log factory
        LogFactory logFactory = new ScreenLogFactory(true, true, true); // Output to console

        // Create a message factory
        MessageFactory messageFactory = new DefaultMessageFactory();

        // Create and start the initiator
        SocketInitiator initiator = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
        initiator.start();


        //send ordercancel request to the FIXIMULATOR
        sendFixMessage();

        System.out.println("Press 'q' and then Enter to quit...");

        // Create a Scanner object to read from System.in
        Scanner scanner = new Scanner(System.in);

        // Loop until 'q' is pressed
        while (true) {
            // Read a line of input
            String input = scanner.nextLine();

            // Check if the input is 'q' (case insensitive)
            if (input.equalsIgnoreCase("q")) {
                System.out.println("Exiting...");
                initiator.stop();
                break;  // Exit the loop and terminate the program
            } else {
                System.out.println("Press 'q' and then Enter to quit...");
            }
        }
    }

    private static void sendFixMessage() {
        System.out.println("******Start to send order cancel request....**********");
        Message orderCancelRequest = new Message();
        orderCancelRequest.getHeader().setField(new BeginString("FIX.4.2"));
        orderCancelRequest.getHeader().setField(new MsgType("F"));
        orderCancelRequest.setField(new ClOrdID("12345")); // Client Order ID
        orderCancelRequest.setField(new OrigClOrdID("67890")); // Original Order ID
        orderCancelRequest.setField(new Symbol("EUR/USD")); // Symbol of the instrument
        orderCancelRequest.setField(new Side(Side.SELL)); // Side of the order (1 = Buy, 2 = Sell)
        orderCancelRequest.setField(new TransactTime()); // Current timestamp
        try {
            Session.sendToTarget(orderCancelRequest, "BANZAI", "FIXIMULATOR");
        } catch (SessionNotFound e) {
            throw new RuntimeException(e);
        }
        System.out.println("***********Successfully sent order cancel request........**************");
    }
}