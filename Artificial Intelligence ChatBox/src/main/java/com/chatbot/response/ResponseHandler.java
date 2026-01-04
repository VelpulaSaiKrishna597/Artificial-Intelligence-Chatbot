package com.chatbot.response;

import com.chatbot.model.FAQ;
import com.chatbot.nlp.NLPProcessor;
import com.chatbot.training.FAQTrainer;
import java.util.*;

/**
 * Handles response generation using rule-based and ML-based approaches
 */
public class ResponseHandler {
    
    private final NLPProcessor nlpProcessor;
    private final FAQTrainer faqTrainer;
    private final Map<String, List<String>> ruleBasedResponses;
    
    public ResponseHandler(FAQTrainer faqTrainer) {
        this.nlpProcessor = new NLPProcessor();
        this.faqTrainer = faqTrainer;
        this.ruleBasedResponses = initializeRuleBasedResponses();
    }
    
    /**
     * Generate response for user input
     */
    public String generateResponse(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return "I'm here to help! Please ask me something.";
        }
        
        // First, try ML-based FAQ matching
        FAQ matchedFAQ = faqTrainer.findBestMatch(userInput);
        if (matchedFAQ != null) {
            return matchedFAQ.getAnswer();
        }
        
        // Then, try rule-based responses
        String intent = nlpProcessor.detectIntent(userInput);
        String ruleBasedResponse = getRuleBasedResponse(intent, userInput);
        if (ruleBasedResponse != null) {
            return ruleBasedResponse;
        }
        
        // Default response
        return generateDefaultResponse(userInput);
    }
    
    /**
     * Get rule-based response based on intent
     */
    private String getRuleBasedResponse(String intent, String userInput) {
        List<String> responses = ruleBasedResponses.get(intent);
        if (responses != null && !responses.isEmpty()) {
            Random random = new Random();
            return responses.get(random.nextInt(responses.size()));
        }
        return null;
    }
    
    /**
     * Generate default response when no match is found
     */
    private String generateDefaultResponse(String userInput) {
        List<String> defaultResponses = Arrays.asList(
            "That's an interesting question. I'm still learning, so I might not have the perfect answer yet.",
            "I understand you're asking about: \"" + userInput + "\". Could you rephrase that or provide more context?",
            "I'm not entirely sure about that. Could you try asking in a different way?",
            "That's a good question! I'm learning new things every day. Can you help me understand better?",
            "I'm processing that... Could you provide more details or try rephrasing your question?"
        );
        
        Random random = new Random();
        return defaultResponses.get(random.nextInt(defaultResponses.size()));
    }
    
    /**
     * Initialize rule-based response patterns
     */
    private Map<String, List<String>> initializeRuleBasedResponses() {
        Map<String, List<String>> responses = new HashMap<>();
        
        responses.put("GREETING", Arrays.asList(
            "Hello! How can I assist you today?",
            "Hi there! What would you like to know?",
            "Greetings! I'm here to help. What's on your mind?",
            "Hey! How can I be of service?"
        ));
        
        responses.put("FAREWELL", Arrays.asList(
            "Goodbye! Have a great day!",
            "See you later! Feel free to come back anytime.",
            "Farewell! It was nice chatting with you.",
            "Bye! Take care!"
        ));
        
        responses.put("HELP", Arrays.asList(
            "I'm here to help! You can ask me questions, and I'll do my best to answer them.",
            "I can answer questions, have conversations, and learn from FAQs you provide.",
            "Feel free to ask me anything! You can also train me by adding new FAQs.",
            "I'm an AI chatbot. Ask me questions, and I'll respond using my knowledge base."
        ));
        
        responses.put("THANKS", Arrays.asList(
            "You're welcome! Happy to help.",
            "My pleasure! Is there anything else you'd like to know?",
            "Glad I could assist! Feel free to ask more questions.",
            "You're very welcome! Let me know if you need anything else."
        ));
        
        responses.put("QUESTION", Arrays.asList(
            "That's a good question. Let me think about that...",
            "I'm processing your question...",
            "Interesting question! Let me see what I can find."
        ));
        
        return responses;
    }
    
    /**
     * Train the bot with a new FAQ
     */
    public void trainBot(String question, String answer) {
        faqTrainer.trainFAQ(question, answer);
    }
}

