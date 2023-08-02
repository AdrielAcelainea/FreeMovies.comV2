package sg.edu.rp.c346.id22016845.freemoviescom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER=1;
    private static final String DATABASE_NAME="movies.db";
    private static final String TABLE_TASK = "Movies";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "movieTitle";
    private static final String COLUMN_GENRE="movieGenre";
    private static final String COLUMN_YEAR="movieYear";
    private static final String COLUMN_RATING="movieRating";
    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_RATING + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insertMovies(String movieTitle, String movieGenre, int movieYear, String movieRating) {

        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_TITLE, movieTitle);
        // Store the column name as key and the date as value
        values.put(COLUMN_GENRE, movieGenre);
        values.put(COLUMN_YEAR, movieYear);
        values.put(COLUMN_RATING, movieRating);
        // Insert the row into the TABLE_TASK
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result);
        return result;
    }
    public ArrayList<Movies> getMovies(String keyword) {
        ArrayList<Movies> Movies = new ArrayList<Movies>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_RATING + " Like ?";
        String[] args = {"%" + keyword + "%"};
        Cursor cursor = db.query(TABLE_TASK, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movies obj = new Movies(id, title, genre, year, rating);
                Movies.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Movies;
    }
    public ArrayList<Movies> getAllMovies() {
        ArrayList<Movies> Movies = new ArrayList<Movies>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};

        Cursor cursor = db.query(TABLE_TASK, columns, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movies obj = new Movies(id, title, genre, year, rating);
                Movies.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Movies;
    }
    public int updateMovie(Movies data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_GENRE, data.getGenre());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_RATING, data.getRating());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }

    public int deleteMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_TASK, condition, args);
        db.close();
        return result;
    }

}
