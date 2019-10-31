package utt.if26.bardcamp.models;

import android.net.Uri;

public class Music {
    private String artistName;
    private String title;
    private Uri path;
    private String picPath;

    private int ID;
    private boolean favourite = false;

    public Music(int ID, String artistName, String title, String path, String picPath, boolean favourite) {
        this.artistName = artistName;
        this.title = title;
        this.ID = ID;
        this.path = Uri.parse(path);
        this.setPicPath(picPath);
        this.favourite = favourite;
    }

    public Music(String artistName, String title, String path, String picPath) {
        this.artistName = artistName;
        this.title = title;
        this.path = Uri.parse(path);
        this.setPicPath(picPath);
    }


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Uri getPath() {
        return path;
    }

    public void setPath(Uri path) {
        this.path = path;
    }

    public String getPicPath() {
        return picPath;
    }

    public int getID() { return ID; }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
