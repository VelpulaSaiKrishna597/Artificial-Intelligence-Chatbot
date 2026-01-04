package com.chatbot.ui;

import com.chatbot.core.ChatBotEngine;
import com.chatbot.model.FAQ;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the ChatBot GUI
 */
public class ChatBotController implements Initializable {
    
    @FXML
    private ScrollPane chatScrollPane;
    
    @FXML
    private VBox chatContainer;
    
    @FXML
    private TextField userInputField;
    
    @FXML
    private Button sendButton;
    
    @FXML
    private Button clearButton;
    
    @FXML
    private TextField trainQuestionField;
    
    @FXML
    private TextArea trainAnswerArea;
    
    @FXML
    private Button trainButton;
    
    @FXML
    private ListView<String> faqListView;
    
    @FXML
    private Button deleteFAQButton;
    
    @FXML
    private TabPane mainTabPane;
    
    private ChatBotEngine chatBot;
    private ObservableList<String> faqList;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatBot = new ChatBotEngine();
        faqList = FXCollections.observableArrayList();
        faqListView.setItems(faqList);
        
        // Initialize chat container
        chatContainer.setSpacing(10);
        chatContainer.setPadding(new Insets(10));
        chatContainer.setStyle("-fx-background-color: #f5f5f5;");
        
        // Configure scroll pane
        chatScrollPane.setFitToWidth(true);
        chatScrollPane.setVvalue(1.0);
        
        // Set up event handlers
        sendButton.setOnAction(e -> sendMessage());
        clearButton.setOnAction(e -> clearChat());
        trainButton.setOnAction(e -> trainBot());
        deleteFAQButton.setOnAction(e -> deleteFAQ());
        
        // Allow Enter key to send message
        userInputField.setOnAction(e -> sendMessage());
        
        // Load FAQs
        refreshFAQList();
        
        // Add welcome message
        addBotMessage("Hello! I'm your AI chatbot. How can I help you today?");
    }
    
    private void sendMessage() {
        String userInput = userInputField.getText().trim();
        if (userInput.isEmpty()) {
            return;
        }
        
        // Add user message
        addUserMessage(userInput);
        userInputField.clear();
        
        // Process and get response
        String response = chatBot.processInput(userInput);
        
        // Add bot response
        addBotMessage(response);
    }
    
    private void addUserMessage(String message) {
        HBox messageBox = new HBox();
        messageBox.setAlignment(Pos.CENTER_RIGHT);
        messageBox.setPadding(new Insets(5, 10, 5, 50));
        
        TextFlow textFlow = new TextFlow();
        Text text = new Text(message);
        text.setFont(Font.font("Arial", 14));
        text.setFill(Color.WHITE);
        textFlow.getChildren().add(text);
        textFlow.setPadding(new Insets(10, 15, 10, 15));
        textFlow.setStyle("-fx-background-color: #007bff; -fx-background-radius: 15;");
        
        messageBox.getChildren().add(textFlow);
        chatContainer.getChildren().add(messageBox);
        
        scrollToBottom();
    }
    
    private void addBotMessage(String message) {
        HBox messageBox = new HBox();
        messageBox.setAlignment(Pos.CENTER_LEFT);
        messageBox.setPadding(new Insets(5, 50, 5, 10));
        
        TextFlow textFlow = new TextFlow();
        Text text = new Text(message);
        text.setFont(Font.font("Arial", 14));
        text.setFill(Color.BLACK);
        textFlow.getChildren().add(text);
        textFlow.setPadding(new Insets(10, 15, 10, 15));
        textFlow.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-border-color: #e0e0e0; -fx-border-radius: 15;");
        
        messageBox.getChildren().add(textFlow);
        chatContainer.getChildren().add(messageBox);
        
        scrollToBottom();
    }
    
    private void scrollToBottom() {
        Platform.runLater(() -> {
            chatScrollPane.setVvalue(1.0);
        });
    }
    
    private void clearChat() {
        chatContainer.getChildren().clear();
        chatBot.clearHistory();
        addBotMessage("Chat cleared. How can I help you?");
    }
    
    private void trainBot() {
        String question = trainQuestionField.getText().trim();
        String answer = trainAnswerArea.getText().trim();
        
        if (question.isEmpty() || answer.isEmpty()) {
            showAlert("Training Error", "Please provide both a question and an answer.");
            return;
        }
        
        chatBot.trainBot(question, answer);
        trainQuestionField.clear();
        trainAnswerArea.clear();
        
        refreshFAQList();
        showAlert("Success", "FAQ added successfully!");
    }
    
    private void deleteFAQ() {
        String selected = faqListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Selection Error", "Please select an FAQ to delete.");
            return;
        }
        
        // Extract question from list item
        String question = selected.split(" -> ")[0];
        chatBot.removeFAQ(question);
        refreshFAQList();
        showAlert("Success", "FAQ deleted successfully!");
    }
    
    private void refreshFAQList() {
        faqList.clear();
        List<FAQ> faqs = chatBot.getAllFAQs();
        for (FAQ faq : faqs) {
            String display = faq.getQuestion() + " -> " + 
                (faq.getAnswer().length() > 50 ? 
                    faq.getAnswer().substring(0, 50) + "..." : 
                    faq.getAnswer());
            faqList.add(display);
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

