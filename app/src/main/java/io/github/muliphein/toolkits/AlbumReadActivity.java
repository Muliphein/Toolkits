package io.github.muliphein.toolkits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.muliphein.toolkits.album.Album;
import io.github.muliphein.toolkits.album.AlbumContentHorizontalAdapter;
import io.github.muliphein.toolkits.album.AlbumContentVerticalAdapter;
import io.github.muliphein.toolkits.databinding.ActivityAlbumReadViewpager2Binding;
import io.github.muliphein.toolkits.databinding.ActivityAlbumReadListviewBinding;

public class AlbumReadActivity extends Activity {
    ActivityAlbumReadViewpager2Binding binding;
    ActivityAlbumReadListviewBinding bindingTest;
    public static final boolean useTest = false;
    public static final String TAG = "AlbumReadActivity";
    Album album;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (useTest == false){
            binding = ActivityAlbumReadViewpager2Binding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            album = getIntent().getParcelableExtra("album");

            AlbumContentHorizontalAdapter adapter = new AlbumContentHorizontalAdapter(this, album);
            binding.viewpager2AlbumRead.setAdapter(adapter);
        } else {
            bindingTest = ActivityAlbumReadListviewBinding.inflate(getLayoutInflater());
            setContentView(bindingTest.getRoot());
            album = getIntent().getParcelableExtra("album");

            AlbumContentHorizontalAdapter adapter = new AlbumContentHorizontalAdapter(this, album);
            bindingTest.recyclerviewAlbumRead.setLayoutManager(new LinearLayoutManager(this));
            bindingTest.recyclerviewAlbumRead.setAdapter(adapter);
        }


    }

    public static void openActivity(Context context, Album album){
        Intent intent = new Intent(context, AlbumReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("album", album);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
