package pl.edu.pwr.aerospace.app4hab.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class SensorStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int ID;
    private Float lat;
    private Float lon;
    private Integer alt;
    private Float outTemp;
    private Float inTemp;
    private Float pressure;
    private Float batteryVoltage;
    private Integer atmegaReadID;
    private Long lastSensorReading;
    private Float phoneTemp;

    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Integer getAlt() {
        return alt;
    }

    public void setAlt(Integer alt) {
        this.alt = alt;
    }

    public Float getOutTemp() {
        return outTemp;
    }

    public void setOutTemp(Float outTemp) {
        this.outTemp = outTemp;
    }

    public Float getInTemp() {
        return inTemp;
    }

    public void setInTemp(Float inTemp) {
        this.inTemp = inTemp;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(Float batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Integer getAtmegaReadID() {
        return atmegaReadID;
    }

    public void setAtmegaReadID(Integer atmegaReadID) {
        this.atmegaReadID = atmegaReadID;
    }

    public Long getLastSensorReading() {
        return lastSensorReading;
    }

    public void setLastSensorReading(Long lastSensorReading) {
        this.lastSensorReading = lastSensorReading;
    }

    public Float getPhoneTemp() {
        return phoneTemp;
    }

    public void setPhoneTemp(Float phoneTemp) {
        this.phoneTemp = phoneTemp;
    }
}
