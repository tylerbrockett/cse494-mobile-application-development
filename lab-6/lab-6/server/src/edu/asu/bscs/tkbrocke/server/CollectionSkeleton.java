/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - Server
 * @version				March 16, 2016
 * @project-description Server that responds to requests from iOS and Android clients
 * @class-name          CollectionSkeleton.java
 * @class-description   Handles response from server and passes data to appropriate MovieLibrary methods.
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

import org.json.JSONObject;
import org.json.JSONArray;

public class CollectionSkeleton {

    public static String RESET = "resetFromJsonFile";
    public static String SAVE = "saveToJsonFile";
    public static String REMOVE = "remove";
    public static String ADD = "add";
    public static String GET = "get";
    public static String GET_ALL = "getAll";
    public static String EDIT = "edit";
    public static String GET_IDS = "getIDs";

    public static String RESULT = "result";

    MovieLibrary mLib;

    public CollectionSkeleton (MovieLibrary mLib){
        this.mLib = mLib;
    }

    public String callMethod(String request){
        JSONObject result = new JSONObject();
        try{
            JSONObject theCall = new JSONObject(request);
            String method = theCall.getString("method");
            int id = theCall.getInt("id");
            JSONArray params = new JSONArray(); // = null
            if(!theCall.isNull("params")){
                params = theCall.getJSONArray("params");
            }
            result.put("id",id);
            result.put("jsonrpc","2.0");
            if(method.equals(RESET)){
                mLib.resetFromJsonFile();
                result.put(RESULT, true);
            }else if(method.equals(SAVE)){
                boolean saved = mLib.saveToJsonFile();
                result.put(RESULT, saved);
            }else if(method.equals(REMOVE)){
                String movieID = params.getString(0);
                boolean removed = mLib.remove(movieID);
                result.put(RESULT, removed);
            }else if(method.equals(ADD)){
                boolean added = mLib.add(new JSONObject(params.getString(0)));
                result.put(RESULT, added);
            }else if(method.equals(GET)){
                String movieID = params.getString(0);
                Movie movie = mLib.search(movieID);
                result.put(RESULT, movie.getJSON());
            }else if(method.equals(GET_ALL)){
                result.put(RESULT, mLib.toJson());
            }else if(method.equals(EDIT)){
                JSONObject movie = new JSONObject(params.getString(0));
                boolean edited = mLib.edit(movie);
                result.put(RESULT, edited);
            }else if(method.equals(GET_IDS)){
                String[] movieIDs = mLib.getIDs();
                JSONArray resArr = new JSONArray();
                for (String movieID : movieIDs) {
                    resArr.put(movieID);
                }
                result.put(RESULT,resArr);
            }
        }catch(Exception ex){
            System.out.println("exception in callMethod: "+ex.getMessage());
            result.put(RESULT, false);
        }
        System.out.println("returning: "+result.toString());
        return "HTTP/1.0 200 Data follows\nServer:localhost:8080\nContent-Type:text/plain\nContent-Length:"+(result.toString()).length()+"\n\n"+result.toString();
    }
}
