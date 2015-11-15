package md.luchfilip;

public class Main {

    public static Thread serverThread;

    public static void main(String[] args) {

        serverThread = new Thread(new ServerThread());
        serverThread.start();

    }
}
