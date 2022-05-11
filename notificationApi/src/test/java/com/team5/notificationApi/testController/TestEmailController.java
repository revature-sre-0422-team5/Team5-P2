package com.team5.notificationApi.testController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.notificationApi.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestEmailController {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailService emailService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Tests whether the controller sends the mail
     * message towards the email service and returns
     * an OK status.
     */
    @Test
    public void shouldReturnOkStatusEmail() throws Exception {
        MimeMessage mockMail = mailSender.createMimeMessage();
        Mockito.when(emailService.buildMimeMessage(Mockito.any())).thenReturn(mockMail);
        Mockito.when(emailService.sendMail(Mockito.any())).thenReturn(mockMail);

        Map<String, String> email = new HashMap<>();
        email.put("recipient", "noreceiver@no-receive-test.com");
        email.put("subject", "Test Email");
        email.put("message", "Test Message");

        mockMvc.perform(MockMvcRequestBuilders.post("/notification/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(email)))
                .andExpect(status().isOk());
    }
}
