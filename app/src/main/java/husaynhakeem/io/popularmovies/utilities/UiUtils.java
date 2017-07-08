package husaynhakeem.io.popularmovies.utilities;

import android.content.res.Resources;

/**
 * Created by husaynhakeem on 7/5/17.
 */

public class UiUtils {


    private static final int TABLET_MIN_WIDTH = 600;
    private static final int ROW_MIN_WIDTH_TABLET = 300;
    private static final int ROW_MIN_WIDTH_PHONE = 350;
    private static final int TRAILER_WIDTH_WITH_MARGINS = 120;
    private static final int TRAILER_OUTER_SPACE_PER_SIDE = 10;


    private static int deviceWidthInDp() {
        int widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (widthPixels / density);
    }


    public static boolean isTablet() {
        return deviceWidthInDp() > TABLET_MIN_WIDTH;
    }


    public static int numberOfMovieRows() {
        if (isTablet())
            return numberOfMovieRowsTablet();
        return numberOfMovieRowsPhone();
    }


    private static int numberOfMovieRowsTablet() {
        int deviceWidth = deviceWidthInDp();
        return deviceWidth / ROW_MIN_WIDTH_TABLET;
    }


    private static int numberOfMovieRowsPhone() {
        int deviceWidth = deviceWidthInDp();
        return deviceWidth / ROW_MIN_WIDTH_PHONE;
    }


    public static int numberOfTrailersPerRow() {
        return deviceWidthInDp() / TRAILER_WIDTH_WITH_MARGINS;
    }


    public static int trailerOuterSpaceInPx() {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (TRAILER_OUTER_SPACE_PER_SIDE * density);
    }
}
