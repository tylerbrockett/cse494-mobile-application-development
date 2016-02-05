package edu.asu.bscs.tkbrocke.lab_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AutoPopulateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_populate);

        JSONReader jr = new JSONReader(getResources().openRawResource(R.raw.movies), "Movies");
        for (int i = 0; i < jr.getSize(); i++){
            String json = jr.getJsonString(i);
            MovieDescription movie = new MovieDescription(json);
            MainActivity.library.add(movie);
            MainActivity.adapter.notifyDataSetChanged();
        }
        finish();
    }
}
