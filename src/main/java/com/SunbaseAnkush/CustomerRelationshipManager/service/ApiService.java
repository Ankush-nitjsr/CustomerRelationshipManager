package com.SunbaseAnkush.CustomerRelationshipManager.service;


import com.SunbaseAnkush.CustomerRelationshipManager.entity.Customer;
import com.SunbaseAnkush.CustomerRelationshipManager.entity.SunbaseToken;
import com.SunbaseAnkush.CustomerRelationshipManager.repository.CustomerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class ApiService {

    private String authToken;

    public String callLoginApi(String apiUrl, String requestBody) {
        // Set up headers
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HttpEntity with the request body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make the API call using POST method
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Get the response body
        String responseBody = responseEntity.getBody();

        // Extract the token from the response
        this.authToken = extractTokenFromResponse(responseBody);

        return responseBody;
    }

    public List<Customer> callGetAllCustomersApi(String cmd, String apiBaseUrl) {
        // Set up headers with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Check if authToken is set
        if (authToken != null) {
            headers.set("Authorization", "Bearer " + authToken);
        } else {
            throw new IllegalStateException("Authentication token is not set. Call login API first.");
        }

        // Construct the API URL with the cmd parameter
        String apiUrl = determineApiUrl(apiBaseUrl, cmd);

        // Create an HttpEntity with the request body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make the other API call using POST method
        ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Customer>>() {}
        );

        // Get the response body
        List<Customer> responseBody = responseEntity.getBody();

        return responseBody;
    }

    private String extractTokenFromResponse(String responseBody) {
        int startIndex = responseBody.indexOf("\"access_token\":\"") + 16;
        int endIndex = responseBody.indexOf("\"", startIndex);
        return responseBody.substring(startIndex, endIndex);
    }

    private String determineApiUrl(String cmd, String otherApiUrl) {
        // Logic to determine the API endpoint based on the command
        // Append the cmd parameter as a query parameter
        return otherApiUrl + "?cmd=" + cmd;
    }
}



