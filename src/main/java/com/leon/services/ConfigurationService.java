package com.leon.services;

public interface ConfigurationService
{
    public String getConfigurationValue(String owner, String key);
    public void loadAllConfigurations();
    public void reconfigure();
}