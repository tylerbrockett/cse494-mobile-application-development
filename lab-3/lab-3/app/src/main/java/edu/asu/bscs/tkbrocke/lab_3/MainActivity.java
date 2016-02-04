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
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static MovieLibrary library;

    TextView numEntries;
    ExpandableListView listView;
    public static ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        library = new MovieLibrary();

        numEntries = (TextView) findViewById(R.id.num_entries);
        listView = (ExpandableListView) findViewById(R.id.expandable_list_view);

        adapter = new ExpandableListAdapter(this);
        adapter.setOnDataChangeListener(new ExpandableListAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged(int size) {
                numEntries.setText(library.getSizeFormatted());
            }
        });
        listView.setAdapter(adapter);
        numEntries.setText(library.getSizeFormatted());
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
            case R.id.auto_import:
                Intent autoImport = new Intent(this, AutoImportActivity.class);
                startActivity(autoImport);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
