package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration
{
    private String key;
    private String value;
    private String owner;

    public Configuration(String owner, String key, String value)
    {
        this.key = key;
        this.value = value;
        this.owner = owner;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    @Override
    public String toString()
    {
        return "Configuration{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return getKey().equals(that.getKey()) &&
                getValue().equals(that.getValue()) &&
                getOwner().equals(that.getOwner());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getKey(), getValue(), getOwner());
    }
}
