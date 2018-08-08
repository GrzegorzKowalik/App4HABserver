package pl.edu.pwr.aerospace.app4hab.server.rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import pl.edu.pwr.aerospace.app4hab.server.daos.CommandsDao;
import pl.edu.pwr.aerospace.app4hab.server.daos.ImageDao;
import pl.edu.pwr.aerospace.app4hab.server.daos.PhoneActivityDao;
import pl.edu.pwr.aerospace.app4hab.server.daos.SensorStatusDao;
import pl.edu.pwr.aerospace.app4hab.server.entities.Commands;
import pl.edu.pwr.aerospace.app4hab.server.entities.Image;
import pl.edu.pwr.aerospace.app4hab.server.entities.PhoneActivity;
import pl.edu.pwr.aerospace.app4hab.server.entities.SensorStatus;

import java.io.IOException;

@Path("api/")
public class PhoneRestController {
    private static final Logger LOG = Logger.getLogger(PhoneRestController.class);

    public PhoneRestController() {
        LOG.info("Initializing PhoneRestController");
    }

    @GET
    @Path("/commands")
    @Produces(MediaType.APPLICATION_JSON)
    public Commands getLatestCommands() {
        LOG.info("Incoming request for latest commands");
        logActivity("commands", "");

        CommandsDao dao = new CommandsDao();

        return dao.getLatestCommands();
    }

    @POST
    @Path("/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response uploadStatus(String statusJson) throws IOException {
        LOG.info("Incoming request with status");
        logActivity("status", statusJson);

        ObjectMapper om = new ObjectMapper();
        SensorStatus status = om.readValue(statusJson, SensorStatus.class);

        SensorStatusDao dao = new SensorStatusDao();
        dao.save(status);

        return Response.status(204).build();
    }

    @POST
    @Path("/photo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response uploadPhoto(String imageJson) throws IOException {
        LOG.info("Incoming request with photo");

        ObjectMapper om = new ObjectMapper();
        Image image = om.readValue(imageJson, Image.class);
        image.processImage();

        logActivity("photo", image.getImg());
        ImageDao dao = new ImageDao();
        dao.save(image);

        return Response.status(204).build();
    }

    @POST
    @Path("/log")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response saveLog(String log) throws IOException {
        LOG.info("Incoming request with photo");
        logActivity("log", log);

        return Response.status(204).build();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_HTML)
    public String test() {
        LOG.info("Incoming test request");
        return "Server runs";
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String def() {
        LOG.info("Incoming default request");
        return "Default";
    }


    private void logActivity(String endpoint, String payload){
        PhoneActivityDao dao = new PhoneActivityDao();

        dao.save(new PhoneActivity(endpoint, payload));
    }
}
