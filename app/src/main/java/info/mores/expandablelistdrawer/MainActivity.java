package info.mores.expandablelistdrawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.mores.expandablelistdrawer.Adapter.CustomExpandableListAdapter;
import info.mores.expandablelistdrawer.Helper.FragmentNavigationManager;
import info.mores.expandablelistdrawer.Interface.INavigationManager;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;
    private int lastPosition = -1;
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

        // View listHeader = getLayoutInflater().inflate(R.layout.nav_header, null, false);
        // expandableListView.addHeaderView(listHeader);

        //listTitle.clear();
        //  lstChild.clear();
        getData();

        addDrawerItems();
        setupDrawer();

        if (savedInstanceState == null) {
            selectedfirstAsDefault();

        }

        //  getActionBar().setHomeButtonEnabled(true);
        //  getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
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
            public void onGroupExpand(int groupPosition) {
                int previousGroup = -1;


                if (lastPosition != -1
                        && groupPosition != lastPosition) {
                    expandableListView.collapseGroup(lastPosition);
                }
                lastPosition = groupPosition;

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                getSupportActionBar().setTitle("Test");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int gruopPosition, int childPosition, long l) {

              /*  String selecteditem = ((List) (lstChild.get(listTitle.get(gruopPosition)))).get(childPosition).toString();

                getSupportActionBar().setTitle(selecteditem);

                if (items[0].equalsIgnoreCase(listTitle.get(gruopPosition))) {
                    iNavigationManager.showFragment(selecteditem);
                } else {
                    throw new IllegalArgumentException("Not supported Fragment");
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;*/

                Toast.makeText(MainActivity.this, "text child", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    private void getData() {

        List<String> titleList = Arrays.asList("Dashboard", "Installation", "Callibration", "Complaints", "Appointment", "AMC", "Warranty", "Toolkit", "Report", "Make in", "Logout");
        List<String> childAll = Arrays.asList("New", "Pending", "Done");
        List<String> childAmc = Arrays.asList("Compressive", "Non Compressive");


        lstChild = new LinkedHashMap<>();
        lstChild.put(titleList.get(0), new ArrayList<String>());
        lstChild.put(titleList.get(1), childAll);
        lstChild.put(titleList.get(2), childAll);
        lstChild.put(titleList.get(3), childAll);
        lstChild.put(titleList.get(4), childAll);
        lstChild.put(titleList.get(5), childAmc);
        lstChild.put(titleList.get(6), new ArrayList<String>());
        lstChild.put(titleList.get(7), new ArrayList<String>());
        lstChild.put(titleList.get(8), new ArrayList<String>());
        lstChild.put(titleList.get(9), new ArrayList<String>());
        lstChild.put(titleList.get(10), new ArrayList<String>());
        //   lstChild.put(titleList.get(11), new ArrayList<String>());

        listTitle = new ArrayList<>(lstChild.keySet());
    }

    private void initItems() {

        items = new String[]{"Dashboard", "Installation", "Callibration", "Complaints", "Appointment", "AMC", "Warranty", "Toolkit", "Report", "Make in", "Logout"};
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

