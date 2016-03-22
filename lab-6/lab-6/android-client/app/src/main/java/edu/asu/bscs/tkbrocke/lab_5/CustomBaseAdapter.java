/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - Android
 * @version				March 16, 2016
 * @project-description	Use Android client to get/post data from/to JSON-RPC Server
 * @class-name			CustomBaseAdapter.java
 * @class-description	Specifies how the ListView interfaces with the data.
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

public class CustomBaseAdapter extends BaseAdapter {
    OnDataChangeListener onDataChangeListener;
    Context context;
    public static JSONArray movies;
    LayoutInflater inflater;

    static class ViewHolder {
        ImageView movie_poster;
        TextView movie_title;
        ImageView delete;
    }

    public CustomBaseAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        if (onDataChangeListener != null){
            onDataChangeListener.onDataChanged(getCount());
        }
    }

    public int getCount(){
        try{
            return movies.length();
        }catch(Exception e){
            //notifyDataSetInvalidated();
            // e.printStackTrace();
            return 0;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;

        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.movie_poster = (ImageView)convertView.findViewById(R.id.movie_poster);
            holder.movie_title = (TextView)convertView.findViewById(R.id.movie_title);
            holder.delete = (ImageView)convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Movie movie;
        try{
            movie = new Movie(movies.getJSONObject(position).toString());
        } catch(Exception e){
            movie = new Movie("");
        }

        holder.movie_title.setText(movie.getTitle());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = getItem(pos);
                ServerRequest request = new ServerRequest(context, MainActivity.ipAddress, ServerRequest.REMOVE, new String[]{movie.getID()});
                new NetworkAsyncTask(context, "Successfully Deleted Movie", "Failure Deleting Movie").execute(request);
            }
        });

        return convertView;
    }

    public Movie getItem(int position){
        Movie movie;
        try {
            movie = new Movie(movies.getJSONObject(position).toString());
        } catch (Exception e){
            movie = new Movie("");
        }
        return movie;
    }

    public long getItemId(int position){
        return 0;
    }


    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        this.onDataChangeListener = onDataChangeListener;
    }
}
