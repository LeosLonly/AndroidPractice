package edu.bistu.computer.handlerapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView resultText = (TextView) findViewById(R.id.display_result);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                resultText.setText(msg.arg1 + "");
            }
        };

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int process = 0;
                while (process <= 100) {
                    Message message = new Message();
                    message.arg1 = process;
                    handler.sendMessage(message);
                    process += 10;
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Message message = handler.obtainMessage();
                message.arg1 = -1;
                handler.sendMessage(message);
            }
        };

        Button start = (Button) findViewById(R.id.start_service);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(null, runnable, "WorkThread");
                thread.start();
            }
        });
    }
}
