package com.jung.smartstreetlight;

public class ItemData {
    String title;
    int image_path;
    String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage_path() {
        return image_path;
    }

    public void setImage_path(int image_path) {
        this.image_path = image_path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public ItemData(String title, int image_path, String content) {
        this.title = title;
        this.image_path = image_path;
        this.content = content;
    }
}
