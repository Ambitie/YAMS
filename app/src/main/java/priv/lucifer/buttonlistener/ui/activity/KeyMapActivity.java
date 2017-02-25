package priv.lucifer.buttonlistener.ui.activity;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import priv.lucifer.buttonlistener.ui.fragment.KeyMapLeftFragment;
import priv.lucifer.buttonlistener.ui.fragment.KeyMapMidFragment;
import priv.lucifer.buttonlistener.ui.fragment.KeyMapRightFragment;

public class KeyMapActivity extends AppCompatActivity {


    private RelativeLayout mRoot;

    private FrameLayout mLeft;
    private FrameLayout mMid;
    private FrameLayout mRight;
    private Scroller mScroller;

    public static final int MID_ID = View.generateViewId();
    public static final int LEFT_ID = MID_ID + 1;
    public static final int RIGHT_ID = LEFT_ID + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLeft = new FrameLayout(this);
        mMid = new FrameLayout(this);
        mRight = new FrameLayout(this);
        mScroller = new Scroller(this, new DecelerateInterpolator());
        mRoot = new RelativeLayout(this) {
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                mMid.measure(widthMeasureSpec, heightMeasureSpec);
                int realWidth = MeasureSpec.getSize(widthMeasureSpec);
                int tempWidthMeasure = MeasureSpec.makeMeasureSpec((int) (realWidth * 0.8), MeasureSpec.EXACTLY);
                mLeft.measure(tempWidthMeasure, heightMeasureSpec);
                mRight.measure(tempWidthMeasure, heightMeasureSpec);
            }

            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                super.onLayout(changed, l, t, r, b);
                mMid.layout(l, t, r, b);
                mLeft.layout(l - mLeft.getMeasuredWidth(), t, r, b);
                mRight.layout(l + mMid.getMeasuredWidth(), t,
                        l + mMid.getMeasuredWidth() + mRight.getMeasuredWidth(), b);
            }

            private boolean isTestCompete;
            private boolean isOnLeftEvent;
            private Point point = new Point();
            private static final int TEST_DIS = 20;

            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                if (!isTestCompete) {
                    getEventType(ev);
                    return true;
                }
                if (isOnLeftEvent) {
                    switch (ev.getActionMasked()) {
                        case MotionEvent.ACTION_MOVE:
                            int curScrollX = getScrollX();
                            int dis_x = (int) (ev.getX() - point.x);
                            int expectX = -dis_x + curScrollX;
                            int finalX = 0;
                            if (expectX < 0) {
                                finalX = Math.max(expectX, -mLeft.getMeasuredWidth());
                            } else {
                                finalX = Math.min(expectX, mRight.getMeasuredWidth());
                            }
                            Log.i("finalX " + finalX, "expectX: " + expectX + " mRight " + mRight.getMeasuredWidth() + " -mLeft " + -mLeft.getMeasuredWidth());
                            scrollTo(finalX, 0);
                            point.x = (int) ev.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            curScrollX = getScrollX();
                            if (Math.abs(curScrollX) > mLeft.getMeasuredWidth() >> 1) {
                                if (curScrollX < 0) {
                                    mScroller.startScroll(curScrollX, 0,
                                            -mLeft.getMeasuredWidth() - curScrollX, 0);
                                } else {
                                    mScroller.startScroll(curScrollX, 0,
                                            mLeft.getMeasuredWidth() - curScrollX, 0);
                                }
                            } else {
                                mScroller.startScroll(curScrollX, 0,
                                        -curScrollX, 0);
                            }
                            isOnLeftEvent = false;
                            isTestCompete = false;
                            break;
                    }
                    invalidate();
                } else {
                    switch (ev.getActionMasked()) {
                        case MotionEvent.ACTION_UP:
                            isOnLeftEvent = false;
                            isTestCompete = false;
                            break;
                    }
                }
                return super.dispatchTouchEvent(ev);
            }

            @Override
            public void computeScroll() {
                super.computeScroll();
                if (!mScroller.computeScrollOffset()) return;
                int tempX = mScroller.getCurrX();
                scrollTo(tempX, 0);
            }

            private void getEventType(MotionEvent ev) {
                switch (ev.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        point.x = (int) ev.getX();
                        point.y = (int) ev.getY();
                        super.dispatchTouchEvent(ev);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dX = Math.abs((int) ev.getX() - point.x);
                        int dY = Math.abs((int) ev.getY() - point.y);
                        if (dX >= TEST_DIS && dX > dY) {
                            isOnLeftEvent = true;
                            isTestCompete = true;
                            point.x = (int) ev.getX();
                            point.y = (int) ev.getY();
                        } else if (dY >= TEST_DIS && dX < dY) {
                            isOnLeftEvent = false;
                            isTestCompete = true;
                            point.x = (int) ev.getX();
                            point.y = (int) ev.getY();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        super.dispatchTouchEvent(ev);
                        isOnLeftEvent = false;
                        isTestCompete = false;
                        break;
                }
            }
        };


        //mLeft.setBackgroundColor(Color.RED);
        mMid.setBackgroundColor(Color.GREEN);
        mRight.setBackgroundColor(Color.RED);

        mMid.setId(MID_ID);
        mLeft.setId(LEFT_ID);
        mRight.setId(RIGHT_ID);

        mRoot.addView(mLeft);
        mRoot.addView(mMid);
        mRoot.addView(mRight);


        setContentView(mRoot);
        getSupportFragmentManager().beginTransaction().add(LEFT_ID,new KeyMapLeftFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(RIGHT_ID,new KeyMapRightFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(MID_ID,new KeyMapMidFragment()).commit();
        //setContentView(R.layout.activity_key_map);
      //getSupportFragmentManager().beginTransaction().add(LEFT_ID,new KeyMapLeftFragment());
    }

}
