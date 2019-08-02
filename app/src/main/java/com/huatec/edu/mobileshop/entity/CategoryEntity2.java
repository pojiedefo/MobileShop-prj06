package com.huatec.edu.mobileshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class CategoryEntity2 implements Serializable {
    private String name ;
    private int cat_id;
    private List<CategoryEntity> entities;

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public List<CategoryEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<CategoryEntity> entities) {
        this.entities = entities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
