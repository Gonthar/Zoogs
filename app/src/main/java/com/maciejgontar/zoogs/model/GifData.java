package com.maciejgontar.zoogs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GifData {
    @Expose
    @SerializedName("data")
    private Gif[] data;
    @Expose
    @SerializedName("pagination")
    private Pagination pagination;
//    @Expose
//    @SerializedName("meta")
//    private Gif[] meta;

    public Gif[] getData() {
        return data;
    }

    public void setData(Gif[] data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}