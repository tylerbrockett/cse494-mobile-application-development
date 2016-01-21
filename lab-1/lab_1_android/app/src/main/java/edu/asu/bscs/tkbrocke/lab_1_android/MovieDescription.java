/*
 * @author Tyler Brockett
 * @project CSE 494 Lab 1 - Android
 * @version January 2016
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
