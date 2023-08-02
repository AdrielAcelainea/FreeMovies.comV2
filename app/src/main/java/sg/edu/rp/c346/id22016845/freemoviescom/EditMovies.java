package sg.edu.rp.c346.id22016845.freemoviescom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditMovies extends AppCompatActivity {
    TextView MovieId;
    EditText title, genre, year;
    Button update,delete,cancel;
    Spinner rating;
    Movies rID, rTitle, rGenre, rYear, rRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);
        MovieId = findViewById(R.id.movieID);
        title = findViewById(R.id.movieTitle);
        genre = findViewById(R.id.movieGenre);
        year = findViewById(R.id.movieYear);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        cancel = findViewById(R.id.cancel);
        rating = findViewById(R.id.spinner2);

        Intent intent = getIntent();
        int movieId = intent.getIntExtra("ID",-1);
        String movieTitle = intent.getStringExtra("Title");
        String movieGenre = intent.getStringExtra("Genre");
        int movieYear = intent.getIntExtra("Year",0);
        String movieRating = intent.getStringExtra("Rating");
        MovieId.setText(String.valueOf(movieId));
        title.setText(movieTitle);
        genre.setText(movieGenre);
        year.setText(String.valueOf(movieYear));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditMovies.this);
                String editedTitle = String.valueOf(title.getText());
                String editedGenre = String.valueOf(genre.getText());
                int editedYear = Integer.valueOf(String.valueOf(year.getText()));
                String editedRating = rating.getSelectedItem().toString();
                Movies updatedMovie = new Movies(movieId,editedTitle,editedGenre,editedYear,editedRating);
                dbh.updateMovie(updatedMovie);
                Toast.makeText(EditMovies.this, "Movie successfully updated", Toast.LENGTH_SHORT).show();
                dbh.close();
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditMovies.this);
                db.deleteMovie(movieId);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}