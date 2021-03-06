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

import org.json.JSONObject;
import android.util.Log;
import org.json.JSONException;

public class MovieDescription implements Comparable<MovieDescription>{

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
        parseJSON(json);
    }

    public MovieDescription(String title,       String year,    String rated,
                            String released,    String runtime, String genre,
                            String actors,      String plot)
    {
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.actors = actors;
        this.plot = plot;
        generateJSON();
    }

    public String generateJSON(){
        this.json =
                "{" +
                        "\"Title\":\"" + this.title + "\"," +
                        "\"Year\":\"" + this.year + "\"," +
                        "\"Rated\":\"" + this.rated + "\"," +
                        "\"Released\":\"" + this.released + "\"," +
                        "\"Runtime\":\"" + this.runtime + "\"," +
                        "\"Genre\":\"" + this.genre + "\"," +
                        "\"Actors\":\"" + this.actors + "\"," +
                        "\"Plot\":\"" + this.plot + "\"" +
                "}";
        return this.json;
    }

    public void parseJSON(String json){
        try {
            JSONObject jo = new JSONObject(json);
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

    public String getJSON(){
        return this.json;
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

    public void setJSON(String json){
        this.json = json;
        parseJSON(json);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setYear(String year){
        this.year = year;
    }

    public void setRated(String rated){
        this.rated = rated;
    }

    public void setReleased(String released){
        this.released = released;
    }

    public void setRuntime(String runtime){
        this.runtime = runtime;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setActors(String actors){
        this.actors = actors;
    }

    public void setPlot(String plot){
        this.plot = plot;
    }

    @Override
    public int compareTo(MovieDescription movie){
        return this.title.compareTo(movie.getTitle());
    }
}
