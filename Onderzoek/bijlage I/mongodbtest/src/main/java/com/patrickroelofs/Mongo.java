package com.patrickroelofs;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.mongodb.client.model.Filters.eq;

@Path("/")
public class Mongo {
    MongoClient mongoClient = MongoClients.create();
    MongoDatabase mongoDatabase = mongoClient.getDatabase("mongojavatest");
    MongoCollection<Document> collection = mongoDatabase.getCollection("test");

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocument() {
        Document document = collection.find().first();

        return Response.status(Response.Status.OK).entity(document).build();
    }

    @POST
    @Path("/")
    public Response createDocument() {
        Document document = new Document("test", "testdocument");
        collection.insertOne(document);

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/")
    public Response deleteDocument() {
        collection.deleteOne(eq("test", "testdocument"));

        return Response.status(Response.Status.OK).build();
    }
}
