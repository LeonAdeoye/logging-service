package com.leon.services;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.classic.Logger;

import javax.annotation.PostConstruct;

import static java.lang.System.exit;

@Service
public class LoggingServiceImpl implements LoggingService
{
    private static final org.slf4j.Logger loggerInstance = LoggerFactory.getLogger(LoggingServiceImpl.class);
    private static Map<String,Logger> loggerMap = new HashMap<>();
    private LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    @PostConstruct
    public void checkForLogsDirectory()
    {
        File f = new File("../logs");
        if (f.exists() && f.isDirectory())
        {
            loggerInstance.info("Detected a '../logs' directory to hold app log files.");
        }
        else
        {
            loggerInstance.error("A ../logs directory to hold app log files could not be detected. This micro-service will now terminate.");
            exit(1);
        }
    }

    @Override
    public void log(String loggerName, String level, String message)
    {
        logMessage(getLogger(loggerName), level, message);
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
        rollingPolicy.setFileNamePattern("../logs/" + loggerName + ".log.%d{yyyy-MM-dd}");
        rollingPolicy.setParent(appender);
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setMaxHistory(7);
        rollingPolicy.start();
        rollingPolicy.setCleanHistoryOnStart(true);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%d %-5level %msg%n");
        encoder.start();

        appender.setContext(loggerContext);
        appender.setRollingPolicy(rollingPolicy);
        appender.setEncoder(encoder);
        appender.setFile("../logs/" + loggerName + ".log");
        appender.setAppend(true);
        appender.start();

        Logger loggerInstance = (Logger) LoggerFactory.getLogger(loggerName);
        loggerInstance.addAppender(appender);
        loggerInstance.setLevel(Level.TRACE);
        loggerInstance.setAdditive(true);

        return loggerInstance;
    }
}
