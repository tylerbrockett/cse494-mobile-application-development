/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 7
 * @version				March 29, 2016
 * @project-description	Store data from http://www.omdbapi.com/ and store it to SQLite Database.
 * @class-name			MainActivity.java
 * @class-description   Displays list containing database contents to the user.
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

package edu.asu.bscs.tkbrocke.android_client;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import edu.asu.bscs.tkbrocke.android_client.CustomAdapter.CustomCursorAdapter;
import edu.asu.bscs.tkbrocke.android_client.DatabaseTemplate.MovieDatabase;

public class MainActivity extends AppCompatActivity {

    TextView numEntries;
    ListView listView;
    Cursor cursor;
    public static CustomCursorAdapter adapter;
    MovieDatabase database;
    SQLiteDatabase db;

    String DB_QUERY = "SELECT * FROM " + MovieDatabase.TABLE_MOVIES + " ORDER BY " + MovieDatabase.KEY_TITLE +" ASC;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numEntries = (TextView) findViewById(R.id.num_entries);
        listView = (ListView) findViewById(R.id.list_view);

        database = new MovieDatabase(this);
        db = database.getReadableDatabase();
        cursor = db.rawQuery(DB_QUERY, null);
        adapter = new CustomCursorAdapter(this, cursor, 0);
        listView.setAdapter(adapter);
        adapter.setOnDataChangeListener(new CustomCursorAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged(int size) {
                if (size == 1){
                    numEntries.setText("1 Movie");
                }
                else {
                    numEntries.setText(size + " Movies");
                }
            }
        });
        adapter.setOnDataDeletedListener(new CustomCursorAdapter.OnDataDeletedListener() {
            @Override
            public void onDataDeleted(Context c) {
                cursor = db.rawQuery(DB_QUERY, null);
                adapter.changeCursor(cursor);
            }
        });
        adapter.setOnDataAddedListener(new CustomCursorAdapter.OnDataAddedListener() {
            @Override
            public void onDataAdded() {
                cursor = db.rawQuery(DB_QUERY, null);
                adapter.changeCursor(cursor);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewMovie = new Intent(MainActivity.this, ViewEntryActivity.class);
                viewMovie.putExtra("id", id);
                startActivity(viewMovie);
            }
        });
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_new:
                Intent newEntry = new Intent(this, NewEntryActivity.class);
                startActivity(newEntry);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
