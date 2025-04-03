package edu.uga.cs.countryquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private static final int TOTAL_QUESTIONS = 6;

    public QuizPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return QuestionFragment.newInstance(position); // Use zero-based index
    }

    @Override
    public int getItemCount() {
        return TOTAL_QUESTIONS;
    }
}
