package husaynhakeem.io.popularmovies.features;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.discovery.DiscoveryPresenter;
import husaynhakeem.io.popularmovies.features.discovery.DiscoveryView;

public class MainActivity extends AppCompatActivity {


    private static DiscoveryView discoveryView;
    private static DiscoveryPresenter discoveryPresenter;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            return;

        startDiscoveryView();
    }


    private void startDiscoveryView() {

        discoveryView = new DiscoveryView();
        discoveryPresenter = new DiscoveryPresenter();
        discoveryView.setPresenter(discoveryPresenter);
        discoveryPresenter.setView(discoveryView);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_frame, discoveryView)
                .commit();
    }


    public void onMovieDetailDisplayed() {
        findViewById(R.id.tv_no_detail_message).setVisibility(View.GONE);
    }


    public void onFavoritesChanged() {
        discoveryView.onFavoritesChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_discovery, menu);
        this.menu = menu;
        return true;
    }


    public void showMenu() {
        if (menu != null && menu.findItem(R.id.action_sort) != null)
            menu.findItem(R.id.action_sort).setVisible(true);
    }


    public void hideMenu() {
        if (menu != null && menu.findItem(R.id.action_sort) != null)
            menu.findItem(R.id.action_sort).setVisible(false);
    }
}
