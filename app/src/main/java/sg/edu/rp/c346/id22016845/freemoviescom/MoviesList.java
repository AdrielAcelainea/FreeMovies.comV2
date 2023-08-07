package sg.edu.rp.c346.id22016845.freemoviescom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MoviesList extends AppCompatActivity {
    Button pg13button;
    ArrayList<Movies> alMoviesList;
    ListView moviesList;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        pg13button = findViewById(R.id.parentalGuidance);
        pg13button.setBackgroundColor(Color.parseColor("#B0E0E6"));
        moviesList = findViewById(R.id.movieList);
        alMoviesList = new ArrayList<Movies>();
        adapter = new CustomAdapter(this, R.layout.row, alMoviesList);
        moviesList.setAdapter(adapter);

        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movies data = alMoviesList.get(position);
                Intent i = new Intent(MoviesList.this,EditMovies.class);
                i.putExtra("ID", data.getId());
                i.putExtra("Title", data.getTitle());
                i.putExtra("Genre", data.getGenre());
                i.putExtra("Year", data.getYear());
                i.putExtra("Rating", data.getRating());
                startActivity(i);
            }
        });
        pg13button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MoviesList.this);
                alMoviesList.clear();
                String pg13 = "PG13";
                alMoviesList.addAll(dbh.getMovies(pg13));
                adapter.notifyDataSetChanged();
            }
        });


    }
    @Override
    public void onResume() {
        super.onResume();
        DBHelper db = new DBHelper(MoviesList.this);
        alMoviesList.clear();
        alMoviesList.addAll(db.getAllMovies());
        adapter.notifyDataSetChanged();
    }

}
