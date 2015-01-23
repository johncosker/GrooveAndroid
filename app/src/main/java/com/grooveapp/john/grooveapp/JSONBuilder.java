package com.grooveapp.john.grooveapp;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by john on 1/22/15.
 */
public  class JSONBuilder {
    public static JSONObject buildOutCommand(String cmd, String user, String target, String info){
        JSONObject obj = new JSONObject();
        obj.put("cmd", cmd);
        obj.put("user", user);
        obj.put("target", target);
        obj.put("info", info);
        return obj;
    }

    public static JSONObject buildAddCommand(String cmd,
                                             String user,
                                             String target,
                                             String info,
                                             String songId,
                                             String artistId,
                                             String song,
                                             String album,
                                             String artist){
        JSONObject obj = new JSONObject();
        obj.put("cmd", "addSongBySourceType");
        obj.put("user", user);
        obj.put("target", "dataBase");
        obj.put("info", info);
        obj.put("SongID", songId);
        obj.put("ArtistID", artistId);
        obj.put("song", song);
        obj.put("album", album);
        obj.put("artist", artist);
        return obj;
    }

    public static ArrayList<Song> getSongsFromSearch(String jsonStr){
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        JSONObject jsonSong = null;
        ArrayList<Song> songs = new ArrayList<Song>();
        Song song = null;
        try {
            obj = (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray songList = (JSONArray) obj.get("songs");
        for(int i = 0; i < songList.size(); i++){
            jsonSong = (JSONObject) songList.get(i);
            String name = jsonSong.get("song").toString();
            String album = jsonSong.get("album").toString();
            String artist = jsonSong.get("artist").toString();
            String id = jsonSong.get("SongID").toString();
            String artistId = jsonSong.get("ArtistID").toString();
            song = new Song(id, name, artist, artistId, album, "", "");
            songs.add(song);
            System.out.println(song.toString());
        }

        return songs;
    }
}
