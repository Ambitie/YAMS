package priv.lucifer.buttonlistener.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.lucifer.buttonlistener.R;
import priv.lucifer.buttonlistener.listener.SettingsItemClick;
import priv.lucifer.buttonlistener.ui.adapter.SettingListAdapter;


public class SettingFragment extends Fragment {


    @BindView(R.id.setting_listview)
    ListView mListView;



    public SettingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_list, container, false);
        ButterKnife.bind(this, view);

        // 加载适配器
        //mListView.setAdapter(simp_ada);
        mListView.setAdapter(new SettingListAdapter(getActivity()));
        mListView.setOnItemClickListener(new SettingsItemClick(getActivity()));
        return view;
    }


}
