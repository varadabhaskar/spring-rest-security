package com.sap;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TextAnalysisRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    //@WithMockUser is not working with TestRestTemplate
    @Autowired
    private TestRestTemplate restTemplate;

  
    @Test
    public void find_login_ok() throws Exception {

        String expected = "{No of Words:1}";

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/textAnalysis", String.class);

        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        JSONAssert.assertEquals(expected, response.getBody(), false);
        
    }

    @Test
    public void find_nologin_401() throws Exception {

        String expected = "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Unauthorized\",\"path\":\"/textAnalysis/test\"}";

        ResponseEntity<String> response = restTemplate
                .getForEntity("/textAnalysis/1", String.class);

        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println("JSON-----***"+ result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
