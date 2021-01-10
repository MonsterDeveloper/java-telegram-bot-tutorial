---
search:
  keywords:
    - lesson 7
    - '7'
    - mongodb
    - database
    - users database
    - statistics
---

# Lesson 7. Creating users database with MongoDB

Hey! As you're reading this, you know that I returned from Italy. It was very nice, but okey - you want to create users database for your bot. Disputes about what DB is better can live very long time, but I will choose MongoDB. It is [high-performance, schema-free document-oriented database](https://mongodb.com). Let's create actual 'body' of our bot. Well, as always. I will skip this step as I know you have your own bot and there is no need to pollute the great lesson unnecessary amount of code. If not, you can find all sources at [GitHub repo](https://github.com/MonsterDeveloper/java-telegram-bot-tutorial/). Now, import MongoDB's driver for Java. You can download it [here](http://mongodb.github.io/mongo-java-driver/) or import it from Maven. With IntelliJ Idea it is easier than you expect. Just go to **File &lt; Project Structure... &lt; Libraries &lt; + &lt; From Maven** and search for `org.mongodb:mongo-java-driver`. That's all. Import it in your bot file:

```java
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;
```

You will also need `org.slf4j:slf4j-nop` library to disable additional logging, like this:

```text
11:01:15.406 [pool-1-thread-1] DEBUG org.mongodb.driver.protocol.query - Query completed

11:01:25.174 [cluster-ClusterId{value='554dbecb1b554f11e86c3a69', description='null'}-localhost:27017] DEBUG org.mongodb.driver.cluster - Checking status of localhost:27017
```

Install it from Maven as you did with MongoDB Java Driver. Let's write our "check" function, that will check if user exists in database. If not, write it.

```java
private String check(String first_name, String last_name, int user_id, String username) {
        MongoClientURI connectionString = new MongoClientURI("mongodb://host:port");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("db_name");
        MongoCollection<Document> collection = database.getCollection("users");
        long found = collection.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
        if (found == 0) {
            Document doc = new Document("first_name", first_name)
                    .append("last_name", last_name)
                    .append("id", user_id)
                    .append("username", username);
            collection.insertOne(doc);
            mongoClient.close();
            System.out.println("User not exists in database. Written.");
            return "no_exists";
        } else {
            System.out.println("User exists in database.");
            mongoClient.close();
            return "exists";
        }


    }
```

Don't do [kernel panic](https://en.wikipedia.org/wiki/Kernel_panic), I will explain everything now.

Here we set new connection to MongoDB's server:

```java
MongoClientURI connectionString = new MongoClientURI("mongodb://host:port");
```

Replace `host:port` with your Mongo's host and port. You can find how to setup MongoDB server for Ubuntu [here](https://www.digitalocean.com/community/tutorials/how-to-install-mongodb-on-ubuntu-16-04). Then we set our database and collection. Replace this names with your own.

```java
MongoClient mongoClient = new MongoClient(connectionString);
MongoDatabase database = mongoClient.getDatabase("db_name");
MongoCollection<Document> collection = database.getCollection("users");
```

We search users like this:

```java
long found = collection.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
```

And just check if user exists or not.

```java
if (found == 0) {
            Document doc = new Document("first_name", first_name)
                    .append("last_name", last_name)
                    .append("id", user_id)
                    .append("username", username);
            collection.insertOne(doc);
            mongoClient.close();
            System.out.println("User not exists in database. Written.");
            return "no_exists";
        } else {
            System.out.println("User exists in database.");
            mongoClient.close();
            return "exists";
        }
```

So now our full function looks like this:

```java
private String check(String first_name, String last_name, int user_id, String username) {
        // Set loggers
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        MongoClientURI connectionString = new MongoClientURI("mongodb://host:port");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("db_name");
        MongoCollection<Document> collection = database.getCollection("users");
        long found = collection.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
        if (found == 0) {
            Document doc = new Document("first_name", first_name)
                    .append("last_name", last_name)
                    .append("id", user_id)
                    .append("username", username);
            collection.insertOne(doc);
            mongoClient.close();
            System.out.println("User not exists in database. Written.");
            return "no_exists";
        } else {
            System.out.println("User exists in database.");
            mongoClient.close();
            return "exists";
        }


    }
```

Just check if user exists in database by calling this function when user sends `/start`:

```java
import static java.lang.Math.toIntExact;

// Set variables
String user_first_name = update.getMessage().getChat().getFirstName();
String user_last_name = update.getMessage().getChat().getLastName();
String user_username = update.getMessage().getChat().getUserName();
long user_id = update.getMessage().getChat().getId();
String message_text = update.getMessage().getText();
long chat_id = update.getMessage().getChatId();

try {
    sendMessage(message);
    check(user_first_name, user_last_name, toIntExact(user_id), user_username);
} catch (TelegramApiException e) {
    e.printStackTrace();
}
```

Well, that's all for now. Hope to see you soon!

> Thanks for reading this.
>
> _MonsterDeveloper_

