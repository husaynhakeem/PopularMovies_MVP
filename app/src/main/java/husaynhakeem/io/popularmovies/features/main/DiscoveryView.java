package husaynhakeem.io.popularmovies.features.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.movies.MoviesView;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class DiscoveryView extends Fragment implements DiscoveryContract.View {


    private View rootView;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private DiscoveryPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.start();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_discovery, container, false);
        setupPager();
        setupTabs();
        return rootView;
    }


    @Override
    public void setupPager() {
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    public void setupTabs() {

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() != 0)
                    return;

                Fragment moviesView = pagerAdapter.getItem(0);
                if (moviesView != null && moviesView instanceof MoviesView)
                    ((MoviesView) moviesView).scrollBackToTop();
            }
        });
    }


    @Override
    public void setPresenter(DiscoveryContract.Presenter presenter) {
        this.presenter = (DiscoveryPresenter) presenter;
    }
}
