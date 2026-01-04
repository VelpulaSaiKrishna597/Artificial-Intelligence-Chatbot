# AI Chatbot - Java-based Interactive Communication System

A sophisticated Java-based chatbot application featuring Natural Language Processing (NLP), machine learning capabilities, and an intuitive GUI interface for real-time interactions.

## Features

### 🤖 Core Capabilities
- **Natural Language Processing (NLP)**: Advanced text analysis including tokenization, keyword extraction, and similarity matching
- **Hybrid Response System**: Combines rule-based patterns with machine learning-based FAQ matching
- **Intent Detection**: Automatically identifies user intents (greetings, questions, farewells, etc.)
- **FAQ Training System**: Train the bot with custom question-answer pairs that improve over time
- **Similarity Matching**: Uses Jaccard similarity algorithm to find best matching responses
- **Data Persistence**: FAQs are saved to JSON format for persistence across sessions

### 💬 Interactive Features
- **Real-time Chat Interface**: Modern JavaFX GUI with chat bubbles and smooth scrolling
- **FAQ Management**: Add, view, and delete FAQs through an intuitive interface
- **Training Panel**: Easy-to-use interface for teaching the bot new responses
- **Conversation History**: Maintains context throughout the conversation

## Technology Stack

- **Java 17**: Core programming language
- **JavaFX 17**: Modern GUI framework
- **Apache OpenNLP**: Natural Language Processing library
- **Gson**: JSON processing for data persistence
- **Maven**: Dependency management and build tool

## Project Structure

```
src/
├── main/
│   ├── java/com/chatbot/
│   │   ├── core/
│   │   │   └── ChatBotEngine.java          # Main chatbot engine
│   │   ├── nlp/
│   │   │   └── NLPProcessor.java           # NLP processing logic
│   │   ├── response/
│   │   │   └── ResponseHandler.java        # Response generation
│   │   ├── training/
│   │   │   └── FAQTrainer.java             # FAQ training system
│   │   ├── storage/
│   │   │   └── FAQStorage.java             # Data persistence
│   │   ├── model/
│   │   │   └── FAQ.java                    # FAQ data model
│   │   ├── ui/
│   │   │   └── ChatBotController.java      # GUI controller
│   │   └── ChatBotApplication.java         # Main application
│   └── resources/com/chatbot/ui/
│       └── chatbot.fxml                    # GUI layout
```

## Prerequisites

- **Java Development Kit (JDK) 17** or higher
- **Maven 3.6+** (for building and dependency management)
- **JavaFX SDK 17** (included via Maven dependencies)

## Installation & Setup

### 1. Clone or Download the Project

```bash
cd "C:\Cursor\Artificial Intelligence ChatBox"
```

### 2. Build the Project

Using Maven:

```bash
mvn clean compile
```

### 3. Run the Application

**Option 1: Using Maven**
```bash
mvn javafx:run
```

**Option 2: Using Java directly**
```bash
# First, compile
mvn clean package

# Then run
java --module-path <path-to-javafx-sdk>/lib --add-modules javafx.controls,javafx.fxml -cp target/ai-chatbot-1.0.0.jar com.chatbot.ChatBotApplication
```

**Option 3: Using IDE**
- Import the project as a Maven project in your IDE (IntelliJ IDEA, Eclipse, etc.)
- Run the `ChatBotApplication` class

## Usage Guide

### Starting a Conversation

1. Launch the application
2. The chatbot will greet you automatically
3. Type your message in the input field and press Enter or click "Send"
4. The bot will respond using its knowledge base

### Training the Bot

1. Navigate to the **"Train Bot"** tab
2. Enter a question in the "Question" field
3. Enter the corresponding answer in the "Answer" field
4. Click **"Train Bot"** to save the FAQ
5. The bot will now use this information to answer similar questions

### Managing FAQs

1. Go to the **"Manage FAQs"** tab
2. View all existing FAQs in the list
3. Select an FAQ and click **"Delete Selected FAQ"** to remove it

### Example Training Scenarios

**Training Example 1:**
- Question: "What is machine learning?"
- Answer: "Machine learning is a subset of artificial intelligence that enables systems to learn and improve from experience without being explicitly programmed."

**Training Example 2:**
- Question: "How do you work?"
- Answer: "I use natural language processing to understand your questions and match them with the best responses from my knowledge base."

## How It Works

### NLP Processing

The `NLPProcessor` class handles:
- **Tokenization**: Breaks down text into individual words
- **Stop Word Removal**: Filters out common words that don't add meaning
- **Keyword Extraction**: Identifies important terms
- **Similarity Calculation**: Uses Jaccard similarity to compare texts
- **Intent Detection**: Recognizes user intentions (greeting, question, etc.)

### Response Generation

The system uses a two-tier approach:

1. **Machine Learning Layer**: 
   - Matches user input against trained FAQs
   - Uses similarity scoring (threshold: 0.3)
   - Considers both question text and keywords
   - Tracks usage statistics

2. **Rule-Based Layer**:
   - Handles common patterns (greetings, farewells, thanks)
   - Provides predefined responses for specific intents
   - Acts as fallback when ML matching fails

### FAQ Training

- FAQs are stored in `faqs.json` file
- Each FAQ includes:
  - Question text
  - Answer text
  - Extracted keywords
  - Usage count (for analytics)
- The system automatically extracts keywords from questions
- Similarity matching improves as more FAQs are added

## Customization

### Adjusting Similarity Threshold

Edit `FAQTrainer.java`:
```java
double threshold = 0.3; // Change this value (0.0 to 1.0)
```

### Adding Rule-Based Responses

Edit `ResponseHandler.java` in the `initializeRuleBasedResponses()` method to add new intent patterns.

### Modifying UI

Edit `chatbot.fxml` to customize the GUI layout and styling.

## Troubleshooting

### Issue: "Module not found" error
**Solution**: Ensure JavaFX modules are properly configured. Use Maven to handle dependencies automatically.

### Issue: FAQs not persisting
**Solution**: Check that the application has write permissions in the project directory. The `faqs.json` file should be created automatically.

### Issue: GUI not displaying
**Solution**: Ensure JavaFX is properly installed and configured. Try running with `mvn javafx:run`.

## Future Enhancements

Potential improvements:
- Integration with external NLP APIs (OpenAI, Google Cloud NLP)
- Sentiment analysis
- Multi-language support
- Voice input/output
- Web-based interface (Spring Boot + React)
- Advanced ML models (neural networks, transformers)
- Context-aware conversations
- User authentication and personalized responses

## License

This project is open source and available for educational and commercial use.

## Contributing

Feel free to fork, modify, and enhance this chatbot. Contributions are welcome!

## Support

For issues or questions, please check the code comments or create an issue in the repository.

---

**Enjoy chatting with your AI assistant!** 🤖💬
