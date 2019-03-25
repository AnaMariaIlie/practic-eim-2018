package ro.pub.cs.systems.eim;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

public class ProcessingThread extends Thread {

    private int firstNumber;
    private int secondNumber;
    private boolean isRunning = true;
    private Context context;
    private double arithmeticMean;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.context = context;
        arithmeticMean = (double)(firstNumber + secondNumber)/2;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.arithmeticMean");
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + arithmeticMean);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}
