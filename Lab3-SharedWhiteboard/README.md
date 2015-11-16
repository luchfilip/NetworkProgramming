###Shared Whiteboard

####Features
   * Create a shared whiteboard tool which enables several people to collaborate in real-time, by drawing sketches on a canvas.
   * Every person chooses their own colour.
   * The stroke will be rendered on the screens of all the participants.
   * each client is also a server, thus people can draw simultaneously, such that everyone can see the same thing on their screen;

####Description
First of all, I created a limited paint application, which has:
   * whiteboard
   * colors buttons
   * clear button

!(whiteboard app)[http://i.imgur.com/oXajrAq.png "whiteboard application"]

In order to achieve this, I extended `JComponent`, and used `Graphics2D` for drawing, and `DrawArea` to create an area where I can draw. 

The I add mouse listener, as following:

		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int currentX = e.getX();
                int currentY = e.getY();

                draw(currentX, currentY, oldX, oldY, mColor);

                // store current coords x,y as olds x,y
                oldX = currentX;
                oldY = currentY;
            }
        });

The draw method actually draws the points:

		public static void draw(int currentX, int currentY, int oldX, int oldY, Color color) {
	        g2.setColor(color);
	        System.out.println(currentX + "," + currentY + "," + oldX + "," + oldY);
	        if (g2 != null) {
	            // draw line if g2 context not null
	            g2.drawLine(oldX, oldY, currentX, currentY);
	            // refresh draw area to repaint
	            drawArea.repaint();

	        	}
    	}

As you can see, the method draw, is static, so that I can call it from anywhere, and draw clients' lines.

This basic application, is copied to client, and then the development splits to server and client side.

#####Server Side

I start the server thread, which listens for connections. Then I open the window with draw area. 
       
        serverThread = new Thread(new ServerThread());
        serverThread.start();
        new Paint().show();

Inside `ServerThread()` I create a new `CommunicationThread()` for every new client.
And the whole magic is happening right here, in `CommunicationThread()`, where data is received from client, then drawn on server, and sent to all clients including the one who is drawing. So if the server is not responding, the clients will not get anything on their drawing area.

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

Whenever a new client is connected, it is added to an `ArrayList`, where all clients are sored. Then, when we want to send a message to all clients, we call the `synchWithAllClients()` method.

		 for (int i = 0; i < clientSockets.size(); i++) {
            	PrintStream printStream = null;
                printStream = new PrintStream(clientSockets.get(i).getOutputStream());
                printStream.println(message);
            }

#####Client Side
On client side, instead of drawing in DrawArea onMouseMotion, we send the data to server, then server responds, and the line is drawn.

		addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // coord x,y when drag mouse
                currentX = e.getX();
                currentY = e.getY();
                if (g2 != null) {
                    PrintStream printStream = null;
                    try {
                        printStream = new PrintStream(ClientThread.socket.getOutputStream());
                        String hex = "#"+Integer.toHexString(g2.getColor().getRGB()).substring(2);
                        printStream.println("cx:" + currentX +
                                            "cy:" + currentY +
                                            "ox:" + oldX +
                                            "oy:" + oldY +
                                            "hex:" + hex);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    // store current coords x,y as olds x,y
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });          

####Conclusion
Before implementing this project, I was not sure how exactly that should work, and if I will be able to send the drawing data to server and back to all others. Then after creating the simple paint application, I saw where and how exactly this has to happen. Also I was happy to see that on client side, the drawing has no delay at all(but in fact, the data goes to server and comes back). 

For future, I would create an application that can be server and client at the same time; when the application is started, it checks for server on desired port and connects to it, thus becaming a client. But if no server is found, the application starts listenning for new connections and becomes the server; so there would be only one application, and code will not be doubled. 


####Keywords
client, server, UDP, BSD sockets, protocol, whiteboard, broadcast

