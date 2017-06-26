package husaynhakeem.io.popularmovies.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class Mapper<T> {


    private static Mapper mapperInstance;


    private Mapper() {
    }


    public static Mapper instance() {
        if (mapperInstance == null)
            mapperInstance = new Mapper();

        return mapperInstance;
    }


    public T convertFromJsonToMovies(String moviesJson, Class<T> classType) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(moviesJson, classType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
