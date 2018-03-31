package pl.edu.pwr.aerospace.app4hab.server;

import javassist.tools.web.BadHttpRequest;
import pl.edu.pwr.aerospace.app4hab.server.entities.Commands;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class CommandsRequestValidator {

    public static Commands parse(String message) throws BadHttpRequest {
        try {
            String[] tokens = message.split("&");
            Map<String, String> map = new HashMap<>();

            for (String token : tokens) {
                String[] pair = token.split("=");
                map.put(pair[0], pair[1]);
            }

            byte[] bytesOfMessage = map.get("secret").getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            String hashedSecret = bytesToHex(md.digest(bytesOfMessage));

            System.out.println("---===Hashed secret: " + hashedSecret);
            if (!hashedSecret.toLowerCase().equals("ee0aab83ce22b7756b251946a442aeb5")){
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


    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
