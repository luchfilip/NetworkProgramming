package md.luchfilip;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    static String advice = "";
    static int advicesCount = 50;
    static int requestDelayMs = 0;
    static long startTime, stopTime;
    static int threadCounter = 0;
    static int retryCount = 0;
    public static void main(String[] args) {
        startTime = System.nanoTime();

        for (int i = 1; i < advicesCount; i++) {
//            startQueueAdviceGetter(i);
//            startAllThreadAdviceGetter(i);

            startMultiThreadingTaskGetter(i);
        }
    }

    public static void startQueueAdviceGetter(int i) {
        Utils.getAdvice(i);
    }

    public static void startAllThreadAdviceGetter(int i) {
        RequestThread requestThread = new RequestThread(i);
        new Thread(requestThread).start();
    }

    public static void startMultiThreadingTaskGetter(final int i) {
        requestDelayMs = 50*i;
        Timer timer = new Timer();
        TimerTask action = new TimerTask() {
            public void run() {
                RequestThread requestThread = new RequestThread(i);
                new Thread(requestThread).start();
            }
        };
        timer.schedule(action, requestDelayMs); //this starts the task
    }

}