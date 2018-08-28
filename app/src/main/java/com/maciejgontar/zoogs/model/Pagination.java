package com.maciejgontar.zoogs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {
    @Expose
    @SerializedName("total_count")
    private int total_count;
    @Expose
    @SerializedName("count")
    private int count;
    @Expose
    @SerializedName("offset")
    private int offset;

    public int getTotalCount() {
        return total_count;
    }

    public void setTotalCount(int total_count) {
        this.total_count = total_count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
