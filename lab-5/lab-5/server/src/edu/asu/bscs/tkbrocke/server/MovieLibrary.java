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

package edu.asu.bscs.tkbrocke.server;

import edu.asu.bscs.tkbrocke.server.JSONReader;
import edu.asu.bscs.tkbrocke.server.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.*;

public class MovieLibrary {

    ArrayList<Movie> library;
    private static final boolean debugOn = false;
    private static final String movieJsonFileName = "movies.json";
    private int nextIssuedID;

    public MovieLibrary(){
        nextIssuedID = 1;
        debug("creating a new movie collection");
        library = new ArrayList<Movie>();
        this.resetFromJsonFile();
    }

    private void debug(String message) {
        if (debugOn)
            System.out.println("debug: "+message);
    }

    public boolean resetFromJsonFile(){
        nextIssuedID = 1;
        boolean ret = true;
        System.out.println("READING IN JSON FILE");
        try{
            library.clear();
            String fileName = movieJsonFileName;
            File f = new File(fileName);
            System.out.println(f.getAbsolutePath());
            FileInputStream is = new FileInputStream(f);
            JSONReader jr = new JSONReader(is, "Movies");
            int i;
            for (i = 0; i < jr.getSize(); i++){
                JSONObject json = jr.getJsonObject(i);
                add(json);
            }
            System.out.println("NUMBER OF MOVIES READ IN ---------> " + i);
        }catch (Exception ex){
            System.out.println("Exception reading json file: "+ex.getMessage());
            ret = false;
        }
        return ret;
    }

    public boolean saveToJsonFile(){
        boolean ret = true;
        try {
            JSONObject obj = toJson();
            PrintWriter out = new PrintWriter(movieJsonFileName);
            out.println(obj.toString(2)); // 2 indent spaces
            out.close();
        }catch(Exception ex){
            ret = false;
        }
        return ret;
    }

    public boolean add(JSONObject movieJson){
        try{
            movieJson.put("ID", "" + (nextIssuedID++));
            Movie movie = new Movie(movieJson.toString());
            library.add(movie);
            sort();
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    public boolean edit(JSONObject object){
        Movie movie = search(object.getString("ID"));
        debug("editing movie titled: "+((movie==null)?"unknown":movie.getTitle()));
        if (movie != null){
            movie.setJSON(object.toString());
            System.out.println("Edit successful: " + movie.getJSON());
            return true;
        }
        System.out.println("Edit Failed");
        return false;
    }

    public void sort(){
        Collections.sort(library);
    }

    public boolean remove(String id){
        debug("removing movie with ID: "+id);
        for(int i = 0; i < library.size(); i++){
            if(library.get(i).getID().equalsIgnoreCase(id)){
                library.remove(i);
                debug("ID \"" + id + "\" Removed. Index: " + i);
                return true;
            }
        }
        return false;
    }

    public String[] getIDs(){
        ArrayList<String> ids = new ArrayList<String>();
        debug("getting " + library.size() + " movie titles.");
        for(int i = 0; i < library.size(); i++){
            ids.add(library.get(i).getID());
        }
        return (String[]) ids.toArray();
    }

    public Movie search(String id){
        for (int i = 0; i < getSize(); i++){
            Movie movie = library.get(i);
            if (movie.getID().equalsIgnoreCase(id)){
                return movie;
            }
        }
        return null;
    }

    public Movie get(int index){
        return library.get(index);
    }

    public void clearAll(){
        while (getSize() > 0){
            library.remove(0);
        }
    }

    public int getSize(){
        return library.size();
    }

    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        JSONArray movieArray = new JSONArray();
        for(int i = 0; i < library.size(); i++){
            Movie movie = library.get(i);
            JSONObject mJson = new JSONObject(movie.getJSON());
            movieArray.put(mJson);
        }
        obj.put("Movies", movieArray);
        return obj;
    }
}
