package org.example;

import quickfix.*;

public class MyFIXApplication implements Application {

    /*private String username;
    private String password;

    // Constructor to set username and password
    public MyFIXApplication(String username, String password) {
        this.username = username;
        this.password = password;
    }*/

    @Override
    public void onCreate(SessionID sessionID) {
        System.out.println("**********Inside the onCreate************");
        System.out.println("SessionID: " + sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        System.out.println("**********Inside the onLogon************");
        // Perform any custom actions upon successful logon
        // Example: Notify a monitoring system, initialize session-specific data, etc.
    }

    @Override
    public void onLogout(SessionID sessionID) {
        System.out.println("**********Inside the onLogout************");
        // Perform any custom actions upon logout
        // Example: Clean up resources, log session termination, etc.
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        System.out.println("**********Inside the toAdmin************");
        // Implement logic to add username and password to Logon message
        /*if (message instanceof Logon) {
            Logon logon = (Logon) message;
            logon.setField(new Username(username));
            logon.setField(new Password(password));
        }*/
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println("**********Inside the fromAdmin************");

        // Implement logic to validate username and password in incoming Logon message
        /*if (message instanceof Logon) {
            Logon logon = (Logon) message;
            String receivedUsername = logon.getString(Username.FIELD);
            String receivedPassword = logon.getString(Password.FIELD);

            // Perform authentication logic (example: simple username/password check)
            if (!this.username.equals(receivedUsername) || !this.password.equals(receivedPassword)) {
                throw new RejectLogon("Invalid username or password");
            }
        }*/
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        System.out.println("**********Inside the toApp************");
    }

    /**
     * Here we receive messages from FIXIMULATOR (fix server)
     * @param message
     * @param sessionID
     * @throws FieldNotFound
     * @throws IncorrectDataFormat
     * @throws IncorrectTagValue
     * @throws UnsupportedMessageType
     */
    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("**********Inside the fromApp************");
        System.out.println("Received message: " + message.toString());

//        9=19935=634=1049=FIXIMULATOR52=20240723-05:32:12.65756=BANZAI15=USD22=523=I172171273266227=1280028=N44=86.088848=CRL.N54=255=CRL62=20240723-06:02:12.657107=CHARLES RIVER LABORATORIES130=N10=121
        try {
            Message fixMessage = new Message(message.toString(), false);

            String version = message.getHeader().getString(8); // Get MsgType (35)
            String msgType = message.getHeader().getString(35); // Get MsgType (35)
            String senderCompID = message.getHeader().getString(49); // Get SenderCompID (49)
            String targetCompID = message.getHeader().getString(56); // Get TargetCompID (56)

            String values = "Version: " + version + "\n "
                    + "msgType: " + msgType + "\n "
                    + "senderCompID: " + senderCompID + "\n "
                    + "targetCompID: " + targetCompID + "\n ";

            System.out.println("Fix message is: \n" + values);


        } catch (InvalidMessage e) {
            throw new RuntimeException(e);
        }

    }
}
