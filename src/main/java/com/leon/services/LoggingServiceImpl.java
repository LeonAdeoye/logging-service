package com.leon.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

import javax.annotation.PostConstruct;

@Service
public class LoggingServiceImpl implements LoggingService
{
    private Map<String,Logger> loggers = new HashMap<>();

    @PostConstruct
    public void initialize()
    {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }

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
            case "WARN":
                loggerInstance.warn(message);
                break;
            case "TRACE":
                loggerInstance.trace(message);
                break;
            case "ERROR":
                loggerInstance.error(message);
                break;
        }
    }
}
