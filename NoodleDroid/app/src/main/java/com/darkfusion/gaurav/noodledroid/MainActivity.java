package com.darkfusion.gaurav.noodledroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String LOG_TAG = "#meToo";
    static final String INVALID_IP_ADDRESS = "IP";
    static final int INVALID_PORT = -1;

    static String ipAddress = "192.168.137.150";
    static int port = 8080;
    static CommunicationHandler communicationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        new SendEOFAsyncTask().execute();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class fragmentClass = null;
        Fragment fragmentInstance = null;
        String title = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                fragmentInstance = HomeFragment.newInstance();
                title = getResources().getString(R.string.app_name);
                break;
            case R.id.nav_keyboard_fragment:
                fragmentClass = KeyboardFragment.class;
                fragmentInstance = KeyboardFragment.newInstance();
                title = getResources().getString(R.string.keyboardFragmentTitle);
                break;
            case R.id.nav_touchpad_fragment:
                fragmentClass = TouchpadFragment.class;
                fragmentInstance = TouchpadFragment.newInstance();
                title = getResources().getString(R.string.touchpadFragmentTitle);
                break;
            case R.id.nav_connect:
                fragmentClass = ConnectionFragment.class;
                fragmentInstance = ConnectionFragment.newInstance();
                title = getResources().getString(R.string.connectFragmentTitle);
        }

        try {
            loadFragment(fragmentClass, fragmentInstance, title);
        } catch (NullPointerException npe) {
            Toast.makeText(getApplicationContext(), "There was an error loading: " + title, Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        /*This method is to be used only for the keyboard fragment
         *  to handle key events like BACK button in the soft keyboard
         * */
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof KeyboardFragment) {
            KeyboardFragment.customOnKeyDown(keyCode, getApplicationContext());
        }

        return super.onKeyDown(keyCode, event);
    }

    static class SendEOFAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            communicationHandler.sendEOF();
            return null;
        }
    }

    boolean isDifferentFromCurrentFragment(Class targetFragmentClass) {
        if (targetFragmentClass == null) {
            return false;
        }

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        return !targetFragmentClass.isInstance(currentFragment);
    }

    /**
     * @param targetFragmentClass    The Class object of the fragment to be loaded
     * @param targetFragmentInstance The instance of the fragment to be loaded
     * @param title The title to be set on the screen
     */
    void loadFragment(Class targetFragmentClass, Fragment targetFragmentInstance, String title) throws
            NullPointerException {
        if (isDifferentFromCurrentFragment(targetFragmentClass)) {
            replaceFragment(targetFragmentInstance, title);
        }
    }

    /**
     * Replaces the old fragment with the new one and updates the screen title accordingly
     *
     * @param fragment The new fragment to replace with
     * @param title    The title of the new screen (Fragment)
     */
    void replaceFragment(Fragment fragment, String title) {
        getSupportFragmentManager().
                beginTransaction().
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                replace(R.id.fragment_container, fragment).
                commit();

        setTitle(title);
    }

    public static void setIpAddress(String ipAddress) {
        MainActivity.ipAddress = isValidIpAddress(ipAddress) ? ipAddress : INVALID_IP_ADDRESS;
    }

    public static void setPort(int port) {
        MainActivity.port = isValidPort(port) ? port : INVALID_PORT;
    }

    private static boolean isValidPort(int port) {
        return port >= 1024 && port <= 65535;
    }

    private static boolean isValidIpAddress(String ipAddress) {
        return true;
    }

}