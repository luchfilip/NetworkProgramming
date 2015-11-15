package md.luchfilip;

public class Main {

    public static void main(String[] args) {
        new Thread(new ClientThread()).start();
        new Paint().show();
    }
}
