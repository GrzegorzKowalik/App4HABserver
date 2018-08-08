package pl.edu.pwr.aerospace.app4hab.server;

import javassist.tools.web.BadHttpRequest;
import org.apache.log4j.Logger;
import pl.edu.pwr.aerospace.app4hab.server.entities.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandsRequestValidator {
    private static Logger log = Logger.getLogger(CommandsRequestValidator.class);

    /**
     * Parses commands from default HTML form to Commands model
     * @param message string from HTML form
     * @return Commands or null in case of parsing error or wrong secret
     */
    public static Commands parse(String message){
        try {
            String[] tokens = message.split("&");
            Map<String, String> map = new HashMap<>();

            for (String token : tokens) {
                String[] pair = token.split("=");
                map.put(pair[0], pair[1]);
            }

            if (!map.get("secret").equals(Config.controlSecret)){
                log.debug("Secret does not match");
                return null;
            }

            String camera = map.getOrDefault("camera", "");
            String logging = map.getOrDefault("logging", "");
            String radio = map.getOrDefault("radio", "");
            String photo = map.getOrDefault("picture", "");
            String device = map.getOrDefault("device", "");

            Commands c = new Commands();

            if (camera.equals("on"))
                c.setCamera(true);
            if (logging.equals("on"))
                c.setLogging(true);
            if (radio.equals("on"))
                c.setRadio(true);
            if (photo.equals("on"))
                c.setSendPicture(true);
            if (device.equals("on"))
                c.setDevice(true);

            return c;
        }
        catch (Exception e){
            return null;
        }
    }
}
