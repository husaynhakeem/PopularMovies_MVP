package husaynhakeem.io.popularmovies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by husaynhakeem on 7/4/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrailersPage {

    @JsonProperty("id")
    private int id;

    @JsonProperty("results")
    private List<Trailer> trailers;


    public TrailersPage() {
    }

    public TrailersPage(int id, List<Trailer> trailers) {
        this.id = id;
        this.trailers = trailers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
