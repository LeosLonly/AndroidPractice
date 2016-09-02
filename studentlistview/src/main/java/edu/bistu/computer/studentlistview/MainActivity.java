package edu.bistu.computer.studentlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String NAME = "name";
    private static final String ClASS = "class";
    private static final String NUMBER = "number";
    private static final String TELEPHONE = "telephone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] name = getResources().getStringArray(R.array.student_name);
        String[] classs = getResources().getStringArray(R.array.student_class);
        String[] number = getResources().getStringArray(R.array.student_number);
        String[] telephone = getResources().getStringArray(R.array.student_telephone);

        List<Map<String,Object>> items = new ArrayList<>();

        for(int i=0;i<name.length;i++){
            Map<String,Object> item = new HashMap<>();
            item.put(NAME,name[i]);
            item.put(ClASS,classs[i]);
            item.put(NUMBER,number[i]);
            item.put(TELEPHONE,telephone[i]);
            items.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,items,R.layout.item,
                new String[]{NAME,ClASS,NUMBER,TELEPHONE},
                new int[]{R.id.name,R.id.classs,R.id.number,R.id.telephone});

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
