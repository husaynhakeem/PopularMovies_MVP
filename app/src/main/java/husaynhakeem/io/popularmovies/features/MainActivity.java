package husaynhakeem.io.popularmovies.features;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.details.DetailsPresenter;
import husaynhakeem.io.popularmovies.features.details.DetailsView;
import husaynhakeem.io.popularmovies.features.main.DiscoveryPresenter;
import husaynhakeem.io.popularmovies.features.main.DiscoveryView;

public class MainActivity extends AppCompatActivity {


    private DiscoveryView discoveryView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDiscoveryView();
//        if (UiUtils.isTablet())
//            startDetailsView();
    }


    private void startDiscoveryView() {

        discoveryView = new DiscoveryView();
        DiscoveryPresenter discoveryPresenter = new DiscoveryPresenter();
        discoveryView.setPresenter(discoveryPresenter);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_frame, discoveryView)
                .commit();
    }


    private void startDetailsView() {

        DetailsView detailsView = new DetailsView();
        DetailsPresenter detailsPresenter = new DetailsPresenter();
        detailsView.setPresenter(detailsPresenter);
        detailsPresenter.setView(detailsView);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_frame, detailsView)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_discovery, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void onFavoritesChanged() {
        discoveryView.onFavoritesChanged();
    }
}
