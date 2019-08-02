package com.huatec.edu.mobileshop.entity;

/**
 * Created by Administrator on 2016/12/12.
 */

public class Region {
    private String id;
    private String name;

    public Region() {
        super();

    }

    public Region(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
