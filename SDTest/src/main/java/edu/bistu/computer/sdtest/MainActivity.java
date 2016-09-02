package edu.bistu.computer.sdtest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

        Button btn_read = (Button) findViewById(R.id.read_from_sd);
        Button btn_write = (Button) findViewById(R.id.write_to_sd);

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BufferedWriter writer = null;
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    File file = Environment.getExternalStorageDirectory();
                    try {
                        File newFile = new File(file.getCanonicalPath() + "/" + FILE_NAME);
                        FileOutputStream out = new FileOutputStream(newFile);
                        writer = new BufferedWriter(new OutputStreamWriter(out));
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
            }
        });

        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BufferedReader reader = null;
                StringBuilder builder = new StringBuilder();
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    File file = Environment.getExternalStorageDirectory();
                    try {
                        File newFile = new File(file.getCanonicalPath() + "/" + FILE_NAME);
                        FileInputStream in = new FileInputStream(newFile);
                        reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        if ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }
}
