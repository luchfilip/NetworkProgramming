### Keylogger Application

####Objectives
  * Understand the architecture of OSX and figure out how a keylogger can be implemented on this platform.
  * Write a simple keylogger that will capture the user's keystrokes and display them on the screen or write them to a file.

####Introduction
Considering the fact that I have done all previews laboratory works in `Java`, 
it is easily predictable that I would do this one in `Java` as well.

Upon researching on how to implement a keylogger in java, I have seen that basically, one should not
 be able to implement a keylogger in Java, because it would change the idea of what Java was actually made for and 
 its way of working. Java can't read anything outside of JVM (Java Virtual Machine).
 
####Developing a Keylogger in Java
  
So, in order to achieve this, one would have to use a combination of Java and native code, using JNI(Java Native Interface)
to call the native code.

I have found a library called `jnativehook` which actually did this, for all platforms. It uses `libJNativeHook` for 
every platform, see screenshot of hierarchy: 

![jnativehook](http://i.imgur.com/8nsfS9Y.png "jnativehook")

So, for OSX it uses `libJNativeHook.dylib`. 

I used this library in order to get keystrokes from System, and then save them to a file, `log.txt`. 

Also, by using a timer, I am taking the screenshot of the display every 10 seconds, but I limited the number of screenshots
to be made, to 10. 

When the user presses `ESC`, the application terminates itself.

See source code in order to check the implementation, the logs and screenshots.
 

####Conclusion
A Keylogger can be used to capture all user inputs, basically malware creators would focus on capturing Credit Card data, 
usernames:passwords and even private messages. So, there are more types of keyloggers:

  * all keystrokes
  * only capture data for accounts like paypal, moneygram, bitcoin etc.
  * only capture data when user tries to pay with credit card
  
This of course is a basic keylogger, and it will not run on a guest account, as it requires additional access, which 
only an admin can allow. When I first ran this project, OSX blocked the access:

![access](http://i.imgur.com/DgGw9RE.png "access")

I had to allow my IDE, which runs the java application, to control the computer. 

In order to check if there is any program which logs user data, I would suggest the following:

   * Check which programs have access to control the system
   * The captured data has to be sent to a server, else it would make no sense for the developer to implement it. So, it would be a good idea to close all applications, and analyze all open internet connections, on osx you would type the following in terminal: 
   
        lsof -i | grep -E "(LISTEN|ESTABLISHED)"
       
       
####Resources
jnativehook: https://github.com/kwhat/jnativehook 


#### Keywords
Privacy, keylogger, identity theft, audit, hooks
