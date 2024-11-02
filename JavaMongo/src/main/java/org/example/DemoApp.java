package org.example;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.example.Aggregation;
import org.example.Crud;
import org.example.Transaction;
//import com.mdbu.utils.MongoClientSingleton;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static java.util.Arrays.asList;


public class DemoApp {
    public static void main(final String[] args) {
        Logger root = (Logger) LoggerFactory.getLogger("org.mongodb.driver");
        // Available levels are: OFF, ERROR, WARN, INFO, DEBUG, TRACE, ALL
        root.setLevel(Level.WARN);

        String connectionString = System.getenv("MONGODB_URI");
        try (MongoClient client = MongoClients.create(connectionString)) {
            Document sampleDocument = new Document("_id", new ObjectId())
                    .append("account_id", "MDB255054629")
                    .append("account_holder", "Mai Kalange")
                    .append("account_type", "savings")
                    .append("balance", 2340)
                    .append("last_updated", new Date());
            //CRUD
            Crud crud = new Crud(client);
            //INSERT ONE
            crud.insertOneDocument(sampleDocument);
        }

        try (MongoClient client = MongoClients.create(connectionString)) {
            Document account1 = new Document().append("account_holder", "John Doe").append("account_id", "MDB99115881").append("balance", 1785).append("account_type", "checking").append("last_updated", new Date());
            Document account2 = new Document().append("account_holder", "Jane Doe").append("account_id", "MDB79101843").append("balance", 1500).append("account_type", "checking").append("last_updated", new Date());
            Document account3 = new Document().append("account_holder", "Mary Doe").append("account_id", "MDB63191563").append("balance", 1500).append("account_type", "checking").append("last_updated", new Date());

            List<Document> sampleDocuments = new ArrayList<>();
            sampleDocuments.add(account1);
            sampleDocuments.add(account2);
            sampleDocuments.add(account3);
            //CRUD
            Crud crud = new Crud(client);
            //INSERT MANY
            crud.insertManyDocuments(sampleDocuments);
        }

        try (MongoClient client = MongoClients.create(connectionString)) {
            //CRUD
            Crud crud = new Crud(client);
            //FIND ONE
            Bson documentToFind = and(gte("balance", 1000), eq("account_type", "checking"));
            crud.findOneDocument(documentToFind);
        }

        try (MongoClient client = MongoClients.create(connectionString)) {
            Document sampleDocument = new Document("_id", new ObjectId())
                    .append("account_id", "MDB255054629")
                    .append("account_holder", "Mai Kalange")
                    .append("account_type", "savings")
                    .append("balance", 2340)
                    .append("last_updated", new Date());
            //CRUD
            Crud crud = new Crud(client);
            //FIND
            Bson documentsToFind = and(gte("balance", 1000), eq("account_type", "checking"));
            crud.findDocuments(documentsToFind);
        }

        try (MongoClient client = MongoClients.create(connectionString)) {
            //CRUD
            Crud crud = new Crud(client);
            //UPDATE ONE
            Bson query = Filters.eq("account_id","MDB333829449");; //TODO: define the query variable
            Bson update = Updates.combine(Updates.set("account_status","active"),Updates.inc("balance",100));; //TODO: define the update variable
            crud.updateOneDocument(query, update);
        }

        try (MongoClient client = MongoClients.create(connectionString)) {
            //CRUD
            Crud crud = new Crud(client);
            //UPDATE MANY
            Document updateManyFilter = new Document().append("account_type", "savings");
            Bson updatesForMany = Updates.combine(Updates.set("minimum_balance", 100));
            crud.updateManyDocuments(updateManyFilter, updatesForMany);
        }

        try (MongoClient client = MongoClients.create(connectionString)) {
            //CRUD
            Crud crud = new Crud(client);
            //DELETE ONE
            Bson documentToDelete = eq("account_holder", "Jacob Thompson");
            crud.deleteOneDocument(documentToDelete);
        }

        try (MongoClient client = MongoClients.create(connectionString)) {
            //CRUD
            Crud crud = new Crud(client);
            //DELETE MANY
            Bson documentsToDelete = lt("balance", 500);
            crud.deleteManyDocuments(documentsToDelete);
        }

        try (MongoClient client = MongoClients.create(connectionString)) {
            //Transaction
            Transaction txn = new Transaction(client);
            var senderAccountFilter = "MDB310054629";
            var receiverAccountFilter = "MDB643731035";
            double transferAmount = 200;
            txn.transferMoney(senderAccountFilter, transferAmount, receiverAccountFilter);
        }

    }
}