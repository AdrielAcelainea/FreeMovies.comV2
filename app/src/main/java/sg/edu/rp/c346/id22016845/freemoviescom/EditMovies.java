package sg.edu.rp.c346.id22016845.freemoviescom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        update.setBackgroundColor(Color.parseColor("#B0E0E6"));
        cancel.setBackgroundColor(Color.parseColor("#B0E0E6"));
        delete.setBackgroundColor(Color.parseColor("#B0E0E6"));

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
        rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditMovies.this);
                myBuilder.setTitle("Delete Movie");
                myBuilder.setMessage("Do you want to delete the movie?");
                myBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper db = new DBHelper(EditMovies.this);
                        db.deleteMovie(movieId);
                        finish();
                    }
                });
                myBuilder.setNegativeButton("Cancel",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditMovies.this);
                myBuilder.setTitle("Cancel");
                myBuilder.setMessage("Do you want to discard all changes?");
                myBuilder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                myBuilder.setNegativeButton("Do not Discard",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }
}