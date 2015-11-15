package md.luchfilip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by luchfilip on 11/14/15.
 *
 */
public class CommunicationThread implements Runnable {

    private Socket clientSocket;
    private BufferedReader input;
    private String clientName;

    public CommunicationThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        clientName = clientSocket.toString();
        System.out.print("New Client Connected successfully: " + clientName + "\n");

//        ps.println("Successfully connected to server. Commands: %Hastalavista, %Time, %Close, %WhoAmI, %WhoAreYou, %SystemData\n");

        try {
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                String read = input.readLine();

                if (read == null) {
                    System.out.print("Closed connection with " + clientName + "\n");
                    Thread.currentThread().interrupt();
                    return;
                } else {
                    System.out.print("Client msg: " + read + "\n");
                    PrintStream printStream =new PrintStream(this.clientSocket.getOutputStream());

                    switch (read) {
                        case "%Time":
                            printStream.println(Utils.getDateTime());
                            break;
                        case "%Hastalavista":
                            selfDestroy();
                            return;
                        case "%WhoAmI":
                            printStream.println(clientName);
                            break;
                        case "%WhoAreYou":
                            printStream.println(System.getProperty("user.name"));
                            break;
                        case "%SystemData":
                            printStream.println(Utils.getSystemProperties());
                            break;
                        default:
                            if (read.contains("?")) {
                                printStream.println("42");
                            } else {
                                printStream.println("Can you elaborate on that?");
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void selfDestroy() {
        Thread.currentThread().interrupt();
        Main.serverThread.interrupt();
        System.out.print("Closed connection with " + clientName + "\n");
        System.exit(0);
    }

}
