package pl.edu.pwr.aerospace.app4hab.server.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PhoneActivity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int ID;
    private Date timestamp;
    private String endpoint;
    @Column(columnDefinition="LONGVARCHAR")
    private String payload;

    public PhoneActivity() {
    }

    public PhoneActivity(String endpoint, String payload) {
        timestamp = new Date();
        this.endpoint = endpoint;
        this.payload = payload;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
