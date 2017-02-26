import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
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
