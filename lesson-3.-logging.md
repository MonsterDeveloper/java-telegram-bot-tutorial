# Lesson 3. Logging.

Good afternoon everyone! Did you look into console? Kinda empty ya? Now, we want to see something, isn't it? Let's make a logging function!

## Creating project
As always, open `IntelliJ Idea` and create new project. Within the `src` folder create 2 files: `Main.java` and `LoggingTestBot.java`. Let's create a `body` of our bot:

> src/LoggingTestBot.java

```java
public class LoggingTestBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(message_text);
            try {
                sendMessage(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "LoggingTestBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "12345:qwertyuiopASDGFHKMK";
    }
}
```

And our startup file:

> src/Main.java

```java
public class Main {
    public static void main(String[] args) {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new LoggingTestBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("LoggingTestBot successfully started!");
    }
}
```

## Logs, where are you?

Lets set additional variables for logging:

```java
public void onUpdateReceived(Update update) {
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
        // Set variables
        String user_first_name = update.getMessage().getChat().getFirstName();
        String user_last_name = update.getMessage().getChat().getLastName();
        String user_username = update.getMessage().getChat().getUserName();
        long user_id = update.getMessage().getChat().getId();
        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();

        SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(message_text);
        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```

Create `logging` function:

> Dont forget to import:
```java
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
```

Add new `private` function:

```java
private void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }
```

Now we need just call this function when we want to log 

```java
public void onUpdateReceived(Update update) {
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
        // Set variables
        String user_first_name = update.getMessage().getChat().getFirstName();
        String user_last_name = update.getMessage().getChat().getLastName();
        String user_username = update.getMessage().getChat().getUserName();
        long user_id = update.getMessage().getChat().getId();
        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();
        String answer = message_text;
        SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(answer);
        log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);
        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```

Our files:

> src/Main.java

```java
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new LoggingTestBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("LoggingTestBot successfully started!");
    }
}
```

> src/LoggingTestBot.java

```java
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingTestBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String answer = message_text;
            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(answer);
            log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);
            try {
                sendMessage(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "LoggingTestBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "12345:qwertyuiopASDGFHKMK";
    }
    private void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }
}
```

Now it will do ~~ugly~~ log for us:)

![Beautiful Logs 1](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/Bot_Logging_1.png "Beautiful Logging 1")

![Beautiful Logs 2](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/Bot_Logging_2.png "Beautiful Logging 2")

Well, thats all for now. In the next lesson we will learn about [webhooks](https://core.telegram.org/bots/api#setwebhook).
