package md.luchfilip;

import java.awt.*;
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

            while (Thread.currentThread().isAlive()) {
                DataInputStream dataInputStreamReceive =new DataInputStream(socket.getInputStream());
                String read = dataInputStreamReceive.readLine();
                if (read == null) {
                    System.out.print("Server is down. Self Terminating.");
                    System.exit(0);
                } else {
                    System.out.print("Response: "+ read + "\n");
                    int cx, cy, ox, oy;
                    cx = Integer.parseInt(read.substring(read.indexOf("cx:") + 3, read.indexOf("cy:")));
                    cy = Integer.parseInt(read.substring(read.indexOf("cy:") + 3, read.indexOf("ox:")));
                    ox = Integer.parseInt(read.substring(read.indexOf("ox:") + 3, read.indexOf("oy:")));
                    oy = Integer.parseInt(read.substring(read.indexOf("oy:") + 3, read.indexOf("hex:")));
                    Color color = Color.decode(read.substring(read.indexOf("hex:") + 4));
                    // draw on server
                    DrawArea.draw(cx, cy, ox, oy, color);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}