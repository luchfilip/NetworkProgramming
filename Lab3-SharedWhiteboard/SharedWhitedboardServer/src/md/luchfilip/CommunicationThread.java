package md.luchfilip;

import java.awt.*;
import java.awt.event.MouseEvent;
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

    private BufferedReader input;
    private String clientName;

    public CommunicationThread(Socket clientSocket) {
        clientName = clientSocket.toString();
        System.out.print("New Client Connected successfully: " + clientName + "\n");
        Main.clientSockets.add(clientSocket);

        try {
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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

                    int cx, cy, ox, oy;
                    cx = Integer.parseInt(read.substring(read.indexOf("cx:") + 3, read.indexOf("cy:")));
                    cy = Integer.parseInt(read.substring(read.indexOf("cy:") + 3, read.indexOf("ox:")));
                    ox = Integer.parseInt(read.substring(read.indexOf("ox:") + 3, read.indexOf("oy:")));
                    oy = Integer.parseInt(read.substring(read.indexOf("oy:") + 3, read.indexOf("hex:")));
                    Color clientColor = Color.decode(read.substring(read.indexOf("hex:") + 4));
                    // draw on server
                    DrawArea.draw(cx, cy, ox, oy, clientColor);

                    // send draw to all clients
                    Main.syncWithAllClients(read);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
