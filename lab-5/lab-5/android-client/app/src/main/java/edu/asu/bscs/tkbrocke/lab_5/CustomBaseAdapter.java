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

        //holder.movie_poster.setImageDrawable(parent.getResources().getDrawable(R.drawable.reel));
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
