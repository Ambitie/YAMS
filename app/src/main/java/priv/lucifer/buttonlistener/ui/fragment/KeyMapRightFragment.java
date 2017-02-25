package priv.lucifer.buttonlistener.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.lucifer.buttonlistener.R;

/**
 * Created by Lucifer Wong on 2017/2/20.
 */

public class KeyMapRightFragment extends Fragment {


    @BindView(R.id.gv_apps)
    GridView gvApps;

    private PackageManager pManager;
    private List<AppsItemInfo> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_keymap_right, container, false);
        ButterKnife.bind(this, v);
        // 取得gridview

        // 获取图片、应用名、包名
        pManager = getActivity().getPackageManager();
        List<PackageInfo> appList = getAllApps(getActivity());

        list = new ArrayList<AppsItemInfo>();

        for (int i = 0; i < appList.size(); i++) {
            PackageInfo pinfo = appList.get(i);
            AppsItemInfo shareItem = new AppsItemInfo();
            // 设置图片
            shareItem.setIcon(pManager
                    .getApplicationIcon(pinfo.applicationInfo));
            // 设置应用程序名字
            shareItem.setLabel(pManager.getApplicationLabel(
                    pinfo.applicationInfo).toString());
            // 设置应用程序的包名
            shareItem.setPackageName(pinfo.applicationInfo.packageName);

            list.add(shareItem);

        }

        // 设置gridview的Adapter
        gvApps.setAdapter(new baseAdapter());

        // 点击应用图标时，做出响应
        gvApps.setOnItemClickListener(new ClickListener());
        //apps.get(1).
        return v;
    }

    public class baseAdapter extends BaseAdapter {

        @BindView(R.id.apps_image)
        ImageView appsImage;
        @BindView(R.id.apps_textview)
        TextView appsTextview;

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = View.inflate(getActivity(), R.layout.fragment_keymap_right_item, null);
            ButterKnife.bind(this,item);
            appsImage.setImageDrawable(list.get(position).getIcon());
            appsTextview.setText(list.get(position).getLabel().toString());
            return item;

        }

    }

    // 当用户点击应用程序图标时，将对这个类做出响应
    private class ClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Intent intent = new Intent();
            intent = getActivity().getPackageManager().getLaunchIntentForPackage(list.get(arg2).getPackageName());
            startActivity(intent);
            // 销毁当前Activity
           // finish();
        }

    }


    public static List<PackageInfo> getAllApps(Context context) {

        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        // 获取手机内所有应用
        List<PackageInfo> packlist = pManager.getInstalledPackages(0);
        for (int i = 0; i < packlist.size(); i++) {
            PackageInfo pak = (PackageInfo) packlist.get(i);

            // 判断是否为非系统预装的应用程序
            // 这里还可以添加系统自带的，这里就先不添加了，如果有需要可以自己添加
            // if()里的值如果<=0则为自己装的程序，否则为系统工程自带
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // 添加自己已经安装的应用程序
                apps.add(pak);
            }

        }
        return apps;
    }

    // 自定义一个 AppsItemInfo 类，用来存储应用程序的相关信息
    private class AppsItemInfo {

        private Drawable icon; // 存放图片
        private String label; // 存放应用程序名
        private String packageName; // 存放应用程序包名

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

    }


}
