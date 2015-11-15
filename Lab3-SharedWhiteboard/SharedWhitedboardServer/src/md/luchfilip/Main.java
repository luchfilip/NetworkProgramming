package md.luchfilip;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static Thread serverThread;
    public static ArrayList<Socket> clientSockets = new ArrayList<>();

    public static void main(String[] args) {

        serverThread = new Thread(new ServerThread());
        serverThread.start();

        new Paint().show();
    }

    public static void syncWithAllClients(String message) {
        for (int i = 0; i < clientSockets.size(); i++) {
            PrintStream printStream = null;
            try {
                printStream = new PrintStream(clientSockets.get(i).getOutputStream());
                printStream.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
