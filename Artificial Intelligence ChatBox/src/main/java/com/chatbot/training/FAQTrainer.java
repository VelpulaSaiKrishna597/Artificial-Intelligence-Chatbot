package com.chatbot.training;

import com.chatbot.model.FAQ;
import com.chatbot.nlp.NLPProcessor;
import com.chatbot.storage.FAQStorage;
import java.util.*;

/**
 * FAQ Trainer for machine learning-based responses
 */
public class FAQTrainer {
    
    private final FAQStorage storage;
    private final NLPProcessor nlpProcessor;
    
    public FAQTrainer(FAQStorage storage) {
        this.storage = storage;
        this.nlpProcessor = new NLPProcessor();
    }
    
    /**
     * Train the bot with a new FAQ
     */
    public void trainFAQ(String question, String answer) {
        List<String> keywords = new ArrayList<>(nlpProcessor.extractKeywords(question));
        FAQ faq = new FAQ(question, answer, keywords);
        storage.addFAQ(faq);
        storage.saveFAQs();
    }
    
    /**
     * Find best matching FAQ using similarity scoring
     */
    public FAQ findBestMatch(String userInput) {
        List<FAQ> faqs = storage.getAllFAQs();
        if (faqs.isEmpty()) {
            return null;
        }
        
        FAQ bestMatch = null;
        double bestScore = 0.0;
        double threshold = 0.3; // Minimum similarity threshold
        
        for (FAQ faq : faqs) {
            // Calculate similarity with the question
            double questionScore = nlpProcessor.calculateSimilarity(userInput, faq.getQuestion());
            
            // Calculate similarity with keywords
            double keywordScore = 0.0;
            if (faq.getKeywords() != null && !faq.getKeywords().isEmpty()) {
                String keywordString = String.join(" ", faq.getKeywords());
                keywordScore = nlpProcessor.calculateSimilarity(userInput, keywordString);
            }
            
            // Combined score (weighted average)
            double combinedScore = (questionScore * 0.7) + (keywordScore * 0.3);
            
            if (combinedScore > bestScore && combinedScore >= threshold) {
                bestScore = combinedScore;
                bestMatch = faq;
            }
        }
        
        if (bestMatch != null) {
            bestMatch.incrementUsageCount();
            storage.saveFAQs();
        }
        
        return bestMatch;
    }
    
    /**
     * Get all FAQs
     */
    public List<FAQ> getAllFAQs() {
        return storage.getAllFAQs();
    }
    
    /**
     * Remove an FAQ
     */
    public void removeFAQ(String question) {
        storage.removeFAQ(question);
        storage.saveFAQs();
    }
    
    /**
     * Initialize with default FAQs
     */
    public void initializeDefaultFAQs() {
        if (storage.getAllFAQs().isEmpty()) {
            trainFAQ("What is your name?", "I am an AI chatbot designed to help you with various questions and tasks.");
            trainFAQ("What can you do?", "I can answer questions, have conversations, learn from FAQs, and assist you with various tasks.");
            trainFAQ("How do you work?", "I use natural language processing and machine learning techniques to understand and respond to your messages.");
            trainFAQ("Can you learn?", "Yes! You can train me by adding new FAQs. I'll learn from them and use them to answer similar questions.");
            trainFAQ("What is artificial intelligence?", "Artificial Intelligence (AI) is the simulation of human intelligence by machines, enabling them to learn, reason, and make decisions.");
            trainFAQ("How are you trained?", "I use a combination of rule-based patterns and machine learning techniques, including similarity matching and keyword extraction.");
        }
    }
}

