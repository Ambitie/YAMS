package priv.lucifer.buttonlistener.listener;

import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;

/**
 * Created by Lucifer Wong on 2017/2/11.
 */

public class PageChange implements ViewPager.OnPageChangeListener {


    private CommonTabLayout mTabLayout;

    public PageChange(CommonTabLayout mTabLayout) {
        this.mTabLayout = mTabLayout;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Log.d("onPageScrolled", position + " "+positionOffset+" "+positionOffsetPixels);
    }

    //滑动mViewPager时，停在那个上面
    @Override
    public void onPageSelected(int position) {
        //  Log.d("onPageSelected", position + "");
        mTabLayout.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //  Log.d("PageScrollStateChanged", state + "");
    }
}
