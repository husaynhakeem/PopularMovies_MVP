package husaynhakeem.io.popularmovies.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class Mapper {

    public static MoviesPage convertFromJsonToMovies(String moviesJson) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(moviesJson, MoviesPage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
