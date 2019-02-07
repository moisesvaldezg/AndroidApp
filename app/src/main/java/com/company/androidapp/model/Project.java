package com.company.androidapp.model;

public class Project {

    private String name;
    private String fullName;
    private String htmlUrl;

    public Project(String name, String fullName, String htmlUrl) {
        this.name = name;
        this.fullName = fullName;
        this.htmlUrl = htmlUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}
