### Reverse engineering a network protocol

####Objectives
	* Understand and document the protocol used by QuickChat - a chat program for local area networks
	* get used to reverse engineering
	* learn to appreciate the documentation, when you have it ;-)
	* learn to document activities

####Packet Sniffing
I had to use Windows from VirtualBox, because `QuickChat` is not available on OSX.

In order to analyze packets sent by QuickChat I followed the following steps:

1. I have installed WireShark
2. Started Quickchat 
3. Started Live Capture on Ethernet
4. Sent a message to myself (I dont have space for another Virtual Machine)
5. The I get this light blue(UDP) packet in wireshark: 
![UPD Packet Captured](http://i.imgur.com/fPFLnPR.jpg "UDP Packet")
6. As we can see, upon sending `mama` to myself, I could capture the packet. Let's see what inside.
7. by looking at detailed packet info, we can see:
  * IP: 10.0.2.15 (which is the virtual machine ip in its virtual network inside virtualBox)
  * Source port: 57447
  * Destination port: 8167
  * The data: `J2FEEL.FEEL.ma ma.` (I dont know what the space is doing between `ma` and `ma`, but this string should mean that a message is sent from FEEL to FEEl, and the message is `mama`)

![Packet Details](http://i.imgur.com/gH2AXSX.jpg "Packet Details")


#### Detailed events/actions

I have tried to capture different events and see how they are formatted. 

Here is the list of events/actions and their meaning:

   `DFEEL.20` enter away mode

   `3FEEL.WEED.0` change nickname from `FEEL` to `WEED`
   
   `2#Main.WEED.m essage.` user `WEED`, send message `message` to channel `Main`
   
   `2WEED.WEED. private.` send message `private` from `WEED` to `WEED` 
   
   `BNewTopic (WEE D).` change topic to `NewTopic (WEED)
   
   `5WEED.#UTM. 0` close channel `UTM`
   
   `4WEED.#UTM.20` join channel `UTM`

#### Conclusion
While working on with packets sniffing I understood how simply packets are sent in QuickChat and their notation. Of course this is a bad practice to send the packets without encrypting them, because someone could as well capture the packets and read private messages of other participants. I would suggest two ways to overcome this issue:

1. Send packets through an `SSL` certificate, which would encrypt the connection. In `Java` I would do it this way:

         System.setProperty("javax.net.ssl.trustStore", "clienttrust");
         SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
         Socket s = ssf.createSocket(ip_address, port);

2. Encrypt the data manually. So that the client would encrypt the data with a generated key before send the data, then the server would decrypt, and read the message. 

##### Keywords
UDP, IP, BSD sockets, protocol, reverse engineering, sniffing, privacy, spoofing
