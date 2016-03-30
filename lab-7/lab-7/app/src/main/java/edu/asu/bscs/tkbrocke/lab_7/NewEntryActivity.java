/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 7
 * @version				March 29, 2016
 * @project-description	Store data from omdbapi.com and store it to SQLite Database.
 * @class-name			NewEntryActivity.java
 * @class-description	Allows user to either manually enter data or search the omdbapi.com server for data.
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
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import edu.asu.bscs.tkbrocke.lab_7.DatabaseTemplate.MovieDatabase;
import edu.asu.bscs.tkbrocke.lab_7.Network.HttpRequest;
import edu.asu.bscs.tkbrocke.lab_7.HelperClasses.Movie;

public class NewEntryActivity extends AppCompatActivity {

    EditText search, title, year, rated, released, runtime, actors, genre, plot, poster;
    ImageView iv;
    ProgressBar pb;
    Button add;
    MovieDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie_search);
        database = new MovieDatabase(this);

        search = (EditText)findViewById(R.id.movie_search);
        add = (Button)findViewById(R.id.btn_search);

        title = (EditText)findViewById(R.id.title);
        year = (EditText)findViewById(R.id.year);
        rated = (EditText)findViewById(R.id.rated);
        released = (EditText)findViewById(R.id.released);
        runtime = (EditText)findViewById(R.id.runtime);
        genre = (EditText)findViewById(R.id.genre);
        actors = (EditText)findViewById(R.id.actors);
        plot = (EditText)findViewById(R.id.plot);
        poster = (EditText)findViewById(R.id.poster);
        iv = (ImageView) findViewById(R.id.movie_poster);
        pb = (ProgressBar) findViewById(R.id.poster_progress);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NetworkAsyncTask(NewEntryActivity.this).execute(search.getText().toString().replace(" ", "%20"));
            }
        });
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
                    cv.put(MovieDatabase.KEY_POSTER, poster.getText().toString());
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.insert(MovieDatabase.TABLE_MOVIES, null, cv);
                    db.close();
                    MainActivity.adapter.dataAdded();
                    Toast.makeText(this, title.getText().toString() + " saved to database", Toast.LENGTH_LONG).show();
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

    public class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
        Context context;

        public NetworkAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... movieTitle) {
            String resultStr = "{\"Response\": \"False\"}";
            try {
                String urlString = "http://www.omdbapi.com/?t=" + movieTitle[0] + "&plot=short&r=json";
                Log.d("URL", urlString);
                HttpRequest conn = new HttpRequest(new URL(urlString), context);
                resultStr = conn.call(urlString);
            }catch (Exception ex){
                android.util.Log.d(this.getClass().getSimpleName(),"exception in remote call "+
                        ex.getMessage());
            }
            return resultStr;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jo = new JSONObject(result);

                if(jo.getBoolean("Response")){
                    Movie movie = new Movie(jo.toString());
                    title.setText(movie.getTitle());
                    year.setText(movie.getYear());
                    rated.setText(movie.getRated());
                    released.setText(movie.getReleased());
                    runtime.setText(movie.getRuntime());
                    actors.setText(movie.getActors());
                    genre.setText(movie.getGenre());
                    plot.setText(movie.getPlot());
                    poster.setText(movie.getPoster());
                    Toast.makeText(context, "Movie Found: " + movie.getTitle(), Toast.LENGTH_LONG).show();
                    new DownloadPosterTask().execute(movie.getPoster());
                }
                else {
                    Toast.makeText(context, "Could not find movie", Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                android.util.Log.d(this.getClass().getSimpleName(),"Exception: "+ex.getMessage());
            }
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
