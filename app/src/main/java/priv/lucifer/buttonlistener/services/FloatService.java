package priv.lucifer.buttonlistener.services;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import priv.lucifer.buttonlistener.R;
import priv.lucifer.buttonlistener.UserInfo;
import priv.lucifer.buttonlistener.ui.activity.KeyMapActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static priv.lucifer.buttonlistener.GlobeVer.curr_key;

public class FloatService extends AccessibilityService {


    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;

    ImageView mFloatView;

    private boolean longClick = false;


    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams();
        //获取WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.START | Gravity.TOP;

        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 200;


        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        //浮动窗口按钮
        mFloatView = (ImageView) mFloatLayout.findViewById(R.id.float_id);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        //设置监听浮动窗口的触摸移动
        mFloatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (longClick) {

                    //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                    wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth() / 2;
                    //25为状态栏的高度
                    wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight() / 2 - getStatusBarHeight();
                    //刷新
                    mWindowManager.updateViewLayout(mFloatLayout, wmParams);

                    if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                        //   Log.i("222", "onTouch: "+event.getRawX()+" "+getResources().getDisplayMetrics().widthPixels);

                        wmParams.x = event.getRawX() < getResources().getDisplayMetrics().widthPixels / 2 ? 0 : (int) getResources().getDisplayMetrics().widthPixels - mFloatView.getMeasuredWidth() / 2;
                        mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                    }
                }
                return false;
            }
        });
        mFloatView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClick = true;
                return false;
            }
        });
        mFloatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                longClick = false;
            }

        });

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        curr_key = event.getKeyCode();
        System.out.println("KeyCode"+curr_key);
        handleKey(curr_key);
        return super.onKeyEvent(event);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        System.out.println("1111");
        if (Build.VERSION.SDK_INT >= 23) {
            System.out.println("2222");
            if (!Settings.canDrawOverlays(getApplication())) {
                System.out.println("3333");
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return;
            }
            System.out.println("4444");
        }
        System.out.println("5555");
        createFloatView();
        super.onCreate();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Auto-generated method stub

    }

    public void handleKey(int key) {
        PackageInfo app = UserInfo.getAppByKey(key);
        if (null != app) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(app.applicationInfo.packageName);
            startActivity(intent);
        }
    }

}
