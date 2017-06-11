package husaynhakeem.io.popularmovies.utilities;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class StringUtils {


    private static final String VOTE_AVERAGE_SUFFIX = "/10";


    /**
     * @param date String in the format YYYY-MM-DD
     * @return
     */
    public static String getYearFromDate(String date) {
        if (date == null || date.length() < 4)
            return "";

        return date.substring(0, 4);
    }


    public static String getVoteAverageToDisplay(double voteAverage) {
        return voteAverage + VOTE_AVERAGE_SUFFIX;
    }
}
