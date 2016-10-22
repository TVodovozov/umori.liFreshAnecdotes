package com.vodovozov.umorilifreshanecdotes.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.vodovozov.umorilifreshanecdotes.R;
import com.vodovozov.umorilifreshanecdotes.dataBase.entityes.AllJokesEntity;
import com.vodovozov.umorilifreshanecdotes.ui.adapters.AdapterFavorites;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment(R.layout.favorites_fragment)
@OptionsMenu(R.menu.menu_search)
public class FavoritesFragment extends Fragment {

    final String SEARCH_QUERY_ID = "search_query_id";
    SearchView searchView;


    @ViewById(R.id.favorites_fragment_root_layout)
    CoordinatorLayout rootLayout;
    @ViewById(R.id.list_of_all_favorites)
    RecyclerView recyclerView;
    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @AfterViews
    void LinearLayoutManager() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BackgroundExecutor.cancelAll(SEARCH_QUERY_ID, true);
                //queryExpenses(newText);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorite();
    }

    public void loadFavorite() {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<AllJokesEntity>>() {
            @Override
            public Loader<List<AllJokesEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<AllJokesEntity>> loader = new AsyncTaskLoader<List<AllJokesEntity>>(getActivity()) {
                    @Override
                    public List<AllJokesEntity> loadInBackground() {
                        return AllJokesEntity.selectFavorite();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<AllJokesEntity>> loader, List<AllJokesEntity> data) {
                recyclerView.setAdapter(new AdapterFavorites(data));

            }

            @Override
            public void onLoaderReset(Loader<List<AllJokesEntity>> loader) {

            }
        });

    }

}
