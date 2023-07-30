package com.automation.modules;

import com.automation.payload.Login;
import com.automation.payload.Note;
import com.automation.payload.Registeration;
import com.automation.payload.UpdateNote;
import com.automation.utils.RandomStringGenerator;
import com.automation.utils.StoreRegisteredUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;

import java.io.IOException;

public class PayloadManager {

    ObjectMapper objectMapper;
    public StoreRegisteredUserDetails storeRegisteredUserDetails = new StoreRegisteredUserDetails();
    public String createRegisterationPayload() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        Registeration registeration = new Registeration();
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String random = randomStringGenerator.generateRandomString(4);
        registeration.setName("testUser12"+random);
        registeration.setEmail("mukuluser123asf"+random+"@gmail.com");
        registeration.setPassword("abc123");
        String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(registeration);
        storeRegisteredUserDetails.saveStringPayloadToFile("user_details.txt",payload);
        return payload;
    }
    public String getRegisteredEmailPayloadValue(){
        String payload= storeRegisteredUserDetails.readStringPayloadFromFile("user_details.txt");
        try {
            JsonNode rootNode = objectMapper.readTree(payload);
              return rootNode.get("email").asText();
        } catch (IOException e) {
            System.err.println("Error parsing JSON payload: " + e.getMessage());
        }
        return null;
    }
    public String getRegisteredPasswordPayloadValue(){
        String payload= storeRegisteredUserDetails.readStringPayloadFromFile("user_details.txt");
        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            return rootNode.get("password").asText();
        } catch (IOException e) {
            System.err.println("Error parsing JSON payload: " + e.getMessage());
        }
        return null;
    }

    public String createLoginPayload() throws JsonProcessingException{
        objectMapper = new ObjectMapper();
        Login login = new Login();
        login.setEmail(this.getRegisteredEmailPayloadValue());
        login.setPassword(this.getRegisteredPasswordPayloadValue());
        String paylaod = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(login);
        return paylaod;
    }

    public String createNote() throws JsonProcessingException{
        objectMapper = new ObjectMapper();
        Note note = new Note();
        note.setTitle("Homework");
        note.setDescription("Do Homework");
        note.setCategory("Home");
        String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(note);
        return payload;
    }

    public String updateNote(String id,String title) throws JsonProcessingException{
        objectMapper = new ObjectMapper();
        UpdateNote updateNote = new UpdateNote();
        updateNote.setId(id);
        updateNote.setTitle(title);
        updateNote.setCategory("Work");
        updateNote.setDescription("updated ");
        updateNote.setCompleted(true);
        String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateNote);
        return payload;
    }
}
