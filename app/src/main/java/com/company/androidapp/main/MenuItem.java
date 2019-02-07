package com.company.androidapp.main;

public class MenuItem {

    private int iconId;
    private String name;
    private String title;

    public MenuItem(int iconId, String name, String title) {
        this.iconId = iconId;
        this.name = name;
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
