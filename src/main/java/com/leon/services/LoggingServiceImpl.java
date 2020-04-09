package com.leon.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoggingServiceImpl implements LoggingService
{
    private Map<String,Logger> loggers = new HashMap<>();

    @Override
    public void log(String logger, String level, String message)
    {
        Logger loggerInstance = getLogger(logger);
        logMessage(loggerInstance, level, message);
    }

    private Logger getLogger(String logger)
    {
        Logger loggerInstance;

        if(loggers.containsKey(logger))
        {
            loggerInstance = loggers.get(logger);
        }
        else
        {
            loggerInstance =  LoggerFactory.getLogger(logger);
            loggers.put(logger, loggerInstance);
        }

        return loggerInstance;
    }

    private void logMessage(Logger loggerInstance, String level, String message)
    {
        switch(level)
        {
            case "DEBUG":
                loggerInstance.debug(message);
                break;
            case "INFO":
                loggerInstance.info(message);
                break;
        }
    }
}
