/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 7
 * @version				March 29, 2016
 * @project-description	Store data from http://www.omdbapi.com/ and store it to SQLite Database.
 * @class-name			ViewEntryActivity.java
 * @class-description	View / Edit data stored in the database.
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

package edu.asu.bscs.tkbrocke.lab_7;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;

import edu.asu.bscs.tkbrocke.lab_7.DatabaseTemplate.MovieDatabase;
import edu.asu.bscs.tkbrocke.lab_7.R;

public class ViewEntryActivity extends AppCompatActivity {

    EditText title, year, rated, released, runtime, actors, genre, plot;
    ImageView iv;
    ProgressBar pb;
    MovieDatabase database;
    long movieID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie_details);
        Intent callingIntent = getIntent();

        database = new MovieDatabase(this);

        movieID = callingIntent.getLongExtra("id", -1);
        if (movieID < 0) {
            Toast.makeText(this, "Error: Movie ID does not exist", Toast.LENGTH_SHORT).show();
            finish();
        }

        title = (EditText) findViewById(R.id.title);
        year = (EditText) findViewById(R.id.year);
        rated = (EditText) findViewById(R.id.rated);
        released = (EditText) findViewById(R.id.released);
        runtime = (EditText) findViewById(R.id.runtime);
        genre = (EditText) findViewById(R.id.genre);
        actors = (EditText) findViewById(R.id.actors);
        plot = (EditText) findViewById(R.id.plot);
        iv = (ImageView) findViewById(R.id.movie_poster);
        pb = (ProgressBar) findViewById(R.id.poster_progress);

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDatabase.TABLE_MOVIES + " WHERE " + MovieDatabase.ID + " = " + movieID, null);

        if (cursor.moveToFirst()){
            title.setText(cursor.getString(MovieDatabase.COLUMN_TITLE));
            year.setText(cursor.getString(MovieDatabase.COLUMN_YEAR));
            rated.setText(cursor.getString(MovieDatabase.COLUMN_RATED));
            released.setText(cursor.getString(MovieDatabase.COLUMN_RELEASED));
            runtime.setText(cursor.getString(MovieDatabase.COLUMN_RUNTIME));
            genre.setText(cursor.getString(MovieDatabase.COLUMN_GENRE));
            actors.setText(cursor.getString(MovieDatabase.COLUMN_ACTORS));
            plot.setText(cursor.getString(MovieDatabase.COLUMN_PLOT));
            new DownloadPosterTask().execute(cursor.getString(MovieDatabase.COLUMN_POSTER));
        }
        else {
            Toast.makeText(this, "Error loading movie data", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                if (title.length() > 0 && year.length() > 0  && rated.length() > 0 && released.length() > 0 &&
                        runtime.length() > 0 && genre.length() > 0 && actors.length() > 0 && plot.length() > 0){
                    ContentValues cv = new ContentValues();
                    cv.put(MovieDatabase.KEY_TITLE, title.getText().toString());
                    cv.put(MovieDatabase.KEY_YEAR, year.getText().toString());
                    cv.put(MovieDatabase.KEY_RATED, rated.getText().toString());
                    cv.put(MovieDatabase.KEY_RELEASED, released.getText().toString());
                    cv.put(MovieDatabase.KEY_RUNTIME, runtime.getText().toString());
                    cv.put(MovieDatabase.KEY_GENRE, genre.getText().toString());
                    cv.put(MovieDatabase.KEY_ACTORS, actors.getText().toString());
                    cv.put(MovieDatabase.KEY_PLOT, plot.getText().toString());
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.update(MovieDatabase.TABLE_MOVIES, cv, MovieDatabase.ID + "=" + movieID, null);
                    db.close();
                    finish();
                }
                else {
                    Toast.makeText(this, "You must enter data into all fields first!", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class DownloadPosterTask extends AsyncTask<String, Void, Bitmap> {

        public DownloadPosterTask() {
        }

        @Override
        protected void onPreExecute(){
            iv.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... input) {
            String url = input[0];
            Bitmap posterBM = null;
            try {
                InputStream is = new java.net.URL(url).openStream();
                posterBM = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                Log.e("DownloadPosterTask", "Error getting image");
            }
            return posterBM;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if(iv != null && pb != null) {
                iv.setVisibility(View.VISIBLE);
                pb.setVisibility(View.INVISIBLE);
                if(result != null){
                    iv.setImageBitmap(result);
                } else {
                    iv.setImageResource(R.drawable.reel);
                }
            }
        }
    }
}
