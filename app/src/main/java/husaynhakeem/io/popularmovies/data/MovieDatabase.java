package husaynhakeem.io.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by husaynhakeem on 6/27/17.
 */

@Database(entities = {PopularMovie.class, TopRatedMovie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movies.db";

    public abstract PopularMovieDao popularMovieDao();

    public abstract TopRatedMovieDao topRatedMovieDao();

    private static MovieDatabase dbInstance;

    public static MovieDatabase instance(Context context) {
        if (dbInstance == null)
            dbInstance = Room.databaseBuilder(context, MovieDatabase.class, DATABASE_NAME).build();
        return dbInstance;
    }
}
