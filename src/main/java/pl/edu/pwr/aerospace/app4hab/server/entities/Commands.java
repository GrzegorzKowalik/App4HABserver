package pl.edu.pwr.aerospace.app4hab.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Commands {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int ID;
    private boolean logging;
    private boolean camera;
    private boolean radio;
    private boolean device;
    private boolean sendPicture;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public boolean isCamera() {
        return camera;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    public boolean isRadio() {
        return radio;
    }

    public void setRadio(boolean radio) {
        this.radio = radio;
    }

    public boolean isDevice() {
        return device;
    }

    public void setDevice(boolean device) {
        this.device = device;
    }

    public boolean isSendPicture() {
        return sendPicture;
    }

    public void setSendPicture(boolean sendPicture) {
        this.sendPicture = sendPicture;
    }
}
