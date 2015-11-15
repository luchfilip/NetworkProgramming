package md.luchfilip;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by luchfilip on 11/14/15.
 *
 */
public class ServerThread implements Runnable {
    public static ServerSocket serverSocket;
    public static final int SERVERPORT = 5000;

    public void run() {
        try {
            serverSocket = new ServerSocket(SERVERPORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                CommunicationThread commThread = new CommunicationThread(socket);
                new Thread(commThread).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
