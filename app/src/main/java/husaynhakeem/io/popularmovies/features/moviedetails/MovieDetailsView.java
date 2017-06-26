package husaynhakeem.io.popularmovies.features.moviedetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Review;

import static husaynhakeem.io.popularmovies.models.Movie.MOVIE_OVERVIEW;
import static husaynhakeem.io.popularmovies.models.Movie.MOVIE_RELEASE_DATE;
import static husaynhakeem.io.popularmovies.models.Movie.MOVIE_TITLE;
import static husaynhakeem.io.popularmovies.models.Movie.MOVIE_VOTE_AVERAGE;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MovieDetailsView implements MovieDetailsContract {


    private View rootView;
    private TextView titleTextView;
    private ImageView posterImageView;
    private TextView releaseDateTextView;
    private TextView voteAverageTextView;
    private TextView overViewTextView;
    private ProgressBar loadingProgressBar;


    public MovieDetailsView(LayoutInflater layoutInflater, ViewGroup parent) {
        rootView = layoutInflater.inflate(R.layout.activity_movie_details, parent, false);
        initViews();
    }


    @Override
    public void initViews() {
        titleTextView = (TextView) rootView.findViewById(R.id.tv_title);
        posterImageView = (ImageView) rootView.findViewById(R.id.iv_movie_poster);
        releaseDateTextView = (TextView) rootView.findViewById(R.id.tv_release_date);
        voteAverageTextView = (TextView) rootView.findViewById(R.id.tv_vote_average);
        overViewTextView = (TextView) rootView.findViewById(R.id.tv_overview);
        loadingProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_loading);
    }


    @Override
    public void setMoviePoster(String posterPath) {
        Picasso.with(rootView.getContext())
                .load(posterPath)
                .into(posterImageView);
    }


    @Override
    public void setMovieDetails(Bundle bundle) {
        if (bundle == null)
            return;

        populateTextView(bundle, MOVIE_TITLE, titleTextView);
        populateTextView(bundle, MOVIE_RELEASE_DATE, releaseDateTextView);
        populateTextView(bundle, MOVIE_VOTE_AVERAGE, voteAverageTextView);
        populateTextView(bundle, MOVIE_OVERVIEW, overViewTextView);
    }


    public void setMovieReviews(List<Review> reviews) {

    }


    private void populateTextView(Bundle bundle, String key, TextView textView) {
        if (bundle.containsKey(key)) {
            textView.setText(bundle.getString(key));
        }
    }


    public void showLoadingIndicator(boolean shouldDisplay) {
        if (shouldDisplay) {
            loadingProgressBar.setVisibility(View.VISIBLE);
        } else {
            loadingProgressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public View getRootView() {
        return rootView;
    }


    @Override
    public Bundle getViewState() {
        return null;
    }
}
