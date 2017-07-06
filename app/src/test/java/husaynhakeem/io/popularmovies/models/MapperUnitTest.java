package husaynhakeem.io.popularmovies.models;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MapperUnitTest {


    private static final String MOVIES_PAGE_JSON = "{\n" +
            "\"page\": 1,\n" +
            "\"total_results\": 19637,\n" +
            "\"total_pages\": 982,\n" +
            "\"results\": [\n" +
            "{\n" +
            "\"vote_count\": 805,\n" +
            "\"id\": 297762,\n" +
            "\"video\": false,\n" +
            "\"vote_average\": 7.2,\n" +
            "\"title\": \"Wonder Woman\",\n" +
            "\"popularity\": 136.396465,\n" +
            "\"poster_path\": \"/gfJGlDaHuWimErCr5Ql0I8x9QSy.jpg\",\n" +
            "\"original_language\": \"en\",\n" +
            "\"original_title\": \"Wonder Woman\",\n" +
            "\"genre_ids\": [\n" +
            "28,\n" +
            "12,\n" +
            "14,\n" +
            "878\n" +
            "],\n" +
            "\"backdrop_path\": \"/hA5oCgvgCxj5MEWcLpjXXTwEANF.jpg\",\n" +
            "\"adult\": false,\n" +
            "\"overview\": \"An Amazon princess comes to the world of Man to become the greatest of the female superheroes.\",\n" +
            "\"release_date\": \"2017-05-30\"\n" +
            "}]}";


    @Test
    public void mappingIsCorrect() {
        MoviesPage moviesPage = dummyMoviePage();
        MoviesPage moviesPageFromJson = (MoviesPage) Mapper.instance().convertFromJsonToObject(MOVIES_PAGE_JSON, MoviesPage.class);

        Assert.assertEquals(moviesPage, moviesPageFromJson);
    }


    private MoviesPage dummyMoviePage() {
        MoviesPage moviesPage = new MoviesPage();
        moviesPage.setPage(1);
        moviesPage.setTotalResults(19637);
        moviesPage.setTotalPages(982);
        moviesPage.setMovies(dummyMovieList());
        return moviesPage;
    }


    private List<Movie> dummyMovieList() {
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie(297762,
                7.2,
                "Wonder Woman",
                "/gfJGlDaHuWimErCr5Ql0I8x9QSy.jpg",
                "An Amazon princess comes to the world of Man to become the greatest of the female superheroes.",
                "2017-05-30")
        );

        return movies;
    }
}
