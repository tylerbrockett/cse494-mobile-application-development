/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - Android
 * @version				March 16, 2016
 * @project-description	Use Android client to get/post data from/to JSON-RPC Server
 * @class-name			MainActivity.java
 * @class-description	Displays the list of movies to the user. Allows users to click on a movie to view/edit details.
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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView numEntries;
    ListView listView;
    public static CustomBaseAdapter adapter;
    public static String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = getString(R.string.defaulturl);

        numEntries = (TextView) findViewById(R.id.num_entries);
        listView = (ListView) findViewById(R.id.list_view);

        adapter = new CustomBaseAdapter(this);
        adapter.setOnDataChangeListener(new CustomBaseAdapter.OnDataChangeListener() {
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
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editMovie = new Intent(MainActivity.this, EditEntryActivity.class);
                editMovie.putExtra("movie", adapter.getItem(position).getJSON());
                startActivity(editMovie);
            }
        });
        refresh();
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
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_reset:
                reset();
                return true;
            case R.id.action_change_ip:
                Intent changeIP = new Intent(this, ActivitySetIP.class);
                startActivity(changeIP);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refresh(){
        ServerRequest request = new ServerRequest(this, MainActivity.ipAddress, ServerRequest.GET_ALL, new String[]{});
        new NetworkAsyncTask(this, "Successfully Loaded Movies", "Failure Loading Movies").execute(request);
    }

    private void reset(){
        ServerRequest request = new ServerRequest(this, MainActivity.ipAddress, ServerRequest.RESET, new String[]{});
        new NetworkAsyncTask(this, "Successfully Reset Server", "Failure Resetting Server").execute(request);
    }
}
