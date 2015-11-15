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


`DFEEL.20` enter away mode
`3FEEL.WEED.0` change nickname from `FEEL` to `WEED`
`2#Main.WEED.m essage.` user `WEED`, send message `message` to channel `Main`
`2WEED.WEED. private.` send message `private` from `WEED` to `WEED` 
`BNewTopic (WEE D).` change topic to `NewTopic (WEED)
`5WEED.#UTM. 0` close channel `UTM`
`4WEED.#UTM.20` join channel `UTM`




##### Keywords
UDP, IP, BSD sockets, protocol, reverse engineering, sniffing, privacy, spoofing
