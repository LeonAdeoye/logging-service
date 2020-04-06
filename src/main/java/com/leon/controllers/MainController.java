package com.leon.controllers;

import com.leon.services.ConfigurationService;
import com.leon.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class MainController
{
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private ConfigurationService configurationService;
    private LoggingService loggingService;

    @RequestMapping("/reconfigure")
    public void reconfigure()
    {
        logger.info("Received request to reconfigure.");
        this.configurationService.reconfigure();
    }

    @RequestMapping("/log")
    public void log()
    {
        logger.info("Received request to log information.");
        this.loggingService.log();
    }
}