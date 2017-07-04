package husaynhakeem.io.popularmovies.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.MainActivity;

/**
 * Created by husaynhakeem on 7/5/17.
 */

public class TrailerUtils {

    public static void openTrailer(Context context, Uri trailerUri, String trailerUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, trailerUri);
        if (intentCanBeHandled(context, intent)) {
            context.startActivity(intent);
        } else {
            openTrailerOnBrowser(context, Uri.parse(trailerUrl));
        }
    }


    private static void openTrailerOnBrowser(Context context, Uri trailerUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, trailerUri);
        if (intentCanBeHandled(context, intent))
            context.startActivity(intent);
        else {
            Snackbar.make(((MainActivity) context).findViewById(android.R.id.content),
                    context.getString(R.string.message_trailer_cannot_be_played),
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }


    private static boolean intentCanBeHandled(Context context, Intent intent) {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }
}
