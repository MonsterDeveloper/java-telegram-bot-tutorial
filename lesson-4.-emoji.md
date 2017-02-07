# Lesson 4. Emoji

Welcome back! Now your know, how to log messages from users. But how to make bot's messages more user-friendly and beautiful? The answer is - [emoji](https://en.wikipedia.org/wiki/Emoji). I think you know what is emoji, so let's move forward. 

Now, open `IntelliJ Idea` and create a new project. Create files `Main.java` and `EmojiTestBot.java` within the `src` directory. Here is first look of our files:

> `src/Main.java`

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
            botsApi.registerBot(new EmojiTestBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("EmojiTestBot successfully started!");
    }
}
```

> `src/EmojiTestBot.java`

```java
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmojiTestBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
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
        return "EmojiTestBot";
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

Okey. Now lets install [emoji library](https://github.com/vdurmont/emoji-java):

* Via `Maven`:
```xml
<dependency>
  <groupId>com.vdurmont</groupId>
  <artifactId>emoji-java</artifactId>
  <version>3.1.3</version>
</dependency>
```

* Via `Gradle`:

```
compile 'com.vdurmont:emoji-java:3.1.3'
```

* Or just download `.jar` from [here](https://github.com/vdurmont/emoji-java/releases/download/v3.1.3/emoji-java-3.1.3.jar)

Once library is installed, import it to your bot class:

```java
import com.vdurmont.emoji.EmojiParser;
```

Now you can view list of emojis at [EmojiPedia](http://emojipedia.org/) or [Emoji Cheat Sheet](webpagefx.com/tools/emoji-cheat-sheet/).
To insert emoji, do this:

```java
String answer = EmojiParser.parseToUnicode("Here is a smile emoji: :smile:\n\n Here is alien emoji: :alien:");
```

Where `:smile:` or `:alien:` is emoji alias or emoji short code. You can also view them at EmojiPedia or Emoji Cheat Sheet. 

Here is source code. You can also find it on [GitHub](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial).

> `src/Main.java`

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
            botsApi.registerBot(new EmojiTestBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("EmojiTestBot successfully started!");
    }
}
```

> `src/EmojiTestBot.java`

```java
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmojiTestBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            long user_id = update.getMessage().getChat().getId();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String answer = EmojiParser.parseToUnicode("Here is a smile emoji: :smile:\n\n Here is alien emoji: :alien:");
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
        return "EmojiTestBot";
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

Now you can see our beautiful messages:

![Bot sending emoji](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/Bot_emoji.png "Bot sends messages with emoji")

Our lesson came to an end. Thank you for reading this. See you soon!
