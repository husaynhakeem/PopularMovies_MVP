package husaynhakeem.io.popularmovies.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.MainActivity;

import static husaynhakeem.io.popularmovies.utilities.UiUtils.trailerOuterSpaceInPx;

/**
 * Created by husaynhakeem on 7/5/17.
 */

public class TrailerUtils {

    public static void openTrailer(Context context, Uri trailerUri, String trailerUrl) {
        if (!openTrailerOnYoutubeApp(context, trailerUri))
            openTrailerOnBrowser(context, trailerUrl);
    }


    private static boolean openTrailerOnYoutubeApp(Context context, Uri trailerUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, trailerUri);
        if (intentCanBeHandled(context, intent)) {
            context.startActivity(intent);
            return true;
        }
        return false;
    }


    private static void openTrailerOnBrowser(Context context, String trailerUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        if (intentCanBeHandled(context, intent))
            context.startActivity(intent);
        else {
            onTrailerCannotBeOpened(context);
        }
    }


    private static void onTrailerCannotBeOpened(Context context) {
        Snackbar.make(((MainActivity) context).findViewById(android.R.id.content),
                context.getString(R.string.message_trailer_cannot_be_played),
                Snackbar.LENGTH_SHORT
        ).show();
    }


    private static boolean intentCanBeHandled(Context context, Intent intent) {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }


    public static View trailerView(View rootView) {
        View itemView = LayoutInflater.from(rootView.getContext()).inflate(R.layout.item_trailer, (ViewGroup) rootView, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(itemView.getLayoutParams());
        layoutParams.setMargins(trailerOuterSpaceInPx(), 0, trailerOuterSpaceInPx(), 0);
        itemView.setLayoutParams(layoutParams);
        return itemView;
    }
}
