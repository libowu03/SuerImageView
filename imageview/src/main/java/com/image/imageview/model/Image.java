package com.image.imageview.model;

/**
 * 保存缓存具体数据的描述类
 */
public class Image {
    public int height;
    public int width;
    public Enum type;
    public Object bitmap;

    public Image(){

    }

    public Image(int height, int width, Enum type, Object bitmap) {
        this.height = height;
        this.width = width;
        this.type = type;
        this.bitmap = bitmap;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
        this.type = type;
    }

    public Object getBitmap() {
        return bitmap;
    }

    public void setBitmap(Object bitmap) {
        this.bitmap = bitmap;
    }
}
