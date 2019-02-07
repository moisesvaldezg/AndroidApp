package com.company.androidapp.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.company.androidapp.R;
import com.company.androidapp.browser.BrowserFragment;
import com.company.androidapp.project.ProjectListFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView drawerList;
    private RelativeLayout drawerPane;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

    private Fragment fragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Options
        menuItems.add(new MenuItem(R.drawable.google, "openSite", "Google"));
        menuItems.add(new MenuItem(0, "buttons", "Buttons"));
        menuItems.add(new MenuItem(R.drawable.github, "listProjects", "10 Android Projects in GitHub"));

        // Menu
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerPane = (RelativeLayout) findViewById(R.id.menu);
        drawerList = (ListView) findViewById(R.id.menuItems);
        MenuAdapter adapter = new MenuAdapter(this, menuItems);
        drawerList.setAdapter(adapter);

        // Menu item click listener
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position, true);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_launcher_foreground, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState != null)
            restoreUI(savedInstanceState);
        else
            updateFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        saveUI(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveUI(outState);
    }

    private void saveUI(Bundle outState){
        //fragment
        getSupportFragmentManager().putFragment(outState, "mainContent", fragment);
        //title
        outState.putCharSequence("title", this.getTitle());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        restoreUI(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreUI(savedInstanceState);
    }

    private void restoreUI(Bundle savedInstanceState){
        //fragment
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragment = getSupportFragmentManager().getFragment(savedInstanceState, "mainContent");
        updateFragment();
        //title
        setTitle(savedInstanceState.getString("title"));
    }

    private void updateFragment(){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();
    }

    protected void selectItemFromDrawer(int position, boolean closeDrawer) {
        switch (menuItems.get(position).getName())
        {
            case "openSite":
                fragment = new BrowserFragment("https://www.google.com");
                break;
            case "buttons":
                fragment = new Fragment();
                break;
            case "listProjects":
                fragment = new ProjectListFragment();
                break;
            default:
                return;
        }

        updateFragment();

        drawerList.setItemChecked(position, true);
        setTitle(menuItems.get(position).getTitle());

        if (closeDrawer)
            drawerLayout.closeDrawer(drawerPane);
    }
}
