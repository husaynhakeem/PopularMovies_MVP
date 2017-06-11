package husaynhakeem.io.popularmovies.view;

import android.os.Bundle;
import android.view.View;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public interface BaseView {

    void initViews();
    View getRootView();
    Bundle getViewState();
}
