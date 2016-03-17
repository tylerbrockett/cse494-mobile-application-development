package edu.asu.bscs.tkbrocke.lab_5;

/*
 * Copyright (c) 2016 Tim Lindquist,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Purpose: Example Android application that uses an AsyncTask to accomplish the same effect
 * as using a Thread and android.os.Handler
 *
 * Ser423 Mobile Applications
 * see http://pooh.poly.asu.edu/Mobile
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version February 2016
 */

import android.content.Context;

public class ServerRequest {

    public static String RESET = "resetFromJsonFile";
    public static String SAVE = "saveToJsonFile";
    public static String REMOVE = "remove";
    public static String ADD = "add";
    public static String GET = "get";
    public static String GET_ALL = "getAll";
    public static String EDIT = "edit";
    public static String GET_IDS = "getIDs";

    public static String RESULT = "result";

    public String method;
    public String[] params;
    public Context context;
    public String urlString;
    public String resultAsJson;

    public ServerRequest(Context context, String urlString, String method, String[] params){
        this.context = context;
        this.urlString = urlString;
        this.method = method;
        this.params = params;
        this.resultAsJson = "{}";
    }
}
