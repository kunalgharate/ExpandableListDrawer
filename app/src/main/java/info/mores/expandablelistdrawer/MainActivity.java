package info.mores.expandablelistdrawer;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import info.mores.expandablelistdrawer.Adapter.CustomExpandableListAdapter;
import info.mores.expandablelistdrawer.Helper.FragmentNavigationManager;
import info.mores.expandablelistdrawer.Interface.INavigationManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> listTitle;
    private Map<String, List<String>> lstChild;
    private INavigationManager iNavigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view Init

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = findViewById(R.id.navList);
        iNavigationManager = FragmentNavigationManager.getmInstance(this);

        initItems();

        View listHeader = getLayoutInflater().inflate(R.layout.nav_header, null, false);
        expandableListView.addHeaderView(listHeader);

        getData();

        addDrawerItems();
        setupDrawer();

        if (savedInstanceState == null) {
            selectedfirstAsDefault();

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Main");
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectedfirstAsDefault() {

        if (iNavigationManager != null) {
            String firstItem = listTitle.get(0);
            iNavigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
              //  getSupportActionBar().setTitle("Demo Opens");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
              //  getSupportActionBar().setTitle("Demo Close");
                invalidateOptionsMenu();
            }
        };


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.setDrawerSlideAnimationEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems() {

        adapter = new CustomExpandableListAdapter(this, listTitle, lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                getSupportActionBar().setTitle(listTitle.get(i).toString());
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                getSupportActionBar().setTitle("Test");
            }
        });

     /*   expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                String selecteditem = ((List) (lstChild.get(listTitle.get(i)))).get(i1).toString();

                getSupportActionBar().setTitle(selecteditem);

                if (items[0].equalsIgnoreCase(listTitle.get(i))) {
                    iNavigationManager.showFragment(selecteditem);
                } else {
                    throw new IllegalArgumentException("Not supported Fragment");
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });*/



    }

    private void getData() {

        List<String> titleList = Arrays.asList("TAndroid", "TIos", "TXamrin", "TJAVA");
        List<String> childList = Arrays.asList("cAndroid", "cIos", "cXamrin", "cJAVA");

        lstChild = new TreeMap<>();
        lstChild.put(titleList.get(0), childList);
        lstChild.put(titleList.get(1), childList);
        lstChild.put(titleList.get(2), childList);

        listTitle = new ArrayList<>(lstChild.keySet());
    }

    private void initItems() {

        items = new String[]{"Android", "Ios", "Xamrin", "JAVA"};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
