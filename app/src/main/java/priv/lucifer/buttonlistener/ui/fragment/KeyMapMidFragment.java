package priv.lucifer.buttonlistener.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import priv.lucifer.buttonlistener.GlobeVer;
import priv.lucifer.buttonlistener.R;
import priv.lucifer.buttonlistener.UserInfo;
import priv.lucifer.buttonlistener.ui.dialog.AlertDialog;

import static priv.lucifer.buttonlistener.ui.fragment.KeyMapRightFragment.getAllApps;

/**
 * Created by Lucifer Wong on 2017/2/23.
 */

public class KeyMapMidFragment extends Fragment {

    @BindView(R.id.mid_btn_show)
    Button midBtnShow;
    @BindView(R.id.mid_spn_list)
    Spinner midSpnList;
    @BindView(R.id.mid_btn_add)
    Button midBtnAdd;
    @BindView(R.id.mid_ll)
    LinearLayout midLl;
    @BindView(R.id.mid_lv)
    ListView midLv;

    KeyMapMidLvAdapter lvAdapter;
    List<PackageInfo> appList = null;

    PackageManager pManager = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_keymap_mid, null);
        ButterKnife.bind(this, view);
        appList = getAllApps(getActivity());
        pManager = getActivity().getPackageManager();
        List<String> pack_list = new ArrayList<>();
        for (PackageInfo pack : appList) {
            pack_list.add(pManager.getApplicationLabel(
                    pack.applicationInfo).toString());
        }

        midSpnList.setAdapter(new KeyMapMidSpnAdapter());
        lvAdapter = new KeyMapMidLvAdapter();
        midLv.setAdapter(lvAdapter);
        return view;
    }

    @OnClick({R.id.mid_btn_show, R.id.mid_btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mid_btn_show:
                try {
                    midBtnShow.setText(GlobeVer.KeyVal.reflect(GlobeVer.curr_key));
                } catch (Exception e) {
                    midBtnShow.setText("");
                }

                break;
            case R.id.mid_btn_add:
                PackageInfo selected = (PackageInfo) midSpnList.getSelectedItem();
                UserInfo.Ignore ignore = new UserInfo.Ignore() {
                    @Override
                    public boolean ignoreKey() {

                        final boolean[] res = {true};
                        if (res[0])
                            return res[0];
                        new AlertDialog(getActivity()).builder().setTitle("冲突的按键")
                                .setMsg("发现有冲突的按键，是否替换以前的按键")
                                .setPositiveButton("确认替换", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        res[0] = true;
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                res[0] = false;
                            }
                        }).show();
                        // while (dialog.isShowIng());
                        return res[0];
                    }

                    @Override
                    public boolean ignoreValue() {
                        final boolean[] res = {true};
                        if (res[0])
                            return res[0];
                        new AlertDialog(getActivity()).builder().setTitle("冲突的应用")
                                .setMsg("发现有冲突的应用，是否替换以前的应用")
                                .setPositiveButton("确认替换", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        res[0] = true;
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                res[0] = false;
                            }
                        }).show();

                        return res[0];
                    }
                };
                try {
                    UserInfo.addMapping(GlobeVer.KeyVal.reflect(midBtnShow.getText().toString()), selected, ignore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(UserInfo.getAppMappingSize());
                lvAdapter.notifyDataSetInvalidated();
                break;
        }
    }


    public class KeyMapMidLvAdapter extends BaseAdapter {

        @BindView(R.id.mid_lv_btn_show)
        Button midLvBtnShow;
        @BindView(R.id.mid_lv_tv)
        TextView midLvTv;
        @BindView(R.id.mid_lv_btn_remove)
        Button midLvBtnRemove;
        @BindView(R.id.mid_lv_iv)
        ImageView midLvIv;

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return UserInfo.getAppMappingSize();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return UserInfo.getAppByIndex(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(), R.layout.fragment_keymap_mid_lv_item, null);
            ButterKnife.bind(this, view);

            try {
                midLvBtnShow.setText(GlobeVer.KeyVal.reflect(UserInfo.getKeyFromIndex(position)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            midLvTv.setText(pManager.getApplicationLabel(
                    UserInfo.getAppByIndex(position).applicationInfo).toString());
            midLvIv.setImageDrawable(pManager
                    .getApplicationIcon(UserInfo.getAppByIndex(position).applicationInfo));
            midLvBtnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserInfo.removeMapping(UserInfo.getKeyFromIndex(position));
                    notifyDataSetInvalidated();
                }
            });
            return view;
        }



    }

    public class KeyMapMidSpnAdapter extends BaseAdapter {

        @BindView(R.id.spn_it_iv)
        ImageView spnItIv;
        @BindView(R.id.spn_it_tv)
        TextView spnItTv;

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            if (appList != null)
                return appList.size();
            return 0;
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return appList.get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(getActivity(), R.layout.fragment_keymap_mid_spn_item, null);
            ButterKnife.bind(this, view);
            // 设置图片
            spnItIv.setImageDrawable(pManager
                    .getApplicationIcon(appList.get(position).applicationInfo));
            spnItTv.setText(pManager.getApplicationLabel(
                    appList.get(position).applicationInfo).toString());
            return view;
        }
    }
}
