package creation.philip.navtabs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by philip on 14-7-18.
 */
public class TabBarView extends LinearLayout {
    private static String ALIGN_TOP = "top";
    private static String ALIGN_BOTTOM = "bottom";
    private String mAlignType;
    private TabBar mTabBar;


    public TabBarView(Context context) {
        super(context);
        init(context);
    }

    public TabBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TabBar.NavTab createNavTab() {
        return mTabBar.createNavTab();
    }

    private void init(Context context) {
        mTabBar = new TabBar(context);
        setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
    }

    public void addTab(TabBar.NavTab tab) {
        if (tab != null) {
            mTabBar.addTab(tab);
            addView(tab.getView());
            invalidate();
        }
    }

    public int getSelectedTabPosition() {
        return mTabBar.getSelectedTabPosition();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i ++) {
            getChildAt(i).measure(
                    MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec)/childCount, MeasureSpec.EXACTLY),
                    heightMeasureSpec);
        }

        // tabBar view should wrap tab view
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getChildAt(0).getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();

        int perLeft = l;
        for(int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            child.layout(perLeft, 0, perLeft + child.getMeasuredWidth(), child.getMeasuredHeight());
            perLeft += child.getMeasuredWidth();
        }
    }
}
