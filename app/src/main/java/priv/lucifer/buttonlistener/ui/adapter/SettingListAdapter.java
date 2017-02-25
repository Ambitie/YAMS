package priv.lucifer.buttonlistener.ui.adapter;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import priv.lucifer.buttonlistener.R;
import priv.lucifer.buttonlistener.listener.SettingSwichChange;
import priv.lucifer.buttonlistener.ui.activity.MainActivity;
import priv.lucifer.buttonlistener.ui.fragment.SettingFragment;

import static priv.lucifer.buttonlistener.SettingConfigs.settingsStr;
import static priv.lucifer.buttonlistener.SettingConfigs.settings_icon;

public class SettingListAdapter extends BaseAdapter {


    private Activity mRootActivity;


    public SettingListAdapter(Activity mRootActivity) {
        this.mRootActivity = mRootActivity;
    }

    @Override
    public int getCount() {
        return settingsStr.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        SettingItem item = new SettingItem(settingsStr[i], settings_icon[i]);
        item.getSwich().setOnCheckedChangeListener(new SettingSwichChange(i,mRootActivity));
        return item.getView();

    }

    public class SettingItem {

        private ImageView icon;
        private Switch swch;
        private TextView text;
        private View item;

        public Switch getSwich() {
            return swch;
        }

        public View getView() {
            return item;
        }

        public SettingItem(String str, int icon_id) {
            item = View.inflate(mRootActivity, R.layout.fragment_setting_item, null);
            icon = (ImageView) item.findViewById(R.id.setting_icon);
            swch = (Switch) item.findViewById(R.id.setting_swch);
            text = (TextView) item.findViewById(R.id.setting_text);

            icon.setImageResource(icon_id);
            text.setText(str);
        }
    }

}
