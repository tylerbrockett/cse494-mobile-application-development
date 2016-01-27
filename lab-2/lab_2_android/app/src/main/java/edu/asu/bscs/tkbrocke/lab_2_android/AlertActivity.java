package edu.asu.bscs.tkbrocke.lab_2_android;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AlertActivity extends Activity {

    Button alertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Log.w(this.getClass().getSimpleName(), "AlertActivity .onCreate()");

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
        Log.w(this.getClass().getSimpleName(), "AlertActivity .onRestart()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.w(this.getClass().getSimpleName(), "AlertActivity .onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.w(this.getClass().getSimpleName(), "AlertActivity .onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.w(this.getClass().getSimpleName(), "AlertActivity .onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.w(this.getClass().getSimpleName(), "AlertActivity .onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.w(this.getClass().getSimpleName(), "AlertActivity .onDestroy()");
    }
}
