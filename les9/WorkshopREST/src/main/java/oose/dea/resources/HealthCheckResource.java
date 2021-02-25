package oose.dea.resources;


import javax.ws.rs.Path;
import javax.ws.rs.GET;

@Path("health")
public class HealthCheckResource {

    @GET
    public String healthy() {
        return "Up & Running";
    }
}
