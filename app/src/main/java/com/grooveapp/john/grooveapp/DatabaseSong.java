package com.grooveapp.john.grooveapp;

/**
 * Created by john on 2/16/15.
 */
public class DatabaseSong extends Song {
    private int mVotes = 0;
    public DatabaseSong(String id, String name, String artist, String artistId, String album, String length, String thumb, int votes) {
        super(id, name, artist, artistId, album, length, thumb);
        mVotes = votes;
    }

    public int getVotes() {
        return mVotes;
    }

    public void setVotes(int mVotes) {
        this.mVotes = mVotes;
    }
}
