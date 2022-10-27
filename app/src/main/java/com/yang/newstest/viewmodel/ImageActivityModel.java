package com.yang.newstest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ImageActivityModel extends ViewModel {
    private String src;
    private List<String> imageSrcs;
    private int position;

    public ImageActivityModel(String src, List<String> imageSrcs, int position) {
        this.src = src;
        this.imageSrcs = imageSrcs;
        this.position = position;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public List<String> getImageSrcs() {
        return imageSrcs;
    }

    public void setImageSrcs(List<String> imageSrcs) {
        this.imageSrcs = imageSrcs;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
