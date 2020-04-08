package com.leon.services;

public interface LoggingService
{
    void log(String logger, String level, String timestamp, String message);
}