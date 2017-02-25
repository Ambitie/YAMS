package priv.lucifer.buttonlistener.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.lucifer.buttonlistener.R;
import priv.lucifer.buttonlistener.ui.adapter.PagerAdapter;
import priv.lucifer.buttonlistener.listener.OnTabSelect;
import priv.lucifer.buttonlistener.listener.PageChange;
import priv.lucifer.buttonlistener.ui.fragment.SettingFragment;
import priv.lucifer.buttonlistener.ui.fragment.SimpleCardFragment;
import priv.lucifer.buttonlistener.TabEntity;
import priv.lucifer.buttonlistener.ui.fragment.WebFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vpgr)
    ViewPager mViewPager;
    @BindView(R.id.cmlot)
    CommonTabLayout mTabLayout;
    private String[] mTitles = {"首页", "消息", "联系人", "设置"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFragments.add(new WebFragment());
        mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + mTitles[1]));
        mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + mTitles[2]));
        mFragments.add(new SettingFragment());


        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        //设置菜单图标
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelect(mViewPager, mTabLayout));
        mViewPager.addOnPageChangeListener(new PageChange(mTabLayout));

        // mViewPager.setCurrentItem(1);
        //两位数
        //mTabLayout.showMsg(0, 55);
        // mTabLayout.setMsgMargin(0, -5, 5);
        ;
    }

}
