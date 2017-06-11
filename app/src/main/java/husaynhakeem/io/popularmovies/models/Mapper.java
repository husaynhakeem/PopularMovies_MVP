package husaynhakeem.io.popularmovies.models;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class Mapper {

    private static final String TAG = Mapper.class.getSimpleName();

    public static MoviesPage convertFromJsonToMovies(String moviesJson) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(moviesJson, MoviesPage.class);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error while converting movies from JSON to objects: " + e.getMessage());
            return null;
        }
    }
}
