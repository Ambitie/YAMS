package priv.lucifer.buttonlistener.listener;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.CompoundButton;

import priv.lucifer.buttonlistener.services.FloatService;

import static priv.lucifer.buttonlistener.SettingConfigs.FLOAT;
import static priv.lucifer.buttonlistener.SettingConfigs.KEYMAP;


/**
 * Created by Lucifer Wong on 2017/2/19.
 */

public class SettingSwichChange implements CompoundButton.OnCheckedChangeListener {
    private int index;
    private Activity mRootActivity;

    public SettingSwichChange(int index, Activity mRootActivity) {
        this.index = index;
        this.mRootActivity = mRootActivity;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (index) {
            case KEYMAP:
                if (b) {

                } else {

                }
                break;
            case FLOAT:

                if (b) {
                    Intent intent = new Intent(mRootActivity, FloatService.class);
                    mRootActivity.startService(intent);
                } else {
                    Intent intent = new Intent(mRootActivity, FloatService.class);
                    mRootActivity.stopService(intent);
                }
        }

    }
}
