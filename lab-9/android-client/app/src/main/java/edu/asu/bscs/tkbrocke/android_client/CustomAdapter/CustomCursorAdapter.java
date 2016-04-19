/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 9 - Android
 * @version				April 19, 2016
 * @project-description	Get movie data from two sources and play movie if file exists.
 * @class-name			CustomCursorAdapter.java
 * @class-description	Specifies how ListView interfaces with the data
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
 
package edu.asu.bscs.tkbrocke.android_client.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import edu.asu.bscs.tkbrocke.android_client.DatabaseTemplate.MovieDatabase;
import edu.asu.bscs.tkbrocke.android_client.Network.HttpRequest;
import edu.asu.bscs.tkbrocke.android_client.Network.ServerRequest;
import edu.asu.bscs.tkbrocke.android_client.R;
import edu.asu.bscs.tkbrocke.android_client.WatchMediaActivity;

public class CustomCursorAdapter extends CursorAdapter {
    OnDataChangeListener onDataChangeListener;
    OnDataDeletedListener onDataDeletedListener;
    OnDataAddedListener onDataAddedListener;
    Context context;
    LayoutInflater inflater;
    MovieDatabase template;
    SQLiteDatabase db;

    static class ViewHolder {
        ImageView movie_poster;
        TextView movie_title;
        ImageView delete;
        ImageView watch;
        ProgressBar poster_progress;
        DownloadPosterTask poster_loader;
    }

    public void dataAdded(){
        if (onDataAddedListener != null){
            onDataAddedListener.onDataAdded();
        }
    }

    public CustomCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        template = new MovieDatabase(this.context);
    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        if (onDataChangeListener != null){
            onDataChangeListener.onDataChanged(getCount());
        }
    }

    @Override
    public void bindView(View v, final Context context, final Cursor cursor) {
        final int position = cursor.getPosition();
        final long id = cursor.getLong(MovieDatabase.COLUMN_ID);
        final String title = cursor.getString(MovieDatabase.COLUMN_TITLE);
        final String posterURL = cursor.getString(MovieDatabase.COLUMN_POSTER);
        final String filename = cursor.getString(MovieDatabase.COLUMN_FILENAME);

        final ViewHolder vh = (ViewHolder) v.getTag();
        vh.movie_title.setText(cursor.getString(MovieDatabase.COLUMN_TITLE));
        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whereClause = MovieDatabase.ID + "=?";
                String[] whereArgs = new String[]{String.valueOf(id)};
                db = template.getWritableDatabase();
                db.delete(MovieDatabase.TABLE_MOVIES, whereClause, whereArgs);
                db.close();
                if (onDataDeletedListener != null) {
                    onDataDeletedListener.onDataDeleted(context);
                }
                Toast.makeText(context, "Deleted Title: " + title, Toast.LENGTH_SHORT).show();
            }
        });
        vh.watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WatchMediaActivity.class);
                intent.putExtra("filename", filename);
                context.startActivity(intent);
            }
        });

        if ( ! filename.equals("")) {
            vh.watch.setVisibility(View.VISIBLE);
        } else {
            vh.watch.setVisibility(View.INVISIBLE);
        }

        /* 1. Cancel any previous AsyncTask trying to load image for cell.
               This prevents cell from loading multiple attempted calls before
                it gets to the correct call.
           2. Hide the ImageView and show ProgressBar until image loads.
           3. Create new instance of DownloadPosterTask and store it to ViewHolder (bad practice? nahhh)
           4. Execute said DownloadPosterTask
          */
        vh.poster_loader.cancel(true);
        if( ! posterURL.equals("")){
            vh.movie_poster.setVisibility(View.INVISIBLE);
            vh.poster_progress.setVisibility(View.VISIBLE);
            vh.poster_loader = new DownloadPosterTask(vh);
            vh.poster_loader.execute(posterURL);
        } else {
            vh.movie_poster.setVisibility(View.VISIBLE);
            vh.poster_progress.setVisibility(View.INVISIBLE);
            vh.movie_poster.setImageResource(R.drawable.reel);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.movie_title = (TextView)v.findViewById(R.id.movie_title);
        vh.delete = (ImageView)v.findViewById(R.id.delete);
        vh.movie_poster = (ImageView)v.findViewById(R.id.movie_poster);
        vh.poster_progress = (ProgressBar)v.findViewById(R.id.poster_progress);
        vh.watch = (ImageView) v.findViewById(R.id.watch);
        vh.poster_loader = new DownloadPosterTask(vh);
        v.setTag(vh);
        return v;
    }

    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }

    public interface OnDataDeletedListener {
        public void onDataDeleted(Context c);
    }

    public interface OnDataAddedListener {
        public void onDataAdded();
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        this.onDataChangeListener = onDataChangeListener;
    }

    public void setOnDataDeletedListener(OnDataDeletedListener onDataDeletedListener){
        this.onDataDeletedListener = onDataDeletedListener;
    }

    public void setOnDataAddedListener(OnDataAddedListener onDataAddedListener){
        this.onDataAddedListener = onDataAddedListener;
    }

    private class DownloadPosterTask extends AsyncTask<String, Void, Bitmap> {
        ViewHolder vh;

        public DownloadPosterTask(ViewHolder vh) {
            this.vh = vh;
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
            if(result != null){
                vh.movie_poster.setImageBitmap(result);
            } else {
                vh.movie_poster.setImageResource(R.drawable.reel);
            }
            vh.poster_progress.setVisibility(View.INVISIBLE);
            vh.movie_poster.setVisibility(View.VISIBLE);
        }
    }
}
