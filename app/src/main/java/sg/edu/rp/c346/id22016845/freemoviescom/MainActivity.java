package sg.edu.rp.c346.id22016845.freemoviescom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText movieTitle;
    EditText movieGenre;
    EditText movieYear;
    Spinner movieRating;
    ArrayList<Movies> alMoviesList;
    ArrayAdapter<Movies> aaMoviesList;
    Button input;
    Button showList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieTitle=findViewById(R.id.movieTitle);
        movieGenre=findViewById(R.id.movieGenre);
        movieYear=findViewById(R.id.movieYear);
        movieRating=findViewById(R.id.spinner);
        input=findViewById(R.id.InsertButton);
        showList=findViewById(R.id.ShowButton);

        alMoviesList= new ArrayList<Movies>();
        aaMoviesList= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alMoviesList);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = movieTitle.getText().toString();
                String genre = movieGenre.getText().toString();
                int year = Integer.parseInt(movieYear.getText().toString());
                String rating = movieRating.getSelectedItem().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                dbh.insertMovies(title,genre,year,rating);
                Toast.makeText(MainActivity.this,"Movie Successfully Added",Toast.LENGTH_SHORT).show();
                movieTitle.setText("");
                movieGenre.setText("");
                movieYear.setText("");
            }
        });
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, MoviesList.class);
                intent.putExtra("movieTitle", movieTitle.getText().toString());
                intent.putExtra("movieGenre", movieGenre.getText().toString());
                intent.putExtra("movieYear", movieYear.getText().toString());
                intent.putExtra("movieRating",movieRating.getSelectedItem().toString());
                startActivity(intent);
                alMoviesList.addAll(dbh.getAllMovies());
                aaMoviesList.notifyDataSetChanged();
                dbh.close();
            }
        });
    }
}