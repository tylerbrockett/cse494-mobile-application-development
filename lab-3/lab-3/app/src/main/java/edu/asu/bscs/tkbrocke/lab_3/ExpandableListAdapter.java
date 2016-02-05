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

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    MovieLibrary library;
    MainActivity parent;

    OnDataChangeListener onDataChangeListener;

    public ExpandableListAdapter(MainActivity parent){
        library = MainActivity.library;
        this.parent = parent;
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        this.onDataChangeListener = onDataChangeListener;
    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        if (onDataChangeListener != null){
            onDataChangeListener.onDataChanged(library.getSize());
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return library.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = ((LayoutInflater)this.parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item, null);
        }
        if (childPosition != 0){
            Log.e("ExpandableListAdapter", "Error: getChildView");
            return null;
        }

        TextView year = (TextView)convertView.findViewById(R.id.list_year);
        TextView rated = (TextView)convertView.findViewById(R.id.list_rated);
        TextView released = (TextView)convertView.findViewById(R.id.list_released);
        TextView runtime = (TextView)convertView.findViewById(R.id.list_runtime);
        TextView genre = (TextView)convertView.findViewById(R.id.list_genre);
        TextView actors = (TextView)convertView.findViewById(R.id.list_actors);
        TextView plot = (TextView)convertView.findViewById(R.id.list_plot);

        year.setText(library.get(groupPosition).getYear());
        rated.setText(library.get(groupPosition).getRated());
        released.setText(library.get(groupPosition).getReleased());
        runtime.setText(library.get(groupPosition).getRuntime());
        genre.setText(library.get(groupPosition).getGenre());
        actors.setText(library.get(groupPosition).getActors());
        plot.setText(library.get(groupPosition).getPlot());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return library.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return library.getSize();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = ((LayoutInflater)this.parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_group, null);
        }

        ImageView imgView = (ImageView)convertView.findViewById(R.id.group_image);
        TextView groupTitle = (TextView) convertView.findViewById(R.id.group_title);

        if (isExpanded){
            imgView.setImageDrawable(parent.getResources().getDrawable(R.drawable.reel_white));
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.expanded_color));
            groupTitle.setTextColor(parent.getResources().getColor(R.color.white));
        }
        else{
            imgView.setImageDrawable(parent.getResources().getDrawable(R.drawable.reel));
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.group_background));
            groupTitle.setTextColor(parent.getResources().getColor(R.color.default_text_color));
        }

        String headerTitle = ((MovieDescription)getGroup(groupPosition)).getTitle();

        groupTitle.setText(headerTitle);
        ImageView img = (ImageView)convertView.findViewById(R.id.delete_image);

        final int gPosition = groupPosition;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                library.remove(gPosition);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }
}
