package edu.asu.bscs.tkbrocke.lab_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

public class AutoImportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_import);

        JSONObject movies = new JSONObject(this, getResources().getResourceEntryName(R.raw.movies));
    }
}
