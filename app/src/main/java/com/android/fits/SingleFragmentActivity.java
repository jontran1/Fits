package com.android.fits;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Nearly every activity here will use this use.
 * In this code you set the activity's view to be inflated from activity_fragment.xml.
 * Then you look for the fragment in FragmentManager in that container, creating
 * and adding it if it does not exist.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    /**
     * Returns the Res Id for the activity.
     * Subclasses can override this method to return its
     * own res id.
     * @LayoutRes means that any implementation of this
     * method should return a valid layout res id.
     * @return
     */
    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }


    /**
     * In this code you set the activity's view to be inflated from activity_fragment.xml.
     * Then you look for the fragment in FragmentManager in that container, creating
     * and adding it if it does not exist.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Previously SingleFragmentActivity was set to inflate activity_fragment.xml
        We made it more flexible by enabling a subclass to provide its own
        res ID for the layout instead.
         */
        setContentView(getLayoutResId());

        /*
        You call getSupportFragmentManager() because you are using the support library
        and the AppCompatActivity class. If you were not interested in using the support library, then
        you would subclass Activity and call getFragmentManager().
         */
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            /*
            This code creates and commits a fragment transaction.
            Fragment transactions are used to add, remove, detach, or replace fragments
            in the fragment list. They are the heart of how you use fragments to compose
            and recompose screens at runtime.
            The FragmentManager maintains a back stack of fragment transactions that you can
            navigate.

            The code basically says create a new fragment transaction. include one add operation in it, and
            then commit it.

            A container view ID serves two purposes. IT tells the FragmentManager where in the activity's view the fragment's
            view should appear. It is used as a unique identifier for a fragment in the FragmentManager's list.
             */
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

}