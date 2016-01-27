/*
 * @author Tyler Brockett
 * @project Lab 2 - Android
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

package edu.asu.bscs.tkbrocke.lab_2_android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AlertActivity extends Activity {

    Button alertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Log.w(this.getClass().getSimpleName(), "AlertActivity onCreate()");

        alertButton = (Button)findViewById(R.id.alert_button);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(this.getClass().getSimpleName(), "AlertActivity OnClick()");
                finish();
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.w(this.getClass().getSimpleName(), "AlertActivity onRestart()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.w(this.getClass().getSimpleName(), "AlertActivity onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.w(this.getClass().getSimpleName(), "AlertActivity onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.w(this.getClass().getSimpleName(), "AlertActivity onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.w(this.getClass().getSimpleName(), "AlertActivity onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.w(this.getClass().getSimpleName(), "AlertActivity onDestroy()");
    }
}
