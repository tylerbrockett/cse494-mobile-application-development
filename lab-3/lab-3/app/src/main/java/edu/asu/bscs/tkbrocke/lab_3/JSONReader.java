package edu.asu.bscs.tkbrocke.lab_3;

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
