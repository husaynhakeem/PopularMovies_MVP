package husaynhakeem.io.popularmovies.features.moviesDiscovery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.network.NetworkUtils;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {


    private List<Movie> movies;
    private ClickListener clickListener;


    public MoviesAdapter(List<Movie> movies) {
        this.movies = movies;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie currentMovie = movies.get(position);

        holder.movieId = currentMovie.getId();

        Picasso.with(holder.posterImageView.getContext())
                .load(NetworkUtils.buildPosterUrl(currentMovie.getPosterPath()).toString())
                .into(holder.posterImageView);
    }


    @Override
    public int getItemCount() {
        return (movies == null) ? 0 : movies.size();
    }


    public void addMovies(List<Movie> movies) {
        if (this.movies == null) {
            this.movies = new ArrayList<>();
        }

        this.movies.addAll(movies);
    }


    public void setMovies() {
        if (this.movies == null) {
            this.movies = new ArrayList<>();
        } else {
            this.movies.clear();
        }

        this.movies.addAll(movies);
    }


    interface ClickListener {
        void onMovieClick(int id);
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView posterImageView;
        private int movieId;


        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        }

        @Override
        public void onClick(View v) {
            clickListener.onMovieClick(movieId);
        }
    }
}
