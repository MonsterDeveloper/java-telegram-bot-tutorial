# Lesson 2. Photo bot.

Our today's mission - create a "photo" bot, that will send user a photo. It is just an example so there there will be no photos from online, no group chat support. Just local pics. But there is a good thing: we will learn how to create [custom keyboards](https://core.telegram.org/bots/api#replykeyboardmarkup), how to send [photos](https://core.telegram.org/bots/api#photosize) and create commands.

## Let's respect Telegram's servers

Okey, for a start, let's prepare our pictures. Lets download 5 ~~completely unknown~~ photos. Just look: we will send same files to users many many times, so lets coast our traffic and disk space on Telegram Servers. It is amazing that we can upload our files at their server once and then just send files (photos, audio files, documents, voice messages and [etc.](https://core.telegram.org/bots/api#available-types)) by their unique `file_id`.
Okey then. Now lets know photo's `file_id` when we will send it to our bot. As always, create a new project in `IntelliJ Idea` and create two files within the `src` directory: `Main.java` and `PhotoBot.java`. Open up first file and type next:

> Dont forget to install [TelegramBots library](https://github.com/rubenlagus/TelegramBots)

```java
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new PhotoBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("PhotoBot successfully started!");
    }
}
```

This code will register our bot print "PhotoBot successfully started!" when it is successfully started. Then, save it and open up `PhotoBot.java`. Paste the following code from previous lesson:

> Dont forget to change `bot username` and `bot token` if you created another bot.

```java
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class PhotoBot extends TelegramLongPollingBot {
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
        return "PhotoBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "12345:qwertyuiopASDGFHKMK";
    }
}
```

Now lets update our `onUpdateReceived` method. We want to send `file_id` of the picture we send to bot. Lets check if message contains photo object:

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
    } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
        // Message contains photo
    }
}
```

We want our bot to send `file_id` of the photo. Well, lets do this:

```java
else if (update.hasMessage() && update.getMessage().hasPhoto()) {
    // Message contains photo
    // Set variables
    long chat_id = update.getMessage().getChatId();
    
    // Array with photo objects with different sizes
    // We will get the biggest photo from that array
    List<PhotoSize> photos = update.getMessage().getPhoto();
    // Know file_id
    String f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();
    // Know photo width
    int f_width = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();
    // Know photo height
    int f_height = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();
    // Set photo caption
    String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
    SendPhoto msg = new SendPhoto()
                    .setChatId(chat_id)
                    .setPhoto(f_id)
                    .setCaption(caption);
    try {
        sendPhoto(msg); // Call method to send the photo with caption
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}
```

Lets take a look:

![Photo's file_id](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/Bot_sends_photo.png "Bot sends file_id")

Amazing! Now we know photo's file_id so we can send them by file_id. Lets make our bot answer with that photo when we send command `/pic`.

```java
if (update.hasMessage() && update.getMessage().hasText()) {
    // Set variables
    String message_text = update.getMessage().getText();
    long chat_id = update.getMessage().getChatId();
    if (message_text.equals("/start")) {
        // User send /start
        SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText(message_text);
        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    } else if (message_text.equals("/pic")) {
        // User sent /pic
        SendPhoto msg = new SendPhoto()
                        .setChatId(chat_id)
                        .setPhoto("AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC")
                        .setCaption("Photo");
                try {
                    sendPhoto(msg); // Call method to send the photo
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
    } else {
        // Unknown command
        SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Unknown command");
        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```

Now bot sends photo like this:

![/pic command](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/Bot_sends_photo_command.png "Bot sends photo by command /pic")

And he can even reply to unknown command!

![Unknown command](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/bot_unknown_command.png "Bot answers to unknown command")

Now lets take a look at [ReplyKeyboardMarkup](https://core.telegram.org/bots/api#replykeyboardmarkup). We will now create custom keyboard like this:

![Custom keyboards preview](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/custom_keyboard_preview.png "Custom keyboards preview")

Well, you already now how to make our bot recognise command. Lets make another `if` for command `/markup`.

> Remember! When you press the button, it will send to bot the text on this button.
> For example, if I put "Hello" text on the button, when I press it, it will send "Hello" text for me

```java
else if (message_text.equals("/markup")) {
    SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Here is your keyboard");
    // Create ReplyKeyboardMarkup object
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    // Create the keyboard (list of keyboard rows)
    List<KeyboardRow> keyboard = new ArrayList<>();
    // Create a keyboard row
    KeyboardRow row = new KeyboardRow();
    // Set each button, you can also use KeyboardButton objects if you need something else than text
    row.add("Row 1 Button 1");
    row.add("Row 1 Button 2");
    row.add("Row 1 Button 3");
    // Add the first row to the keyboard
    keyboard.add(row);
    // Create another keyboard row
    row = new KeyboardRow();
    // Set each button for the second line
    row.add("Row 2 Button 1");
    row.add("Row 2 Button 2");
    row.add("Row 2 Button 3");
    // Add the second row to the keyboard
    keyboard.add(row);
    // Set the keyboard to the markup
    keyboardMarkup.setKeyboard(keyboard);
    // Add it to the message
    message.setReplyMarkup(keyboardMarkup);
    try {
        sendMessage(message); // Sending our message object to user
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}
```

Amazing! Now lets teach our bot to react on this buttons:

```java
else if (message_text.equals("Row 1 Button 1")) {
    SendPhoto msg = new SendPhoto()
                .setChatId(chat_id)
                .setPhoto("AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC")
                .setCaption("Photo");
    try {
        sendPhoto(msg); // Call method to send the photo
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}
```

Now, when user press button with "Row 1 Button 1" text on it, bot will send picture by `file_id` to user:

![Bot sends photo from keyboard](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/raw/master/media/Bot_custom_keyboard_photo.png "Bot sends photo from keyboard")

And lets add "Hide keyboard" function when user send `/hide` command to bot. This can be done with `ReplyMarkupRemove`.

```java
else if (message_text.equals("/hide")) {
    SendMessage msg = new SendMessage()
                        .setChatId(chat_id)
                        .setText("Keyboard hidden");
    ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
    msg.setReplyMarkup(keyboardMarkup);
    try {
        sendMessage(msg); // Call method to send the photo
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}
```

Here is code of our files. You can also find all sources at [GitHub repository](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/).


> src/Main.java

```java
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new PhotoBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("PhotoBot successfully started!");
    }
}
```

> src/PhotoBot.java

```java
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PhotoBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            if (message_text.equals("/start")) {
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText(message_text);
                try {
                    sendMessage(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/pic")) {
                SendPhoto msg = new SendPhoto()
                        .setChatId(chat_id)
                        .setPhoto("AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC")
                        .setCaption("Photo");
                try {
                    sendPhoto(msg); // Call method to send the photo
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/markup")) {
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Here is your keyboard");
                // Create ReplyKeyboardMarkup object
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                // Create the keyboard (list of keyboard rows)
                List<KeyboardRow> keyboard = new ArrayList<>();
                // Create a keyboard row
                KeyboardRow row = new KeyboardRow();
                // Set each button, you can also use KeyboardButton objects if you need something else than text
                row.add("Row 1 Button 1");
                row.add("Row 1 Button 2");
                row.add("Row 1 Button 3");
                // Add the first row to the keyboard
                keyboard.add(row);
                // Create another keyboard row
                row = new KeyboardRow();
                // Set each button for the second line
                row.add("Row 2 Button 1");
                row.add("Row 2 Button 2");
                row.add("Row 2 Button 3");
                // Add the second row to the keyboard
                keyboard.add(row);
                // Set the keyboard to the markup
                keyboardMarkup.setKeyboard(keyboard);
                // Add it to the message
                message.setReplyMarkup(keyboardMarkup);
                try {
                    sendMessage(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("Row 1 Button 1")) {
                SendPhoto msg = new SendPhoto()
                        .setChatId(chat_id)
                        .setPhoto("AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC")
                        .setCaption("Photo");

                try {
                    sendPhoto(msg); // Call method to send the photo
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/hide")) {
                SendMessage msg = new SendMessage()
                        .setChatId(chat_id)
                        .setText("Keyboard hidden");
                ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
                msg.setReplyMarkup(keyboardMarkup);
                try {
                    sendMessage(msg); // Call method to send the photo
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Unknown command");
                try {
                    sendMessage(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // Message contains photo
            // Set variables
            long chat_id = update.getMessage().getChatId();

            List<PhotoSize> photos = update.getMessage().getPhoto();
            String f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();
            int f_width = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();
            int f_height = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();
            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
            SendPhoto msg = new SendPhoto()
                    .setChatId(chat_id)
                    .setPhoto(f_id)
                    .setCaption(caption);
            try {
                sendPhoto(msg); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "PhotoBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "12345:qwertyuiopASDGFHKMK";
    }
}
```

Now you can create and remove custom `ReplyMarkup` keyboards, create custom commands and send photos by `file_id`! You are doing very well! Hope to see you soon:)
