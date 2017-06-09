package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import io.mdevlab.ocatraining.R;

public class AboutActivity extends ActivityBase implements View.OnClickListener {

    ImageView playStore;
    ImageView github;
    ImageView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setUpToolbar(getString(R.string.title_activity_about));
        setUpDFP();
        setUpViews();
    }


    private void setUpViews() {
        playStore = (ImageView) findViewById(R.id.playstore_account);
        playStore.setOnClickListener(this);

        github = (ImageView) findViewById(R.id.github_account);
        github.setOnClickListener(this);

        email = (ImageView) findViewById(R.id.email_account);
        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.playstore_account:
                openUrl(getString(R.string.play_store_url));
                break;
            case R.id.github_account:
                openUrl(getString(R.string.github_url));
                break;
            case R.id.email_account:
                startActivity(new Intent(AboutActivity.this, ContactActivity.class));
                break;
        }
    }

    /**
     * Method that opens a given url in a web browser of the user's device
     *
     * @param url Url of the web page we want to open
     */
    private void openUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
