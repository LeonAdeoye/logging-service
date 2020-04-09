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
    public void log_whenPassedValidRequestParams_shouldCallLoggingServiceLogMethod() throws Exception
    {
        // Act
        mockMVC.perform(MockMvcRequestBuilders.post("/log")
                .param("logger", "Horatio")
                .param("level", "DEBUG")
                .param("message", "Hello Papa")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assert
        verify(loggingServiceMock, times(1)).log("Horatio", "DEBUG", "Hello Papa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void log_whenPassedInvalidLoggerRequestParam_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .param("logger", "")
                    .param("level", "DEBUG")
                    .param("message", "Hello Papa"));
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
    public void log_whenPassedInvalidMessageRequestParam_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .param("logger", "Horatio")
                    .param("level", "DEBUG")
                    .param("message", ""));
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
    public void log_whenPassedInvalidLevelRequestParam_shouldNotCallLoggingServiceLogMethodAndShouldThrowException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(MockMvcRequestBuilders.post("/log")
                    .param("logger", "Horatio")
                    .param("level", "")
                    .param("message", "Hello Papa"));
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
}