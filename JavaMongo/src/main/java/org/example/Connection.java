package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.InsertOneResult;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Connection {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("D:/NoSQL/MongoDBU/JavaMongo/.env")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        String connectionString = dotenv.get("MONGODB_URI");
        System.out.println(connectionString);
//        String connectionString = System.getProperty("mongodb.uri");
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));

//            MongoDatabase database = mongoClient.getDatabase("sample_training");
//            MongoCollection<Document> collection = database.getCollection("inspection");
//            Document inspection = new Document("_id", new ObjectId())
//                    .append("id","10021-2015-ENFO")
//                    .append("certificate_number",9278806)
//                    .append("business_name","ATLIXCO DELI GROCERY INC.")
//                    .append("date", Date.from(LocalDate.of(2015,2,20).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                    .append("result","No Violation Issued")
//                    .append("sector","Cigarette Retail Dealer - 127")
//                    .append("address",new Document().append("city", "RIDGEWOOD").append("zip",11385).append("street", "MENAHAN ST").append("number", 1712));
//            InsertOneResult result = collection.insertOne(inspection);
//            BsonValue id = result.getInsertedId();
//            System.out.println(id);

//            MongoDatabase database = mongoClient.getDatabase("bank");
//            MongoCollection<Document> collection = database.getCollection("accounts");
//            Document doc1 = new Document().append("account_holder","john doe").append("account_id","MDB99115881").append("balance",1785).append("account_type","checking");
//            Document doc2 = new Document().append("account_holder","jane doe").append("account_id","MDB79101843").append("balance",1468).append("account_type","checking");
//            List<Document> accounts = Arrays.asList(doc1, doc2);
//            InsertManyResult result = collection.insertMany(accounts);
//            result.getInsertedIds().forEach((x,y)-> System.out.println(y.asObjectId()));

        }
    }
