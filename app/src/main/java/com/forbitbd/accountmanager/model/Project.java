package com.forbitbd.accountmanager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Project implements Serializable {

    private String name;
    private String location;

    public Project(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
