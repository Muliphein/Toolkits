package io.github.muliphein.toolkits.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.muliphein.toolkits.AlbumReadActivity;
import io.github.muliphein.toolkits.R;
import io.github.muliphein.toolkits.album.AlbumEntryAdapter;
import io.github.muliphein.toolkits.databinding.FragmentGalleryBinding;
import io.github.muliphein.toolkits.album.Album;
import io.github.muliphein.toolkits.ui.photoview.PhotoviewFragment;
import io.github.muliphein.toolkits.utils.WindowsSorter;

public class GalleryFragment extends Fragment {

    private static final String TAG="GalleryFragment";
    private ArrayList<MutableLiveData<String>> fileNameList;
    private FragmentGalleryBinding binding;

    public void getFileName(){
        fileNameList = new ArrayList<>();
        Context thisContext = this.getContext();

        File filePath = thisContext.getExternalFilesDir(null);

        if (filePath != null){
            if (!filePath.exists()){
                try{
                    if (!filePath.mkdirs())
                        throw new IOException();
                }
                catch (Exception error){
                    Log.e(TAG, "testFillFileName: Mkdirs Error");
                }
            }
        }

        File[] tempList = filePath.listFiles();
        Arrays.sort(tempList, 0, tempList.length, new WindowsSorter.WindowsExplorerComparatorReverse());
        for (File file : tempList) {
            fileNameList.add(new MutableLiveData<>(file.toString()));
            Log.d(TAG, "testFillFileName: Album : "+(file.toString()));
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getFileName();

        List<Album> albumList = new ArrayList<>();
        for (int i=0; i<fileNameList.size(); ++i){
            albumList.add(new Album(fileNameList.get(i).getValue()));
            Log.d(TAG, "onCreateView: String " + fileNameList.get(i).getValue());
        }

        AlbumEntryAdapter adapter = new AlbumEntryAdapter(this.getContext(), R.layout.album_entry, R.id.album_entry_text, albumList);

        binding.listGallery.setAdapter(adapter);

        binding.listGallery.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album album=albumList.get(position);
                AlbumReadActivity.openActivity(getContext(), album);
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new PhotoviewFragment(album.getContent().get(0)), null).addToBackStack(null).commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}