package edu.bistu.computer.wordstest3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/2.
 */
public class WordsHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "words";
    private static final int DB_VERSION = 1;

    private static final String DB_CREATE_TABLE = "create table "
            + edu.bistu.computer.wordstest3.Words.Word.TABLE_NAME
            + "(" + edu.bistu.computer.wordstest3.Words.Word._ID + " integer primary key autoincrement,"
            + edu.bistu.computer.wordstest3.Words.Word.COLUMN_NAME_WORD + " text" + ","
            + edu.bistu.computer.wordstest3.Words.Word.COLUMN_NAME_MEANING + " text" + ","
            + edu.bistu.computer.wordstest3.Words.Word.COLUMN_NAME_SAMPLE + " text" + ")";

    private static final String DB_DELETE_TABLE = "DROP TABLE IF EXISTS "
            + edu.bistu.computer.wordstest3.Words.Word.TABLE_NAME;

    public WordsHelper(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DB_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
