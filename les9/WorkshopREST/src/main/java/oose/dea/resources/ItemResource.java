package oose.dea.resources;

import oose.dea.services.ItemService;
import oose.dea.services.dto.ItemDTO;

import javax.ejb.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("items")
@Singleton
public class ItemResource {
    private ItemService itemService;

    public ItemResource() {
        this.itemService = new ItemService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJsonItems() {
        return Response.status(200).entity(itemService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemOnId(@PathParam("id") int id){
        return Response.status(200).entity(itemService.getItem(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(ItemDTO itemDTO){
        itemService.addItem(itemDTO);
        return Response.status(201).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteItem(@PathParam("id") int id){
        itemService.deleteItem(id);
        return Response.status(202).build();
    }
}
