package husaynhakeem.io.popularmovies.features;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.moviesdiscovery.MoviesDiscoveryPresenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_content, new MoviesDiscoveryPresenter())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_discovery, menu);
        return true;
    }
}
