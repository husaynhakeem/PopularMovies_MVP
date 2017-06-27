package husaynhakeem.io.popularmovies.features.moviesdiscovery;

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
import husaynhakeem.io.popularmovies.network.MoviePosterNetworkUtils;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {


    private List<Movie> movies;
    private ClickListener clickListener;


    public MoviesAdapter(List<Movie> movies, ClickListener clickListener) {
        this.movies = movies;
        this.clickListener = clickListener;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie currentMovie = movies.get(position);

        holder.movie = currentMovie;

        Picasso.with(holder.posterImageView.getContext())
                .load(MoviePosterNetworkUtils.buildPosterUrl(currentMovie.getPosterPath()).toString())
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


    public List<Movie> getMovies() {
        return movies;
    }

    public void setClickListener(ClickListener listener) {
        clickListener = listener;
    }


    interface ClickListener {
        void onMovieClick(Movie movie);
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView posterImageView;
        private Movie movie;



        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            posterImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onMovieClick(movie);
        }
    }
}
