package priv.lucifer.buttonlistener.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Lucifer Wong on 2017/2/11.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private FragmentManager supportFragmentManager;
    private ArrayList<Fragment> mFragments;
    private String[] mTitles;

    public PagerAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> mFragments, String[] mTitles) {
        super(supportFragmentManager);
        this.supportFragmentManager = supportFragmentManager;
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
