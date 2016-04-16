/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - Android
 * @version				March 16, 2016
 * @project-description	Use Android client to get/post data from/to JSON-RPC Server
 * @class-name			NewEntryActivity.java
 * @class-description	Allows user to create new Movie and save it to the server.
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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class NewEntryActivity extends AppCompatActivity {

    EditText json, title, year, rated, released, runtime, actors, plot;
    Spinner genre;
    ArrayAdapter<String> spinnerAdapter;

    String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        list = new String[]{"Action", "Adventure", "Animation", "Biography", "Comedy", "Crime",
                "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Musical",
                "Mystery", "Romance", "Sci-Fi", "Sports", "Thriller", "War", "Western"};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie_details);
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
        genre.setSelection(0);

        actors = (EditText) findViewById(R.id.actors);
        plot = (EditText) findViewById(R.id.plot);
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
                Movie newMovie;
                if ( ! json.getText().toString().isEmpty()){
                    newMovie = new Movie(json.getText().toString());
                }
                else{
                    newMovie = new Movie(
                            title.getText().toString(),
                            "",
                            year.getText().toString(),
                            rated.getText().toString(),
                            released.getText().toString(),
                            runtime.getText().toString(),
                            list[genre.getSelectedItemPosition()],
                            actors.getText().toString(),
                            plot.getText().toString());
                }

                ServerRequest request = new ServerRequest(this, MainActivity.ipAddress, ServerRequest.ADD, new String[]{newMovie.getJSON()});
                new NetworkAsyncTask(this, "Successfully Added Movie", "Failure Adding Movie").execute(request);
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
