package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;
import org.bson.BsonValue;
import org.bson.conversions.Bson;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.List;

public class Crud {
    private final MongoCollection<Document> collection;

    public Crud(MongoClient client) {
        this.collection = client.getDatabase("bank").getCollection("accounts");
    }

    public void insertOneDocument(Document doc) {
        //TODO implement insertOne code here
        System.out.println("Inserting one account document");
        InsertOneResult result = collection.insertOne(doc);
        BsonValue id = result.getInsertedId();
        System.out.println("Inserted document Id: " + id);
    }

    public void insertManyDocuments(List<Document> documents) {
        //TODO Add code to insert many documents
        InsertManyResult result = collection.insertMany(documents);
        System.out.println("\tTotal # of documents: " + result.getInsertedIds().size());
    }

    public void findOneDocument(Bson query) {
        //TODO: implement the findOne query
        Document documentToFind = collection.find(query).first();
        System.out.println(documentToFind != null ? documentToFind.toJson() : null);
    }

    public void findDocuments(Bson query) {
        try (MongoCursor<Document> documentsToFind = collection.find(query).iterator()) {
            while (documentsToFind.hasNext()) {
                System.out.println(documentsToFind.next().toJson());
            }
        }
    }

    public void updateOneDocument(Bson query, Bson update) {
        //TODO: Implement the updateOne operation
        UpdateResult documentToUpdate = collection.updateOne(query, update);
        System.out.println("Updated a document:");
        System.out.println("\t" + documentToUpdate.getModifiedCount());

    }

    public void updateManyDocuments(Bson query, Bson update) {
        //TODO: Implement the updateMany operation
        UpdateResult updatesForMany = collection.updateMany(query, update);
        System.out.println("Updated this many documents:");
        System.out.println("\t" + updatesForMany.getModifiedCount());
    }

    public void deleteOneDocument(Bson query) {
        //TODO: implement the deleteOne operation
        DeleteResult documentToDelete = collection.deleteOne(query);
        System.out.println("Deleted a document:");
        System.out.println("\t" + documentToDelete.getDeletedCount());
    }

    public void deleteManyDocuments(Bson query) {
        //TODO: implement the deleteMany operation
        DeleteResult documentsToDelete = collection.deleteMany(query);
        System.out.println("Deleted this many documents:");
        System.out.println(documentsToDelete.getDeletedCount());

    }


}
