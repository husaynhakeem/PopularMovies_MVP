package husaynhakeem.io.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by husaynhakeem on 6/30/17.
 */

public class MovieDatabase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    private static final String QUERY_CREATE_POPULAR_TABLE = "CREATE TABLE " +
            PopularMovieTable.TABLE_NAME + "(" +
            MovieTable.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY," +
            MovieTable.COLUMN_MOVIE_TITLE + " TEXT," +
            MovieTable.COLUMN_MOVIE_POSTER + " TEXT," +
            MovieTable.COLUMN_MOVIE_RELEASE_DATE + " TEXT," +
            MovieTable.COLUMN_MOVIE_VOTE_AVERAGE + " DOUBLE," +
            MovieTable.COLUMN_MOVIE_OVERVIEW + " TEXT" +
            ")";

    private static final String QUERY_CREATE_TOP_RATED_TABLE = "CREATE TABLE " +
            TopRatedMovieTable.TABLE_NAME + "(" +
            MovieTable.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY," +
            MovieTable.COLUMN_MOVIE_TITLE + " TEXT," +
            MovieTable.COLUMN_MOVIE_POSTER + " TEXT," +
            MovieTable.COLUMN_MOVIE_RELEASE_DATE + " TEXT," +
            MovieTable.COLUMN_MOVIE_VOTE_AVERAGE + " DOUBLE," +
            MovieTable.COLUMN_MOVIE_OVERVIEW + " TEXT" +
            ")";

    private static final String QUERY_DELETE_POPULAR_TABLE = "DROP TABLE IF EXISTS " + PopularMovieTable.TABLE_NAME;

    private static final String QUERY_DELETE_TOP_RATED_TABLE = "DROP TABLE IF EXISTS " + TopRatedMovieTable.TABLE_NAME;


    public MovieDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_POPULAR_TABLE);
        db.execSQL(QUERY_CREATE_TOP_RATED_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DELETE_POPULAR_TABLE);
        db.execSQL(QUERY_DELETE_TOP_RATED_TABLE);
        onCreate(db);
    }
}
