package com.grooveapp.john.grooveapp;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AsyncResponse {
    public static final String PREFS_NAME = "UserFile";
    private String mUserName;
    private String mServer;
    private String mPassword;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        boolean isLogged = getIntent().getBooleanExtra("isLogged", false);
        if(!isLogged) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_key_username), MODE_PRIVATE);
            mUserName = prefs.getString(getString(R.string.preference_key_username), "");
            mPassword = prefs.getString(getString(R.string.preference_key_password), "");
            mServer = prefs.getString(getString(R.string.preference_key_server), "");
            getCurrentSong();
        }
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_key_username), MODE_PRIVATE);
        boolean isLogged = false;
        isLogged = sharedPref.getBoolean(getString(R.string.is_logged_in), isLogged);
        return isLogged;
    }

    @Override
    public void onDestroy()
    {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(getString(R.string.is_logged_in), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.is_logged_in), false);
        editor.commit();
        super.onDestroy();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    public void getCurrentSong(){
        TCPSender tcpTask = new TCPSender();
        tcpTask.delegate = this;
        tcpTask.execute(mServer, JSONBuilder.buildOutCommand("current", mUserName, "player", "").toString());
    }

    @Override
    public void processFinish(String output) {
        Song song = JSONBuilder.getSong(output);

        TextView name = (TextView)findViewById(R.id.current_name);
        name.setText(song.getName());
        TextView artist = (TextView)findViewById(R.id.current_artist);
        artist.setText(song.getArtist());
        TextView album = (TextView)findViewById(R.id.current_album);
        album.setText(song.getAlbum());
        ImageView image = (ImageView)findViewById(R.id.current_image);
        //image.setImageURI("http://eofdreams.com/data_images/dreams/cat/cat-05.jpg");
        new DownloadImageTask(image).execute("http://eofdreams.com/data_images/dreams/cat/cat-05.jpg");
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
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_admin) {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
