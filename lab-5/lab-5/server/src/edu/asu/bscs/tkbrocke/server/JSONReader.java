/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - Server
 * @version				March 16, 2016
 * @project-description Server that responds to requests from iOS and Android clients
 * @class-name          JSONReader.java
 * @class-description   Reads JSON file from input stream, returns JSONArray.
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

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;

public class JSONReader {
    InputStream inputStream;
    String json;
    JSONArray array;

    public JSONReader(InputStream is, String name){
        inputStream = is;
        json = jsonToString();
        array = generateJsonObjects(name);
    }

    private String jsonToString(){
        try{
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public JSONArray generateJsonObjects(String name){
        try{
            JSONObject obj = new JSONObject(json);
            array = obj.getJSONArray(name);
            return array;
        } catch (Exception e){
            e.printStackTrace();
        }
        array = new JSONArray();
        return array;
    }

    public int getSize(){
        return array.length();
    }

    public JSONObject getJsonObject(int index){
        if (index < getSize()){
            try{
                return (JSONObject) array.get(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getJsonString(int index){
        if (index < getSize()){
            try{
                return array.get(index).toString();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }
}
