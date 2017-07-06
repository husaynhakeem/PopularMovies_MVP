package husaynhakeem.io.popularmovies.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by husaynhakeem on 6/26/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewsPage {


    @JsonProperty("id")
    private long id;

    @JsonIgnore
    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private List<Review> reviews;

    @JsonIgnore
    @JsonProperty("total_pages")
    private int totalPages;

    @JsonIgnore
    @JsonProperty("total_results")
    private int totalResults;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
