package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.ChapterAdapter;
import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.modelManager.ChapterManager;
import io.mdevlab.ocatraining.util.UtilActions;


public class MainActivity extends ActivityBase implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar(getString(R.string.title_activity_main));
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
    }


    private void setUpNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (mDrawer == null)
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
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
        if (item.getItemId() == R.id.action_settings) {
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        switch (itemId) {
            case R.id.nav_upgrade:
                UtilActions.displayUpgradeDialog(MainActivity.this);
                break;
            case R.id.nav_favorite_questions:
                intent = new Intent(MainActivity.this, FavoriteQuestionsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_certification_info:
                intent = new Intent(MainActivity.this, ActivityCertificationInfo.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                UtilActions.share(MainActivity.this);
                break;
            case R.id.nav_about:
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
