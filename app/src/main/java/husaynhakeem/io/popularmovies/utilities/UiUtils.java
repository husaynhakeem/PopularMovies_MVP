package husaynhakeem.io.popularmovies.utilities;

import android.content.res.Resources;

/**
 * Created by husaynhakeem on 7/5/17.
 */

public class UiUtils {


    public static boolean isTablet() {
        int widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
        float density = Resources.getSystem().getDisplayMetrics().density;
        int widthDp = (int) (widthPixels / density);
        return widthDp > 600;
    }
}
