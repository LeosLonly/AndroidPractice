package edu.bistu.computer.components;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox book = (CheckBox) findViewById(R.id.book);
        CheckBox movice = (CheckBox) findViewById(R.id.movice);
        CheckBox music = (CheckBox) findViewById(R.id.music);

        RadioButton male = (RadioButton) findViewById(R.id.radio_male);
        final TextView resultText = (TextView) findViewById(R.id.text_result);
        final TextView saveRex = (TextView) findViewById(R.id.save_rex);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button change = (Button) findViewById(R.id.change_text);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("The text after the change");
            }
        });

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    saveRex.setText("男");
                }else{
                    saveRex.setText("女");
                }
            }
        });

    }

    public void onCheckboxClicked(View view){
        int id = view.getId();
        switch (id){
            case R.id.movice:

        }
    }
}
