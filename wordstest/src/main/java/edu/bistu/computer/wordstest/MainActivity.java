package edu.bistu.computer.wordstest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private WordsHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDialog();
            }
        });

        ListView listView = (ListView) findViewById(R.id.wordlist);
        registerForContextMenu(listView);

        helper = new WordsHelper(this);

        ArrayList<Map<String, String>> wordMap = getAll();
        setWordsListView(wordMap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.word_search:
                SearchDialg();
                return true;
            case R.id.word_add:
                InsertDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_wordlist, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TextView tvId;
        TextView tvWord;
        TextView tvMeaning;
        TextView tvSample;
        AdapterView.AdapterContextMenuInfo info;
        View view;

        switch (item.getItemId()) {
            case R.id.word_delete:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                view = info.targetView;
                tvId = (TextView) view.findViewById(R.id.tv_id);
                if (tvId != null) {
                    String idText = tvId.getText().toString();
                    DeleteDialog(idText);
                }
                break;
            case R.id.word_update:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                view = info.targetView;
                tvId = (TextView) view.findViewById(R.id.tv_id);
                tvWord = (TextView) view.findViewById(R.id.tv_word);
                tvMeaning = (TextView) view.findViewById(R.id.tv_meaning);
                tvSample = (TextView) view.findViewById(R.id.tv_sample);
                if (tvId != null && tvWord != null && tvMeaning != null && tvSample != null) {
                    String idText = tvId.getText().toString();
                    String wordText = tvWord.getText().toString();
                    String meaningText = tvMeaning.getText().toString();
                    String sampleText = tvSample.getText().toString();
                    UpdateDialog(idText, wordText, meaningText, sampleText);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    private void InsertDialog() {
        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.word_insert_main, null);
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.add_word))
                .setView(linearLayout)
                .setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newWord = ((EditText) linearLayout.findViewById(R.id.et_word)).getText().toString();
                        String newMeaning = ((EditText) linearLayout.findViewById(R.id.et_meaning)).getText().toString();
                        String newSample = ((EditText) linearLayout.findViewById(R.id.et_sample)).getText().toString();

                        insertWordUseSql(newWord, newMeaning, newSample);

                        ArrayList<Map<String, String>> wordMap = getAll();
                        setWordsListView(wordMap);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }

    private void SearchDialg() {
        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.word_search_main, null);
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.search_word))
                .setView(linearLayout)
                .setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String word = ((EditText) linearLayout.findViewById(R.id.et_search)).getText().toString();

                        ArrayList<Map<String, String>> wordMap = searchWordUseSql(word);

                        if (wordMap.size() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("result", wordMap);
                            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.match),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton(getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }

    private void DeleteDialog(final String idText) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.delete_word))
                .setMessage(getResources().getString(R.string.back))
                .setPositiveButton(getResources().getString(R.string.btn_ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteWordUseSql(idText);
                                setWordsListView(getAll());
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.btn_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                .create()
                .show();
    }

    private void UpdateDialog(final String idText, final String wordText, final String meaningText, final String sampleText) {
        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.word_insert_main, null);
        ((EditText) linearLayout.findViewById(R.id.et_word)).setText(wordText);
        ((EditText) linearLayout.findViewById(R.id.et_meaning)).setText(meaningText);
        ((EditText) linearLayout.findViewById(R.id.et_sample)).setText(sampleText);
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.update_word))
                .setView(linearLayout)
                .setPositiveButton(getResources().getString(R.string.btn_ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String newWordText = ((EditText) linearLayout.findViewById(R.id.et_word)).getText().toString();
                                String newMeaningText = ((EditText) linearLayout.findViewById(R.id.et_meaning)).getText().toString();
                                String newSampleText = ((EditText) linearLayout.findViewById(R.id.et_sample)).getText().toString();

                                updateWordUseSql(idText, newWordText, newMeaningText, newSampleText);
                                setWordsListView(getAll());
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.btn_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                .create()
                .show();
    }

    private void insertWordUseSql(String newWord, String newMeaning, String newSample) {
        String sql = "insert into " + Words.Word.TABLE_NAME + "(" + Words.Word.COLUMN_NAME_WORD
                + "," + Words.Word.COLUMN_NAME_MEANING + "," + Words.Word.COLUMN_NAME_SAMPLE + ")"
                + "values(?,?,?)";
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL(sql, new String[]{newWord, newMeaning, newSample});
    }

    private ArrayList<Map<String, String>> searchWordUseSql(String word) {
        String sql = "select * from " + Words.Word.TABLE_NAME + " where " + Words.Word.COLUMN_NAME_WORD
                + " like ? order by " + Words.Word.COLUMN_NAME_WORD + " asc";
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{"%" + word + "%"});
        return ConvertCursor2List(cursor);
    }

    private void deleteWordUseSql(String wordId) {
        String sql = "delete from " + Words.Word.TABLE_NAME + " where " + Words.Word._ID + "='"
                + wordId + "'";
        SQLiteDatabase database = helper.getReadableDatabase();
        database.execSQL(sql);
    }

    private void updateWordUseSql(String newId, String newWord, String newMeaning, String newSample) {
        String sql = "update " + Words.Word.TABLE_NAME + " set " + Words.Word.COLUMN_NAME_WORD +
                "=?," + Words.Word.COLUMN_NAME_MEANING + "=?," + Words.Word.COLUMN_NAME_SAMPLE
                + "=? where " + Words.Word._ID + "=?";
        SQLiteDatabase database = helper.getReadableDatabase();
        database.execSQL(sql, new String[]{newWord, newMeaning, newSample, newId});
    }

    private void setWordsListView(ArrayList<Map<String, String>> items) {
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.word_item_main,
                new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.tv_id, R.id.tv_word, R.id.tv_meaning, R.id.tv_sample});

        ListView list = (ListView) findViewById(R.id.wordlist);

        list.setAdapter(adapter);
    }

    private ArrayList<Map<String, String>> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                Words.Word._ID,
                Words.Word.COLUMN_NAME_WORD,
                Words.Word.COLUMN_NAME_MEANING,
                Words.Word.COLUMN_NAME_SAMPLE
        };

        String sortOrder =
                Words.Word.COLUMN_NAME_WORD + " ASC";


        Cursor c = db.query(
                Words.Word.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return ConvertCursor2List(c);
    }

    private ArrayList<Map<String, String>> ConvertCursor2List(Cursor cursor) {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put(Words.Word._ID, String.valueOf(cursor.getInt(0)));
            map.put(Words.Word.COLUMN_NAME_WORD, cursor.getString(1));
            map.put(Words.Word.COLUMN_NAME_MEANING, cursor.getString(2));
            map.put(Words.Word.COLUMN_NAME_SAMPLE, cursor.getString(3));
            result.add(map);
        }
        return result;
    }
}
