package sg.edu.rp.c346.id22016845.freemoviescom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Movies> alMoviesList;

    public CustomAdapter(Context context, int resource, ArrayList<Movies> objects){
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        alMoviesList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout_id, parent, false);
        TextView tvTitle = rowView.findViewById(R.id.movieTitle);
        TextView tvGenre = rowView.findViewById(R.id.movieGenre);
        TextView tvYear = rowView.findViewById(R.id.movieYear);
        ImageView tvRating = rowView.findViewById(R.id.ratingImage);

        Movies currentItem = alMoviesList.get(position);
        tvTitle.setText(currentItem.getTitle());
        tvGenre.setText(currentItem.getGenre());
        tvYear.setText(String.valueOf(currentItem.getYear()));
        if("G".equals(currentItem.getRating())){
            tvRating.setImageResource(R.drawable.rating_g);
        }else if("PG".equals(currentItem.getRating())){
            tvRating.setImageResource(R.drawable.rating_pg);
        }else if("PG13".equals(currentItem.getRating())){
            tvRating.setImageResource(R.drawable.rating_pg13);
        }else if("NC16".equals(currentItem.getRating())){
            tvRating.setImageResource(R.drawable.rating_nc16);
        }else if("M18".equals(currentItem.getRating())){
            tvRating.setImageResource(R.drawable.rating_m18);
        }else if("R21".equals(currentItem.getRating())){
            tvRating.setImageResource(R.drawable.rating_r21);
        }
        return rowView;
    }
}
