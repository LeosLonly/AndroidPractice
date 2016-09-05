package edu.bistu.computer.wordstest3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/3.
 */
public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<Map<String,String>> wordMap = (ArrayList<Map<String, String>>) bundle.getSerializable("result");

        SimpleAdapter adapter = new SimpleAdapter(this, wordMap, R.layout.word_item_main,
                new String[]{Words.Word._ID,Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.tv_id,R.id.tv_word, R.id.tv_meaning, R.id.tv_sample});

        ListView list = (ListView) findViewById(R.id.lv_activity_search);

        list.setAdapter(adapter);
    }
}
