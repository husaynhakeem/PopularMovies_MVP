package husaynhakeem.io.popularmovies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import husaynhakeem.io.popularmovies.data.MovieDatabase;
import husaynhakeem.io.popularmovies.data.PopularMovie;
import husaynhakeem.io.popularmovies.data.TopRatedMovie;
import husaynhakeem.io.popularmovies.models.Movie;

import static husaynhakeem.io.popularmovies.provider.MoviesContract.CONTENT_AUTHORITY;
import static husaynhakeem.io.popularmovies.provider.MoviesContract.POPULAR_MOVIES_PATH;
import static husaynhakeem.io.popularmovies.provider.MoviesContract.TOP_RATED_MOVIES_PATH;

/**
 * Created by husaynhakeem on 6/27/17.
 */

public class MoviesProvider extends ContentProvider {


    private static final int CODE_POPULAR_MOVIES = 100;
    private static final int CODE_TOP_RATED_MOVIES = 101;


    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private MovieDatabase movieDb;


    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, POPULAR_MOVIES_PATH, CODE_POPULAR_MOVIES);
        uriMatcher.addURI(CONTENT_AUTHORITY, TOP_RATED_MOVIES_PATH, CODE_TOP_RATED_MOVIES);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {

        movieDb = MovieDatabase.instance(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        int match = sUriMatcher.match(uri);

        switch (match) {

            case CODE_POPULAR_MOVIES:
                return movieDb.popularMovieDao().loadAll();

            case CODE_TOP_RATED_MOVIES:
                return movieDb.topRatedMovieDao().loadAll();

            default:
                throw new RuntimeException("Undefined query uri");
        }
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Movie movie = new Movie(values);
        int match = sUriMatcher.match(uri);

        switch (match) {

            case CODE_POPULAR_MOVIES:
                movieDb.popularMovieDao().insert((PopularMovie) movie);

            case CODE_TOP_RATED_MOVIES:
                movieDb.topRatedMovieDao().insert((TopRatedMovie) movie);

            default:
                throw new RuntimeException("Undefined query uri");
        }
    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
