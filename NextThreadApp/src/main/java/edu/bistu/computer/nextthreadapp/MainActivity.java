package edu.bistu.computer.nextthreadapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + 10 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    while (!Thread.interrupted()) {
                        synchronized (this) {
                            try {
                                wait();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        Button startService = (Button) findViewById(R.id.start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread myThread = new Thread(null, runnable, "WorkThread");
                myThread.start();
            }
        });
    }
}
