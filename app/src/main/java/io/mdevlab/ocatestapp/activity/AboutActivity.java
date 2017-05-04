package io.mdevlab.ocatestapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.mdevlab.ocatestapp.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private final String PLAY_STORE_URL = "";
    private final String GITHUB_URL = "https://github.com/mdevlab";

    TextView playStore;
    TextView github;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        playStore = (TextView) findViewById(R.id.playstore_account);
        github = (TextView) findViewById(R.id.github_account);
        email = (TextView) findViewById(R.id.email_account);

        // Setting listeners
        playStore.setOnClickListener(this);
        github.setOnClickListener(this);
        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.playstore_account:
                openUrl(PLAY_STORE_URL);
                break;
            case R.id.github_account:
                openUrl(GITHUB_URL);
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
