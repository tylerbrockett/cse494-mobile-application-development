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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NewEntryActivity extends AppCompatActivity {

    EditText json, title, year, rated, released, runtime, genre, actors, plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        json = (EditText) findViewById(R.id.json);
        title = (EditText) findViewById(R.id.title);
        year = (EditText) findViewById(R.id.year);
        rated = (EditText) findViewById(R.id.rated);
        released = (EditText) findViewById(R.id.released);
        runtime = (EditText) findViewById(R.id.runtime);
        genre = (EditText) findViewById(R.id.genre);
        actors = (EditText) findViewById(R.id.actors);
        plot = (EditText) findViewById(R.id.plot);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_entry_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                MovieDescription newMovie;
                if ( ! json.getText().toString().isEmpty()){
                    newMovie = new MovieDescription(json.getText().toString());
                }
                else{
                    newMovie = new MovieDescription(
                            title.getText().toString(),
                            year.getText().toString(),
                            rated.getText().toString(),
                            released.getText().toString(),
                            runtime.getText().toString(),
                            genre.getText().toString(),
                            actors.getText().toString(),
                            plot.getText().toString());
                }
                MainActivity.library.add(newMovie);
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
