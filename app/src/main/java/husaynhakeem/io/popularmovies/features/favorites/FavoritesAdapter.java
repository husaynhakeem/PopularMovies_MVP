package husaynhakeem.io.popularmovies.features.favorites;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.network.MoviePosterNetworkUtils;

import static husaynhakeem.io.popularmovies.database.MovieTable.COLUMN_MOVIE_ID;
import static husaynhakeem.io.popularmovies.database.MovieTable.COLUMN_MOVIE_OVERVIEW;
import static husaynhakeem.io.popularmovies.database.MovieTable.COLUMN_MOVIE_POSTER;
import static husaynhakeem.io.popularmovies.database.MovieTable.COLUMN_MOVIE_RELEASE_DATE;
import static husaynhakeem.io.popularmovies.database.MovieTable.COLUMN_MOVIE_TITLE;
import static husaynhakeem.io.popularmovies.database.MovieTable.COLUMN_MOVIE_VOTE_AVERAGE;

/**
 * Created by husaynhakeem on 7/2/17.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {


    private Cursor favorites;
    private FavoritesView view;


    public FavoritesAdapter(Cursor favorites, FavoritesView view) {
        this.favorites = favorites;
        this.view = view;
    }


    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavoriteViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        favorites.moveToPosition(position);

        Movie currentMovie = new Movie();
        currentMovie.setId(favorites.getInt(favorites.getColumnIndex(COLUMN_MOVIE_ID)));
        currentMovie.setTitle(favorites.getString(favorites.getColumnIndex(COLUMN_MOVIE_TITLE)));
        currentMovie.setPosterPath(favorites.getString(favorites.getColumnIndex(COLUMN_MOVIE_POSTER)));
        currentMovie.setReleaseDate(favorites.getString(favorites.getColumnIndex(COLUMN_MOVIE_RELEASE_DATE)));
        currentMovie.setVoteAverage(favorites.getDouble(favorites.getColumnIndex(COLUMN_MOVIE_VOTE_AVERAGE)));
        currentMovie.setOverview(favorites.getString(favorites.getColumnIndex(COLUMN_MOVIE_OVERVIEW)));

        holder.movie = currentMovie;
        Picasso.with(holder.poster.getContext())
                .load(MoviePosterNetworkUtils.buildPosterUrl(currentMovie.getPosterPath()).toString())
                .placeholder(R.drawable.ic_poster_placeholder)
                .into(holder.poster);
    }


    public void putFavorites(Cursor favorites) {
        if (this.favorites != null)
            this.favorites = null;
        this.favorites = favorites;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.getCount();
    }


    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Movie movie;
        private ImageView poster;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            poster.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            view.onMovieClicked(movie);
        }
    }
}
