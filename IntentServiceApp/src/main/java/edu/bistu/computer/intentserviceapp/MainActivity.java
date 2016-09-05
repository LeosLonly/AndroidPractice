package edu.bistu.computer.intentserviceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startMethod1 = (Button) findViewById(R.id.start_method1);
        Button startMethod2 = (Button) findViewById(R.id.start_method2);

        startMethod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyIntentService.startActionFoo(MainActivity.this, "1", "2");
            }
        });

        startMethod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyIntentService.startActionBaz(MainActivity.this, "3", "4");
            }
        });
    }
}
