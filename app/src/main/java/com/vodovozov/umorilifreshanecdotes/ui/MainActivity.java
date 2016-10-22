package com.vodovozov.umorilifreshanecdotes.ui;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.BuildConfig;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.vodovozov.umorilifreshanecdotes.R;
import com.vodovozov.umorilifreshanecdotes.rest.NetworkStatusChecker;
import com.vodovozov.umorilifreshanecdotes.rest.RestService;
import com.vodovozov.umorilifreshanecdotes.rest.models.QueryServerModel;
import com.vodovozov.umorilifreshanecdotes.ui.fragments.AllJokesFragment_;
import com.vodovozov.umorilifreshanecdotes.ui.fragments.FavoritesFragment_;
import com.vodovozov.umorilifreshanecdotes.ui.util.ConstansManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.io.IOException;
import java.util.List;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private final static String LOG_TAG = MainActivity_.class.getSimpleName();
    private FragmentManager fragmentManager;


    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById(R.id.navigation_view)
    NavigationView navigationView;
    @ViewById(R.id.relative_layout)
    RelativeLayout relativeLayout;
    @StringRes(R.string.item_menu_all_jokes)
    String allJokesTitle;
    @StringRes(R.string.item_menu_favorites)
    String favoritesTitle;
    @InstanceState
    String toolbarTitle;
    @Bean
    protected BackgroundTask backTask;


    @AfterViews
    void setupViews() {
        setActionBar();
        setDrawerLayout();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        replaceFragment(new AllJokesFragment_());
    }

    @AfterViews
    void startConnectionServer() {
        if (NetworkStatusChecker.isNetworkAvailable(this)) {
            String site = ConstansManager.SITE;
            String name = ConstansManager.NAME;
            int num = ConstansManager.NUM;
            backTask.connectionServer(site, name, num);
        } else {
            Snackbar.make(relativeLayout,
                    getString(R.string.error_no_internet),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @UiThread
    void showUnknownError() {
        Snackbar.make(relativeLayout,
                getString(R.string.unknown_error),
                Snackbar.LENGTH_LONG).show();
    }


    private void setDrawerLayout() {
        onNavigationItemSelected();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        if (toolbarTitle != null)
            setTitle(toolbarTitle);
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackStackChanged() {
        Fragment f = fragmentManager.findFragmentById(R.id.main_container);
        if (f != null) {
            setToolbarTitle(f.getClass().getName());
        }
    }

    private boolean onNavigationItemSelected() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (drawer != null) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.item_all_jokes:
                        replaceFragment(new AllJokesFragment_());
                        break;
                    case R.id.item_favorites:
                        replaceFragment(new FavoritesFragment_());
                        break;
                }
                return true;
            }
        });
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        String backStackName = fragment.getClass().getName();

        boolean isFragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);

        if (!isFragmentPopped && fragmentManager.findFragmentByTag(backStackName) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment, backStackName);
            transaction.addToBackStack(backStackName);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();

        }
    }

    private void setToolbarTitle(String backStackEntryName) {
        if (backStackEntryName.equals(AllJokesFragment_.class.getName())) {
            setTitle(allJokesTitle);
            toolbarTitle = allJokesTitle;
            navigationView.setCheckedItem(R.id.item_all_jokes);
        } else if (
                backStackEntryName.equals(FavoritesFragment_.class.getName())) {
            setTitle(favoritesTitle);
            toolbarTitle = favoritesTitle;
            navigationView.setCheckedItem(R.id.item_favorites);
        }
    }

}
