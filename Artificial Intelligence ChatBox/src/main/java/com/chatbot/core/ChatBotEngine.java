package com.chatbot.core;

import com.chatbot.response.ResponseHandler;
import com.chatbot.storage.FAQStorage;
import com.chatbot.training.FAQTrainer;
import java.util.ArrayList;
import java.util.List;

/**
 * Main chatbot engine that coordinates all components
 */
public class ChatBotEngine {
    
    private final ResponseHandler responseHandler;
    private final FAQTrainer faqTrainer;
    private final List<String> conversationHistory;
    
    public ChatBotEngine() {
        FAQStorage storage = new FAQStorage();
        this.faqTrainer = new FAQTrainer(storage);
        this.responseHandler = new ResponseHandler(faqTrainer);
        this.conversationHistory = new ArrayList<>();
        
        // Initialize with default FAQs
        faqTrainer.initializeDefaultFAQs();
    }
    
    /**
     * Process user input and generate response
     */
    public String processInput(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return "Please enter a message.";
        }
        
        // Add to conversation history
        conversationHistory.add("User: " + userInput);
        
        // Generate response
        String response = responseHandler.generateResponse(userInput);
        
        // Add response to history
        conversationHistory.add("Bot: " + response);
        
        return response;
    }
    
    /**
     * Train the bot with a new FAQ
     */
    public void trainBot(String question, String answer) {
        responseHandler.trainBot(question, answer);
    }
    
    /**
     * Get conversation history
     */
    public List<String> getConversationHistory() {
        return new ArrayList<>(conversationHistory);
    }
    
    /**
     * Clear conversation history
     */
    public void clearHistory() {
        conversationHistory.clear();
    }
    
    /**
     * Get all FAQs
     */
    public List<com.chatbot.model.FAQ> getAllFAQs() {
        return faqTrainer.getAllFAQs();
    }
    
    /**
     * Remove an FAQ
     */
    public void removeFAQ(String question) {
        faqTrainer.removeFAQ(question);
    }
}

