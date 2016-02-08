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

package edu.asu.bscs.tkbrocke.lab_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditMovieActivity extends AppCompatActivity {

    EditText json, title, year, rated, released, runtime, genre, actors, plot;
    MovieDescription movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        Intent callingIntent = getIntent();
        String movieTitle;

        try{
            movieTitle = callingIntent.getStringExtra("movie_title");
            movie = MainActivity.library.search(movieTitle);
            if (movie == null){
                Toast.makeText(this, "Search failed", Toast.LENGTH_SHORT).show();
                finish();
            }
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
        genre = (EditText) findViewById(R.id.genre);
        actors = (EditText) findViewById(R.id.actors);
        plot = (EditText) findViewById(R.id.plot);

        json.setText(movie.getJSON());
        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        rated.setText(movie.getRated());
        released.setText(movie.getReleased());
        runtime.setText(movie.getRuntime());
        genre.setText(movie.getGenre());
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
                if ( ! json.getText().toString().equals(movie.getJSON())){
                    movie.setJSON(json.getText().toString());
                }
                else{
                    movie.setTitle(title.getText().toString());
                    movie.setYear(year.getText().toString());
                    movie.setRated(rated.getText().toString());
                    movie.setReleased(released.getText().toString());
                    movie.setRuntime(runtime.getText().toString());
                    movie.setGenre(genre.getText().toString());
                    movie.setActors(actors.getText().toString());
                    movie.setPlot(plot.getText().toString());
                    movie.generateJSON();
                }
                MainActivity.library.sort();
                MainActivity.adapter.notifyDataSetChanged();
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
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
