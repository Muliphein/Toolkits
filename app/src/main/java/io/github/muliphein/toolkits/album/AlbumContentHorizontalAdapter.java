package io.github.muliphein.toolkits.album;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.github.muliphein.toolkits.R;

public class AlbumContentHorizontalAdapter extends RecyclerView.Adapter<AlbumContentHorizontalAdapter.AlbumContentViewHolder> {

    private static final String TAG = "AlbumContentAdapter";
    private final Album album;
    private final Context context;
    public AlbumContentHorizontalAdapter(Context _context, Album _album){
        context = _context;
        album = _album;
    }

    @NonNull
    @Override
    public AlbumContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new AlbumContentViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_photoview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumContentViewHolder holder, int position){
        holder.imageView.setImageBitmap(album.getContentBitmap(position, handler));
        holder.textView.setText(
                String.valueOf(position+1)+ "/"  + String.valueOf(album.getContent().size())
                        + "  "+ album.getContent().get(position).getName()
        );
    }

    @Override
    public int getItemCount(){
        return album.getContent().size();
    }


    static class AlbumContentViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        AlbumContentViewHolder (@NonNull View view){
            super(view);
            imageView = view.findViewById(R.id.picture_photoview);
            textView = view.findViewById(R.id.text_photoview);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String message = msg.obj.toString();
            Log.d(TAG, "handleMessage: "+message);
            notifyItemChanged(Integer.parseInt(message));
        }

    };
}
