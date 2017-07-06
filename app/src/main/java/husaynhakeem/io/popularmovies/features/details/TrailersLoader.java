package husaynhakeem.io.popularmovies.features.details;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.net.URL;
import java.util.List;

import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.Trailer;
import husaynhakeem.io.popularmovies.models.TrailersPage;
import husaynhakeem.io.popularmovies.network.GeneralNetworkUtils;
import husaynhakeem.io.popularmovies.network.MovieTrailersNetworkUtils;

import static husaynhakeem.io.popularmovies.features.details.DetailsPresenter.MOVIE_ID;

/**
 * Created by husaynhakeem on 7/4/17.
 */

public class TrailersLoader implements LoaderManager.LoaderCallbacks<List<Trailer>> {


    public static final int MOVIE_TRAILERS_LOADER_ID = 11;
    private DetailsView view;


    public TrailersLoader(DetailsView view) {
        this.view = view;
    }


    @Override
    public Loader<List<Trailer>> onCreateLoader(int id, final Bundle args) {

        switch (id) {

            case MOVIE_TRAILERS_LOADER_ID:
                return new AsyncTaskLoader<List<Trailer>>(view.getContext()) {

                    private List<Trailer> movieTrailers;


                    @Override
                    protected void onStartLoading() {
                        if (movieTrailers != null) {
                            deliverResult(movieTrailers);
                        } else {
                            forceLoad();
                        }
                    }

                    @Override
                    public List<Trailer> loadInBackground() {
                        try {
                            URL movieTrailersUrl = MovieTrailersNetworkUtils.buildTrailersUrl(getContext(), args.getInt(MOVIE_ID));
                            String movieTrailersJson = GeneralNetworkUtils.getResponseFromUrl(movieTrailersUrl);
                            TrailersPage trailersPage = (TrailersPage) Mapper.instance().convertFromJsonToObject(movieTrailersJson, TrailersPage.class);
                            movieTrailers = trailersPage.getTrailers();
                            return movieTrailers;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };

            default:
                return null;
        }
    }


    @Override
    public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {

        int id = loader.getId();

        switch (id) {
            case MOVIE_TRAILERS_LOADER_ID:
                view.setMovieTrailers(data);
                break;

            default:
                break;
        }
    }


    @Override
    public void onLoaderReset(Loader<List<Trailer>> loader) {
    }
}
