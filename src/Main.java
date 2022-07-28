import Database.Connector;
import TextController.TextController;
import TextController.CommandType;

public class Main {
    public static void main(String[] args) {
        tryToConnect();
        TextController.inputCommand();
    }

    private static void tryToConnect(){
        TextController.println("Enter your database password:");
        Connector.setPass(TextController.getLine());
        if (!Connector.checkConnection()){
            TextController.println("Unable to make connection to database.");
            tryToConnect();
            return;
        }
        TextController.println("Successfully connected to database. You can now use commands. Try /" + CommandType.HELP);
    }
}