package io.github.muliphein.toolkits.ui.photoview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

import io.github.muliphein.toolkits.databinding.FragmentPhotoviewBinding;
import io.github.muliphein.toolkits.utils.PictureReader;

public class PhotoviewFragment extends Fragment{
    private FragmentPhotoviewBinding binding;
    private final File fileName;
    public PhotoviewFragment(File _fileName){
        fileName = _fileName;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPhotoviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.picturePhotoview.setImageBitmap(new PictureReader().picReaderAsBitmap(fileName));
        return root;
    }
}
