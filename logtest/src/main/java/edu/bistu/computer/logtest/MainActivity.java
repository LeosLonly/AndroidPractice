package edu.bistu.computer.logtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG,"Verbose Message");
        Log.d(TAG,"Debug Message");
        Log.w(TAG,"Warnig Message");
        Log.i(TAG,"Info Message");
        Log.e(TAG,"Error Message");
    }
}
