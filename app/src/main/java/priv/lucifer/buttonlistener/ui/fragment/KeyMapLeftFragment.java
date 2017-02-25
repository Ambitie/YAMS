package priv.lucifer.buttonlistener.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import priv.lucifer.buttonlistener.GlobeVer;
import priv.lucifer.buttonlistener.R;
import priv.lucifer.buttonlistener.ui.dialog.AlertDialog;

/**
 * Created by Lucifer Wong on 2017/2/20.
 */

public class KeyMapLeftFragment extends Fragment {

    @BindView(R.id.iv_rot1)
    ImageView ivRot1;
    @BindView(R.id.iv_rot2)
    ImageView ivRot2;
    @BindView(R.id.lv_rots)
    ListView lvRots;
    @BindView(R.id.tv_show)
    TextView tvShow;


    private RotateAnimation an;
    private RotateAnimation an2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_keymap_left, container, false);
        ButterKnife.bind(this, v);
        an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        an.setInterpolator(new LinearInterpolator());//不停顿
        an.setRepeatCount(1);//重复次数
        an.setFillAfter(true);//停在最后
        an.setDuration(1500);
        ivRot1.setAnimation(an);
         an2 = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        an2.setInterpolator(new LinearInterpolator());//不停顿
        an2.setRepeatCount(1);//重复次数
        an2.setFillAfter(true);//停在最后
        an2.setDuration(1500);
        ivRot2.setAnimation(an2);
        return v;
    }


    @OnClick(R.id.tv_show)
    public void onClick(View view) {
        //tvShow.setText(new String(GlobeVer.curr_key+""));
        switch (view.getId()) {
            case R.id.tv_show:
                AlertDialog dialog = new AlertDialog(getActivity()).builder()
                        .setMsg("请按键后点击“确定”按钮")
                        .setNegativeButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    tvShow.setText(GlobeVer.KeyVal.reflect(GlobeVer.curr_key));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    tvShow.setText("Unknow Key"+GlobeVer.curr_key);
                                }

                            }
                        });
                dialog.show();

                break;
            case R.id.lv_rots:
                break;
        }

    }



}
