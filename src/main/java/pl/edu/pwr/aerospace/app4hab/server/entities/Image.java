package pl.edu.pwr.aerospace.app4hab.server.entities;

import pl.edu.pwr.aerospace.app4hab.server.Config;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int ID;
    private String img;
    private Date timestamp;
    // count of already taken photos
    private int count;
    // coordinates where the photo was taken
    private Float longitude;
    private Float latitude;
    private Float altitude;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    /**
     * Saves image to disk
     * File database engine can has some troubles with storing long (~2MB) strings, instead storing base64 encoded image
     * to database, the image is stored locally on paths defined in environment variables. Instead of whole base64 string
     * the databases stores only the name of saved file.
     */
    public void processImage(){
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(img);
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            String uploadsFolder = Config.uploadsFolder;
            String tomcatFolder = Config.tomcatFolder;

            File outputFile = new File( uploadsFolder + timestamp.getTime() + ".jpg");
            ImageIO.write(bufferedImage, "jpg", outputFile);
            outputFile = new File( tomcatFolder + timestamp.getTime() + ".jpg");
            ImageIO.write(bufferedImage, "jpg", outputFile);
            img = timestamp.getTime() + ".jpg";
        } catch (IOException e) {
            e.printStackTrace();
            img = "Failed: " + e.getMessage();
        }

    }
}
