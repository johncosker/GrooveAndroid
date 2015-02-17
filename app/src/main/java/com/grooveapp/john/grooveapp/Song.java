package com.grooveapp.john.grooveapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by john on 1/19/15.
 */
@SuppressWarnings("unused")
public class Song implements Parcelable {


    private String mName;
    private String mArtist;
    private String mAlbum;
    private String mLen;
    private String mThumb;
    private String mId;
    private String mSongID;
    private String mArtistID;

    public Song(String id,
                String name,
                String artist,
                String artistId,
                String album,
                String length,
                String thumb) {
        mId = id;
        mName = name;
        mAlbum = album;
        mArtist = artist;
        mLen = length;
        mThumb = thumb;
        mArtistID = artistId;
    }

    public Song(Parcel in){
        String[] data = new String[7];

        in.readStringArray(data);
        this.mId = data[0];
        this.mName = data[1];
        this.mArtist = data[2];
        this.mAlbum = data[3];
        this.mLen = data[4];
        this.mThumb = data[5];
        this.mArtistID = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.mId,
                this.mName,
                this.mArtist,
                this.mAlbum,
                this.mLen,
                this.mThumb,
                this.mArtistID
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String toString(){
        return mName + " - " + mArtist;
    }
    //region Getters/Setters
    public String getName() { return mName; }
    public String getArtist() { return mArtist; }
    public String getAlbum() { return mAlbum; }
    public String getLen() { return mLen; }
    public String getThumb() { return mThumb; }
    public String getId() { return mId; }
    public String getArtistID() { return mArtistID;}
    public void setName(String mName) { this.mName = mName; }
    public void setArtist(String mArtist) { this.mArtist = mArtist; }
    public void setAlbum(String mAlbum) { this.mAlbum = mAlbum; }
    public void setLen(String mLen) { this.mLen = mLen; }
    public void setThumb(String mThumb) { this.mThumb = mThumb; }
    public void setId(String mId) { this.mId = mId; }
    public void setArtistID(String mArtistId) { this.mArtistID = mArtistId; }
    //endregion
}
