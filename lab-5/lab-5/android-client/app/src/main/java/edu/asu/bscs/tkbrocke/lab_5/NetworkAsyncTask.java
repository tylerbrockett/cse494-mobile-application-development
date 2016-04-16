/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - Android
 * @version				March 16, 2016
 * @project-description	Use Android client to get/post data from/to JSON-RPC Server
 * @class-name			NetworkAsyncTask.java
 * @class-description	Performs network operations on separate thread and handles response from server.
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

package edu.asu.bscs.tkbrocke.lab_5;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class NetworkAsyncTask extends AsyncTask<ServerRequest, Integer, ServerRequest> {

    Context context;
    String successMessage, failMessage;

    public NetworkAsyncTask(Context context, String successMessage, String failMessage){
        this.context = context;
        this.successMessage = successMessage;
        this.failMessage = failMessage;
    }

    @Override
    protected ServerRequest doInBackground(ServerRequest... request) {
        try {
            JSONArray ja = new JSONArray(request[0].params);
            android.util.Log.d(this.getClass().getSimpleName(),"params: "+ja.toString());
            String requestData = "{ \"jsonrpc\":\"2.0\", \"method\":\""+ request[0].method+"\", \"params\":"+ja.toString()+
                    ",\"id\":3}";
            android.util.Log.d(this.getClass().getSimpleName(),"requestData: "+requestData+" url: "+ request[0].urlString);
            JsonRpcHttpRequest conn = new JsonRpcHttpRequest((new URL(request[0].urlString)), request[0].context);
            String resultStr = conn.call(requestData);
            request[0].resultAsJson = resultStr;
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"exception in remote call "+
                    ex.getMessage());
        }
        return request[0];
    }

    @Override
    protected void onPostExecute(ServerRequest result) {
        try {
            JSONObject jo = new JSONObject(result.resultAsJson);
            // Covers ONLY fetching all data
            if (result.method.equals(ServerRequest.GET_ALL)) {
                JSONObject object = jo.getJSONObject(ServerRequest.RESULT);
                JSONArray movies = object.getJSONArray("Movies");
                if(movies.length() > 0){
                    CustomBaseAdapter.movies = movies;
                    MainActivity.adapter.notifyDataSetChanged();
                }
                else{
                    CustomBaseAdapter.movies = null;
                    MainActivity.adapter.notifyDataSetChanged();
                }
            }
            // Covers Adding, Deleting, and Editing.
            else {
                if (jo.getBoolean("result")) {
                    Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
                    if (!result.method.equals(ServerRequest.GET_ALL)) {
                        ServerRequest request = new ServerRequest(context, MainActivity.ipAddress, ServerRequest.GET_ALL, new String[]{});
                        new NetworkAsyncTask(context, "Successfully Loaded Movies", "Failure Loading Movies").execute(request);
                    }
                } else {
                    Toast.makeText(context, failMessage, Toast.LENGTH_SHORT).show();
                    MainActivity.adapter.notifyDataSetInvalidated();
                }
            }
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"Exception: "+ex.getMessage());
        }
    }
}
