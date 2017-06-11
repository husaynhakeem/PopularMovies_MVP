package husaynhakeem.io.popularmovies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by husaynhakeem on 6/11/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {


    @JsonProperty("id")
    private int id;

    @JsonProperty("vote_average")
    private double voteAverage;

    @JsonProperty("title")
    private String title;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;


    public Movie() {
    }

    public Movie(int id, double voteAverage, String title, String posterPath, String overview, String releaseDate) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Movie) {
            Movie movieObject = (Movie) obj;

            return id == movieObject.id
                    && voteAverage == movieObject.voteAverage
                    && title.equals(movieObject.title)
                    && posterPath.equals(movieObject.posterPath)
                    && overview.equals(movieObject.overview)
                    && releaseDate.equals(movieObject.releaseDate);
        }

        return super.equals(obj);
    }
}
