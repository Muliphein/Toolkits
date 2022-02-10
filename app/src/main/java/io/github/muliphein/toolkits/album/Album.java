package io.github.muliphein.toolkits.album;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.muliphein.toolkits.utils.PictureReader;
import io.github.muliphein.toolkits.utils.WindowsSorter;

public class Album implements Parcelable{
    private final String albumPath;
    private final String albumName;
    private static final String TAG="Album";
    private Bitmap thumbBitMap = null;
    public boolean isGetThumbBitmap = false;
    private ArrayList<File> albumContent = new ArrayList<>();
    private ArrayList<Bitmap> albumContentBitMap = new ArrayList<>();

    public Album(String _filePath) {
        this.albumPath = _filePath;
        albumName = new File(_filePath).getName();
    }

    public Album(Parcel parcel) {
        Log.d(TAG, "Album: Start Reading");
        albumPath = parcel.readString();
        albumName = parcel.readString();
        int count = parcel.readInt();
        for (int i=0; i<count; ++i){
            albumContent.add(new File(parcel.readString()));
            albumContentBitMap.add(null);
        }
    }

    public static final Parcelable.Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel parcel) {
            return new Album(parcel);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents(){ return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(albumPath);
        parcel.writeString(albumName);
        if (albumContent == null){
            parcel.writeInt(0);
        } else {
            parcel.writeInt(albumContent.size());
            for (File file:albumContent){
                parcel.writeString(file.getPath());
            }
        }

        Log.d(TAG, "writeToParcel: Done");
    }

    public List<File> getContent(){
        return albumContent;
    }

    public String getName(){
        return this.albumName;
    }

    public Bitmap getThumbBitMap(Handler handler){
        new Thread(){
            @Override
            public void run(){
                if (!isGetThumbBitmap){
                    isGetThumbBitmap = true;
                    readThumb();
                    Log.d(TAG, "run: Get Picture Over");
                    Message message = new Message();
                    message.obj = "Load Over";
                    handler.sendMessage(message);
                }
            }
        }.start();

        return thumbBitMap;
    }

    public Bitmap getThumbBitMap(){
        if (!isGetThumbBitmap){
            readThumb();
        }
        return thumbBitMap;
    }

    public Bitmap getContentBitmap(int position, Handler handler) {

        new Thread(){
            @Override
            public void run(){
                if (albumContentBitMap.get(position) == null){
                    albumContentBitMap.set(position, new PictureReader().picReaderAsBitmap(albumContent.get(position)));
                    Message message = new Message();
                    message.obj = String.valueOf(position);
                    handler.sendMessage(message);
                }
            }
        }.start();
        return albumContentBitMap.get(position);
    }

    static class PicFileFilter implements FileFilter{
        @Override
        public boolean accept(File pathName){
            return pathName.getName().endsWith(".jpg")
                    ||pathName.getName().endsWith(".gif")
                    ||pathName.getName().endsWith(".png")
                    ||pathName.getName().endsWith(".bmp")
                    ||pathName.getName().endsWith(".thumb")
                    ;
        }
    };

    public static ArrayList<File> getAllPicFile(File path){
        ArrayList<File> list = new ArrayList<File>();
        File[] files = path.listFiles();

        Arrays.sort(files, 0, files.length, new WindowsSorter.WindowsExplorerComparator());
        for (File file: files){
            if (file.isDirectory()) list.addAll(getAllPicFile(file));
            else if (new PicFileFilter().accept(file)) list.add(file);
        }
        return list;
    }

    public void readThumb() {
        this.isGetThumbBitmap = true;
        albumContent = getAllPicFile(new File(albumPath));
        if (albumContent.size()>0){
            File thumbFile = albumContent.get(0);
            thumbBitMap = new PictureReader().picReaderAsBitmapWithBounding(thumbFile, 200, 200);
        }
    }

    public void clearContent(){
        albumContentBitMap.clear();
    }

}
