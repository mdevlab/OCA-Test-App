package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import io.mdevlab.ocatraining.BuildConfig;
import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.ChapterAdapter;
import io.mdevlab.ocatraining.analytics.AnalyticsManager;
import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.modelManager.ChapterManager;
import io.mdevlab.ocatraining.util.UtilActions;

import static io.mdevlab.ocatraining.R.id.nav_upgrade;


public class MainActivity extends ActivityBase implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawer;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar(getString(R.string.app_name));
        setUpDFP(null);
        initChapterTestList();
        setUpDrawer();
        setUpNavigationDrawer();
    }


    /**
     * Init the chapter test List
     * containing all chapter as a header and other chapters as sub elements
     */
    private void initChapterTestList() {

        RecyclerView mChapterRecyclerView = (RecyclerView) findViewById(R.id.chapter_recycler_view);

        //chapter List
        List<Chapter> chapterList = ChapterManager.getAllChapters();

        //Adapter
        final ChapterAdapter chapterAdapter = new ChapterAdapter(this, chapterList);
        mChapterRecyclerView.setAdapter(chapterAdapter);
        mChapterRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //LayoutManager
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        //Set number of spans 2 for header and 1 for other elements > 0
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return chapterAdapter.isHeader(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        mChapterRecyclerView.setLayoutManager(gridLayoutManager);

    }


    private void setUpDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        trackOpenDrawer();
    }


    private void setUpNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (BuildConfig.IS_FREE_FLAVOR) {
            navigationView.getMenu().findItem(R.id.nav_upgrade).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_test_dashboard).setVisible(true);

        }

    }


    @Override
    public void onBackPressed() {
        if (mDrawer == null)
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;

            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 3000);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            trackClickedOnSettings();
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void trackClickedOnSettings() {
        Bundle bundle = new Bundle();
        bundle.putString(getResources().getString(R.string.property_name_source), getResources().getString(R.string.attribute_value_home));
        trackEvents(getResources().getString(R.string.event_view_settings), bundle);
    }

    /**
     * Track events
     */
    private void trackEvents(String eventName, Bundle properties) {
        AnalyticsManager.getInstance().logEvent(eventName, properties);

    }

    public void trackOpenDrawer() {
        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                trackEvents(getResources().getString(R.string.event_view_drawer), null);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    /**
     * Method that handles on click events on the drawer items
     *
     * @param item: Clicked on item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent;
        Bundle bundle;
        switch (itemId) {
            case nav_upgrade:
                //Firebase Analytics Tracking
                bundle = new Bundle();
                bundle.putString(getString(R.string.property_name_source), getString(R.string.attribute_value_home));
                trackEvents(getResources().getString(R.string.event_view_upgrade), bundle);

                UtilActions.displayUpgradeDialog(MainActivity.this);
                break;
            case R.id.nav_favorite_questions:
                //Firebase Analytics Tracking
                trackEvents(getResources().getString(R.string.event_view_favorite_questions), null);

                intent = new Intent(MainActivity.this, FavoriteQuestionsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_test_dashboard:
                //Firebase Analytics Tracking
                AnalyticsManager.getInstance().logEvent(getString(R.string.event_view_dashboard), null);

                intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_certification_info:
                //Firebase Analytics Tracking
                trackEvents(getResources().getString(R.string.event_view_certification_info), null);

                intent = new Intent(MainActivity.this, ActivityCertificationInfo.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                //Firebase Analytics Tracking
                bundle = new Bundle();
                bundle.putString(getResources().getString(R.string.property_name_source), getResources().getString(R.string.attribute_value_drawer));
                trackEvents(getResources().getString(R.string.event_view_settings), bundle);

                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                //Firebase Analytics Tracking
                trackEvents(getResources().getString(R.string.event_click_share), null);

                UtilActions.share(MainActivity.this);
                break;
            case R.id.nav_about:
                //Firebase Analytics Tracking
                trackEvents(getResources().getString(R.string.event_view_about), null);

                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }

        if (mDrawer == null)
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
