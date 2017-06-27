---
search:
    keywords: ['echo', 'bot', 'telegram', 'simple', 'getting started', '1']

---

# Lesson 1. Writing your first "echo" bot

Hello! If you want to know, how to code Telegram Bots on Java, you are on the right way!

## Prepare to launch

Bot API is based on HTTP-requests, but in this book I will use [Rubenlagus' library for Java](https://github.com/rubenlagus/TelegramBots).

### Install the library

You can install TelegramBots library with different methods:

* Using Maven: 

```xml
<dependency>
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots</artifactId>
        <version>3.0.1</version>
</dependency>
```

* Using [Jitpack](https://jitpack.io/#rubenlagus/TelegramBots/3.0.1)
* Or just download `.jar` file with dependencies from [here](https://github.com/rubenlagus/TelegramBots/releases/latest)

In this tutorial I will use next machines:
* Ubuntu 16.04 Server with 1GB of RAM
* My home Windows 10 laptop with IntelliJ Idea pre-installed

## Lets go to code!
Well, enough for words. Let's get down to buisness. In this lesson we will write simple bot that echoes everything we sent to him. Now, open `IntelliJ Idea` and create a new project. You can call it whatever you want. Then, dont forget to install `TelegramBots` library with preffered method. I think, that it is most easy to just download `.jar` from [here](https://github.com/rubenlagus/TelegramBots/releases/latest)

Now, when you are in the project, create files `MyAmazingBot.java` and `Main.java` within the `src` directory. Open `MyAmazingBot.java` and lets write our actual bot:
> Remember! The class must extends `TelegramLongPollingBot` and implement necessary methods

```java
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        // TODO
    }

    @Override
    public String getBotUsername() {
        // TODO
        return null;
    }

    @Override
    public String getBotToken() {
        // TODO
        return null;
    }
}
```
As you can understand, `getBotUsermane()` and `getBotToken()` must return bot's username and bot's token, obtained from [@BotFather](https://telegram.me/botfather). So now, our `MyAmazingBot.java` file will look like this:
```java
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        // TODO
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "MyAmazingBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "12345:qwertyuiopASDGFHKMK";
    }
}
```
Now, let's move on to the logic of our bot. As I said before, we want him to reply every text we send to him. `onUpdateReceived(Update update)` method is for us. When an update recieved, it will call this method.
```java
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
```
Good! But how do I run the bot? Well, its a good question.
Lets save that file and open `Main.java`. This file will instantiate TelegramBotsApi and register our new bot. It will look like this:
```java
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
public class Main {
    public static void main(String[] args) {

        // TODO Initialize Api Context

        // TODO Instantiate Telegram Bots API

        // TODO Register our bot
    }
}
```
Now, lets initialize Api Context
```java
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
public class Main {
    public static void main(String[] args) {
        // Initialize Api Context
        ApiContextInitializer.init();

        // TODO Instantiate Telegram Bots API

        // TODO Register our bot
    }
}
   ```
Instantiate Telegram Bots API:
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

        // TODO Register our bot
    }
}
  ```
And register our bot:
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
            botsApi.registerBot(new MyAmazingBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
  ```
Here is all our files:
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
            botsApi.registerBot(new MyAmazingBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
  ```
  
 > `src/MyAmazingBot.java`
 
 ```java
 import org.telegram.telegrambots.api.objects.Update;
 import org.telegram.telegrambots.bots.TelegramLongPollingBot;
 public class MyAmazingBot extends TelegramLongPollingBot {
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
        return "MyAmazingBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "12345:qwertyuiopASDGFHKMK";
    }
}

```

Well done! Now we can pack our project into runnable `.jar` file and run it on our computer/server!

You can find all sources to this lesson in [GitHub repository](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/).


```
java -jar MyAmazingBot.jar
```

Now we can see our bot running:

![Bot is running](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/Bot_Reply.jpg "Here it is!")

Well, thats all for now. Hope to see you soon!:)
  
