package husaynhakeem.io.popularmovies.features.details;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.Review;
import husaynhakeem.io.popularmovies.models.Trailer;
import husaynhakeem.io.popularmovies.network.MovieTrailersNetworkUtils;
import husaynhakeem.io.popularmovies.utilities.StringUtils;
import husaynhakeem.io.popularmovies.utilities.TrailerUtils;
import husaynhakeem.io.popularmovies.utilities.UiUtils;

import static husaynhakeem.io.popularmovies.utilities.TrailerUtils.trailerView;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class DetailsView extends Fragment implements DetailsContract.View {


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
    private ExpandableLayout reviewsExpandableLayout;
    private LinearLayout reviewsLayout;
    private ProgressBar reviewsLoadingProgressBar;
    private View reviewsNoInternetLayout;
    private Button reviewRetryButton;
    private TextView noReviewsTextView;
    private DetailsPresenter presenter;

    // Trailers
    private ExpandableLayout trailersExpandableLayout;
    private GridLayout trailersGridView;
    private TextView noTrailersTextView;
    private TextView trailersNoInternetTextView;

    private boolean isMovieSaved;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        initViews();
        if (presenter == null) {
            setPresenter(new DetailsPresenter());
            presenter.setView(this);
        }
        presenter.start();
        return rootView;
    }


    @Override
    public void initViews() {
        posterImageView = (ImageView) rootView.findViewById(R.id.iv_movie_poster);
        titleTextView = (TextView) rootView.findViewById(R.id.tv_title);
        releaseDateTextView = (TextView) rootView.findViewById(R.id.tv_release_date);
        voteAverageTextView = (TextView) rootView.findViewById(R.id.tv_vote_average);
        overViewTextView = (TextView) rootView.findViewById(R.id.tv_overview);
        trailersExpandableLayout = (ExpandableLayout) rootView.findViewById(R.id.exl_trailers);
        trailersGridView = (GridLayout) rootView.findViewById(R.id.gl_movie_trailers);
        trailersGridView.setColumnCount(UiUtils.numberOfTrailersPerRow());
        noTrailersTextView = (TextView) rootView.findViewById(R.id.tv_no_trailers);
        trailersNoInternetTextView = (TextView) rootView.findViewById(R.id.tv_no_internet_trailers);
        reviewsLayout = (LinearLayout) rootView.findViewById(R.id.ll_movie_reviews);
        reviewsExpandableLayout = (ExpandableLayout) rootView.findViewById(R.id.exl_reviews);
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
                presenter.onRetry();
            }
        });

        saveMovieFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveMovieClicked();
            }
        });

        rootView.findViewById(R.id.tv_trailers_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trailersExpandableLayout.isExpanded())
                    trailersExpandableLayout.collapse();
                else
                    trailersExpandableLayout.expand();
            }
        });

        rootView.findViewById(R.id.tv_reviews_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reviewsExpandableLayout.isExpanded())
                    reviewsExpandableLayout.collapse();
                else
                    reviewsExpandableLayout.expand();
            }
        });
    }


    @Override
    public void setFABImage(boolean isMovieSaved) {
        if (isMovieSaved)
            saveMovieFAB.setImageResource(R.drawable.ic_action_save_movie_checked);
        else
            saveMovieFAB.setImageResource(R.drawable.ic_action_save_movie_unchecked);
    }


    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
        this.presenter = (DetailsPresenter) presenter;
    }


    @Override
    public void setMoviePoster(String posterPath) {
        Picasso.with(rootView.getContext())
                .load(posterPath)
                .placeholder(R.drawable.ic_poster_placeholder)
                .into(posterImageView);
    }


    @Override
    public void setMovieGeneralInfo(Movie movie) {
        if (movie == null)
            return;

        titleTextView.setText(movie.getTitle());
        releaseDateTextView.setText(getString(R.string.release_date, StringUtils.getYearFromDate(movie.getReleaseDate())));
        voteAverageTextView.setText(getString(R.string.vote_average, StringUtils.getVoteAverageToDisplay(movie.getVoteAverage())));
        overViewTextView.setText(movie.getOverview());
    }


    @Override
    public void setMovieTrailers(List<Trailer> trailers) {
        if (trailers == null || trailers.size() == 0) {
            onNoTrailers();
            return;
        }

        trailersGridView.setVisibility(View.VISIBLE);
        noTrailersTextView.setVisibility(View.GONE);
        trailersNoInternetTextView.setVisibility(View.GONE);

        for (Trailer trailer : trailers) {
            trailersGridView.addView(trailerItemView(trailer));
            Log.e("Trailer", trailer.getKey());
        }
    }


    private View trailerItemView(final Trailer trailer) {
        View itemView = trailerView(rootView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri trailerUriForYoutubeApp = MovieTrailersNetworkUtils.buildYoutubeTrailerUri(trailer.getKey());
                String trailerUrlForBrowser = MovieTrailersNetworkUtils.buildYoutubeTrailerUrl(trailer.getKey());
                TrailerUtils.openTrailer(getContext(), trailerUriForYoutubeApp, trailerUrlForBrowser);
            }
        });
        return itemView;
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


    @Override
    public void onNoTrailers() {
        noTrailersTextView.setVisibility(View.VISIBLE);
        trailersGridView.setVisibility(View.GONE);
    }


    @Override
    public void onNoReviews() {
        noReviewsTextView.setVisibility(View.VISIBLE);
        reviewsLayout.setVisibility(View.GONE);
    }


    @Override
    public void onNoInternet() {
        trailersGridView.setVisibility(View.GONE);
        trailersNoInternetTextView.setVisibility(View.VISIBLE);

        reviewsLayout.setVisibility(View.GONE);
        reviewsNoInternetLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onInternet() {
        reviewsLayout.setVisibility(View.VISIBLE);
        reviewsNoInternetLayout.setVisibility(View.GONE);
    }


    @Override
    public void onSaveMovieClicked() {
        presenter.onSaveMovieClicked(isMovieSaved);
    }


    @Override
    public void setMovieSaved(boolean isMovieSaved) {
        this.isMovieSaved = isMovieSaved;
    }


    @Override
    public void onMovieSaved() {
        saveMovieFAB.setImageResource(R.drawable.ic_action_save_movie_checked);
        Toast.makeText(getContext(), R.string.message_movie_saved, Toast.LENGTH_SHORT).show();
        isMovieSaved = true;
    }


    @Override
    public void onMovieUnsaved() {
        saveMovieFAB.setImageResource(R.drawable.ic_action_save_movie_unchecked);
        Toast.makeText(getContext(), R.string.message_movie_unsaved, Toast.LENGTH_SHORT).show();
        isMovieSaved = false;
    }


    @Override
    public void onRetry() {
        presenter.onRetry();
    }
}