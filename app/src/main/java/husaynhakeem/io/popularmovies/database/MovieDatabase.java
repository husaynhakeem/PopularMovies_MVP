package husaynhakeem.io.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_ID;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_OVERVIEW;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_POSTER;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_RELEASE_DATE;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_TITLE;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_VOTE_AVERAGE;

/**
 * Created by husaynhakeem on 6/30/17.
 */

public class MovieDatabase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 2;

    private static final String QUERY_CREATE_FAVORITE_TABLE = "CREATE TABLE " +
            FavoriteMovieTable.TABLE_NAME + "(" +
            COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY," +
            COLUMN_MOVIE_TITLE + " TEXT," +
            COLUMN_MOVIE_POSTER + " TEXT," +
            COLUMN_MOVIE_RELEASE_DATE + " TEXT," +
            COLUMN_MOVIE_VOTE_AVERAGE + " DOUBLE," +
            COLUMN_MOVIE_OVERVIEW + " TEXT" +
            ")";

    private static final String QUERY_DELETE_FAVORITE_TABLE = "DROP TABLE IF EXISTS " + FavoriteMovieTable.TABLE_NAME;


    public MovieDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_FAVORITE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DELETE_FAVORITE_TABLE);
        onCreate(db);
    }
}
