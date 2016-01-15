// TODO Add headers to all source files!

package edu.asu.bscs.tkbrocke.lab_1_android;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDescription {

    private String json;
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String actors;
    private String plot;

    public MovieDescription(String json){
        this.json = json;
        try {
            JSONObject jo = new JSONObject(this.json);
            this.title = jo.getString("Title");
            this.year = jo.getString("Year");
            this.rated = jo.getString("Rated");
            this.released = jo.getString("Released");
            this.runtime = jo.getString("Runtime");
            this.genre = jo.getString("Genre");
            this.actors = jo.getString("Actors");
            this.plot = jo.getString("Plot");
        }catch(JSONException e){
            Log.e("Json Error", e.toString());
            this.title = "Error";
            this.year = "Error";
            this.rated = "Error";
            this.released = "Error";
            this.runtime = "Error";
            this.genre = "Error";
            this.actors = "Error";
            this.plot = "Error";
        }
    }

    public String getTitle(){
        return this.title;
    }

    public String getYear(){
        return this.year;
    }

    public String getRated(){
        return this.rated;
    }

    public String getReleased(){
        return this.released;
    }

    public String getRuntime(){
        return this.runtime;
    }

    public String getGenre(){
        return this.genre;
    }

    public String getActors(){
        return this.actors;
    }

    public String getPlot(){
        return this.plot;
    }
}
