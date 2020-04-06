package com.leon.services;

import com.leon.models.Configuration;

import java.util.List;

public interface MessagingService
{
    public void saveConfiguration(Configuration configuration);
    public List<Configuration> loadAllConfigurations();
}