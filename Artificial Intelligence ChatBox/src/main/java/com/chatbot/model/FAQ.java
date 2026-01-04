package com.chatbot.model;

import java.util.List;

/**
 * FAQ model for storing question-answer pairs
 */
public class FAQ {
    private String question;
    private String answer;
    private List<String> keywords;
    private int usageCount;
    
    public FAQ() {
        this.usageCount = 0;
    }
    
    public FAQ(String question, String answer, List<String> keywords) {
        this.question = question;
        this.answer = answer;
        this.keywords = keywords;
        this.usageCount = 0;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public List<String> getKeywords() {
        return keywords;
    }
    
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    
    public int getUsageCount() {
        return usageCount;
    }
    
    public void incrementUsageCount() {
        this.usageCount++;
    }
}

