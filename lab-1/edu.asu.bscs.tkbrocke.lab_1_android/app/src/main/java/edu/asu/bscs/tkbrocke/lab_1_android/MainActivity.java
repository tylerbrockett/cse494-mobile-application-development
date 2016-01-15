// TODO Add headers to all source files!

package edu.asu.bscs.tkbrocke.lab_1_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText json_entry;
    Button submit;
    TextView title, year, rated, released, runtime, genre, actors, plot;

    String sampleData = "{\"Title\":\"Star Wars: The Force Awakens\",\"Year\":\"2015\",\"Rated\":\"PG-13\",\"Released\":\"18 Dec 2015\",\"Runtime\":\"135 min\",\"Genre\":\"Action, Adventure, Fantasy\",\"Director\":\"J.J. Abrams\",\"Writer\":\"Lawrence Kasdan, J.J. Abrams, Michael Arndt\",\"Actors\":\"Harrison Ford, Mark Hamill, Carrie Fisher, Adam Driver\",\"Plot\":\"Three decades after the defeat of the Galactic Empire, a new threat arises. The First Order attempts to rule the galaxy and only a rag-tag group of heroes can stop them, along with the help of the Resistance.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"N/A\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_SX300.jpg\",\"Metascore\":\"81\",\"imdbRating\":\"8.9\",\"imdbVotes\":\"77,178\",\"imdbID\":\"tt2488496\",\"Type\":\"movie\",\"Response\":\"True\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        json_entry = (EditText) findViewById(R.id.json_entry);
        submit = (Button) findViewById(R.id.json_submit);
        title = (TextView) findViewById(R.id.title);
        year = (TextView) findViewById(R.id.year);
        rated = (TextView) findViewById(R.id.rated);
        released = (TextView) findViewById(R.id.released);
        runtime = (TextView) findViewById(R.id.runtime);
        genre = (TextView) findViewById(R.id.genre);
        actors = (TextView) findViewById(R.id.actors);
        plot = (TextView) findViewById(R.id.plot);

        json_entry.setText(sampleData);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = json_entry.getText().toString();
                MovieDescription movie = new MovieDescription(json);

                title.setText(movie.getTitle());
                year.setText(movie.getYear());
                rated.setText(movie.getRated());
                released.setText(movie.getReleased());
                runtime.setText(movie.getRuntime());
                genre.setText(movie.getGenre());
                actors.setText(movie.getActors());
                plot.setText(movie.getPlot());

                Log.w("Title", movie.getTitle());
                Log.w("Year", movie.getYear());
                Log.w("Rated", movie.getRated());
                Log.w("Released", movie.getReleased());
                Log.w("Runtime", movie.getRuntime());
                Log.w("Genre", movie.getGenre());
                Log.w("Actors", movie.getActors());
                Log.w("Plot", movie.getPlot());
            }
        });
    }
}
