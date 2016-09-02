package edu.bistu.computer.filetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "student";
    private static final String CONTENT = "学号:2014011467 姓名:田朋朋";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_read = (Button) findViewById(R.id.read_from_file);
        Button btn_write = (Button) findViewById(R.id.write_to_file);

        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BufferedReader reader;
                StringBuilder builder = new StringBuilder();
                try {
                    FileInputStream fileInputStream = openFileInput(FILE_NAME);
                    reader = new BufferedReader(new InputStreamReader(fileInputStream));
                    String line;
                    if ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_write.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                BufferedWriter writer = null;
                try {
                    FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    writer.write(CONTENT);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
