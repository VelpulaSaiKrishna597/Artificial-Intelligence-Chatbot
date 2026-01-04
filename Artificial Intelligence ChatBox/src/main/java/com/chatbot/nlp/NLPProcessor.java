package com.chatbot.nlp;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Natural Language Processing processor for text analysis
 */
public class NLPProcessor {
    
    private static final Set<String> STOP_WORDS = Set.of(
        "a", "an", "the", "is", "are", "was", "were", "be", "been", "being",
        "have", "has", "had", "do", "does", "did", "will", "would", "should",
        "could", "may", "might", "must", "can", "this", "that", "these", "those",
        "i", "you", "he", "she", "it", "we", "they", "me", "him", "her", "us", "them"
    );
    
    /**
     * Tokenize and normalize text
     */
    public List<String> tokenize(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // Convert to lowercase and remove special characters
        String normalized = text.toLowerCase()
            .replaceAll("[^a-z0-9\\s]", " ")
            .trim();
        
        // Split by whitespace and filter out empty strings and stop words
        return Arrays.stream(normalized.split("\\s+"))
            .filter(word -> !word.isEmpty() && !STOP_WORDS.contains(word))
            .collect(Collectors.toList());
    }
    
    /**
     * Extract keywords from text
     */
    public Set<String> extractKeywords(String text) {
        return new HashSet<>(tokenize(text));
    }
    
    /**
     * Calculate similarity between two texts using Jaccard similarity
     */
    public double calculateSimilarity(String text1, String text2) {
        Set<String> tokens1 = extractKeywords(text1);
        Set<String> tokens2 = extractKeywords(text2);
        
        if (tokens1.isEmpty() && tokens2.isEmpty()) {
            return 1.0;
        }
        if (tokens1.isEmpty() || tokens2.isEmpty()) {
            return 0.0;
        }
        
        Set<String> intersection = new HashSet<>(tokens1);
        intersection.retainAll(tokens2);
        
        Set<String> union = new HashSet<>(tokens1);
        union.addAll(tokens2);
        
        return (double) intersection.size() / union.size();
    }
    
    /**
     * Detect intent from user input
     */
    public String detectIntent(String input) {
        String lowerInput = input.toLowerCase();
        
        // Greeting patterns
        if (Pattern.compile("\\b(hi|hello|hey|greetings|good morning|good afternoon|good evening)\\b")
            .matcher(lowerInput).find()) {
            return "GREETING";
        }
        
        // Question patterns
        if (Pattern.compile("\\b(what|who|where|when|why|how|which|can|could|would|should)\\b")
            .matcher(lowerInput).find()) {
            return "QUESTION";
        }
        
        // Farewell patterns
        if (Pattern.compile("\\b(bye|goodbye|see you|farewell|exit|quit)\\b")
            .matcher(lowerInput).find()) {
            return "FAREWELL";
        }
        
        // Help patterns
        if (Pattern.compile("\\b(help|assist|support|guide)\\b")
            .matcher(lowerInput).find()) {
            return "HELP";
        }
        
        // Thank you patterns
        if (Pattern.compile("\\b(thank|thanks|appreciate|grateful)\\b")
            .matcher(lowerInput).find()) {
            return "THANKS";
        }
        
        return "GENERAL";
    }
    
    /**
     * Find best matching keywords from a list of patterns
     */
    public String findBestMatch(String input, List<String> patterns) {
        String bestMatch = null;
        double bestScore = 0.0;
        
        for (String pattern : patterns) {
            double score = calculateSimilarity(input, pattern);
            if (score > bestScore) {
                bestScore = score;
                bestMatch = pattern;
            }
        }
        
        return bestMatch;
    }
    
    /**
     * Calculate word frequency in text
     */
    public Map<String, Integer> calculateWordFrequency(String text) {
        List<String> tokens = tokenize(text);
        Map<String, Integer> frequency = new HashMap<>();
        
        for (String token : tokens) {
            frequency.put(token, frequency.getOrDefault(token, 0) + 1);
        }
        
        return frequency;
    }
}

