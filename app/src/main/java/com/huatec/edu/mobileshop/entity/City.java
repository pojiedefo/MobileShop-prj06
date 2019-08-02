package com.huatec.edu.mobileshop.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class City
{
    private String id;
    private String name;
    private List<Region> diQus;
    public City()
    {
        super();

    }
    public City(String id, String name, List<Region> diQus)
    {
        super();
        this.id = id;
        this.name = name;
        this.diQus = diQus;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public List<Region> getDiQus()
    {
        return diQus;
    }
    public void setDiQus(List<Region> diQus)
    {
        this.diQus = diQus;
    }
    @Override
    public String toString()
    {
        return name;
    }

}
