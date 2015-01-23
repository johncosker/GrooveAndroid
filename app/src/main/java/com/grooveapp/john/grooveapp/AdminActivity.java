package com.grooveapp.john.grooveapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.simple.JSONObject;


public class AdminActivity extends Activity {

    private String mUserName;
    private String mPassword;
    private String mServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_key_username), MODE_PRIVATE);
        mUserName = prefs.getString(getString(R.string.preference_key_username), "");
        mPassword = prefs.getString(getString(R.string.preference_key_password), "");
        mServer = prefs.getString(getString(R.string.preference_key_server), "");

        ImageButton button= (ImageButton) findViewById(R.id.pause_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = JSONBuilder.buildOutCommand("pause", mUserName, "player", "");
                new TCPSender().execute(mServer, obj.toString());
            }
        });
        button= (ImageButton) findViewById(R.id.play_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = JSONBuilder.buildOutCommand("play", mUserName, "player", "");
                new TCPSender().execute(mServer, obj.toString());
            }
        });
        button= (ImageButton) findViewById(R.id.skip_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = JSONBuilder.buildOutCommand("skip", mUserName, "player", "");
                new TCPSender().execute(mServer, obj.toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
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
}
