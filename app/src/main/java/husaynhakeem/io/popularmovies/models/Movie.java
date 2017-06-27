package husaynhakeem.io.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by husaynhakeem on 6/11/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Parcelable {


    public static final String MOVIE = "movie";
    public static final String MOVIE_ID = "movie id";
    public static final String MOVIE_TITLE = "movie title";
    public static final String MOVIE_POSTER = "movie poster";
    public static final String MOVIE_RELEASE_DATE = "movie release date";
    public static final String MOVIE_VOTE_AVERAGE = "movie vote average";
    public static final String MOVIE_OVERVIEW = "movie overview";


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

    public Movie(Parcel in) {
        id = in.readInt();
        voteAverage = in.readDouble();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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
