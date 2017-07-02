package husaynhakeem.io.popularmovies.features.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import husaynhakeem.io.popularmovies.R;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class FavoritesView extends Fragment implements FavoritesContract.View {

    private View rootView;
    private FavoritesPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        return rootView;
    }


    @Override
    public void setPresenter(FavoritesContract.Presenter presenter) {
        this.presenter = (FavoritesPresenter) presenter;
    }
}
