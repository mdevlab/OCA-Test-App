package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import io.mdevlab.ocatraining.R;


public class ActivityCertificationInfo extends ActivityBase {

    final String TAG = ActivityCertificationInfo.class.getSimpleName();
    final String SPACE = " ";
    final String BEGIN_LIST_ITEM = "<ul><li>";
    final String END_LIST_ITEM = "</li></ul>";
    final String BEGIN_BOLD = "<strong>";
    final String END_BOLD = "</strong>";

    Button subscribeToCertification;

    HtmlTextView benefitOne;
    HtmlTextView benefitTwo;
    HtmlTextView benefitThree;
    HtmlTextView benefitFour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_info);

        setUpToolbar(getString(R.string.title_activity_certification_info));
        setUpDFP(null);
        setUpViews();
        setupSubscriptionToCertification();
        formatBenefitsText();
    }


    private void setUpViews() {
        subscribeToCertification = (Button) findViewById(R.id.subscribe_to_certification);
        benefitOne = (HtmlTextView) findViewById(R.id.benefit_one);
        benefitTwo = (HtmlTextView) findViewById(R.id.benefit_two);
        benefitThree = (HtmlTextView) findViewById(R.id.benefit_three);
        benefitFour = (HtmlTextView) findViewById(R.id.benefit_four);
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


    private void formatBenefitsText() {
        benefitOne.setHtml(BEGIN_LIST_ITEM + BEGIN_BOLD + getString(R.string.info_benefit_one_bold) + END_BOLD + SPACE + getString(R.string.info_benefit_one_normal) + END_LIST_ITEM);
        benefitTwo.setHtml(BEGIN_LIST_ITEM + BEGIN_BOLD + getString(R.string.info_benefit_two_bold) + END_BOLD + SPACE + getString(R.string.info_benefit_two_normal) + END_LIST_ITEM);
        benefitThree.setHtml(BEGIN_LIST_ITEM + BEGIN_BOLD + getString(R.string.info_benefit_three_bold) + END_BOLD + SPACE + getString(R.string.info_benefit_three_normal) + END_LIST_ITEM);
        benefitFour.setHtml(BEGIN_LIST_ITEM + BEGIN_BOLD + getString(R.string.info_benefit_four_bold) + END_BOLD + SPACE + getString(R.string.info_benefit_four_normal) + END_LIST_ITEM);
    }
}
