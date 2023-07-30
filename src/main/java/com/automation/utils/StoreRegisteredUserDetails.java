package com.automation.utils;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreRegisteredUserDetails {

    private ObjectMapper objectMapper;

    public void StringPayloadHandler() {
        this.objectMapper = new ObjectMapper();
    }

    // Method to save the string payload to a file
    public void saveStringPayloadToFile(String fileName, String payload) {
        try {
            Files.write(Paths.get(fileName), payload.getBytes(StandardCharsets.UTF_8));
            System.out.println("String payload saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving string payload to file: " + e.getMessage());
        }
    }

    // Method to read the string payload from the file and use it
    public String readStringPayloadFromFile(String fileName) {
        try {
            byte[] encodedBytes = Files.readAllBytes(Paths.get(fileName));
            String payload = new String(encodedBytes, StandardCharsets.UTF_8);
            if (!payload.isEmpty()) {
                // Use the payload as needed
                System.out.println("String Payload: " + payload);
                return payload;
            } else {
                System.out.println("String payload is empty or invalid.");
            }
        } catch (IOException e) {
            System.err.println("Error reading string payload from file: " + e.getMessage());
        }
        return null;
    }

    // Method to extract the email from the JSON payload string
    public String getEmailFromPayload(String payload) {
        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            return rootNode.get("email").asText();
        } catch (IOException e) {
            System.err.println("Error parsing JSON payload: " + e.getMessage());
        }
        return null;
    }
}