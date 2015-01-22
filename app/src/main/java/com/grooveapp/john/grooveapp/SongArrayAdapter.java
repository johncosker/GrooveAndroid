package com.grooveapp.john.grooveapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by john on 1/21/15.
 */
public class SongArrayAdapter extends ArrayAdapter<Song> {
    private final Activity context;
    private final Song[] songs;

    static class ViewHolder {
        public TextView name;
        public TextView album;
        public TextView artist;
        public ImageView image;
    }

    public SongArrayAdapter(Activity context, Song[] songs) {
        super(context, R.layout.rowlayout, songs);
        this.context = context;
        this.songs = songs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) rowView.findViewById(R.id.name);
            viewHolder.album = (TextView) rowView.findViewById(R.id.album);
            viewHolder.artist = (TextView) rowView.findViewById(R.id.artist);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.list_image);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Song s = songs[position];
        holder.name.setText(s.getName());
        holder.album.setText(s.getAlbum());
        holder.artist.setText(s.getArtist());
        //holder.image.setImageResource(R.drawable.thumbsup);
        new DownloadImageTask((ImageView) holder.image).execute("http://images.gs-cdn.net/static/artists/120_923.jpg");

        return rowView;
    }
}
