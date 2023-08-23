package com.leon.controllers;

import com.leon.services.LoggingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest
{
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private LoggingService loggingServiceMock;

    @Test
    public void log_whenPassedValidBody_shouldCallLoggingServiceLogMethod() throws Exception
    {
        // Arrange
        String body = "{\"logger\":\"Horatio\",\"level\":\"DEBUG\",\"message\":\"Hello Papa\"}";

        // Act
        mockMVC.perform(MockMvcRequestBuilders.post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());

        // Assert
        verify(loggingServiceMock, times(1)).log("Horatio", "DEBUG", "Hello Papa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void log_whenPassedInvalidLoggerInBody_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Arrange
            String body = "{\"logger\":\"\",\"level\":\"DEBUG\",\"message\":\"Hello Papa\"}";

            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(loggingServiceMock, never()).log("", "DEBUG", "Hello Papa");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void log_whenPassedInvalidMessageInBody_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Arrange
            String body = "{\"logger\":\"Horatio\",\"level\":\"DEBUG\",\"message\":\"\"}";

            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(loggingServiceMock, never()).log("Horatio", "DEBUG", "");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void log_whenPassedInvalidLevelInBody_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Arrange
            String body = "{\"logger\":\"Horatio\",\"level\":\"\",\"message\":\"Hello Papa\"}";

            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(loggingServiceMock, never()).log("Horatio", "", "Hello Papa");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void log_whenPassedNullLevelInBody_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Arrange
            String body = "{\"logger\":\"Horatio\",\"level\":null,\"message\":\"Hello Papa\"}";

            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(loggingServiceMock, never()).log("Horatio", null, "Hello Papa");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void log_whenPassedNullLoggerInBody_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Arrange
            String body = "{\"logger\":null,\"level\":\"DEBUG\",\"message\":\"Hello Papa\"}";

            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(loggingServiceMock, never()).log(null, "DEBUG", "Hello Papa");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void log_whenPassedNullMessageInBody_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Arrange
            String body = "{\"logger\":\"Horatio\",\"level\":\"DEBUG\",\"message\":null}";

            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(loggingServiceMock, never()).log("Horatio", "DEBUG", null);
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }
}