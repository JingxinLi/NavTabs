package creation.philip.navtabs;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by philip on 14-7-18.
 */
public class TabBar {
    private Context mContext;
    private ArrayList<NavTab> mTabs = new ArrayList<TabBar.NavTab>();
    private NavTab mSelectedTab;

    public TabBar(Context context) {
        mContext = context;
    }

    public NavTab createNavTab() {
        return new NavTab(mContext);
    }

    public void addTab(TabBar.NavTab tab) {
        if (tab != null) {
            mTabs.add(tab);

            // should add first,or the getSelectedTabPostion will be wrong
            if (mTabs.size() == 1) {
                selectTab(tab);
                tab.removeDivider();
            }
        }

    }

    public void selectTab(NavTab tab) {
        final FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager()
                                                                   .beginTransaction()
                                                                   .disallowAddToBackStack();
        // no reselect
        if (mSelectedTab != tab) {
            if (mSelectedTab != null) {
                mSelectedTab.setSelected(false);
            }
            mSelectedTab = tab;
            mSelectedTab.getCallback().onTabSelected(mSelectedTab, ft);
            mSelectedTab.setSelected(true);
        }

        if (!ft.isEmpty()) {
            ft.commit();
        }
    }


    public int getSelectedTabPosition() {
        return mTabs.indexOf(mSelectedTab);
    }

    public class NavTab {
        private String mText;
        private NavTabView mView;
        private NavTabListener mCallback;
        private boolean mSelected;


        public NavTab(Context context) {
            mView = new NavTabView(context);
        }

        public void setSelected(boolean selected) {
            mSelected = selected;
            mView.toggleIndicator();
        }

        public void setText(String text) {
            mText = text;
            mView.setText(text);
        }

        public String getText() {
            return mText;
        }

        public void setBackGroudColor(int color) {
            mView.setBackgroundColor(color);
        }

        public NavTabView getView() {
            return mView;
        }


        private void removeDivider() {
            mView.removeDivider();
        }

        public void setTabListener(NavTabListener callback) {
            mCallback = callback;
        }

        public NavTabListener getCallback() {
            return mCallback;
        }

        private class NavTabView  extends RelativeLayout {
            private TextView mTextView;
            private ImageView mIndicator;
            private ImageView mDivier;
            private View mContainer;

            public NavTabView(Context context) {
                super(context);
                init(context);
            }

            private void init(Context context) {

                //mContainer = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);

                //addView(mContainer);
                LayoutInflater.from(context).inflate(R.layout.tab_layout, this);
                mTextView = (TextView) findViewById(R.id.navtab_text);
                mIndicator = (ImageView) findViewById(R.id.navtab_indicator);
                mDivier = (ImageView) findViewById(R.id.navtab_divider);

                toggleIndicator();
            }

            private void setText(String text) {
                mTextView.setText(text);
            }

            public void setBackgroudColor(int color) {
                if (color > 0 ) {
                    setBackgroundColor(color);
                }
            }

            public void toggleIndicator() {
                if (!mSelected) {
                    Log.i("TAG", "up");
                    mIndicator.setVisibility(GONE);
                } else {
                    Log.i("TAG", "down");
                    mIndicator.setVisibility(VISIBLE);
                }
            }
            public void removeDivider() {
                mDivier.setVisibility(GONE);
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    selectTab(NavTab.this);
                    return true;
                }
                return false;
            }

            @Override
            public void onMeasure(int widthMeaSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeaSpec, heightMeasureSpec);
                //Log.i("TAG", "lp.height " + getMeasuredHeight());
                ViewGroup.LayoutParams lp = mIndicator.getLayoutParams();
                lp.height = getMeasuredHeight()/ 6;
            }
        }
    }

    public interface NavTabListener {
        public void onTabSelected(NavTab tab, FragmentTransaction ft);
    }

}


