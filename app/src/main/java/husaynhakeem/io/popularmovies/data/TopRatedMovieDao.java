package husaynhakeem.io.popularmovies.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

/**
 * Created by husaynhakeem on 6/27/17.
 */

@Dao
public interface TopRatedMovieDao {

    @Insert
    void insert(TopRatedMovie... movies);


    @Delete
    void delete(TopRatedMovie... movies);


    @Query("SELECT * FROM movie_top_rated")
    Cursor loadAll();
}
