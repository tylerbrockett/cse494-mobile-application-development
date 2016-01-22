package edu.asu.bscs.tkbrocke.lab_2_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w(this.getClass().getSimpleName(), "MainActivity .onCreate()");

        mainButton = (Button)findViewById(R.id.main_button);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("edu.asu.bscs.tkbrocke.lab_2_android.AlertActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.w(this.getClass().getSimpleName(), "Main .onRestart()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.w(this.getClass().getSimpleName(), "Main .onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.w(this.getClass().getSimpleName(), "Main .onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.w(this.getClass().getSimpleName(), "Main .onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.w(this.getClass().getSimpleName(), "Main .onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.w(this.getClass().getSimpleName(), "Main .onDestroy()");
    }
}
