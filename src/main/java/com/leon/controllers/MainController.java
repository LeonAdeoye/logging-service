package com.leon.controllers;

import com.leon.models.LogMessage;
import com.leon.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MainController
{
    private static final Logger loggerInstance = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private LoggingService loggingService;

    @CrossOrigin
    @RequestMapping(value = "/log", method=POST)
    public void log(@RequestBody LogMessage logMessage) throws IllegalArgumentException
    {
        if(logMessage.getLogger() == null || logMessage.getLogger().isEmpty())
        {
            loggerInstance.error("The logger request param cannot be null or empty.");
            throw new IllegalArgumentException("Logger argument is invalid.");
        }

        if(logMessage.getMessage() == null || logMessage.getMessage().isEmpty())
        {
            loggerInstance.error("The message request param cannot be null or empty.");
            throw new IllegalArgumentException("Message argument is invalid.");
        }

        if(logMessage.getLevel() == null || logMessage.getLevel().isEmpty())
        {
            loggerInstance.error("The log level request param cannot be null or empty.");
            throw new IllegalArgumentException("Log level message argument is invalid.");
        }

        this.loggingService.log(logMessage.getLogger(), logMessage.getLevel(), logMessage.getMessage());
    }
}