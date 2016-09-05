package edu.bistu.computer.wordstest3;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

public class MyWordProvider extends ContentProvider {

    private static final int MULTIPLE_WORDS = 1; //UriMathcher匹配结果码
    private static final int SINGLE_WORD = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private WordsHelper helper;


    static {
        uriMatcher.addURI(Words.AUTHORITY, Words.Word.PATH_SINGLE, SINGLE_WORD);
        uriMatcher.addURI(Words.AUTHORITY, Words.Word.PATH_MULTIPLE, MULTIPLE_WORDS);
    }

    public MyWordProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        SQLiteDatabase database = helper.getReadableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                count = database.delete(Words.Word.TABLE_NAME, selection, selectionArgs);
                break;
            case SINGLE_WORD:
                String where = Words.Word._ID + "=" + uri.getPathSegments().get(1);
                count = database.delete(Words.Word.TABLE_NAME, where, selectionArgs);
                break;
            default:
                break;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                return Words.Word.MINE_TYPE_MULTIPLE;
            case SINGLE_WORD:
                return Words.Word.MINE_TYPE_SINGLE;
            default:
                throw new IllegalArgumentException("Unknow Uri:" + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        SQLiteDatabase database = helper.getReadableDatabase();
        long id = database.insert(Words.Word.TABLE_NAME, null, values);
        if (id > 0) {
            Uri newUri = ContentUris.withAppendedId(Words.Word.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        helper = new WordsHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        SQLiteDatabase database = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Words.Word.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                Cursor cursor;
                cursor = database.query(Words.Word.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                return cursor;
            case SINGLE_WORD:
                builder.appendWhere(Words.Word._ID + "="
                        + uri.getPathSegments().get(1));
                return builder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.

        SQLiteDatabase database = helper.getReadableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                count = database.update(Words.Word.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SINGLE_WORD:
                String where = Words.Word._ID + "=" + uri.getPathSegments().get(1);
                count = database.update(Words.Word.TABLE_NAME, values, where, selectionArgs);
                break;
            default:
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
