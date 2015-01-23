package com.grooveapp.john.grooveapp;

import android.app.ListActivity;
import org.json.simple.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchActivity extends ListActivity implements AsyncResponse {
    private String mUserName;
    private String mPassword;
    private String mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_key_username), MODE_PRIVATE);
        mUserName = prefs.getString(getString(R.string.preference_key_username), "");
        mPassword = prefs.getString(getString(R.string.preference_key_password), "");
        mServer = prefs.getString(getString(R.string.preference_key_server), "");

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchResults();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Song item = (Song) getListAdapter().getItem(position);
        String strSong = item.toString();
        JSONObject obj = JSONBuilder.buildAddCommand("addSongBySourceType", mUserName, "dataBase", "",
                item.getId(),
                item.getArtistID(),
                item.getName(),
                item.getAlbum(),
                item.getArtist());
        new TCPSender().execute(mServer, obj.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    public void getSearchResults(){
        EditText searchView = (EditText) findViewById(R.id.search_text);
        String searchText = searchView.getText().toString();


        TCPSender tcpTask = new TCPSender();
        tcpTask.delegate = this;
        tcpTask.execute(mServer, JSONBuilder.buildOutCommand("search", mUserName, "search", searchText).toString());
    }

    @Override
    public void processFinish(String output) {
        ArrayList<Song> songs;
        songs = JSONBuilder.getSongsFromSearch(output);
        SongArrayAdapter adapter = new SongArrayAdapter(this, songs.toArray(new Song[songs.size()]));
        setListAdapter(adapter);
    }
}
