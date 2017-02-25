package priv.lucifer.buttonlistener.listener;

import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

/**
 * Created by Lucifer Wong on 2017/2/11.
 */

public class OnTabSelect implements OnTabSelectListener {


    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout;

    public OnTabSelect(ViewPager mViewPager, CommonTabLayout mTabLayout) {
        this.mTabLayout = mTabLayout;
        this.mViewPager = mViewPager;
    }

    //第一次点击菜单图标触发
    @Override
    public void onTabSelect(int position) {

        mViewPager.setCurrentItem(position);
    }

    //第二次以及以后点击菜单图标触发
    @Override
    public void onTabReselect(int position) {
        if (position == 0) {
            mTabLayout.showMsg(0, 0);
//                    UnreadMsgUtils.show(mTabLayout.getMsgView(0), mRandom.nextInt(100) + 1);
        }
    }
}
