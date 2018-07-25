package info.mores.expandablelistdrawer.Helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import info.mores.expandablelistdrawer.BuildConfig;
import info.mores.expandablelistdrawer.Fragments.FragmentContent;
import info.mores.expandablelistdrawer.Interface.INavigationManager;
import info.mores.expandablelistdrawer.MainActivity;
import info.mores.expandablelistdrawer.R;

public class FragmentNavigationManager implements INavigationManager {

    private static FragmentNavigationManager mInstance;
    private static MainActivity mainActivity;
    private FragmentManager mFragementManager;

    public static FragmentNavigationManager getmInstance(MainActivity activity) {

        if (mInstance== null)

            mInstance = new FragmentNavigationManager();
            mInstance.configure(activity);
                return mInstance;
    }

    private void configure(MainActivity activity)
        {

            mainActivity= activity;

            mFragementManager = mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {


        showFragment1(FragmentContent.newInstance(title),false);
    }

    private void showFragment1(Fragment fragmentContent, boolean b)
    {
        FragmentManager fm = mFragementManager;
        FragmentTransaction ft= fm.beginTransaction().replace(R.id.container,fragmentContent);
        ft.addToBackStack(null);

        if (b  || !BuildConfig.DEBUG)
        {
            ft.commitAllowingStateLoss();

        }
        else
        ft.commit();
        fm.executePendingTransactions();

    }
}
