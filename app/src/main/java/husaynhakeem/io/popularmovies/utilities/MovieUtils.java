package husaynhakeem.io.popularmovies.utilities;

import java.util.List;

import husaynhakeem.io.popularmovies.models.Movie;

/**
 * Created by husaynhakeem on 7/6/17.
 */

public class MovieUtils {


    public static boolean areMovieListsEqual(List<Movie> movies, List<Movie> moviesToCompare) {

        if (moviesToCompare == null) {
            return movies == null;
        }

        return !(movies.size() != moviesToCompare.size()
                || !moviesToCompare.containsAll(movies));
    }
}
