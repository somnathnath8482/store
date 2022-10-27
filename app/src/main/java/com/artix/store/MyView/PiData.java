package com.artix.store.MyView;

import androidx.annotation.Nullable;

public class PiData {
    int id;
    String title;
    int point;
    @Nullable
int color;
    public PiData() {
    }

    public PiData(int id, String title, int point, @Nullable int color) {
        this.id = id;
        this.title = title;
        this.point = point;
        this.color = color;
    }

    public PiData(int id, String title, int point) {
        this.id = id;
        this.title = title;
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
