package md.luchfilip;


/**
 * Created by luchfilip on 11/15/15.
 *
 */
public class RequestThread implements Runnable {

    int adviceNumber;

    public RequestThread(int adviceNumber) {
        this.adviceNumber = adviceNumber;
    }

    public void run() {
        Utils.getAdvice(adviceNumber);
        Thread.currentThread().interrupt();
        Main.threadCounter++;

        if (Main.threadCounter+1 == Main.advicesCount) {
            // finished last request
            Main.stopTime = System.nanoTime();
            double executionSeconds = (double)(Main.stopTime-Main.startTime) / 1000000000.0;
            System.out.println("Execution time: " + executionSeconds + " seconds");
            System.out.println("Retry count: " + Main.retryCount);
        }
    }
}
