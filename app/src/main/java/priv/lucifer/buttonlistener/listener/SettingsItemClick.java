package priv.lucifer.buttonlistener.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;


import priv.lucifer.buttonlistener.R;
import priv.lucifer.buttonlistener.ui.activity.KeyMapActivity;


import static priv.lucifer.buttonlistener.SettingConfigs.KEYMAP;

/**
 * Created by Lucifer Wong on 2017/2/19.
 */

public class SettingsItemClick implements AdapterView.OnItemClickListener {

    private Activity mRoot;

    public SettingsItemClick(Activity mRoot) {
        this.mRoot = mRoot;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case KEYMAP:

                Intent intent = new Intent();
                intent.setClass(mRoot, KeyMapActivity.class);
                mRoot.startActivity(intent);
                mRoot.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
        }
    }
}

