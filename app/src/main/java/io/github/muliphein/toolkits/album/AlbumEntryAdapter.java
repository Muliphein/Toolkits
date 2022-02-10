package io.github.muliphein.toolkits.album;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaExtractor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.muliphein.toolkits.R;
import io.github.muliphein.toolkits.databinding.AlbumEntryBinding;

public class AlbumEntryAdapter extends ArrayAdapter<Album> {
    private static final String TAG = "AlbumEntryAdapter";
    private AlbumEntryBinding albumEntryBinding;
    private final int resourceId;
    public AlbumEntryAdapter(Context context, int viewResourceId, int textItemId, List<Album> albumList){
        super(context, viewResourceId, textItemId, albumList);
        resourceId = viewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Album album = getItem(position);
        View view;
        AlbumViewHolder albumViewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(this.getContext()).inflate(resourceId, parent,false);
            albumViewHolder = new AlbumViewHolder();
            albumViewHolder.albumName = view.findViewById(R.id.album_entry_text);
            albumViewHolder.albumImage = view.findViewById(R.id.album_enrty_image);
            view.setTag(albumViewHolder);
        } else {
            view = convertView;
            albumViewHolder = (AlbumViewHolder) view.getTag();
        }
        albumViewHolder.albumName.setText(album.getName());
        albumViewHolder.albumImage.setImageBitmap(album.getThumbBitMap(handler));
        return view;

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String message = msg.obj.toString();
            Log.d(TAG, "handleMessage: "+message);
            if (message.equals("Load Over"))
                notifyDataSetChanged();
        }

    };

    static class AlbumViewHolder {
        TextView albumName;
        ImageView albumImage;
    }
}
