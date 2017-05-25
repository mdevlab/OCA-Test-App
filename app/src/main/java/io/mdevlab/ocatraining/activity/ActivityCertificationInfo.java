package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.util.UtilConnection;


public class ActivityCertificationInfo extends ActivityBase {

    final String TAG = ActivityCertificationInfo.class.getSimpleName();

    View noInternetLayout;
    View internetAvailableLayout;
    WebView certificationInfo;
    ProgressBar loadingProgressBar;
    Button subscribeToCertification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_info);

        setUpViews();
        setUpToolbar(getString(R.string.title_activity_certification_info));
        setUpCertificationInfoScreenContent();
        setupSubscriptionToCertification();
    }


    private void setUpViews() {
        noInternetLayout = findViewById(R.id.layout_no_internet);
        internetAvailableLayout = findViewById(R.id.layout_internet_available);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);
        certificationInfo = (WebView) findViewById(R.id.certification_info);
        subscribeToCertification = (Button) findViewById(R.id.subscribe_to_certification);
    }


    private void setUpCertificationInfoScreenContent() {
        if (UtilConnection.with(this).internetIsAvailable()) {
            setUpCertificationInfoView();
        } else {
            displayNoInternetScreen();
        }
    }


    private void setUpCertificationInfoView() {
        certificationInfo.loadUrl(getString(R.string.certification_info_url));
        certificationInfo.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }


    private void displayNoInternetScreen() {
        noInternetLayout.setVisibility(View.VISIBLE);
        internetAvailableLayout.setVisibility(View.GONE);
    }


    private void setupSubscriptionToCertification() {
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


    public void retryConnecting(View view) {
        restartActivity();
    }
}
