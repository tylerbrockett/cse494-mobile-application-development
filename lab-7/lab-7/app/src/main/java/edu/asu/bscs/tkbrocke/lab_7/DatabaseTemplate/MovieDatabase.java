/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 7
 * @version				March 29, 2016
 * @project-description	Store data from http://www.omdbapi.com/ and store it to SQLite Database.
 * @class-name			MovieDatabase.java
 * @class-description	Template and helper for SQLite Database
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Tyler Brockett
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.asu.bscs.tkbrocke.lab_7.DatabaseTemplate;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import edu.asu.bscs.tkbrocke.lab_7.HelperClasses.JSONHelper;
import edu.asu.bscs.tkbrocke.lab_7.MainActivity;
import edu.asu.bscs.tkbrocke.lab_7.HelperClasses.Movie;
import edu.asu.bscs.tkbrocke.lab_7.R;

public class MovieDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;
    Context context;

    public static final String TABLE_MOVIES = "Movies";
    public static final String ID = "_id";

    public static final String KEY_TITLE = "title";
    public static final String KEY_YEAR = "year";
    public static final String KEY_RATED = "rated";
    public static final String KEY_RELEASED = "released";
    public static final String KEY_RUNTIME = "runtime";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_ACTORS = "actors";
    public static final String KEY_PLOT = "plot";
    public static final String KEY_POSTER = "poster";

    public static final int COLUMN_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_YEAR = 2;
    public static final int COLUMN_RATED = 3;
    public static final int COLUMN_RELEASED = 4;
    public static final int COLUMN_RUNTIME = 5;
    public static final int COLUMN_GENRE = 6;
    public static final int COLUMN_ACTORS = 7;
    public static final int COLUMN_PLOT = 8;
    public static final int COLUMN_POSTER = 9;

    public static final String CREATE_TABLE_MOVIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MOVIES + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_TITLE + " TEXT, " +
                    KEY_YEAR + " TEXT, " +
                    KEY_RATED + " TEXT, " +
                    KEY_RELEASED + " TEXT, " +
                    KEY_RUNTIME + " TEXT, " +
                    KEY_GENRE + " TEXT, " +
                    KEY_ACTORS + " TEXT, " +
                    KEY_PLOT + " TEXT, " +
                    KEY_POSTER + " TEXT);";
    public static final String DROP_TABLE_MOVIES = "DROP TABLE IF EXISTS " + TABLE_MOVIES;


    public MovieDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_MOVIES);
        new CopyDatabaseAsyncTask(context).execute(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // Drop it for now if upgrading
        database.execSQL(DROP_TABLE_MOVIES);
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion){
        // Drop it for now if downgrading
        database.execSQL(DROP_TABLE_MOVIES);
    }


    class CopyDatabaseAsyncTask extends AsyncTask<SQLiteDatabase, Integer, Void> {
        Context context;
        Dialog dialog;

        public CopyDatabaseAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.show();
        }

        @Override
        protected Void doInBackground(SQLiteDatabase... database) {
            try {
                InputStream is = context.getResources().openRawResource(R.raw.movies);
                JSONHelper jr = new JSONHelper(is, "Movies");
                for (int i = 0; i < jr.getArray().length(); i++){
                    Movie movie = new Movie(jr.getArray().getJSONObject(i).toString());
                    ContentValues cv = new ContentValues();
                    cv.put(KEY_TITLE, movie.getTitle());
                    cv.put(KEY_YEAR, movie.getYear());
                    cv.put(KEY_RATED, movie.getRated());
                    cv.put(KEY_RELEASED, movie.getReleased());
                    cv.put(KEY_RUNTIME, movie.getRuntime());
                    cv.put(KEY_GENRE, movie.getGenre());
                    cv.put(KEY_ACTORS, movie.getActors());
                    cv.put(KEY_PLOT, movie.getPlot());
                    cv.put(KEY_POSTER, movie.getPoster());
                    database[0].insert(TABLE_MOVIES, null, cv);
                }
            } catch (Exception e){
                Log.d("MovieDatabase", "Database initialization FAILED!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (dialog != null){
                dialog.cancel();
            }
            MainActivity.adapter.dataAdded();
        }
    }
}
