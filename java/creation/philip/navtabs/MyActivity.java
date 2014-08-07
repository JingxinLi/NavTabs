package creation.philip.navtabs;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MyActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        if (Build.VERSION.SDK_INT > 3) {
            getActionBar().hide();
        }



        final TabBarView tabs = (TabBarView) findViewById(R.id.tabs);
        TabBar.NavTabListener listener = new TabBar.NavTabListener() {
            @Override
            public void onTabSelected(TabBar.NavTab tab, FragmentTransaction ft) {
                final DummyFragment fragment = new DummyFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("key", tabs.getSelectedTabPosition());
                fragment.setArguments(bundle);
                ft.replace(R.id.fragment_container, fragment);
                Log.i("TAG", "tab1 selected");
            }
        };

        TabBar.NavTab tab = tabs.createNavTab();
        tab.setText("tab1");
        tab.setTabListener(listener);
        tab.setBackGroudColor(Color.BLUE);
        tabs.addTab(tab);

        TabBar.NavTab tab2 = tabs.createNavTab();
        tab2.setText("tab2");
        tab2.setTabListener(listener);
        tab2.setBackGroudColor(Color.GREEN);
        tabs.addTab(tab2);
    }

    public static class DummyFragment extends Fragment {
        public DummyFragment(){};

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, null);
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                int pos = bundle.getInt("key");
                ((TextView) rootView.findViewById(R.id.dummy_text)).setText("current tab is Tab " + (pos+1));
            }

            return rootView;
        }
    }
}
