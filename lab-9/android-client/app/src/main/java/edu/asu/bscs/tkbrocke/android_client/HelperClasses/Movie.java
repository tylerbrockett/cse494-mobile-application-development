/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 9 - Android
 * @version				April 19, 2016
 * @project-description	Get movie data from two sources and play movie if file exists.
 * @class-name			Movie.java
 * @class-description	Used to just parse data from JSON.
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

package edu.asu.bscs.tkbrocke.android_client.HelperClasses;

import org.json.JSONObject;
import org.json.JSONException;

public class Movie implements Comparable<Movie>{

    private String json = "";
    private String title = "";
    private String year = "";
    private String rated = "";
    private String released = "";
    private String runtime = "";
    private String genre = "";
    private String actors = "";
    private String plot = "";
    private String poster = "";
    private String filename = "";

    public Movie(String json){
        this.json = json;
        parseJSON(json);
    }

    public Movie(String title,      String year,    String rated,
                 String released,   String runtime, String genre,
                 String actors,     String plot,    String poster,
                 String filename)
    {
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.actors = actors;
        this.plot = plot;
        this.poster = poster;
        this.filename = filename;
        generateJSON();
    }

    public Movie(JSONObject json){
        this.json = json.toString();
        parseJSON(this.json);
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
                        "\"Plot\":\"" + this.plot + "\"," +
                        "\"Poster\":\"" + this.poster + "\"," +
                        "\"Filename\":\"" + this.filename + "\"" +
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
            this.poster = jo.getString("Poster");
            try {
                String temp = (jo.getString("Filename"));
                if(temp.equals("unknown.mp4")) {
                    this.filename = "";
                } else {
                    this.filename = temp;
                }
            } catch (Exception e){
                this.filename = "";
            }
        }catch(JSONException e){
            System.out.println("Json Error:  " + e.toString());
            this.title = "Error";
            this.year = "Error";
            this.rated = "Error";
            this.released = "Error";
            this.runtime = "Error";
            this.genre = "Error";
            this.actors = "Error";
            this.plot = "Error";
            this.poster = "Error";
            this.filename = "Error";
        }
    }

    public String getJSON(){
        return this.json;
    }

    public String getTitle(){ return this.title; }

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

    public String getPoster(){
        return this.poster;
    }

    public String getFilename(){
        return this.filename;
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

    public void setPoster(String poster){
        this.poster = poster;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    @Override
    public int compareTo(Movie movie){
        return this.title.compareTo(movie.getTitle());
    }
}
