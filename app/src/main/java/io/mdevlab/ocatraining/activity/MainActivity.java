package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.ChapterAdapter;
import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.modelManager.ChapterManager;
import io.mdevlab.ocatraining.test.Test;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private final String SHARE_TYPE = "text/plain";

    private RecyclerView mChapterRecyclerView;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dummy data
        Test.populateDataBase(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initChapterTestList();

        //Drawer
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * Init the chapter test List
     * containing all chapter as a header and other chapters as sub elements
     */
    private void initChapterTestList() {

        mChapterRecyclerView = (RecyclerView) findViewById(R.id.chapter_recycler_view);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {

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
                shareApp();
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

    /**
     * Method used for sharing the app.
     * It shares via a messaging/email/social application the link to this app
     * on the play store
     */
    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(SHARE_TYPE);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + getString(R.string.application_play_store_url));
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }
}
