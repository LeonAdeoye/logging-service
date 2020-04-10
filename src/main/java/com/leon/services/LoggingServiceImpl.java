package com.leon.services;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.classic.Logger;

@Service
public class LoggingServiceImpl implements LoggingService
{
    private static Map<String,Logger> loggerMap = new HashMap<>();
    private LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Override
    public void log(String logger, String level, String message)
    {
        logMessage(getLogger(logger), level, message);
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

    synchronized private Logger getLogger(String loggerName)
    {
        if(!loggerMap.containsKey(loggerName))
            loggerMap.put(loggerName, createLoggerInstance(loggerName));

        return loggerMap.get(loggerName);
    }

    private Logger createLoggerInstance(String loggerName)
    {
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setFileNamePattern("%d{yyyy-MM-dd_HH-mm}.log");
        rollingPolicy.setParent(appender);
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.start();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%msg%n");
        encoder.start();

        appender.setContext(loggerContext);
        appender.setRollingPolicy(rollingPolicy);
        appender.setEncoder(encoder);
        appender.setFile(loggerName);
        appender.setAppend(true);
        appender.start();

        Logger loggerInstance = (Logger) LoggerFactory.getLogger(loggerName);
        loggerInstance.addAppender(appender);
        loggerInstance.setLevel(Level.TRACE);
        loggerInstance.setAdditive(true);

        return loggerInstance;
    }
}
