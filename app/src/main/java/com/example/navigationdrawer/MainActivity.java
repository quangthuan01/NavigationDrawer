package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.navigationdrawer.fragment.GioiThieuFragment;
import com.example.navigationdrawer.fragment.TaiKhoanFragment;
import com.example.navigationdrawer.fragment.ChiFragment;
import com.example.navigationdrawer.fragment.ThongKeFragment;
import com.example.navigationdrawer.fragment.ThuFragment;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //initialize variable
    SNavigationDrawer sNavigationDrawer;
    Class aClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign variable
        sNavigationDrawer = findViewById(R.id.navigation_drawer);

        // initialize munu item
        List<MenuItem> itemList = new ArrayList<>();
        //add menu item in list
        itemList.add(new MenuItem("Revenues",R.drawable.message_bg));
        itemList.add(new MenuItem("Spending money",R.drawable.news_bg));
        itemList.add(new MenuItem("Statistical",R.drawable.music_bg));
        itemList.add(new MenuItem("Introduce",R.drawable.feed_bg));
        itemList.add(new MenuItem("Account",R.drawable.account));

        sNavigationDrawer.setMenuItemList(itemList);

        //set default title
        sNavigationDrawer.setAppbarTitleTV("Revenues");
        //set default fragment
        aClass = ChiFragment.class;
        //Open fragment
        openFragment();

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                switch (position){
                    case 0:
                        aClass = ChiFragment.class;
                        break;
                    case 1:
                        aClass = ThuFragment.class;
                        break;
                    case 2:
                        aClass = ThongKeFragment.class;
                        break;
                    case 3:
                        aClass = GioiThieuFragment.class;
                        break;
                    case 4:
                        aClass = TaiKhoanFragment.class;
                        break;

                }
            }
        });

        sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {
            @Override
            public void onDrawerOpening() {

            }

            @Override
            public void onDrawerClosing() {
                //open Fragment
                openFragment();
            }

            @Override
            public void onDrawerOpened() {

            }

            @Override
            public void onDrawerClosed() {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void openFragment() {
        try {
            //Initialize fragment
            Fragment fragment = (Fragment) aClass.newInstance();
            //Open Fragment
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in
                            , android.R.anim.fade_out)
                    .replace(R.id.frame_layout,fragment)
                    .commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}