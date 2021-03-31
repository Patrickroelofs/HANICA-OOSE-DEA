package com.spotitube.datasource.dao.mongo;

import com.mongodb.client.*;
import com.spotitube.datasource.IUserDAO;
import org.bson.Document;

import javax.enterprise.inject.Alternative;
import javax.ws.rs.InternalServerErrorException;

import static com.mongodb.client.model.Filters.eq;

@Alternative
public class UserDAOMongoDB implements IUserDAO {
    public static final String DATABASE = "spotitube";
    public static final String USERS_COLLECTION = "users";

    MongoClient client = MongoClients.create();
    MongoDatabase db = client.getDatabase(DATABASE);
    MongoCollection<Document> users = db.getCollection(USERS_COLLECTION);

    @Override
    public boolean verifyUser(String username, String password) throws InternalServerErrorException {
        try {
            MongoCursor<Document> cursor = users.find(eq("user", username)).iterator();

            return cursor.hasNext();



        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }
}
