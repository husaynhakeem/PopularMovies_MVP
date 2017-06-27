package husaynhakeem.io.popularmovies.features.moviedetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.Review;
import husaynhakeem.io.popularmovies.utilities.StringUtils;

/**
 * Created by husaynhakeem on 6/11/17.
 */
public class MovieDetailsView implements MovieDetailsContract {

    private View rootView;
    private FloatingActionButton saveMovieFAB;

    // General info
    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView voteAverageTextView;


    // overview
    private TextView overViewTextView;


    // Reviews
    private LinearLayout reviewsLayout;
    private ProgressBar reviewsLoadingProgressBar;
    private View reviewsNoInternetLayout;
    private Button reviewRetryButton;
    private TextView noReviewsTextView;


    private Movie movie;
    private ClickHandler clickHandler;


    public MovieDetailsView(LayoutInflater layoutInflater, ViewGroup parent) {
        rootView = layoutInflater.inflate(R.layout.activity_movie_details, parent, false);
        initViews();
    }


    @Override
    public void initViews() {
        posterImageView = (ImageView) rootView.findViewById(R.id.iv_movie_poster);
        titleTextView = (TextView) rootView.findViewById(R.id.tv_title);
        releaseDateTextView = (TextView) rootView.findViewById(R.id.tv_release_date);
        voteAverageTextView = (TextView) rootView.findViewById(R.id.tv_vote_average);
        overViewTextView = (TextView) rootView.findViewById(R.id.tv_overview);
        reviewsLayout = (LinearLayout) rootView.findViewById(R.id.ll_movie_reviews);
        reviewsLoadingProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_reviews_loading);
        reviewsNoInternetLayout = rootView.findViewById(R.id.ll_no_internet_reviews);
        reviewRetryButton = (Button) rootView.findViewById(R.id.btn_retry_reviews);
        noReviewsTextView = (TextView) rootView.findViewById(R.id.tv_no_reviews);
        saveMovieFAB = (FloatingActionButton) rootView.findViewById(R.id.fab_save_movie);

        initListeners();
    }


    private void initListeners() {

        reviewRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHandler.onRetry();
            }
        });


        saveMovieFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHandler.onSaveMovieClicked();
            }
        });
    }


    @Override
    public void setMoviePoster(String posterPath) {
        Picasso.with(rootView.getContext())
                .load(posterPath)
                .into(posterImageView);
    }


    @Override
    public void setMovieGeneralInfo(Movie movie) {
        if (movie == null)
            return;

        titleTextView.setText(movie.getTitle());
        releaseDateTextView.setText(StringUtils.getYearFromDate(movie.getReleaseDate()));
        voteAverageTextView.setText(StringUtils.getVoteAverageToDisplay(movie.getVoteAverage()));
        overViewTextView.setText(movie.getOverview());
    }


    @Override
    public void setMovieReviews(List<Review> reviews) {

        if (reviews == null || reviews.size() == 0) {
            onNoReviews();
            return;
        }

        for (Review review : reviews) {
            reviewsLayout.addView(reviewView(review));
        }
    }


    private View reviewView(Review review) {

        View itemView = LayoutInflater.from(rootView.getContext()).inflate(R.layout.item_review, (ViewGroup) rootView, false);

        TextView author = (TextView) itemView.findViewById(R.id.tv_review_author);
        TextView content = (TextView) itemView.findViewById(R.id.tv_review_content);

        author.setText(review.getAuthor());
        content.setText(review.getContent());

        return itemView;
    }


    @Override
    public void onReviewsLoading(boolean doneLoading) {
        if (doneLoading) {
            reviewsLayout.setVisibility(View.GONE);
            noReviewsTextView.setVisibility(View.GONE);
            reviewsLoadingProgressBar.setVisibility(View.VISIBLE);
        } else {
            reviewsLayout.setVisibility(View.VISIBLE);
            reviewsLoadingProgressBar.setVisibility(View.GONE);
        }
    }


    public void onNoReviews() {
        noReviewsTextView.setVisibility(View.VISIBLE);
        reviewsLayout.setVisibility(View.GONE);
    }


    @Override
    public void onNoInternet() {
        reviewsLayout.setVisibility(View.GONE);
        reviewsNoInternetLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onInternet() {
        reviewsLayout.setVisibility(View.VISIBLE);
        reviewsNoInternetLayout.setVisibility(View.GONE);
    }


    @Override
    public void setClickHandler(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
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
