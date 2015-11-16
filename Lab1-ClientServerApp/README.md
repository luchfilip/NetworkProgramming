###Simple Server/Client BSD Socket Application

####Generic requirements
The program must rely on the BSD sockets API, not some other library which is an abstraction on top of BSD sockets.

TCP must be used at the transport layer.

IP must be used at the network layer.

####Server Features

Server creates a thread for every connection on given port. It receives commands from all clients, and then responds with the coresponding answer to the client that has sent the command.

Server Commands:
   * `%Hastalavista`  if the server receives this command, it will terminate the connection and shut itself down;
   * `%Time` sends time on server
   * `WhoAmI` sends client name (something like connection name)
   * `WhoAreYou` sends server username
   * `SystemData` sends system properties
   * `?` message containing `?`, server sends `42`
   * any other command - sends "Can you elaborate on that?"

####Client Features
Upon starting the client app, it connects to the give server on given port. Then a list of commands are printed on console; this is a welcome message from server. 

When sending a command, the client prints the response in console. 

When the server is shut down, all clients terminate themselves as well.

####Conclusion
While creating a Server/Client Application using BSD Sockets, I understood how simple and fast, can a communication be established between two applications/devices. The tricky part was to create multi-threaded server; I had to refactor everything and adapt, so that the feature works as expected. 


####Keywords
client, server, TCP, IP, BSD sockets