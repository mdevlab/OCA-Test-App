package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import io.mdevlab.ocatraining.R;


public class ActivityCertificationInfo extends AppCompatActivity {

    WebView certificationInfo;
    ProgressBar loadingProgressBar;
    Button subscribeToCertification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_info);

        setUpToolbar();
        setUpCertificationInfoView();
        setupSubscriptionToCertification();
    }


    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_certification_info));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setUpCertificationInfoView() {
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);

        certificationInfo = (WebView) findViewById(R.id.certification_info);
        certificationInfo.loadUrl(getString(R.string.certification_info_url));
        certificationInfo.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }


    private void setupSubscriptionToCertification() {
        subscribeToCertification = (Button) findViewById(R.id.subscribe_to_certification);
        subscribeToCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToSubscriptionPage();
            }
        });
    }


    private void redirectToSubscriptionPage() {
        Uri subscriptionUri = Uri.parse(getString(R.string.certification_subscription_url));
        Intent subscriptionIntent = new Intent(Intent.ACTION_VIEW, subscriptionUri);
        startActivity(subscriptionIntent);
    }
}
