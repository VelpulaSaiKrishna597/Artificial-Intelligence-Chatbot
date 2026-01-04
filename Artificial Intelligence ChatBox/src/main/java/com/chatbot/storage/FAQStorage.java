package com.chatbot.storage;

import com.chatbot.model.FAQ;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistence of FAQ data
 */
public class FAQStorage {
    
    private static final String FAQ_FILE = "faqs.json";
    private final List<FAQ> faqs;
    private final Gson gson;
    
    public FAQStorage() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.faqs = new ArrayList<>();
        loadFAQs();
    }
    
    /**
     * Load FAQs from JSON file
     */
    private void loadFAQs() {
        File file = new File(FAQ_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<FAQ>>(){}.getType();
            List<FAQ> loaded = gson.fromJson(reader, listType);
            if (loaded != null) {
                faqs.clear();
                faqs.addAll(loaded);
            }
        } catch (IOException e) {
            System.err.println("Error loading FAQs: " + e.getMessage());
        }
    }
    
    /**
     * Save FAQs to JSON file
     */
    public void saveFAQs() {
        try (FileWriter writer = new FileWriter(FAQ_FILE)) {
            gson.toJson(faqs, writer);
        } catch (IOException e) {
            System.err.println("Error saving FAQs: " + e.getMessage());
        }
    }
    
    /**
     * Add a new FAQ
     */
    public void addFAQ(FAQ faq) {
        // Check if FAQ with same question already exists
        faqs.removeIf(f -> f.getQuestion().equalsIgnoreCase(faq.getQuestion()));
        faqs.add(faq);
    }
    
    /**
     * Remove an FAQ by question
     */
    public void removeFAQ(String question) {
        faqs.removeIf(f -> f.getQuestion().equalsIgnoreCase(question));
    }
    
    /**
     * Get all FAQs
     */
    public List<FAQ> getAllFAQs() {
        return new ArrayList<>(faqs);
    }
    
    /**
     * Get FAQ by question
     */
    public FAQ getFAQ(String question) {
        return faqs.stream()
            .filter(f -> f.getQuestion().equalsIgnoreCase(question))
            .findFirst()
            .orElse(null);
    }
}

