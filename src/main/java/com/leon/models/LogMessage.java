package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("LogMessage")
public class LogMessage
{
    private String logger;
    private String level;
    private String message;

    public LogMessage(String logger, String level, String message)
    {
        this.logger = logger;
        this.level = level;
        this.message = message;
    }

    public LogMessage()
    {
        this.logger = "";
        this.level = "";
        this.message = "";
    }

    public String getLogger()
    {
        return logger;
    }

    public void setLogger(String logger)
    {
        this.logger = logger;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogMessage that = (LogMessage) o;
        return Objects.equals(getLogger(), that.getLogger()) && Objects.equals(getLevel(), that.getLevel()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getLogger(), getLevel(), getMessage());
    }

    @Override
    public String toString()
    {
        return "LogMessage{" + "logger='" + logger + '\'' + ", level='" + level + '\'' + ", message='" + message + '\'' + '}';
    }
}