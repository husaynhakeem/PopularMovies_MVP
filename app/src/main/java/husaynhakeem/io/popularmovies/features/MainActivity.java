package husaynhakeem.io.popularmovies.features;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.moviesdiscovery.MoviesDiscoveryPresenter;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPager();
        setUpTabs();
    }


    private void setupPager() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
    }


    private void setUpTabs() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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

                Fragment moviesPresenter = pagerAdapter.getItem(0);
                if (moviesPresenter != null && moviesPresenter instanceof MoviesDiscoveryPresenter)
                    ((MoviesDiscoveryPresenter) moviesPresenter).scrollBackToTop();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_discovery, menu);
        return true;
    }
}
