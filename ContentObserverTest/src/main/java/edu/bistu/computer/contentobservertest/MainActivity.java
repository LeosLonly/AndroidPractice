package edu.bistu.computer.contentobservertest;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI,
                true, new MyContentObserver(new Handler()));
    }

    class MyContentObserver extends ContentObserver {

        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Toast.makeText(MainActivity.this, "Contacts data has changed",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
