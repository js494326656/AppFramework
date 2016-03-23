package com.jsware;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.jsware.swiperefresh.fragment.SwipeRefreshFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NavigationActivity extends ActionBarActivity {

    @Bind(R.id.id_toolbar)
    Toolbar idToolbar;
    @Bind(R.id.id_nv_menu)
    NavigationView idNvMenu;
    @Bind(R.id.id_drawer_layout)
    DrawerLayout idDrawerLayout;

    SwipeRefreshFragment listFragment;
    @Bind(R.id.scalpel)
    ScalpelFrameLayout scalpel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setSupportActionBar(idToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(idNvMenu);

        listFragment = SwipeRefreshFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fl_content, listFragment);
        ft.commit();

        scalpel.setLayerInteractionEnabled(true);
        scalpel.setDrawViews(true);
        scalpel.setDrawIds(true);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    idDrawerLayout.closeDrawers();
                    return true;
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            idDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
