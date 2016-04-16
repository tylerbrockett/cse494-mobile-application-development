/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - Android
 * @version				March 16, 2016
 * @project-description	Use Android client to get/post data from/to JSON-RPC Server
 * @class-name			ActivitySetIP.java
 * @class-description	Allows the user to change IP address on the fly instead of rebuilding project each time.
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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySetIP extends AppCompatActivity {

    Button saveIP;
    EditText ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ip);
        saveIP = (Button)findViewById(R.id.btn_save_ip);
        ipAddress = (EditText)findViewById(R.id.et_ip_address);
        ipAddress.setText(MainActivity.ipAddress);
        saveIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ipAddress = ipAddress.getText().toString();
                ServerRequest request = new ServerRequest(v.getContext(), MainActivity.ipAddress, ServerRequest.GET_ALL, new String[]{});
                new NetworkAsyncTask(v.getContext(), "Successfully Loaded Movies", "Failure Loading Movies").execute(request);
                Toast.makeText(v.getContext(), "New IP Address Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
