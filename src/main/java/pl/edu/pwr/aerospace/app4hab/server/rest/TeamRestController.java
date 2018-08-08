package pl.edu.pwr.aerospace.app4hab.server.rest;

import javassist.tools.web.BadHttpRequest;
import org.apache.log4j.Logger;
import pl.edu.pwr.aerospace.app4hab.server.CommandsRequestValidator;
import pl.edu.pwr.aerospace.app4hab.server.daos.CommandsDao;
import pl.edu.pwr.aerospace.app4hab.server.daos.ImageDao;
import pl.edu.pwr.aerospace.app4hab.server.daos.PhoneActivityDao;
import pl.edu.pwr.aerospace.app4hab.server.daos.SensorStatusDao;
import pl.edu.pwr.aerospace.app4hab.server.entities.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("control")
public class TeamRestController {
    private static final Logger LOG = Logger.getLogger(TeamRestController.class);

    @POST
    @Path("/commands")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateCommands(String s) throws BadHttpRequest {
        LOG.info("Incoming request for updating commands with payload: " + s);

        Commands commands = CommandsRequestValidator.parse(s);

        if (commands == null)
            return Response.status(400).build();

        CommandsDao dao = new CommandsDao();
        dao.save(commands);

        return Response.status(204).build();
    }

    @GET
    @Path("/lastcommands")
    @Produces(MediaType.APPLICATION_JSON)
    public Commands getLastCommands() {
        LOG.info("Incoming request for last commands");

        CommandsDao dao = new CommandsDao();

        return dao.getLatestCommands();
    }

    @GET
    @Path("/lastactivity")
    @Produces(MediaType.APPLICATION_JSON)
    public PhoneActivity getLastActivity() {
        PhoneActivityDao dao = new PhoneActivityDao();
        return dao.getLastActivity();
    }

    @GET
    @Path("/allactivities")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PhoneActivity> getAllActivities() {
        LOG.info("Incoming request for all activities");

        PhoneActivityDao dao = new PhoneActivityDao();
        return dao.getAllActivities();
    }

    @GET
    @Path("/activity/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PhoneActivity getActivity(@PathParam("id") int id) {
        LOG.info("Incoming request for activity with id " + id);

        PhoneActivityDao dao = new PhoneActivityDao();
        return dao.getActivity(id);
    }

    @GET
    @Path("/statuses")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorStatus> getAllStatuses() {
        LOG.info("Incoming request for all statuses");

        SensorStatusDao dao = new SensorStatusDao();
        return dao.getAllStatuses();
    }

    @GET
    @Path("/status/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorStatus getStatus(@PathParam("id") int id) {
        LOG.info("Incoming request for status with id " + id);

        SensorStatusDao dao = new SensorStatusDao();
        return dao.getStatus(id);
    }

    @GET
    @Path("/photos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Image> getAllPhotos() {
        LOG.info("Incoming request for all photos");

        ImageDao dao = new ImageDao();
        return dao.getAllImages();
    }

    @GET
    @Path("/photo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Image getPhoto(@PathParam("id") int id) {
        LOG.info("Incoming request for Photo with id " + id);

        ImageDao dao = new ImageDao();
        return dao.getImage(id);
    }

    @GET
    @Path("/logs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PhoneActivity> getLogs() {
        LOG.info("Incoming request for logs");

        PhoneActivityDao dao = new PhoneActivityDao();
        return dao.getLogs();
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String def() {
        LOG.info("Incoming default request");
        return "Default";
    }
}
