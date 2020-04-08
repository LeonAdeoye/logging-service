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
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private LoggingService loggingService;

    @RequestMapping("/reconfigure")
    public void reconfigure()
    {
        logger.info("Received request to reconfigure.");
        this.configurationService.reconfigure();
    }

    @CrossOrigin
    @RequestMapping(value = "/log", method=POST)
    public void log(@RequestParam String owner, @RequestParam String message) throws IllegalArgumentException
    {
        if(owner == null || owner.isEmpty())
        {
            logger.error("The log owner request param cannot be null or empty.");
            throw new IllegalArgumentException("Log owner argument is invalid");
        }

        if(message == null || message.isEmpty())
        {
            logger.error("The log message request param cannot be null or empty");
            throw new IllegalArgumentException("Log message argument is invalid");
        }

        logger.info("Received request to log information.");
        this.loggingService.log(owner, message);
    }
}