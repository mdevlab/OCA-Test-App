package io.mdevlab.ocatraining.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.model.TestQuestion;


/**
 * Created by bachiri on 5/4/17.
 */

/**
 * Test Questions adapter
 */
public class TestQuestionAdapter extends FragmentStatePagerAdapter {

    List<TestQuestion> mTestQuestionList;

    public TestQuestionAdapter(FragmentManager fm, List<TestQuestion> testQuestionList) {
        super(fm);
        this.mTestQuestionList = testQuestionList;
    }

    @Override
    public int getCount() {
        return mTestQuestionList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionFragment.newInstance(mTestQuestionList.get(position), false, false);
    }
}




