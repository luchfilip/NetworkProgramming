package md.luchfilip;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by luchfilip on 11/14/15.
 *
 */
public class ClientThread implements Runnable {

    public static Socket socket;

    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "localhost";

    @Override
    public void run() {

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVERPORT);

            DataInputStream dataInputStream =new DataInputStream(System.in);
            System.out.print("commands: %Hastalavista, %Time, %Close\n");

            while (socket.isConnected()) {
                String command = dataInputStream.readLine();
                if (command.equals("%Close")) {
                    Thread.currentThread().interrupt();
                    return;
                }
                PrintStream printStream =new PrintStream(socket.getOutputStream());
                printStream.println(command);
                DataInputStream dataInputStreamReceive =new DataInputStream(socket.getInputStream());
                String response = dataInputStreamReceive.readLine();
                if (response == null) {
                    System.out.print("Server is down. Self Terminating.");
                    System.exit(0);
                } else {
                    System.out.print("Response: "+ response + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}