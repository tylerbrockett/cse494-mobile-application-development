/*
 * @author Tyler Brockett   mailto:tylerbrockett@gmail.com
 * @project CSE 494 Lab 3 - Android
 * @version February 3, 2016
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

package edu.asu.bscs.tkbrocke.lab_5;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class EditEntryActivity extends AppCompatActivity {

    EditText json, title, year, rated, released, runtime, actors, plot;
    Spinner genre;
    ArrayAdapter<String> spinnerAdapter;

    String[] list;

    Movie movie;
    JSONObject movieJson;
    String movieJsonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie_details);

        Intent callingIntent = getIntent();

        list = new String[]{"Action", "Adventure", "Animation", "Biography", "Comedy", "Crime",
            "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Musical",
            "Mystery", "Romance", "Sci-Fi", "Sports", "Thriller", "War", "Western"};

        try{
            movieJsonString = callingIntent.getStringExtra("movie");
            movieJson = new JSONObject(movieJsonString);
            movie = new Movie(movieJson.toString());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error: No movie found by that title", Toast.LENGTH_SHORT).show();
            finish();
        }

        json = (EditText) findViewById(R.id.json);
        title = (EditText) findViewById(R.id.title);
        year = (EditText) findViewById(R.id.year);
        rated = (EditText) findViewById(R.id.rated);
        released = (EditText) findViewById(R.id.released);
        runtime = (EditText) findViewById(R.id.runtime);
        genre = (Spinner) findViewById(R.id.genre);
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        genre.setAdapter(spinnerAdapter);

        actors = (EditText) findViewById(R.id.actors);
        plot = (EditText) findViewById(R.id.plot);

        json.setText(movieJsonString);
        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        rated.setText(movie.getRated());
        released.setText(movie.getReleased());
        runtime.setText(movie.getRuntime());
        genre.setSelection(spinnerAdapter.getPosition(movie.getGenre()));
        actors.setText(movie.getActors());
        plot.setText(movie.getPlot());
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
                try{
                    if ( ! json.getText().toString().equals(movieJsonString)){
                        movieJson = new JSONObject(json.getText().toString());
                    }
                    else{
                        movieJson.put("Title", title.getText().toString());
                        movieJson.put("Year", year.getText().toString());
                        movieJson.put("Rated", rated.getText().toString());
                        movieJson.put("Released", released.getText().toString());
                        movieJson.put("Runtime", runtime.getText().toString());
                        movieJson.put("Genre", list[genre.getSelectedItemPosition()]);
                        movieJson.put("Actors", actors.getText().toString());
                        movieJson.put("Plot", plot.getText().toString());
                    }
                }catch(Exception e){
                    movieJson = new JSONObject();
                }

                ServerRequest request = new ServerRequest(this, MainActivity.ipAddress, ServerRequest.EDIT, new String[]{movieJson.toString()});
                new NetworkAsyncTask(this, "Successfully Edited Movie", "Failure Editing Movie").execute(request);
                finish();

                return true;
            case R.id.action_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
