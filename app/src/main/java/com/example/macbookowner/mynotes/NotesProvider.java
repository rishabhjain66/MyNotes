package com.example.macbookowner.mynotes;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by rishabhjain on 15-11-05.
 */
public class NotesProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.macbookowner.mynotes";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
private SQLiteDatabase database;

    //Static : when you access any another method of the classs. run all times whhen we access another method rather then main.
    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH , NOTES);
        uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#" , NOTES_ID);

    }



    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
         database = helper.getWritableDatabase();


        return true;
    }

    //@Nullable
    @Override
    public Cursor query(Uri uri, String[] Projection, String selection, String[] selectionArgs, String sortOrder) {
        // select *
        // from notes
        // order by note_created DESC
        return database.query(DBOpenHelper.TABLE_NOTES,DBOpenHelper.ALL_COLUMNS,selection,null,null,null,DBOpenHelper.NOTE_CREATED + " DESC");

    }

    //@Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //@Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        try {
            long id = database.insert(DBOpenHelper.TABLE_NOTES,null , values);
            return Uri.parse(BASE_PATH + "/" + id);
        } catch (Exception e){
            e.printStackTrace();

        }
        return null;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_NOTES,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectArgs) {
        return database.update(DBOpenHelper.TABLE_NOTES, values, selection, selectArgs);

    }
}
