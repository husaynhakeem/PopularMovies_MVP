package husaynhakeem.io.popularmovies.features.discovery.main;

import husaynhakeem.io.popularmovies.BasePresenter;
import husaynhakeem.io.popularmovies.BaseView;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public interface DiscoveryContract {


    interface View extends BaseView<Presenter> {

        void setupPager();

        void setupTabs();
    }


    interface Presenter extends BasePresenter {

    }
}
