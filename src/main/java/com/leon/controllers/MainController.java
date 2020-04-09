package com.leon.controllers;

import com.leon.services.ConfigurationService;
import com.leon.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
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
    private ConfigurationService configurationService;
    @Autowired
    private LoggingService loggingService;

    @RequestMapping("/reconfigure")
    public void reconfigure()
    {
        loggerInstance.info("Received request to reconfigure.");
        this.configurationService.reconfigure();
    }

    @CrossOrigin
    @RequestMapping(value = "/log", method=POST)
    public void log(@RequestParam String logger, @RequestParam String level, @RequestParam String message) throws IllegalArgumentException
    {
        if(logger == null || logger.isEmpty())
        {
            loggerInstance.error("The logger request param cannot be null or empty.");
            throw new IllegalArgumentException("Logger argument is invalid.");
        }

        if(message == null || message.isEmpty())
        {
            loggerInstance.error("The message request param cannot be null or empty.");
            throw new IllegalArgumentException("Message argument is invalid.");
        }

        if(level == null || level.isEmpty())
        {
            loggerInstance.error("The level request param cannot be null or empty.");
            throw new IllegalArgumentException("Level message argument is invalid.");
        }

        loggerInstance.info("Received request to log information.");
        this.loggingService.log(logger, level, message);
    }
}