package husaynhakeem.io.popularmovies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static husaynhakeem.io.popularmovies.utilities.MovieUtils.areMovieListsEqual;

/**
 * Created by husaynhakeem on 6/11/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviesPage {


    @JsonProperty("page")
    private int page;

    @JsonProperty("total_results")
    private int totalResults;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("results")
    private List<Movie> movies;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof MoviesPage) {
            MoviesPage moviesPageObject = (MoviesPage) obj;

            return page == moviesPageObject.page
                    && totalResults == moviesPageObject.totalResults
                    && totalPages == moviesPageObject.totalPages
                    && areMovieListsEqual(movies, moviesPageObject.movies);
        }

        return super.equals(obj);
    }
}
