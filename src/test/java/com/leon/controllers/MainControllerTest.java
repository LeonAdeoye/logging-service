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
    public void log() throws Exception
    {
        // Act
        mockMVC.perform(MockMvcRequestBuilders.post("/log")
                .param("owner", "Horatio")
                .param("message", "Hello Papa")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assert
        verify(loggingServiceMock, times(1)).log("Horatio", "Hello Papa");
    }
}