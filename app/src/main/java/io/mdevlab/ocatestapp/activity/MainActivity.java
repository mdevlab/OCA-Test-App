package io.mdevlab.ocatestapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.activitie.ResponseActivity;
import io.mdevlab.ocatestapp.adapter.ChapterAdapter;
import io.mdevlab.ocatestapp.model.Chapter;
import io.mdevlab.ocatestapp.test.ChapterTest;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mchapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mchapterRecyclerView = (RecyclerView) findViewById(R.id.chapter_recycler_view);

        List<Chapter> chapterList = new ArrayList<>();
        chapterList.add(ChapterTest.createChapter(0));
        chapterList.add(ChapterTest.createChapter(1));
        chapterList.add(ChapterTest.createChapter(2));
        chapterList.add(ChapterTest.createChapter(3));
        chapterList.add(ChapterTest.createChapter(4));
        chapterList.add(ChapterTest.createChapter(5));

        ChapterAdapter chapterAdapter = new ChapterAdapter(this,chapterList);
        mchapterRecyclerView.setAdapter(chapterAdapter);
        mchapterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mchapterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Todo Delete This Call
            Intent intent = new Intent(this, ResponseActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favorite) {
            // Handle the favorite click
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}