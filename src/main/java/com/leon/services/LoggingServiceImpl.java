package com.leon.services;

import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements LoggingService
{
    @Override
    public void log(String logger, String level, String timestamp, String message)
    {
    }
}
