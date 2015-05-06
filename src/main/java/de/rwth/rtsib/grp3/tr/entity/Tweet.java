package de.rwth.rtsib.grp3.tr.entity;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import org.mongodb.morphia.annotations.Id;

import twitter4j.Status;

public class Tweet {

	@Id
	long id;
    private String user;
    private String text;
    private Timestamp timestamp;
    private Double locationLatitude = null;
    private Double locationLongitude = null;
    private Boolean hasLocation = false;
    
	public Tweet() {}
	
    public Tweet(Status status) {
        this.id = status.getId();
        this.user = status.getUser().getName();
        this.text = status.getText();
        this.timestamp = new Timestamp(status.getCreatedAt().getTime());
        if (status.getGeoLocation() != null) {
            this.hasLocation = true;
            this.locationLatitude = status.getGeoLocation().getLatitude();
            this.locationLongitude = status.getGeoLocation().getLongitude();
        }
    }

    public Tweet(long id, String user, String text, Timestamp timestamp, Double locationLatitude, Double locationLongitude) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.timestamp = timestamp;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.hasLocation = locationLatitude != null || locationLongitude != null;
    }

    public long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public void print() {
        System.out.println(timestamp.toLocalDateTime().toString() + ", " + user + " says: " + text.trim());
    }

    public Boolean hasLocation() {
        return hasLocation;
    }
}
