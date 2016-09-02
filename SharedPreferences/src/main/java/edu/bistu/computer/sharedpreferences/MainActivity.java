package edu.bistu.computer.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_FILE_NAME = "shared";

    private static final String KEY_STUDENT_NUMBER = "userNumber";
    private static final String KEY_STUDENT_NAME = "userNumber";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Button read = (Button) findViewById(R.id.read_from_sharedPreferences);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName = sharedPreferences.getString(KEY_STUDENT_NAME, null);
                String studentNumber = sharedPreferences.getString(KEY_STUDENT_NUMBER, null);

                if (studentName != null && studentNumber != null) {
                    Toast.makeText(MainActivity.this,
                            "姓名:" + studentName + "学号:" + studentNumber,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "没有输入", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button write = (Button) findViewById(R.id.write_to_sharedPreferences);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(KEY_STUDENT_NAME, "ZhangSan");
                editor.putString(KEY_STUDENT_NUMBER, "2001");

                editor.apply();
            }
        });
    }
}
